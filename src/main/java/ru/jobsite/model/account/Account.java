package ru.jobsite.model.account;

import org.codehaus.jackson.annotate.JsonIgnore;
import ru.jobsite.model.resume.ResumeModel.Resume;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL_OR_LOGIN, query = "select a from Account a where a.email = :value or a.login = :value")
public class Account implements java.io.Serializable {

    public static final String FIND_BY_EMAIL_OR_LOGIN = "Account.findByEmailOrLogin";

    @Id
    @GeneratedValue
    private Long id;

    private String login;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = "ROLE_USER";

    @Transient
    private Resume resume;

    protected Account() {

    }

    public Account(String login, String email, String password, String role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.resume = new Resume();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Resume getResume() {
        return resume;
    }

    public void addResume(Resume resume) {
        this.resume = resume;
    }
}
