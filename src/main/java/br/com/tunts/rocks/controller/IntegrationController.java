package br.com.tunts.rocks.controller;

import br.com.tunts.rocks.service.GoogleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@RestController
public class IntegrationController {

    @Autowired
    private GoogleApiService googleApiService;

    @GetMapping("/test")
    public String test() {
        return "Testando api";
    }

    @GetMapping("/data")
    public List<List<Object>> getDataFromGoogleSheets() throws GeneralSecurityException, IOException {
        return googleApiService.readDataFromGoogleSheet();
    }

}
