package com.qma_microservices.quantity_service.repository;

import com.qma_microservices.quantity_service.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    
    // Method names should match entity field names
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Custom query for measurement type
    @Query("SELECT e FROM QuantityMeasurementEntity e WHERE e.firstMeasurementType = :type OR e.secondMeasurementType = :type")
    List<QuantityMeasurementEntity> findByMeasurementType(@Param("type") String measurementType);
    
    Long countByOperation(String operation);
    
    List<QuantityMeasurementEntity> findByErrorTrue();
}