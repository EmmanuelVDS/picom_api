package fr.manu.picom_api.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.manu.picom_api.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String name;
    private String firstName;
    @JsonIgnore
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String email, String name, String firstName, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.firstName = firstName;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getFirstName(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
