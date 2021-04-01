package org.kehboard.repository;

import org.kehboard.entity.db.Measure;
import org.kehboard.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureJPA extends JpaRepository<Measure, Long> {

}
