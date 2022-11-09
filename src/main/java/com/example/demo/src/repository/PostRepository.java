package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PostRepository extends JpaRepository<Post,Long> {
    public Post findByPostIdx(Long postIdx);

}
