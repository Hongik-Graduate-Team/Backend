package com.example.Namanba.user.repository;

import com.example.Namanba.common.enums.LoginType;
import com.example.Namanba.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndLoginType(String email, LoginType loginType);

    Optional<User> findByUserId(Long userId);
}
