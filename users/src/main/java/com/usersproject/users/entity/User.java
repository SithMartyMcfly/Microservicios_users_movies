package com.usersproject.users.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String telefono;
    
    @Column(name = "password")
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }


    public User(Long id, String nombre, String apellido, String email, String telefono, String password, Role role) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    // Clase Enum para roles
    public enum Role {
        ADMIN,
        USER,
        GUEST
    }
    
}
