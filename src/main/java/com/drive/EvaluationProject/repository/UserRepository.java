package com.drive.EvaluationProject.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.drive.EvaluationProject.modal.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUsername(String username);
}
