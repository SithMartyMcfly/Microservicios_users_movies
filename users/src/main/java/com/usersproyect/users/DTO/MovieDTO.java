package com.usersproyect.users.DTO;

// Creamos el DTO del servicio

public class MovieDTO {
    private Long id;
    private String title;
    private String director;

    //GETTERS&SETTERS
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    //CONSTRUCTORS
    public void setDirector(String director) {
        this.director = director;
    }

    public MovieDTO() {
        super();
    }  
}
