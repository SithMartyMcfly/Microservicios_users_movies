package sithmcfly.comments.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sithmcfly.comments.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdMovieOrderByCreatedAtDesc (long idMovie);

}
