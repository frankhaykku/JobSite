package ru.jobsite.model.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jobsite.model.account.AccountRepository;
import ru.jobsite.model.resume.ResumeModel.Resume;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

    public static final String VIEW_RESUME_LIST = "resume/list";
    public static final String VIEW_PROFILE = "profile/profile";
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "profile")
    public String profile(ModelMap model, Principal principal) {
        String username = principal.getName();
        boolean isResumes = false;
        List<Resume> resumeList = accountRepository.findResumeByLogin(username);
        if (resumeList.size() > 0) {
            isResumes = true;
        }
        model.addAttribute("isResumes", isResumes);

        model.addAttribute("username", username);
        return VIEW_PROFILE;
    }

    @RequestMapping(value = "myResumes")
    public String profile(ModelMap model, @RequestParam("username") String username) {
        if (!username.isEmpty()) {
            List<Resume> resumeList = accountRepository.findResumeByLogin(username);

            if (resumeList.size() > 0) {
                model.addAttribute(resumeList);
                return VIEW_RESUME_LIST;
            }

        }

        return VIEW_PROFILE;
    }
}
