package com.filmbook.repositories;

import com.filmbook.model.database.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUserId(Long userId);
    @Query("SELECT u.userImage FROM users u WHERE u.username = ?1")
    byte[] findImageByUsername(String username);

    @Query("SELECT u.userImage FROM users u WHERE u.userId = ?1")
    byte[] findImageById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.userImage = ?2 WHERE u.userId = ?1")
    void insertUserImageById(Long id, byte[] img);

    @Modifying
    @Query("UPDATE users u SET u.userImage = NULL WHERE u.userId = ?1")
    void deleteUserImageById(Long id);


    @Query("SELECT u FROM users u WHERE " +
            "CONCAT(LOWER(u.username), LOWER(u.firstName), LOWER(u.lastName)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.username), UPPER(u.firstName), UPPER(u.lastName)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.firstName), ' ', LOWER(u.lastName)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.firstName), ' ', UPPER(u.lastName)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.firstName), ' ', LOWER(u.username)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.firstName), ' ', UPPER(u.username)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.lastName), ' ', LOWER(u.firstName)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.lastName), ' ', UPPER(u.firstName)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.lastName), ' ', LOWER(u.username)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.lastName), ' ', UPPER(u.username)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.username), ' ', LOWER(u.lastName)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.username), ' ', UPPER(u.lastName)) LIKE CONCAT('%', UPPER(:searchExpression), '%') OR " +
            "CONCAT(LOWER(u.username), ' ', LOWER(u.firstName)) LIKE CONCAT('%', LOWER(:searchExpression), '%') OR " +
            "CONCAT(UPPER(u.username), ' ', UPPER(u.firstName)) LIKE CONCAT('%', UPPER(:searchExpression), '%') " +
            "ORDER BY " +
            "CASE WHEN LOWER(u.firstName) LIKE CONCAT('%', LOWER(:searchExpression), '%') THEN 0 ELSE 1 END, " +
            "CASE WHEN LOWER(u.lastName) LIKE CONCAT('%', LOWER(:searchExpression), '%') THEN 0 ELSE 1 END, " +
            "CASE WHEN LOWER(u.username) LIKE CONCAT('%', LOWER(:searchExpression), '%') THEN 0 ELSE 1 END")


    List<User> findByUsernameOrFirstNameOrLastNameContaining(@Param("searchExpression") String searchExpression);



}
