/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telino.avp.seda.sv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sylvain.cailleau
 */
public class OrganisationSeda {

    private final String identification;

    public OrganisationSeda(@JsonProperty("Identification") String identification) {
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }
}
