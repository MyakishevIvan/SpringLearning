package testSpringMVC.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import testSpringMVC.Model.Book;
import testSpringMVC.Model.Person;
import testSpringMVC.dao.BookDAO;
import testSpringMVC.dao.PersonDAO;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PersonDAO personDAO;

    @GetMapping()
    public String index(Model model) {
        List<Book> index = bookDAO.index();
        model.addAttribute("book", index);
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        Optional<Book> currentBook = bookDAO.show(id);
        if (currentBook.isPresent()) {
            model.addAttribute("book", currentBook.get());
        } else {
            throw new RuntimeException();
        }
        Integer bookUserid = currentBook.get().getPerson_id();

        if (bookUserid != null) {
            model.addAttribute("hasUser", "true");
            model.addAttribute("bookUser", personDAO.show(bookUserid).get());
        } else {
            model.addAttribute("hasUser", "false");
            model.addAttribute("people", personDAO.index());
        }
        
        return "book/show";
    }

    @GetMapping("/new")
    public String creat(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("book", book);
        return "book/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }


        bookDAO.save(book);
        return "redirect:/book";
    }


    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id, Model model) {
        Optional<Book> editBook = bookDAO.show(id);
        if (editBook.isPresent()) {
            model.addAttribute("book", editBook.get());
            return "book/edit";
        }

        throw new RuntimeException("book doesn't exist ");
    }

    @PatchMapping("/{id}")
    public String edit(@Valid @ModelAttribute("book") Book book, @PathVariable("id") int id, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            throw new RuntimeException();
        }

        bookDAO.update(book, id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/addUser")
    public String setUser(@ModelAttribute("person") Person person, @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            throw new RuntimeException();
        }

        bookDAO.updateBookUser(id, person.getId());
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        bookDAO.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/deleteUser")
    public String deleteBookUser(@PathVariable("id") int id) {
        bookDAO.deleteBookUser(id);
        return "redirect:/book";
    }
}
