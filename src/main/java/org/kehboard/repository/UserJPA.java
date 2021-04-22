package org.kehboard.repository;

import org.kehboard.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPA extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :login and u.password = :password")
    User selectUserByLoginAndPassword(@Param("login") String login,@Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User selectUserById(@Param("id") Integer id);

}

