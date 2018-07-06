/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

/**
 *
 * @author sylvain.cailleau
 */
public class TraducteurSedaFactory {
    
    private final XmlMapper xmlMapper;
    
    private final TransformerFactory transformerFactory;
    
    private final Templates templateReception;
    
    private final Templates templateEmission;

    private MessageProcesseur processeurEmission;
    
    private MessageProcesseur processeurReception;
    
    @Autowired
    private XMLReaderFactory xmlReaderFactory;

    public TraducteurSedaFactory(XmlMapper xmlMapper, TransformerFactory transformerFactory, Resource scriptReception, Resource scriptEmission) throws IOException, TransformerConfigurationException {
        this.xmlMapper = xmlMapper;
        this.transformerFactory = transformerFactory;
        
        this.templateReception = buildTemplate(scriptReception);
        this.templateEmission = buildTemplate(scriptEmission);
    }

    private Templates buildTemplate(Resource script) throws IOException, TransformerConfigurationException {
        if ( script == null ) {
            return null;
        }
        
        try (InputStream in = script.getInputStream() ) {
            StreamSource stylesource = new StreamSource(in,script.getURI().toString());
            return transformerFactory.newTemplates(stylesource);
        }
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
    
    /**
     * Crée un traducteur correspondant à profil d'utilisation donné
     * @param profil le profil d'utilisation souhaité
     * @return Le traducteur correspondant à ce profil
     * @throws IOException
     * @throws TransformerConfigurationException 
     */
    TraducteurSeda create(String profil) {
        TraducteurSeda traducteur = new TraducteurSeda(templateReception, templateEmission, xmlMapper);
        traducteur.setProcesseurEmission(processeurEmission);
        traducteur.setProcesseurReception(processeurReception);
        traducteur.setXmlReaderFactory(xmlReaderFactory);
        return traducteur;
    }
}
