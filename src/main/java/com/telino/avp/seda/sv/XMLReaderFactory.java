/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 *
 * @author sylvain.cailleau
 */
public class XMLReaderFactory {

    private static final String XML_SCHEMA_URI = "http://www.w3.org/2001/XMLSchema";

    private Map<String,SAXParser> parsers = new HashMap<>();
    
    @Autowired
    private LSResourceResolver resourceResolver;
    
    @Autowired
    ResourceLoader resourceLoader;
    
    /**
     * Liste des URIs des fichiers schemasMap à charger pour la validation
     */
    private Map<String,Collection<String>> schemasMap;

    public void setSchemasMap(Map<String,Collection<String>> schemasMap) {
        this.schemasMap = schemasMap;
    }

    private synchronized SAXParser getParser(String profil) {
        if ( parsers.containsKey(profil) ) {
            return parsers.get(profil);
        }
        
        Collection<String> schemas = schemasMap.get(profil);
        
        if ( schemas == null || schemas.isEmpty() ) {
            return null;
        }
        
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating(true);
            parserFactory.setNamespaceAware(true);
            
            if ( schemas != null ) {
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XML_SCHEMA_URI);
                
                schemaFactory.setResourceResolver(resourceResolver);
                Source[] schemaSources = schemas.stream()
                        .map(s -> {
                            
                            try {
                                Resource resource = resourceLoader.getResource(s);
                                return new StreamSource(resource.getInputStream(), s);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        })
                        
                        .collect(Collectors.toList())
                        .toArray(new Source[0]);
                
                parserFactory.setSchema(schemaFactory.newSchema(schemaSources));
            }
            
            SAXParser newParser = parserFactory.newSAXParser();
            parsers.put(profil, newParser);
            
            return newParser;
        } catch (ParserConfigurationException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    XMLReader create(String profil) {
        XMLReader reader = null;
        
        SAXParser parser = getParser(profil);
        if ( parser != null ) {
            try {
                reader = getParser(profil).getXMLReader();
            } catch (SAXException ex) {
                throw new RuntimeException(ex);
            }
            reader.setErrorHandler(new SimpleErrorHandler());
        }

        return reader;
    }

    private static class SimpleErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }

}
