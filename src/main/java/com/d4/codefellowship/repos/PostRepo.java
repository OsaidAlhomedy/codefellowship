package com.d4.codefellowship.repos;

import com.d4.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findAllByApplicationUserId(Long id);
}
