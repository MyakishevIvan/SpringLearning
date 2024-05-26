package testSpringMVC.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import testSpringMVC.Model.Person;
import testSpringMVC.dao.PersonDAO;


@Controller
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PersonDAO personDAO;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        try {
            Person index = personDAO.show(id);
            model.addAttribute("person", index);
        } catch (RuntimeException exception) {
            return "people/error";
        }
        return "people/show";
    }

    @GetMapping("/new")
    public String creat(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("person", person);
        return "people/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("person")  Person person, BindingResult bindingResult) {
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
        Person editPerson = personDAO.show(id);
        model.addAttribute("person", editPerson);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
            return "people/error";
        }
        
        personDAO.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
