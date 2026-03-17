package sithmcfly.comments.http.request;

public class CommentUpdateRequest {
    private String text;
    private long idComment;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIdComment() {
        return idComment;
    }

    public void setIdComment(long idComment) {
        this.idComment = idComment;
    }

    public CommentUpdateRequest(String text, long idComment) {
        this.text = text;
        this.idComment = idComment;
    }

    public CommentUpdateRequest() {
    }
}
