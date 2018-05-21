package rishabh.demo2.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "Books")
public class Books {
    private String id;
    private String name;
    private String Author;
    private double price;
    private int page;
    private String genres;


    public String getId(){
        return id;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String  getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    @JsonCreator
    public Books(@JsonProperty("name") String name, @JsonProperty("author") String author) {
        //this.id = UUID.randomUUID().toString();
        this.name = name;
        this.Author = author;
    }
}
