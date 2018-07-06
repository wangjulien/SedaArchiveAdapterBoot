/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.telino.avp.modele.Archive;
import com.telino.avp.service.ArchiveDTO;
import com.telino.avp.service.ArchiveService;
import com.telino.avp.service.ArchiveValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXParseException;


@Service
public class ServiceSedaImpl implements ServiceSeda {

    @Autowired
    TraducteurSedaFactory traducteurFactory;
    
    @Autowired
    ArchiveService archiveService;
    
    @Override
    @Transactional
    public void importMessage(String profil, InputStream in, DocumentContentProvider contentProvider) throws ArchiveValidationException {
        
        try {
            MessageSeda message = traducteurFactory.create(profil).reception(in, contentProvider);
            
            switch (message.getNom() ) {
                case "ArchiveTransfer" :
                    transfert(profil, message);
                    break;
                default :
                    throw new ArchiveValidationException(String.format("Le message SEDA %s ne peut pas être importé",message.getNom()));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SAXParseException ex) {
            throw new ArchiveValidationException("Le fichier à importer contient des erreurs",ex);
        }
    }

    @Override
    @Transactional
    public DocumentContentProvider exportMessage(MessageSeda message, String profil, Collection<Long> archiveIds, OutputStream out) throws ArchiveValidationException {
        
        SimpleDocumentContentProvider docContent = new SimpleDocumentContentProvider();
        
        Set<ArchiveDTO> archives = new HashSet<>(archiveIds.size());
        message.setArchives(archives);
        
        for (Long id : archiveIds ) {
            ArchiveDTO archive = archiveService.restitution(id);
            archives.add(archive);
        }
        
        message.allDocumentsStream()
                .forEach( doc -> {
                    docContent.addContent(doc.getNom(), doc.getUri(), doc.getContent());
        });
        
        try {
            traducteurFactory.create(profil).emission(message, out, false);
        } catch (IOException | SAXParseException ex) {
            throw new RuntimeException(ex);
        }
        
        return docContent;
    }
    
    private void transfert(String profil, MessageSeda message) throws ArchiveValidationException {
        for (ArchiveDTO archive : message.getArchives() ) {
            Archive archiveVersee = archiveService.demandeVersement(archive);
            archive.setArchiveId(archiveVersee.getArchiveId());
            archiveService.archivage(archive);
        }
    }
}
