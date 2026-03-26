package sithmcfly.comments.http.request;

public class CommentUpdateRequest {
    private String text;
    private long idComment;
    private long idUser;

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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public CommentUpdateRequest(String text, long idComment, long idUser) {
        this.text = text;
        this.idComment = idComment;
        this.idUser = idUser;
    }

    public CommentUpdateRequest() {
    }
}
