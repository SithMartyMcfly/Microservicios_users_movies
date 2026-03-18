package sithmcfly.comments.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException (long id) {super ("Comentario "+ id + " no encontrado" );}
}
