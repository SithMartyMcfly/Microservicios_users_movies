package sithmcfly.comments.http.response;

import java.time.LocalDateTime;

public class CommentUpdatedResponse {
    private long id;
    private String text;
    private LocalDateTime updatedAt;

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CommentUpdatedResponse(long id, String text, LocalDateTime updatedAt) {
        this.id = id;
        this.text = text;
        this.updatedAt = updatedAt;
    }

    public  CommentUpdatedResponse(){

    }
}
