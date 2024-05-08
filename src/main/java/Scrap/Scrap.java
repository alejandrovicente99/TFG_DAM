package Scrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrap {
    public ArrayList<String> webScrap(String url) {
        ArrayList<String> devuelve = new ArrayList<>();
        try {
            // Realizar la solicitud HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Leer el contenido HTML de la página
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder htmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();

            // Extraer la puntuación utilizando expresiones regulares
            Pattern puntuacionPattern = Pattern.compile("<span[^>]*data-v-4cdca868[^>]*>(\\d+)</span>");

            Matcher puntuacionMatcher = puntuacionPattern.matcher(htmlContent);
            if (puntuacionMatcher.find()) {
                String puntuacionText = puntuacionMatcher.group(1).trim();
                devuelve.add(puntuacionText);
            } else {
                devuelve.add("X");
            }
            // Encontrar la posición del texto de la puntuación dentro del HTML
            int indexStart = htmlContent.indexOf("<span class=\"sc-bde20123-1 cMEQkK\">");
            if (indexStart != -1) {
                indexStart += "<span class=\"sc-bde20123-1 cMEQkK\">".length();
                int indexEnd = htmlContent.indexOf("</span>", indexStart);
                if (indexEnd != -1) {
                    String puntuacion = htmlContent.substring(indexStart, indexEnd);
                    devuelve.add(puntuacion);
                } else {
                    devuelve.add("X");
                }
            } else {
                devuelve.add("X");
            }

            // Encontrar la posición del enlace de la imagen dentro del HTML
            int indexStart1 = htmlContent.indexOf("<a class=\"ipc-lockup-overlay ipc-focusable\" href=\"");
            if (indexStart1 != -1) {
                indexStart1 += "<a class=\"ipc-lockup-overlay ipc-focusable\" href=\"".length();
                int indexEnd = htmlContent.indexOf("\"", indexStart1);
                if (indexEnd != -1) {
                    String enlaceImagen = htmlContent.substring(indexStart1, indexEnd);
                    devuelve.add(enlaceImagen);
                } else {
                    devuelve.add("X");
                }
            } else {
                devuelve.add("X");
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devuelve;
    }
}
