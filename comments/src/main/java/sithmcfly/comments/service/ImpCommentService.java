package sithmcfly.comments.service;

import feign.FeignException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sithmcfly.comments.DTO.CommentDTO;
import sithmcfly.comments.DTO.MovieDTO;
import sithmcfly.comments.client.movieClient;
import sithmcfly.comments.entities.Comment;
import sithmcfly.comments.exception.CommentNotForbiddenException;
import sithmcfly.comments.exception.CommentNotFoundException;
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
                .orElseThrow(() -> new CommentNotFoundException(idComment));
        return CommentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentCreatedResponse createComment(String token, CommentCreateRequest comment) {
        MovieDTO movieExists = movieClient.getMovie(token, comment.getIdMovie());

        // Debo obtener el usuario que está autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userJwt = jwt.getSubject();
        long userIdJwt = Long.parseLong(userJwt);
        comment.setIdUser(userIdJwt);
        // Una vez añadido el userId mapeamos el comment
        Comment createComment = CommentMapper.toEntity(comment);

        commentRepository.save(createComment);
        return CommentMapper.toCreateResponse(createComment);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
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
            throw new CommentNotForbiddenException(id);
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
            throw new CommentNotForbiddenException(idComment);
        }

        updateComment.setText(comment.getText());

        Comment saved = commentRepository.save(updateComment);

        return CommentMapper.toUpdateResponse(saved);
    }

    @Override
    public List<CommentDTO> getCommentsByMovie (String token, long idMovie){

        try{
            MovieDTO movie = movieClient.getMovie(token, idMovie);
        } catch (FeignException.FeignClientException ex){
            throw new RuntimeException("La película no existe");
        }

        List<Comment> comment = commentRepository.findByIdMovieOrderByCreatedAtDesc(idMovie);

        return comment.stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
