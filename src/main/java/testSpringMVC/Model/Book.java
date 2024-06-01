package testSpringMVC.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int publication_Date;
    
    @NotEmpty(message = "year is empty")
    private String Name;
    
    private int id;
    private Integer person_id;

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setName(String name) {
        Name = name;
    }

    public void setPublication_Date(int publication_Date) {
        this.publication_Date = publication_Date;
    }

    public String getName() {
        return Name;
    }

    public int getPublication_Date() {
        return publication_Date;
    }

    public Book(String name, Integer publication_Date) {
        Name = name;
        this.publication_Date = publication_Date;
    }

    public Book() {
        
    }
}
