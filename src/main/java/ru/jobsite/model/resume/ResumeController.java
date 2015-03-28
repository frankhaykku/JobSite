package ru.jobsite.model.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jobsite.model.account.Account;
import ru.jobsite.model.account.AccountRepository;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ResumeController {

    public static final String VIEW_ADD_RESUME = "resume/addResume";
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "addResume")
    public String addResume(ModelMap model) {

        model.addAttribute(new ResumeForm());
        return VIEW_ADD_RESUME;
    }

    @RequestMapping(value = "addResume", method = RequestMethod.POST)
    public String addResume(Principal principal, @Valid @ModelAttribute ResumeForm resumeForm) {
        if (principal == null) {
            return VIEW_ADD_RESUME;
        }

        Account account = accountRepository.findByEmailOrLogin(principal.getName());

        if (resumeForm != null && account != null) {
            System.out.println(resumeForm);
            account.addResume(resumeForm.createUserResume());
            accountRepository.saveResume(account.getResume());
        }

        return "redirect:/";
    }
}
