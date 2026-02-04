package sithmcfly.movies.DTO;

public class MovieDTO {

    private String title;
    private String director;
    private String description;
    private int year;
    private double rating;
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
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    

    //CONSTRUCTORS
    public MovieDTO(String title, String director, String description, int year, double rating, String imageUrl) {
        this.title = title;
        this.director = director;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public MovieDTO() {
    }
    

    

    

}
