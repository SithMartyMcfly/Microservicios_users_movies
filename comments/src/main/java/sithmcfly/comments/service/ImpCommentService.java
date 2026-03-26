package sithmcfly.comments.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.DTO.MovieDTO;
import sithmcfly.comments.client.movieClient;
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
    private final movieClient movieClient;

    public ImpCommentService (CommentRepository commentRepository, movieClient movieClient){
        this.commentRepository = commentRepository;
        this.movieClient = movieClient;
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
        MovieDTO movieExists = movieClient.getMovie(comment.getIdMovie());
        Comment createComment = CommentMapper.toEntity(comment);

        commentRepository.save(createComment);
        return CommentMapper.toCreateResponse(createComment);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no se encuentra"));
        // Obtenemos el token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        // Obtenemos el id del user
        String idUserJwt = jwt.getSubject();
        // Parseamos id que trae el token
        long idUser = Long.parseLong(idUserJwt);
        // Comprobamos si es ADMIN
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        // Comprobamos si es el propietario del comentario
        boolean isOwner = comment.getIdUser() == idUser;
        if(!isOwner && !isAdmin){
            throw new RuntimeException("Fallo Forbidden");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentUpdatedResponse editComment(long idComment, CommentUpdateRequest comment) {
        Comment updateComment = commentRepository.findById(idComment)
                .orElseThrow(()-> new RuntimeException("no se encuentra"));
        // Obtenemos el token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        // Obtenemos el id del user
        String idUserJwt = jwt.getSubject();
        // Parseamos id que trae el token
        long idUser = Long.parseLong(idUserJwt);
        // Comprobamos si es ADMIN
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        // Comprobamos si es el propietario del comentario
        boolean isOwner = updateComment.getIdUser() == idUser;
        if(!isOwner && !isAdmin){
            throw new RuntimeException("Fallo Forbidden");
        }
        updateComment.setText(comment.getText());

        Comment saved = commentRepository.save(updateComment);

        return CommentMapper.toUpdateResponse(saved);
    }
}
