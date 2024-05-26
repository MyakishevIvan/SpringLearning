package testSpringMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import testSpringMVC.Model.Person;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person show(Integer id) {
        return jdbcTemplate.
                query("select * from Person where id=?", new Object[]{id}, new BeanPropertyRowMapper<Person>(Person.class)).
                stream().
                findAny().
                orElse(null);
    }

    public List<Person> index() {
        List<Person> result = jdbcTemplate.query("SELECT * from Person", new BeanPropertyRowMapper<Person>(Person.class));
        return result;
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "insert into Person(name, age, email) VALUES (?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail());
    }

    public void update(Person newPersonData) {
        jdbcTemplate.update("update Person SET name=?, age=?, email=? where id=?",
                newPersonData.getName(), newPersonData.getAge(), newPersonData.getEmail(), newPersonData.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id=?", id);
    }
}
