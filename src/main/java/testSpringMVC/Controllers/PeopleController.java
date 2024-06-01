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
import testSpringMVC.utils.PersonValidator;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private PersonValidator personValidator;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<Person> index = personDAO.show(id);
        if (!index.isPresent()) {
            return "/people/error";
        }
        model.addAttribute("person", index.get());
        List<Book> currentUserBooks = bookDAO.index(id);

        if (currentUserBooks.isEmpty()) {
            model.addAttribute("books", "zero");
        } else {
            model.addAttribute("books", currentUserBooks);
            System.out.println(currentUserBooks.size());
        }
            return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("person", person);
        return "people/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
            return "/people/error";
        }


        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/error")
    public String error() {
        return "people/error";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id, Model model) {
        Optional<Person> editPerson = personDAO.show(id);
        if (editPerson.isPresent()) {
            model.addAttribute("person", editPerson.get());
            return "people/edit";
        }

        throw new RuntimeException("person doesn't exist ");
    }

    @PatchMapping("/{id}")
    public String edit(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        
        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
            return "people/error";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
