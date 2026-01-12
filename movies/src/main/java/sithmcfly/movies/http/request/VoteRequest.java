package sithmcfly.movies.http.request;

public class VoteRequest {
    private int rating;

    public VoteRequest (int rating){
        this.rating = rating;
    }
    
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
