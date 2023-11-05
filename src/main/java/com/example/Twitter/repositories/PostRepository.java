package com.example.Twitter.repositories;

import com.example.Twitter.models.ApplicationUser;
import com.example.Twitter.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Optional<Set<Post>> findByAuthor(ApplicationUser author);
}
