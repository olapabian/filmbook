package com.filmbook.services.collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpCollector {
    URL url;

    private StringBuilder response = new StringBuilder();
    public void collect() {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-locale", "pl-PL");
            con.setReadTimeout(5000);
            int responseCode = con.getResponseCode(); // Pobieranie kodu statusu odpowiedzi HTTP

            // Sprawdzenie, czy odpowiedź jest OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                // W przypadku błędu 403, po prostu pomiń dalsze kroki i zakończ metodę
                System.out.println("Nieudane połączenie. Kod statusu: " + responseCode);
            } else {
                System.out.println("Nieudane połączenie. Nieznany kod statusu: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Pominięcie dalszej części kodu w przypadku błędu IO
        }
    }

    public int getResponseCode() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("x-locale", "pl-PL");
        con.setReadTimeout(5000);
        return con.getResponseCode();
    }



    public byte[] getResponseBytes() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("x-locale", "pl-PL");
        con.setReadTimeout(5000);
        con.getResponseCode();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = con.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return outputStream.toByteArray();
    }

    public HttpCollector(URL url) {
        this.url = url;
    }

}