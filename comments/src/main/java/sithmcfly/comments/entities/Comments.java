package sithmcfly.comments.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getText() {
        return text;
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

    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    //CONSTRUCTORES


    public Comments(long id, String text, LocalDateTime createdAt, LocalDateTime updatedAt, long idUser, long idMovie) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idUser = idUser;
        this.idMovie = idMovie;
    }

    public Comments() {
    }

    @PrePersist
    public void createdAt (){
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void updatedAt (){
        setUpdatedAt(LocalDateTime.now());
    }
}
