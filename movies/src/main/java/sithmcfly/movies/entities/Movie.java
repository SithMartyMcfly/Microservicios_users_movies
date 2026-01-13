package sithmcfly.movies.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Anotacion que dice que es una entidad de la BBDD
@Table(name = "movies") // Anotación para indicar que mapea la tabla 'movies'
public class Movie {
    @Id // indica que es una PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que es autogenerado
    private Long id;

    private String title;
    private String director;
    private String description;
    private int year;
    private int votes;
    private double rating;

    @Column(name = "image_url")
    private String imageUrl;
    // AÑADIR CLAVE FORÁNEA ID_USUARIO
    /* Una List que contendrá los usuarios */
    private List<Long> userSeen = new ArrayList<>();
    
    // en caso de que los nombres de las propiedades coincidan con los
    // nombres de los campos de la BBDD no hay que hacer nada
    // en caso contrario hay que anotarlo como en el campo imageUrl
    
    
    //GETTERS & SETTERS
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDirector() {
        return director;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getVotes() {
        return votes;
    }
    
    public double getRating() {
        return rating;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public List<Long> getUserSeen() {
        return userSeen;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setVotes(int votes) {
        this.votes = votes;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserSeen(List<Long> userSeen) {
        this.userSeen = userSeen;
    }
}
