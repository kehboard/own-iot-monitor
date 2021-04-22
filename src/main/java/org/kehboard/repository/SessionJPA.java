package org.kehboard.repository;

import org.kehboard.entity.db.Session;
import org.kehboard.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionJPA extends JpaRepository<Session, Integer> {
    @Query("SELECT s FROM Session s WHERE s.token = :token AND s.expiredAt>=:time")
    Session selectSessionByToken(@Param("token") String token, @Param("time") Long unixTimeSeconds);
}
