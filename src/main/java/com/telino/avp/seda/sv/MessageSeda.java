/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.telino.avp.service.ArchiveDTO;
import com.telino.avp.service.DocumentDTO;

/**
 *
 * @author sylvain.cailleau
 */
@JacksonXmlRootElement(localName = "messageSeda")
public class MessageSeda {
    private String nom;
    private Date date;
    private OrganisationSeda emetteur;
    private OrganisationSeda destinataire;
    private Map<String, String> identifiants = new HashMap<>();
    private Set<ArchiveDTO> archives = new HashSet<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrganisationSeda getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(OrganisationSeda emetteur) {
        this.emetteur = emetteur;
    }

    public OrganisationSeda getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(OrganisationSeda destinataire) {
        this.destinataire = destinataire;
    }

    public Map<String, String> getIdentifiants() {
        return identifiants;
    }

    public void setIdentifiants(Map<String, String> identifiants) {
        this.identifiants = identifiants;
    }

    public Set<ArchiveDTO> getArchives() {
        return archives;
    }

    public void setArchives(Set<ArchiveDTO> archives) {
        this.archives = archives;
    }
    
    public Stream<DocumentDTO> allDocumentsStream() {
        if ( archives == null ) {
            return Stream.empty();
        }
        return archives.stream().flatMap(a -> a.allDocumentsStream());
    }
}
