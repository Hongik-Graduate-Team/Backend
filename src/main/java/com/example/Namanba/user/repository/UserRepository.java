package com.example.Namanba.user.repository;

import com.example.Namanba.common.enums.LoginType;
import com.example.Namanba.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndLoginType(String email, LoginType loginType);
}
