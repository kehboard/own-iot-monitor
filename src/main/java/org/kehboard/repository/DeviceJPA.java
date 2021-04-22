package org.kehboard.repository;

import org.kehboard.entity.db.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceJPA extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM Device d WHERE d.apiKey =:apiKey")
    Device getDeviceByApiKey(@Param("apiKey") String apiKey);

    @Query("SELECT d FROM Device d WHERE d.userId=:userId")
    List<Device> getDevicesByUserId(@Param("userId") Integer userId);

    @Query("SELECT d FROM Device d WHERE d.userId=:userId AND d.id=:id")
    Device getDeviceByIdAndUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    @Query("select d from Device d WHERE d.id=:id")
    Device getDeviceById(@Param("id") Integer id);

    @Query("UPDATE Device AS d SET d.apiKey=:apikey WHERE d.id=:id")
    Device updateDeviceApiKeyById(@Param("apikey") String apikey,@Param("id") Integer id);
}
