package sithmcfly.comments.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sithmcfly.comments.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
