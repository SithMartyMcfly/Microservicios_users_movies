package com.usersproyect.users.http.request;

//DTO que utilizaremos para enviar datos para loguearnos
public class LoginRequestDTO {
    private String email;
    private String password;

    //GETTERS&SETTERS
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

    //CONSTRUCTORS
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestDTO() {
    }

    


    


}
