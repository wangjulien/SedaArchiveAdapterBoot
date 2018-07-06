/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.telino.avp.modele.DocumentContent;

/**
 * Permet de recuperer le contenu d'un document inclu dans une archive
 * en fonction de l'URI et/ou du nom associé
 * 
 * @author sylvain.cailleau
 */
public interface DocumentContentProvider {
    DocumentContent getContent(String uri, String fileName);
}
