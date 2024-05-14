package Scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract_videogame {
    public Extract_videogame(){};

    public String puntuacionMetacritic(String nombre){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https"+searchMetacriticLink(nombre)).openConnection();
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

            Pattern puntuacionPattern = Pattern.compile("<span[^>]*data-v-4cdca868[^>]*>(\\d+)</span>");

            Matcher puntuacionMatcher = puntuacionPattern.matcher(htmlContent);
            if (puntuacionMatcher.find()) {
                double puntuacion = (double) Integer.parseInt(puntuacionMatcher.group(1).trim()) / 10;
                return String.valueOf(puntuacion);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static void main(String[] args) {
        String gameTitle = "silksong"; // Título del videojuego a buscar
        String metacriticLink = searchMetacriticLink(gameTitle);
        if (metacriticLink != null) {
            System.out.println("Enlace a Metacritic: " + metacriticLink);
        } else {
            System.out.println("No se encontró un enlace a Metacritic para el videojuego '" + gameTitle + "'.");
        }
    }*/

    public static String searchMetacriticLink(String gameTitle) {
        try {
            String searchQuery = URLEncoder.encode(gameTitle + " Metacritic", "UTF-8");
            String url = "https://www.google.com/search?q=" + searchQuery;

            Document document = Jsoup.connect(url).get();
            Elements searchResults = document.select("div.g");

            for (Element result : searchResults) {
                Element linkElement = result.selectFirst("a[href]");
                String link = linkElement.attr("href");
                if (link.contains("metacritic.com/game/")) {
                    return extractMetacriticLink(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractMetacriticLink(String googleSearchResultLink) {
        int start = googleSearchResultLink.indexOf("url?q=") + 6;
        int end = googleSearchResultLink.indexOf("&", start);
        if (end != -1) {
            return googleSearchResultLink.substring(start, end);
        } else {
            return googleSearchResultLink.substring(start);
        }
    }

    public String imagenSteamDB(String nombre){
        try {
            Document doc = Jsoup.connect("https" + searchSteamDBLink(nombre)).get();

            Element metaElement = doc.select("meta[property=og:image]").first();

            String imgUrl = metaElement.attr("content");

            System.out.println("Imagen : " + imgUrl);

            return imgUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String searchSteamDBLink(String gameTitle) {
        try {
            String searchQuery = URLEncoder.encode(gameTitle + " SteamGridDB", "UTF-8");
            String url = "https://www.google.com/search?q=" + searchQuery;

            Document document = Jsoup.connect(url).get();
            Elements searchResults = document.select("div.g");

            for (Element result : searchResults) {
                Element linkElement = result.selectFirst("a[href]");
                String link = linkElement.attr("href");
                if (link.contains("steamgriddb.com")) {
                    System.out.println("Enlace steamdb : "+ extractSteamDBLink(link));
                    return extractSteamDBLink(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractSteamDBLink(String googleSearchResultLink) {
        int start = googleSearchResultLink.indexOf("url?q=") + 6;
        int end = googleSearchResultLink.indexOf("&", start);
        if (end != -1) {
            return googleSearchResultLink.substring(start, end);
        } else {
            return googleSearchResultLink.substring(start);
        }
    }

}

