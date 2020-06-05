import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Book {

    private String googleID, title, publisher, publishDate, description, language, googleBooksInfoURL, categories;
    private int pageCount;
    private double averageRating;
    private int hasMatureContent;
    private String[] authors;
    private Map<String, String> images;
    private Image CoverImage;

    public Book(String googleID, String title, String publisher, String publishDate, String description, String language, String googleBooksInfoURL, int pageCount, double averageRating, int hasMatureContent, String[] authors, String categories, Map<String, String> images) throws IOException {
        this.googleID = googleID;
        this.title = title;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.language = language;
        this.googleBooksInfoURL = googleBooksInfoURL;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.hasMatureContent = hasMatureContent;
        this.authors = authors;
        this.categories = categories;

        if (googleID.equals("none")) {
            if (AddNewBook.selectedFile != null) {
                setCoverImage(new Image(new FileInputStream(AddNewBook.selectedFile.getAbsolutePath())));
            } else setCoverImage(new Image("Pics/question-mark.png"));
        } else {
            Image image = new Image(images.get("thumbnail"));
            setCoverImage(image);
        }
    }

    public Image getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(Image coverImage) {
        CoverImage = coverImage;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGoogleBooksInfoURL() {
        return googleBooksInfoURL;
    }

    public void setGoogleBooksInfoURL(String googleBooksInfoURL) {
        this.googleBooksInfoURL = googleBooksInfoURL;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int HasMatureContent() {
        return hasMatureContent;
    }

    public void setMatureContent(int hasMatureContent) {
        this.hasMatureContent = hasMatureContent;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }
}
