package sithmcfly.comments.service;

import org.springframework.stereotype.Service;
import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.entities.Comment;
import sithmcfly.comments.http.request.CommentCreateRequest;
import sithmcfly.comments.http.request.CommentUpdateRequest;
import sithmcfly.comments.http.response.CommentCreatedResponse;
import sithmcfly.comments.http.response.CommentUpdatedResponse;
import sithmcfly.comments.mappers.CommentMapper;
import sithmcfly.comments.persistence.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImpCommentService implements ICommentService {

    private final CommentRepository commentRepository;

    public ImpCommentService (CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDTO> getCommentsList() {
        List<Comment> commentList = commentRepository.findAll();

        return commentList.stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList());

    }

    @Override
    public CommentDTO getComment(long idComment){
        Comment comment = commentRepository.findById(idComment)
                .orElseThrow(() -> new RuntimeException("No se encontró"));
        return CommentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentCreatedResponse createComment(CommentCreateRequest comment) {
        Comment createComment = CommentMapper.toEntity(comment);

        commentRepository.save(createComment);
        return CommentMapper.toCreateResponse(createComment);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no se encuentra"));
        commentRepository.delete(comment);
    }

    @Override
    public CommentUpdatedResponse editComment(long idComment, CommentUpdateRequest comment) {
        Comment updateComment = commentRepository.findById(idComment)
                .orElseThrow(()-> new RuntimeException("no se encuentra"));
        updateComment.setText(comment.getText());

        Comment saved = commentRepository.save(updateComment);

        return CommentMapper.toUpdateResponse(saved);
    }
}
