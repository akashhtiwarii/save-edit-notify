package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cd_create_edit_save.model.entity.User;

/**
 * Repository interface for {@link User} entity. Provides CRUD operations and
 * custom query methods for user data access.
 * 
 * @author Krishna
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}