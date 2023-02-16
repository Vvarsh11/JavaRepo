package com.capg.campsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capg.campsite.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
