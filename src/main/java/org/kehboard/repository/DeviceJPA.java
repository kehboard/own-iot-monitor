package org.kehboard.repository;

import org.kehboard.entity.db.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceJPA extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM Device d WHERE d.apiKey =:apiKey")
    Device getDeviceByApiKey(@Param("apiKey") String apiKey);

    @Query("SELECT d FROM Device d WHERE d.userId =: userId")
}
