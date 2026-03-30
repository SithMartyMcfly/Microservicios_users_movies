package sithmcfly.comments.exception;

public class CommentNotForbiddenException extends RuntimeException{
    public CommentNotForbiddenException (long idComment){
        super("no tiene permiso para modificar o borrar el comentario " + idComment);
    }
}
