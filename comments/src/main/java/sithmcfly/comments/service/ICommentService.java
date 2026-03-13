package sithmcfly.comments.service;

import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.http.request.CommentRequest;

import java.util.List;

public interface ICommentService {

    List<CommentDTO> getCommentsList ();
    CommentDTO getComment();
    CommentCreateResponseDTO createComment (CommentRequest comment);
    void deleteComment(long id);
    CommentUpdatedResponseDTO editComment (long id, CommentRequest comment);
}
