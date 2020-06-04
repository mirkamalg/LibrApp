import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class BookFinder {

    public static String searchBooksJson(String bookName) throws IOException {
        String[] name = bookName.split("");
        String formattedName = String.join("+", name);

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + formattedName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder respond = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            respond.append(inputLine);
        }
        reader.close();

        return respond.toString();
    }

    public static ArrayList<Book> jsonToBookList(String jsonText) {
        Gson gson = new Gson();
        Map<String, HashMap<String, Object>> map = gson.fromJson(jsonText, Map.class);

        List items = convertObjectToList(map.get("items"));

        ArrayList<Book> books = new ArrayList<>();

        items.forEach(k -> {

            String id;
            if (((Map) k).containsKey("id")) {
                id = ((Map) k).get("id").toString();
            }else {
                id = "?";
            }

            String title;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("title"))) {
                title = ((Map) ((Map) k).get("volumeInfo")).get("title").toString();
            }else {
                title = "?";
            }

            String authorsString;
            String[] authors;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("authors"))) {
                authorsString = ((Map) ((Map) k).get("volumeInfo")).get("authors").toString();
                authorsString = authorsString.replaceAll("\\[", "").replaceAll("]","");
                authors = authorsString.split(", ");
            } else {
                authors = new String[] {"?"};
            }

            String publisher;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("publisher"))) {
                publisher = ((Map) ((Map) k).get("volumeInfo")).get("publisher").toString();
            } else {
                publisher = "?";
            }

            String publishDate;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("publishedDate"))) {
                publishDate = ((Map) ((Map) k).get("volumeInfo")).get("publishedDate").toString();
            } else {
                publishDate = "?";
            }

            String description;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("description"))) {
                description = ((Map) ((Map) k).get("volumeInfo")).get("description").toString();
            } else {
                description = "?";
            }

            int pageCount;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("pageCount"))) {
                double d = Double.parseDouble(((Map) ((Map) k).get("volumeInfo")).get("pageCount").toString());
                pageCount = (int) d;
            } else {
                pageCount = 0;
            }

            String categoriesString;
            String[] categoriesArray;
            String categories;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("categories"))) {
                categoriesString = ((Map) ((Map) k).get("volumeInfo")).get("categories").toString();
                categoriesString = categoriesString.replaceAll("\\[", "").replaceAll("]","");
                categoriesArray = categoriesString.split(", ");
                categories = String.join(", ", categoriesArray);
            } else {
                categories = "?";
            }

            double averageRating;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("averageRating"))) {
                averageRating = Double.parseDouble(((Map) ((Map) k).get("volumeInfo")).get("averageRating").toString());
            } else {
                averageRating = 0;
            }

            int hasMatureContent;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("averageRating"))) {
                if (!((Map) ((Map) k).get("volumeInfo")).get("maturityRating").toString().equals("NOT_MATURE")) {
                    hasMatureContent = 1;
                }else {
                    hasMatureContent = 0;
                }
            } else {
                hasMatureContent = 0;
            }

            Map<String, String> images;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("imageLinks"))) {
                images = (Map<String, String>) ((Map) ((Map) k).get("volumeInfo")).get("imageLinks");
            } else {
                images = new HashMap<>();
                images.put("?", "?");
            }

            String language;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("language"))) {
                language = ((Map) ((Map) k).get("volumeInfo")).get("language").toString();
            } else {
                language = "?";
            }

            String googleLink;
            if ((( (Map) ((Map) k).get("volumeInfo")).containsKey("infoLink"))) {
                googleLink = ((Map) ((Map) k).get("volumeInfo")).get("infoLink").toString();
            } else {
                googleLink = "?";
            }

            Book book = null;
            try {
                book = new Book(id, title, publisher, publishDate, description, language, googleLink, pageCount, averageRating, hasMatureContent, authors, categories, images);
            } catch (IOException e) {
                e.printStackTrace();
            }

            books.add(book);
        });

        return books;
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
}
