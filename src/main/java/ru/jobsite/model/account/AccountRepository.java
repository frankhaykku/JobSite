package ru.jobsite.model.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jobsite.model.resume.ResumeModel.Resume;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        entityManager.persist(account);
        return account;
    }

    @Transactional
    public void saveResume(Resume resume) {
        entityManager.persist(resume);
    }

    public Account findByEmailOrLogin(String value) {
        try {
            return entityManager.createNamedQuery(Account.FIND_BY_EMAIL_OR_LOGIN, Account.class)
                    .setParameter("value", value)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public List<Resume> findByFullName(String fullName) {
        try {
            return entityManager.createNamedQuery(Resume.FIND_BY_FULL_NAME, Resume.class)
                    .setParameter("fullName", fullName)
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Resume findResumeByPhone(String phone) {
        try {
            return entityManager.createNamedQuery(Resume.FIND_RESUME_BY_PHONE, Resume.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Resume> findResumeByLogin(String login) {
        try {
            return entityManager.createNamedQuery(Resume.FIND_RESUME_BY_LOGIN, Resume.class)
                    .setParameter("login", login)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println(e);
            return null;
        }
    }

    public Resume findResumeById(Long id) {
        try {
            return entityManager.createNamedQuery(Resume.FIND_RESUME_BY_ID, Resume.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println(e);
            return null;
        }
    }
}
