package org.kehboard.repository;

import org.kehboard.entity.db.Measure;
import org.kehboard.entity.db.MeasureName;
import org.kehboard.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
@Repository

public interface MeasureNameJPA extends JpaRepository<MeasureName, Integer> {
    @Query("SELECT m FROM MeasureName m WHERE m.devId=:devId")
    List<MeasureName> getMeasureNamesByDevId(@Param("devId") Integer devId);
    @Query("SELECT m FROM MeasureName m WHERE m.devId=:devId AND m.id=:mid")
    MeasureName getMeasureNamesByDevIdAndId(@Param("devId") Integer devId,@Param("mid") Integer mid);

    @Query("SELECT new map (m.id as id, m.iotName as iotName ) FROM MeasureName m WHERE m.devId = :devId AND m.userId=:userId")
    List<Map<String,Object>> getIotNamesAndIdByDevIdAndUserId(@Param("devId") Integer devId, @Param("userId") Integer userId);
}
