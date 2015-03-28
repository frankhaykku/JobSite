package ru.jobsite.model.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.jobsite.model.account.Account;
import ru.jobsite.model.account.AccountRepository;
import ru.jobsite.model.account.UserService;
import ru.jobsite.model.support.web.MessageHelper;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        if (!signupForm.ifPasswordsIsSame()) {
            errors.rejectValue("password", "Парооли не совпадают", "Password not match");
            errors.rejectValue("retryPassword", "Парооли не совпадают", "Password not match");
            return SIGNUP_VIEW_NAME;
        }

        if (userService.checkIfUserIsExist(signupForm.createAccount()))
            return SIGNUP_VIEW_NAME;

        Account account = accountRepository.saveAccount(signupForm.createAccount());
        userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");

        return "redirect:/";

    }
}
