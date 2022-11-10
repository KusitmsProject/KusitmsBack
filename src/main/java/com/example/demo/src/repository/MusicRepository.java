package com.example.demo.src.repository;


import com.example.demo.src.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MusicRepository extends JpaRepository<Music,Long> {
    public Music findByTrack(String track);

}
