package sithmcfly.comments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.http.request.CommentCreateRequest;
import sithmcfly.comments.http.request.CommentUpdateRequest;
import sithmcfly.comments.http.response.CommentCreatedResponse;
import sithmcfly.comments.http.response.CommentUpdatedResponse;
import sithmcfly.comments.service.ICommentService;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final ICommentService commentService;

    public CommentController (ICommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments () {
        return ResponseEntity.ok(commentService.getCommentsList());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CommentDTO> getComment (@PathVariable long id){
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PostMapping
    public ResponseEntity<CommentCreatedResponse> createComment (@RequestBody CommentCreateRequest comment){
        CommentCreatedResponse createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment (@PathVariable long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CommentUpdatedResponse> updateComment (@RequestBody CommentUpdateRequest comment, @PathVariable long id){
        CommentUpdatedResponse updatedComment = commentService.editComment(id, comment);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedComment);
    }
}
