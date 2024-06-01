package testSpringMVC.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import testSpringMVC.Model.Book;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public Optional<Book> show(String name) {
        return jdbcTemplate.query("select * from Book where name=?", new Object[]{name},
                new BeanPropertyRowMapper(Book.class)).
                stream().findAny();
    }
    public Optional<Book> show(int id) {
        return jdbcTemplate.query("select * from Book where id=?", new Object[]{id},
                new BeanPropertyRowMapper(Book.class)).
                stream().findAny();
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * from Book",
                new BeanPropertyRowMapper<Book>(Book.class));    
                
    }

    public List<Book> index(int userId) {
        return jdbcTemplate.query("SELECT * from Book where person_id=?", new Object[]{userId},
                new BeanPropertyRowMapper<Book>(Book.class));

    }
    
    public void save(Book book) {
        jdbcTemplate.update("insert into Book(name, publication_date) VALUES(?, ?)",
                book.getName(), book.getPublication_Date());
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("update Book SET name=?, publication_date=?, person_id=? where id=?", 
                book.getName(), book.getPublication_Date(), book.getPerson_id(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Book where id=?", id);
    }

    public void updateBookUser(int bookId, int userId) {
        jdbcTemplate.update("update Book SET person_id=? where id=?", userId, bookId
        );
    }

    public void deleteBookUser(int bookId) {
        jdbcTemplate.update("UPDATE Book set person_id = NULL where id =?", bookId);
    }

}
