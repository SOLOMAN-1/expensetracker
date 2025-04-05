package com.ust.ExpenseTracker.repository;

import com.ust.ExpenseTracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmailId(String emailId);
}
