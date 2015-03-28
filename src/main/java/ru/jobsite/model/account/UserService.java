package ru.jobsite.model.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collections;

public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    protected void initialize() {
        checkIfUserIsExist(new Account("user", "user@user.ru", "demo", "ROLE_USER"));
        checkIfUserIsExist(new Account("admin", "admin@admin.ru", "admin", "ROLE_ADMIN"));
    }

    public boolean checkIfUserIsExist(Account account) {
        if (accountRepository.findByEmailOrLogin(account.getEmail()) == null &&
                accountRepository.findByEmailOrLogin(account.getLogin()) == null)
            accountRepository.saveAccount(account);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailOrLogin(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    public void signin(Account account) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account) {
        return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));
    }

    private User createUser(Account account) {
        return new User(account.getLogin(), account.getPassword(), Collections.singleton(createAuthority(account)));
    }

    private GrantedAuthority createAuthority(Account account) {
        return new SimpleGrantedAuthority(account.getRole());
    }

}
