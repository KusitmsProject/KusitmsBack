package com.example.demo.src.repository;


import com.example.demo.src.entity.Post;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
@Primary
public interface CalendarRepository extends JpaRepository<Post,Long>,CalendarRepositoryCustom {
}
