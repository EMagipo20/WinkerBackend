package com.upc.winkerbackend.Repositories;

import com.upc.winkerbackend.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p.ofertaEmpleo.id, COUNT(p.id) FROM Post p GROUP BY p.ofertaEmpleo.id")
    List<Object[]> countPostsPorOfertaEmpleo();
}
