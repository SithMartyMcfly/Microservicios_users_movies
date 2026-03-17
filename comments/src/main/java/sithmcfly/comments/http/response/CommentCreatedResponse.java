package sithmcfly.comments.http.response;

import java.time.LocalDateTime;

public class CommentCreatedResponse {
    private long id;
    private String text;
    private LocalDateTime createdAt;

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

    public CommentCreatedResponse(String text, LocalDateTime createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public CommentCreatedResponse(){

    }
}
