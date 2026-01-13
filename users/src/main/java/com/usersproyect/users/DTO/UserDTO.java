package com.usersproyect.users.DTO;

//Generamos un DTO para cuidar los datos que vamos a devolver
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;

    //GETTER&SETTERS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //CONSTRUCTORS
    public UserDTO(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public UserDTO() {
    }
    
}
