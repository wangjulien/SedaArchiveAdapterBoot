/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * Conversion entre un fichier XML au format SEDA et une structure MessageSEDA
 * 
 * @author sylvain.cailleau
 */
public class TraducteurSeda {

    private static String PROFIL_RECEPTION = "reception";
    private static String PROFIL_EMISSION = "emission";
    
    /**
     * Tranformation XSLT pour reception SEDA
     */
    private final Templates templateReception;
    
    /**
     * Transformation XSLT pour emission SEDA
     */
    private final Templates templateEmission;

    /**
     * Serialisation/deserialisation MessageSeda vers XML intermediaire
     */
    private final XmlMapper xmlMapper;
    
    /**
     * Preprocessing de la structure MessageSeda avant emission
     */
    private MessageProcesseur processeurEmission;
    
    /**
     * Postprocessing de la structure MessageSeda apres reception
     */
    private MessageProcesseur processeurReception;
    
    /**
     * Pour obtenir un parser XML
     */
    private XMLReaderFactory xmlReaderFactory;

    protected TraducteurSeda(Templates reception, Templates emission, XmlMapper xmlMapper) {
        this.templateReception = reception;
        this.templateEmission = emission;
        this.xmlMapper = xmlMapper;
    }

    public void setProcesseurEmission(MessageProcesseur processeurEmission) {
        this.processeurEmission = processeurEmission;
    }

    public void setProcesseurReception(MessageProcesseur processeurReception) {
        this.processeurReception = processeurReception;
    }

    public void setXmlReaderFactory(XMLReaderFactory xmlReaderFactory) {
        this.xmlReaderFactory = xmlReaderFactory;
    }
    
    public MessageSeda reception(InputStream in) throws  IOException, SAXParseException {
        return reception(in, null);
    }

    private Source getSource(InputStream in, String profil) {
        Source source = null;
        
        if ( xmlReaderFactory != null ) {
            XMLReader reader = xmlReaderFactory.create(profil);
            if ( reader != null ) {
                source = new SAXSource(reader,new InputSource(in));
            }
        }
        
        if ( source == null ){
            source = new StreamSource(in);
        }
        
        return source;
    }
    
    public MessageSeda reception(InputStream in, DocumentContentProvider contentProvider) throws IOException, SAXParseException {
        if ( templateReception == null ) {
            throw new UnsupportedOperationException();
        }
        
        Source xmlSource = getSource(in, PROFIL_RECEPTION);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamResult xmlResult = new StreamResult(out);
        
        try {
            templateReception.newTransformer().transform(xmlSource, xmlResult);
        } catch (TransformerException ex) {
            Throwable wrapped = ex.getException();
            if ( wrapped instanceof SAXParseException) {
                // en cas d'erreur lors du parsing du document XML d'entree
                // on renvoi l'exception originale
                throw (SAXParseException)wrapped;
            }
            else {
                throw new RuntimeException(ex);
            }
        }
        
        MessageSeda message = xmlMapper.readValue(new ByteArrayInputStream(out.toByteArray()), MessageSeda.class);
        
        if ( contentProvider != null ) {
            mapContent(message,contentProvider);
        }
        
        if ( processeurReception != null ) {
            processeurReception.process(message);
        }
        
        return message;
    }
    
    private void mapContent(MessageSeda message, DocumentContentProvider contentProvider) {
        message.allDocumentsStream()
                .forEach(
                    d -> d.setContent(contentProvider.getContent(d.getUri(), d.getNom()))
                        );
    }
    
    /**
     * Serialize les données contenues dans me MessageSeda dans un document XML
     * @param message Les données à sérialiser
     * @param out Ou ecrire les données XML
     * @param embedContent Si le contenu des Documents des Archives doit également etre sérialisé dans le fichier XML
     */
    public void emission(MessageSeda message,OutputStream out, boolean embedContent) throws IOException, SAXParseException {
        if ( templateEmission == null ) {
            throw new UnsupportedOperationException();
        }

        if ( processeurEmission != null ) {
            processeurEmission.process(message);
        }
        
        File tempXmlFile = null;
        try {
            tempXmlFile = File.createTempFile("seda", ".xml");
            
            xmlMapper.writeValue(tempXmlFile, message);
            
            try ( InputStream in = new FileInputStream(tempXmlFile) ) {
                Source source = getSource(in,PROFIL_EMISSION);
                StreamResult target = new StreamResult(out);
                templateEmission.newTransformer().transform(source, target);
            }
        }
        catch (TransformerException ex) {
            Throwable wrapped = ex.getException();
            if ( wrapped instanceof SAXParseException) {
                // en cas d'erreur lors du parsing du document XML d'entree
                // on renvoi l'exception originale
                throw (SAXParseException)wrapped;
            }
            else {
                throw new RuntimeException(ex);
            }
        }
        finally {
            if ( tempXmlFile != null ) {
                tempXmlFile.delete();
            }
        }
    }
}
