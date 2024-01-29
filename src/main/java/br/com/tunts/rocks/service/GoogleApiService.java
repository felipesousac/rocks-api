package br.com.tunts.rocks.service;

import br.com.tunts.rocks.util.GoogleApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GoogleApiService {

    @Autowired
    private GoogleApiUtil googleApiUtil;

    public String readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        List<List<Object>> values = googleApiUtil.updateStudentsSituation();

        return "Agora você pode se direcionar a planilha";
    }
}
