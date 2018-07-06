/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Résolution des URIs en passant par le système de ressources de Spring
 * 
 * @author sylvain.cailleau
 */
public class SpringResourceURIResolver implements URIResolver {

    @Autowired
    ResourceLoader resourceLoader;
    
    @Override
    public Source resolve(String href, String base) {
        try {
            
            SpringResourceUri baseUri = new SpringResourceUri(base);
            String sourceUri = baseUri.resolve(href);
            
           // on récupère la ressource Spring correspondante
            Resource resource = resourceLoader.getResource(sourceUri);
                    
            // et on essaye de construire la source à partir de cette ressource
            return new StreamSource(
                    resource.getInputStream(), // IOException si la resource n'existe pas ou n'est pas accessible
                    sourceUri
            );
        } catch (Exception ex) {
            // on sait pas interpreter l'URI
            // ou il ne correspond pas à une ressource accessible
            // on renvoi null pour laisser l'appelant essayer de resoudre lui-meme
            return null;
        }
    }
}
