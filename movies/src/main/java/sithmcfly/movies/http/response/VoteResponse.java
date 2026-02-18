package sithmcfly.movies.http.response;

public class VoteResponse {
    private int vote;
    private double rating;
    private String title;

    public VoteResponse (int vote, double rating, String title){
        this.vote = vote;
        this.rating = rating;
        this.title = title;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() { return title; }

    public void setTitle (String title) { this.title = title; }

    
}
