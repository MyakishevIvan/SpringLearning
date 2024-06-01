package testSpringMVC.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import testSpringMVC.Model.Person;
import testSpringMVC.dao.PersonDAO;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    @Autowired
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> result = personDAO.show(person.getEmail());

        if (result.isPresent()) {
            errors.rejectValue("email", "", "this email already exist");
        }
    }
}
