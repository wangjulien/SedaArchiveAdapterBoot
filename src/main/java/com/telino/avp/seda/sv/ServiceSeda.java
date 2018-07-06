/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.telino.avp.service.ArchiveValidationException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Gestion des messages SEDA
 * 
 * @author sylvain.cailleau
 */
public interface ServiceSeda {
    
    /**
     * Import d'un message SEDA dans l'application
     * 
     * @param profil Le profil d'import à utiliser null = profil par defaut
     * @param in Le contenue du message SEDA
     * @param contentProvider Permet d'accéder au contenu des documents importés, au cas ou ce contenu n'est pas inséré directement dans le message SEDA
     */
    void importMessage(String profil, InputStream in, DocumentContentProvider contentProvider) throws ArchiveValidationException;
    
    /**
     * Export d'un message SEDA
     * @param message Le message Seda à exporter
     * @param profil Profil d'export utilisé
     * @param archiveIds Liste des archives à inclure dans le message
     * @param out Ou ecrire le message SEDA
     * @return DocumentContentProvider qui permet d'acceder au contenu des documents inclus dans l'archive. Si null, ce contenu est inclu dans le fichier SEDA
     */
    DocumentContentProvider exportMessage(MessageSeda message, String profil, Collection<Long> archiveIds, OutputStream out) throws ArchiveValidationException;
}
