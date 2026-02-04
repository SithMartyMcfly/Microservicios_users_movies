package sithmcfly.movies.http.request;

import jakarta.validation.constraints.Size;
import sithmcfly.movies.utils.validation.ValidYear;

import jakarta.validation.constraints.NotBlank;

public class MovieRequestDTO {
    @NotBlank(message = "El campo título es obligatorio")
    private String title;
    @NotBlank(message = "El campo director es obligatorio")
    private String director;
    private String description;
    @Size(min = 1888, message= "La primera película de la historia fue en 1888")
    @ValidYear //Validación personalizada
    private int year;
    private String imageUrl;

    //GETTERS & SETTERS
   
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //CONSTRUCTORS
    public MovieRequestDTO(String title, String director, String description, int year, String imageUrl) {
        this.title = title;
        this.director = director;
        this.description = description;
        this.year = year;
        this.imageUrl = imageUrl;
    }
    public MovieRequestDTO() {
    }
    

    
    

    

}
