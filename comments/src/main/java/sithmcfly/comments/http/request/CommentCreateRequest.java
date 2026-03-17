package sithmcfly.comments.http.request;

public class CommentCreateRequest {
    private String text;
    private long idUser;
    private long idMovie;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public CommentCreateRequest(String text, long idUser, long idMovie) {
        this.text = text;
        this.idUser = idUser;
        this.idMovie = idMovie;
    }

    public CommentCreateRequest() {
    }
}
