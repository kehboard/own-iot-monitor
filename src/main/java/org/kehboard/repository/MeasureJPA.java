package org.kehboard.repository;

import org.kehboard.entity.db.Measure;
import org.kehboard.entity.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureJPA extends JpaRepository<Measure, Integer> {
    @Query("SELECT m FROM Measure m WHERE m.measureNameId =:mid")
    Page<Measure> getMeasureByMeasureNameId( @Param("mid") Integer measureNameId, Pageable pageable);
}
