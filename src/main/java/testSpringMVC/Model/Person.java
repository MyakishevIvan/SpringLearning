package testSpringMVC.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Person {
    @NotEmpty(message = "name is empty")
    @Size(min = 2, max = 10, message = "incorrect name")
    private String name;
    
    @Min(value = 0, message = "incorrect age")
    private Integer age;
    
    @NotEmpty
    private String email;

   // @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
    private String address;
    
    private int id;
    public void setId(Integer id) {
        this.id = id;
    }


    public Person() {
        
    }
    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
