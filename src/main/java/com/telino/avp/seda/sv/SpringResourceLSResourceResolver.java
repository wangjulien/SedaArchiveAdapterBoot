/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Resolution des resources XML en passant par les ressources Spring
 * 
 * @author sylvain.cailleau
 */
@Component
class SpringResourceLSResourceResolver implements LSResourceResolver {
    
    private static final String XML_SCHEMA_URI = "http://www.w3.org/2001/XMLSchema";

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        if (XML_SCHEMA_URI.equals(type)) {
            try {
                SpringResourceUri base = new SpringResourceUri(baseURI);
                String resourceUri = base.resolve(systemId);
                Resource resource = resourceLoader.getResource(resourceUri);
                return new DOMInputImpl(null, systemId, baseURI, resource.getInputStream(), null);
            } catch (URISyntaxException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
}
