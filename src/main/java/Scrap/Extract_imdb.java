package Scrap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract_imdb {
    public Extract_imdb(){};

    public String puntuacionIMDB(String nombre){
        if(isInternetAvailable()) return "NO CONEXION";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https"+searchImdbLink(nombre)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder htmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();

            int indexStart = htmlContent.indexOf("<span class=\"sc-bde20123-1 cMEQkK\">");
            if (indexStart != -1) {
                indexStart += "<span class=\"sc-bde20123-1 cMEQkK\">".length();
                int indexEnd = htmlContent.indexOf("</span>", indexStart);
                if (indexEnd != -1) {
                    return htmlContent.substring(indexStart, indexEnd);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String searchImdbLink(String movieName) {
        try {
            String searchQuery = URLEncoder.encode(movieName + " IMDb", "UTF-8");
            String url = "https://www.google.com/search?q=" + searchQuery;

            Document document = Jsoup.connect(url).get();
            Elements searchResults = document.select("div.g");

            for (Element result : searchResults) {
                Element linkElement = result.selectFirst("a[href]");
                String link = linkElement.attr("href");
                if (link.contains("imdb.com/title/")) {
                    return extractImdbLink(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractImdbLink(String googleSearchResultLink) {
        int start = googleSearchResultLink.indexOf("url?q=") + 6;
        int end = googleSearchResultLink.indexOf("&", start);
        if (end != -1) {
            return googleSearchResultLink.substring(start, end);
        } else {
            return googleSearchResultLink.substring(start);
        }
    }

    public String imagenImdb1(String nombre){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https" + searchImdbLink(nombre)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder htmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();

            String regex = "<a\\s+class=\"[^\"]*\"\\s+href=\"([^\"]*)\"[^>]*>";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(htmlContent);
            if (matcher.find()) {
                String enlace = matcher.group(1);
                System.out.println("https://www.imdb.com" + enlace);
                return "https://www.imdb.com" + enlace;
            } else {
                System.out.println("No se encontró ningún enlace.");
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String imagenImdb2(String nombre) {
        if(isInternetAvailable()) return "NO CONEXION";
        try {
            Document doc = Jsoup.connect(imagenImdb1(nombre)).get();
            Elements metaTags = doc.select("meta[property=og:image]");
            String imageUrl = "";
            for (Element metaTag : metaTags) {
                imageUrl = metaTag.attr("content");
            }
            System.out.println("Enlace de la imagen: " + imageUrl);
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isInternetAvailable() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://www.google.com").openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            return false;
        }
    }
}

