/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.telino.avp.modele.DocumentContent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SimpleDocumentContentProvider implements DocumentContentProvider {

    private static class FileUriPair {
        private final String file;
        private final String uri;

        public FileUriPair(String file, String uri) {
            this.file = file;
            this.uri = uri;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + Objects.hashCode(this.file);
            hash = 23 * hash + Objects.hashCode(this.uri);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FileUriPair other = (FileUriPair) obj;
            if (!Objects.equals(this.file, other.file)) {
                return false;
            }
            if (!Objects.equals(this.uri, other.uri)) {
                return false;
            }
            return true;
        }
        
    }
    
    private Map<FileUriPair,DocumentContent> contents = new HashMap<>();
    
    @Override
    public DocumentContent getContent(String uri, String file) {
        return contents.get(new FileUriPair(file,uri));
    }
    
    public void addContent(String file, String uri, DocumentContent content) {
        contents.put(new FileUriPair(file,uri), content);
    }
}
