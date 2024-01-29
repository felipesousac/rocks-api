package br.com.tunts.rocks.service;

import br.com.tunts.rocks.util.GoogleApiUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static br.com.tunts.rocks.util.GoogleApiUtil.JSON_FACTORY;
import static br.com.tunts.rocks.util.GoogleApiUtil.getCredentials;
import static org.springframework.boot.logging.LoggingSystemProperty.APPLICATION_NAME;

@Service
public class GoogleApiService {

    @Autowired
    private GoogleApiUtil googleApiUtil;


    public List<List<Object>> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        List<List<Object>> values = googleApiUtil.getDataFromSheet();

        for (List<Object> value:
             values) {
            value.add("Teste");
        }

        return values;
    }


}
