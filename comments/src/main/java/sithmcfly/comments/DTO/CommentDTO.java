package sithmcfly.comments.DTO;

import java.time.LocalDateTime;

public class CommentDTO {
    private long id;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long idUser;
    private long idMovie;

    //GETTERS & SETTERS


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public CommentDTO(long id, long idMovie, long idUser, LocalDateTime updatedAt, LocalDateTime createdAt, String text) {
        this.id = id;
        this.idMovie = idMovie;
        this.idUser = idUser;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.text = text;
    }

    public CommentDTO(){

    }
}
