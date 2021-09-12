package fr.manu.picom_api.controller;

import fr.manu.picom_api.services.impl.UserDetailsServiceImpl;

import javax.management.remote.JMXAuthenticator;

public class LoginRequest {

    private String email;
    private String password;
    private UserDetailsServiceImpl userDetailsService;
    private JMXAuthenticator authenticationManager;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
