package sithmcfly.movies.http.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class VoteRequest {
    @Min(value = 1, message = "La nota no puede ser inferior a 1")
    @Max(value = 10, message = "La nota no puede ser superior a 10")
    private double rating;
    
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public VoteRequest(double rating) {
        this.rating = rating;
    }

    public VoteRequest() {
    }

    
}

