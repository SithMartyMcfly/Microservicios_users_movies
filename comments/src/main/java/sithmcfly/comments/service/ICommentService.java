package sithmcfly.comments.service;

import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.http.request.CommentCreateRequest;
import sithmcfly.comments.http.request.CommentUpdateRequest;
import sithmcfly.comments.http.response.CommentCreatedResponse;
import sithmcfly.comments.http.response.CommentUpdatedResponse;

import java.util.List;

public interface ICommentService {

    List<CommentDTO> getCommentsList ();
    CommentDTO getComment(long idComment);
    CommentCreatedResponse createComment (CommentCreateRequest comment);
    void deleteComment(long id);
    CommentUpdatedResponse editComment (long idComment, CommentUpdateRequest comment);
}
