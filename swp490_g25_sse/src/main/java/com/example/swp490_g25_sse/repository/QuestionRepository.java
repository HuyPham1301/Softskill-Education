package com.example.swp490_g25_sse.repository;

import java.util.List;
import java.util.Optional;

import com.example.swp490_g25_sse.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // List<Question> findByUserId();

    List<Question> findByCourseId(long id);

    List<Question> findByCourseIdAndStudentId(long courseId, long studentId);

    Optional<Question> findFirstById(long id);
    
}
