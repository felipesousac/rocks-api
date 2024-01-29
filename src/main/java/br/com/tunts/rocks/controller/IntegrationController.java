package br.com.tunts.rocks.controller;

import br.com.tunts.rocks.service.GoogleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class IntegrationController {

    @Autowired
    private GoogleApiService googleApiService;

    @GetMapping("/data")
    public String updateData() throws GeneralSecurityException, IOException {
        return googleApiService.readDataFromGoogleSheet();
    }

}
