package rishabh.demo2.doa;

public class Books {
    private String name;
    private String Author;


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
        this.name = name;
        Author = author;
    }
}
