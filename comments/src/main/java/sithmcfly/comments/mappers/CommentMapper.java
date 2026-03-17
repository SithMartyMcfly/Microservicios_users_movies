package sithmcfly.comments.mappers;

import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.entities.Comment;
import sithmcfly.comments.http.request.CommentCreateRequest;
import sithmcfly.comments.http.response.CommentCreatedResponse;
import sithmcfly.comments.http.response.CommentUpdatedResponse;

public class CommentMapper {

    public static CommentDTO toCommentDTO (Comment comment){
        CommentDTO dto = new CommentDTO();

        dto.setId(comment.getId());
        dto.setIdUser(comment.getIdUser());
        dto.setIdMovie(comment.getIdMovie());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());

        return dto;
    }

    public static Comment toEntity (CommentCreateRequest dto) {
        Comment comment = new Comment();

        comment.setIdUser(dto.getIdUser());
        comment.setIdMovie(dto.getIdMovie());
        comment.setText(dto.getText());

        return comment;
    }

    public static CommentCreatedResponse toCreateResponse(Comment comment){
        CommentCreatedResponse response = new CommentCreatedResponse();

        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setCreatedAt(comment.getCreatedAt());

        return response;
    }

    public static CommentUpdatedResponse toUpdateResponse(Comment comment){
        CommentUpdatedResponse response = new CommentUpdatedResponse();

        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setUpdatedAt(comment.getUpdatedAt());

        return response;
    }
}
