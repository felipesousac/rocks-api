package br.com.tunts.rocks.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

@Component
public class GoogleApiUtil {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret_86944254893-r32nlm9fbdmv5m56bed9fqjb4d0kk9uc.apps.googleusercontent.com.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    // Método retirado pela documentação do Google Sheets que faz a autenticação do usuário a utilizar a aplicação
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // Método que irá atualizar os dados da planilha
    public List<List<Object>> updateStudentsSituation() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Id da planilha do desafio
        final String spreadsheetId = "1YLp32Pl0S9jdgSuag3jnKnN_LN8MKezK5fMjjrB2vx4";

        // Dimensão da planilha com os dados a serem lidos pela aplicação
        final String range = "engenharia_de_software!A4:H";

        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        // Linhas da planilha são retornadas inicialmente como uma lista de Objects
        List<List<Object>> values = response.getValues();

        // Armazena os valores das celulas na planilha
        List<List<String>> storeDataFromGoogleSheet = new ArrayList<>();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                storeDataFromGoogleSheet.add(row);
            }
        }

        // Condicional para verificar a situação do aluno
        for (int i = 0; i < storeDataFromGoogleSheet.size(); i++) {
            List<String> currentLine = storeDataFromGoogleSheet.get(i);
            int cell = 4 + i;

            // Caso alunos exceda número de faltas, automaticamente reprovado por falta
            if (Integer.parseInt(currentLine.get(2)) > 15) {

                List<List<Object>> update = new ArrayList<>();
                update.add(Collections.singletonList("Reprovado por falta"));
                update.add(Collections.singletonList("0"));

                ValueRange body = new ValueRange()
                        .setValues(Collections.singletonList(update.get(0)));

                // Atualiza célula "Situação"
                service.spreadsheets().values().update(spreadsheetId, "engenharia_de_software!G" + cell, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();

                body = new ValueRange()
                        .setValues(Collections.singletonList(update.get(1)));

                // Atualiza célula "Nota para Aprovação Final"
                service.spreadsheets().values().update(spreadsheetId, "engenharia_de_software!H" + cell, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
            } else {
                // Chama função que irá calcular as médias dos alunos e guarda sua situação em um array
                ArrayList<String> arr = calcAverages(currentLine.get(3), currentLine.get(4), currentLine.get(5));

                ValueRange body = new ValueRange()
                        .setValues(Collections.singletonList(Collections.singletonList(arr.get(0))));

                // Atualiza célula "Situação"
                service.spreadsheets().values().update(spreadsheetId, "engenharia_de_software!G" + cell, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();

                body = new ValueRange()
                        .setValues(Collections.singletonList(Collections.singletonList(arr.get(1))));

                // Atualiza célula "Nota para Aprovação Final"
                service.spreadsheets().values().update(spreadsheetId, "engenharia_de_software!H" + cell, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
            }
        }

        return values;
    }

    //Função que calcula as médias dos alunos e retorna um array contendo as informações necessárias para a função de atualização
    static ArrayList<String> calcAverages(String p1, String p2, String p3)  {
        Integer average = (int) Math.ceil((Float.parseFloat(p1) + Float.parseFloat(p2) + Float.parseFloat(p3)) / 3);
        ArrayList<String> arr = new ArrayList<>();

        if (average >= 7) {
            arr.add("Aprovado");
            arr.add("0");
            return arr;
        }

        if (average < 7 && average >= 5) {
            arr.add("Exame final");
            arr.add(String.valueOf(10 - average));
            return arr;
        }

        if (average < 5) {
            arr.add("Reprovado por nota");
            arr.add("0");
            return arr;
        }
        return arr;
    }
}