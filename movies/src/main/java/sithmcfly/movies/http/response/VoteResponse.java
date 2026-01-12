package sithmcfly.movies.http.response;

public class VoteResponse {
    private int vote;
    private double rating;

    public VoteResponse (int vote, double rating){
        this.vote = vote;
        this.rating = rating;
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

    
}
