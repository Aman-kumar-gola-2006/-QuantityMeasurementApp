package com.apps.repository;

import com.apps.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    // ✅ FIXED (error → correct)
    long countByOperationAndErrorFalse(String operation);

    // ✅ FIXED
    List<QuantityMeasurementEntity> findByErrorTrue();
}