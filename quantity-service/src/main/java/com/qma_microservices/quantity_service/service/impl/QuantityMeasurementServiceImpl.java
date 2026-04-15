package com.qma_microservices.quantity_service.service.impl;

import com.qma_microservices.quantity_service.dto.QuantityDTO;
import com.qma_microservices.quantity_service.dto.QuantityMeasurementDTO;
import com.qma_microservices.quantity_service.entity.OperationType;
import com.qma_microservices.quantity_service.entity.QuantityMeasurementEntity;
import com.qma_microservices.quantity_service.exception.QuantityMeasurementException;
import com.qma_microservices.quantity_service.repository.QuantityMeasurementRepository;
import com.qma_microservices.quantity_service.service.IQuantityMeasurementService;
import com.qma_microservices.quantity_service.dto.QuantityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // Helper method to get IMeasurableUnit instance from QuantityDTO
    private QuantityDTO.IMeasurableUnit getUnit(QuantityDTO dto) {
        QuantityDTO.IMeasurableUnit unit = dto.getUnitEnum();
        if (unit == null) {
            logger.error("Failed to get unit instance for unit: {}, type: {}", dto.getUnit(), dto.getMeasurementType());
            throw new QuantityMeasurementException("Invalid unit: " + dto.getUnit() + " for type: " + dto.getMeasurementType());
        }
        return unit;
    }

    private double toBase(QuantityDTO dto) {
        return dto.convertToBaseUnit();
    }

    private double fromBase(double value, QuantityDTO dto) {
        return dto.convertFromBaseUnit(value);
    }

    private void validateSameType(QuantityDTO a, QuantityDTO b) {
        if (a == null || b == null) {
            throw new QuantityMeasurementException("Quantities cannot be null");
        }
        if (!a.getMeasurementType().equals(b.getMeasurementType())) {
            throw new QuantityMeasurementException("Different measurement types not allowed");
        }
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);
            double base1 = toBase(a);
            double base2 = toBase(b);
            boolean result = Double.compare(base1, base2) == 0;
            return saveAndReturn(a, b, OperationType.COMPARE,
                    String.valueOf(result), 0, null, null, false, null);
        } catch (Exception e) {
            logger.error("Error in compare operation", e);
            return saveError(a, b, OperationType.COMPARE, e);
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO source, QuantityDTO target) {
        try {
            validateSameType(source, target);
            
            double baseValue = toBase(source);
            double convertedValue = fromBase(baseValue, target);
            
            QuantityDTO result = new QuantityDTO(convertedValue, target.getUnit(), target.getMeasurementType());
            
            return saveAndReturn(source, target, OperationType.CONVERT,
                    null, convertedValue, target.getUnit(), target.getMeasurementType(), false, null);
        } catch (Exception e) {
            logger.error("Error in convert operation", e);
            return saveError(source, target, OperationType.CONVERT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);
            double base = toBase(a) + toBase(b);
            double result = fromBase(base, a);
            return saveAndReturn(a, b, OperationType.ADD,
                    null, result, a.getUnit(), a.getMeasurementType(), false, null);
        } catch (Exception e) {
            logger.error("Error in add operation", e);
            return saveError(a, b, OperationType.ADD, e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO targetUnit) {
        try {
            validateSameType(a, b);
            validateSameType(a, targetUnit);
            double base = toBase(a) + toBase(b);
            double result = fromBase(base, targetUnit);
            
            return saveAndReturn(a, b, OperationType.ADD,
                    null, result, targetUnit.getUnit(), targetUnit.getMeasurementType(), false, null);
        } catch (Exception e) {
            logger.error("Error in add with target unit operation", e);
            return saveError(a, b, OperationType.ADD, e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);
            double base = toBase(a) - toBase(b);
            double result = fromBase(base, a);
            return saveAndReturn(a, b, OperationType.SUBTRACT,
                    null, result, a.getUnit(), a.getMeasurementType(), false, null);
        } catch (Exception e) {
            logger.error("Error in subtract operation", e);
            return saveError(a, b, OperationType.SUBTRACT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO targetUnit) {
        try {
            validateSameType(a, b);
            validateSameType(a, targetUnit);
            double base = toBase(a) - toBase(b);
            double result = fromBase(base, targetUnit);
            
            return saveAndReturn(a, b, OperationType.SUBTRACT,
                    null, result, targetUnit.getUnit(), targetUnit.getMeasurementType(), false, null);
        } catch (Exception e) {
            logger.error("Error in subtract with target unit operation", e);
            return saveError(a, b, OperationType.SUBTRACT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO a, QuantityDTO b) {
        try {
            validateSameType(a, b);
            double base1 = toBase(a);
            double base2 = toBase(b);
            if (base2 == 0) {
                throw new ArithmeticException("Division by zero");
            }
            double result = base1 / base2;
            return saveAndReturn(a, b, OperationType.DIVIDE,
                    null, result, "DIMENSIONLESS", "Ratio", false, null);
        } catch (Exception e) {
            logger.error("Error in divide operation", e);
            return saveError(a, b, OperationType.DIVIDE, e);
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        try {
            List<QuantityMeasurementEntity> entities = repository.findByOperation(operation);
            return entities.stream()
                    .map(QuantityMeasurementDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching operation history for: {}", operation, e);
            throw new QuantityMeasurementException("Failed to fetch operation history");
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        try {
            List<QuantityMeasurementEntity> entities = repository.findByMeasurementType(measurementType);
            return entities.stream()
                    .map(QuantityMeasurementDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching measurements by type: {}", measurementType, e);
            throw new QuantityMeasurementException("Failed to fetch measurements by type");
        }
    }

    @Override
    public long getOperationCount(String operation) {
        try {
            return repository.countByOperation(operation);
        } catch (Exception e) {
            logger.error("Error counting operations for: {}", operation, e);
            throw new QuantityMeasurementException("Failed to count operations");
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        try {
            List<QuantityMeasurementEntity> entities = repository.findByErrorTrue();
            return entities.stream()
                    .map(QuantityMeasurementDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching error history", e);
            throw new QuantityMeasurementException("Failed to fetch error history");
        }
    }

    // ---------- SAVE METHODS ----------

    private QuantityMeasurementDTO saveAndReturn(
            QuantityDTO a,
            QuantityDTO b,
            OperationType op,
            String resultString,
            double resultValue,
            String resultUnit,
            String resultType,
            boolean error,
            String errorMsg) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setFirstValue(a.getValue());
        entity.setFirstUnit(a.getUnit());
        entity.setFirstMeasurementType(a.getMeasurementType());
        entity.setSecondValue(b.getValue());
        entity.setSecondUnit(b.getUnit());
        entity.setSecondMeasurementType(b.getMeasurementType());
        entity.setOperation(op.name());
        entity.setResultValue(resultValue);
        entity.setResultUnit(resultUnit);
        entity.setResultMeasurementType(resultType);
        entity.setResultString(resultString);
        entity.setError(error);
        entity.setErrorMessage(errorMsg);

        QuantityMeasurementEntity saved = repository.save(entity);
        return QuantityMeasurementDTO.from(saved);
    }

    private QuantityMeasurementDTO saveError(
            QuantityDTO a,
            QuantityDTO b,
            OperationType op,
            Exception e) {
        return saveAndReturn(a, b, op, null, 0, null, null, true, e.getMessage());
    }
}