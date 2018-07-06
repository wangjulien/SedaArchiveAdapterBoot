/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Resolution d'URI compatible avec les URIs utilisés par les resources Spring
 * 
 * @author sylvain.cailleau
 */
public class SpringResourceUri {
    
    private final URI base;
    
    public SpringResourceUri(String uri) throws URISyntaxException {
        base = new URI(uri);
    }

    public SpringResourceUri(URI base) {
        this.base = base;
    }
        
    public String resolve(String href) throws URISyntaxException {
        if ( base.isOpaque() ) {
            // URI de la forme scheme:path1/path2/...
            // la fonction resolve de la classe URI ne fonctionne pas comme on veut
            
            // on crée un URI de la forme scheme:/path1/path2/...
            URI pseudoBase = new URI(base.getScheme(), null, "/" + base.getSchemeSpecificPart(), null);
            // on resoud par rapport a ce nouvel URI
            // comme on a un path absolu, la resolution marche comme on veut
            URI pseudoResolved = pseudoBase.resolve(href);
            
            // on extrai le path obtenu
            String resolvedPath = pseudoResolved.getPath()
                    .substring(1); // dont on soustrait le "/" ajoute precedemment
            
            // On reconstruit l'URI résolu
            String resolved = base.getScheme() + ":" + resolvedPath;
            
            return resolved;
        }
        else {
            // on utilise directement la resolution d'URI fournie par java
            return base.resolve(href).toString();
        }
    }
}
