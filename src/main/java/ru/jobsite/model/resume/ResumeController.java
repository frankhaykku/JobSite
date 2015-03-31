package ru.jobsite.model.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jobsite.model.account.Account;
import ru.jobsite.model.account.AccountRepository;
import ru.jobsite.model.resume.ResumeModel.Resume;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResumeController {

    public static final String VIEW_ADD_RESUME = "resume/addResume";
    public static final String VIEW_SEARCH_RESUME = "resume/searchResume";
    public static final String VIEW_RESUME_LIST = "resume/list";
    public static final String VIEW_RESUME = "resume/viewResume";

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "addResume")
    public String addResume(ModelMap model) {
        List<Integer> years = new ArrayList<Integer>();
        for (int i = 1990; i <= 2015; i++)
            years.add(i);

        model.addAttribute("years", years);
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
            account.addResume(resumeForm.createUserResume(principal.getName()));
            accountRepository.saveResume(account.getResume());
        }

        return "redirect:/";
    }

    @RequestMapping(value = "searchResume")
    public String searchResume() {

        return VIEW_SEARCH_RESUME;
    }

    @RequestMapping(value = "searchResume", method = RequestMethod.POST)
    public String searchResume(ModelMap model, @RequestParam("search") String search, @RequestParam("choice") String choice) {
       
        if (!search.isEmpty()) {
            List<Resume> resumeList = null;

            if ("fullName".equals(choice))
                resumeList = accountRepository.findByFullName(search);
            else if ("city".equals(choice))
                resumeList = accountRepository.findResumeByCity(search);
            else if ("login".equals(choice))
                resumeList = accountRepository.findResumeByLogin(search);

            if (resumeList.size() > 0) {
                model.addAttribute(resumeList);
                for (Resume aResumeList : resumeList) System.out.println(aResumeList);
                return VIEW_RESUME_LIST;
            } else
                System.out.println("Nothing found :(");
        } else
            System.out.println("search param is empty");

        return VIEW_SEARCH_RESUME;
    }

    @RequestMapping(value = "viewResume")
    public String addResume(ModelMap model, @RequestParam("id") Long id) {
        if (id > 0) {
            Resume resume = accountRepository.findResumeById(id);
            ResumeForm resumeForm = new ResumeForm();
            resumeForm.convertResumeToForm(resume);
            model.addAttribute(resumeForm);
            System.out.println("[SINGLE]:" + resume);
        }

        return VIEW_RESUME;
    }
}
