package rishabh.demo2.doa;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Books {
    @Id
    private String id;
    private String name;
    private String Author;
    private double price;
    private int page;
    private int genres;


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

    public int getGenres() {
        return genres;
    }

    public void setGenres(int genres) {
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

    public Books(String name, String author) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.Author = author;
    }
}
