/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

/**
 * Permet d'appliquer un traitement lors de la traduction
 * d'un objet MessageSeda en fichier XML au format SEDA
 * En emission le traitement est appliqué avant la création du fichier XML
 * En réception le traitement est appliqué après la création de l'objet à partir du fichier XML
 * 
 * @author sylvain.cailleau
 */
public interface MessageProcesseur {

    /**
     * Modifier un message avant emission ou après réception.
     * @param message Le message à traiter
     */
    void process(MessageSeda message);
}
