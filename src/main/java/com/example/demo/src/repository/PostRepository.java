package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PostRepository extends JpaRepository<Post,Long> {
    public Post findByPostIdx(Long postIdx);

    List<Post> findAllByUserAndEmotion(User user, String emotion);

}
