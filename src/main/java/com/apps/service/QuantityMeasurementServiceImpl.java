package com.apps.service;

import com.apps.core.*;
import com.apps.dto.QuantityDTO;
import com.apps.dto.QuantityInputDTO;
import com.apps.dto.QuantityMeasurementDTO;
import com.apps.exception.QuantityMeasurementException;
import com.apps.model.QuantityMeasurementEntity;
import com.apps.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // ===== Convert DTO -> Model =====
    private Quantity<?> convertDtoToModel(QuantityDTO dto) {

        try {
            return new Quantity<>(dto.getValue(), LengthUnit.valueOf(dto.getUnit()));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(dto.getValue(), WeightUnit.valueOf(dto.getUnit()));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(dto.getValue(), VolumeUnit.valueOf(dto.getUnit()));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(dto.getValue(), TemperatureUnit.valueOf(dto.getUnit()));
        } catch (Exception ignored) {}

        throw new QuantityMeasurementException("Unsupported Unit: " + dto.getUnit());
    }

    // ===== Get Measurement Type =====
    private String getMeasurementType(Quantity<?> quantity) {
        if (quantity.getUnit() instanceof LengthUnit) return "LengthUnit";
        if (quantity.getUnit() instanceof WeightUnit) return "WeightUnit";
        if (quantity.getUnit() instanceof VolumeUnit) return "VolumeUnit";
        if (quantity.getUnit() instanceof TemperatureUnit) return "TemperatureUnit";
        return "Unknown";
    }

    // ===== Build Base DTO =====
    private QuantityMeasurementDTO buildBaseDTO(QuantityInputDTO input, Quantity<?> q1, Quantity<?> q2, String operation) {

        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.setThisValue(input.getThisQuantityDTO().getValue());
        dto.setThisUnit(input.getThisQuantityDTO().getUnit());
        dto.setThisMeasurementType(getMeasurementType(q1));

        dto.setThatValue(input.getThatQuantityDTO().getValue());
        dto.setThatUnit(input.getThatQuantityDTO().getUnit());
        dto.setThatMeasurementType(getMeasurementType(q2));

        dto.setOperation(operation);

        return dto;
    }

    private void saveToRepository(QuantityMeasurementDTO dto) {
        repository.save(dto.toEntity());
    }

    // ================= COMPARE =================
    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO input) {

        Quantity<?> q1 = convertDtoToModel(input.getThisQuantityDTO());
        Quantity<?> q2 = convertDtoToModel(input.getThatQuantityDTO());

        QuantityMeasurementDTO dto = buildBaseDTO(input, q1, q2, "compare");

        try {
            boolean result = q1.equals(q2);
            dto.setResultString(String.valueOf(result));
            dto.setError(false);
        } catch (Exception e) {
            dto.setErrorMessage(e.getMessage());
            dto.setError(true);
        }

        saveToRepository(dto);
        return dto;
    }

    // ================= CONVERT =================
    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {

        Quantity<?> q1 = convertDtoToModel(input.getThisQuantityDTO());
        Quantity<?> q2 = convertDtoToModel(input.getThatQuantityDTO());

        QuantityMeasurementDTO dto = buildBaseDTO(input, q1, q2, "convert");

        try {

            if (q1.getUnit() instanceof LengthUnit) {
                LengthUnit target = LengthUnit.valueOf(input.getThatQuantityDTO().getUnit());
                Quantity<LengthUnit> result = ((Quantity<LengthUnit>) q1).convertTo(target);
                dto.setResultValue(result.getValue());
                dto.setResultUnit(result.getUnit().toString());
                dto.setResultMeasurementType("LengthUnit");
            }

            dto.setError(false);

        } catch (Exception e) {
            dto.setErrorMessage(e.getMessage());
            dto.setError(true);
        }

        saveToRepository(dto);
        return dto;
    }

    // ================= ADD =================
    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO add(QuantityInputDTO input) {

        Quantity<?> q1 = convertDtoToModel(input.getThisQuantityDTO());
        Quantity<?> q2 = convertDtoToModel(input.getThatQuantityDTO());

        QuantityMeasurementDTO dto = buildBaseDTO(input, q1, q2, "add");

        try {

            if (!q1.getUnit().getClass().equals(q2.getUnit().getClass())) {
                throw new QuantityMeasurementException("Different measurement types");
            }

            Quantity result = ((Quantity) q1).add((Quantity) q2);

            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().toString());
            dto.setResultMeasurementType(getMeasurementType(q1));
            dto.setError(false);

        } catch (Exception e) {
            dto.setErrorMessage(e.getMessage());
            dto.setError(true);
        }

        saveToRepository(dto);
        return dto;
    }

    // ================= SUBTRACT =================
    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO subtract(QuantityInputDTO input) {

        Quantity<?> q1 = convertDtoToModel(input.getThisQuantityDTO());
        Quantity<?> q2 = convertDtoToModel(input.getThatQuantityDTO());

        QuantityMeasurementDTO dto = buildBaseDTO(input, q1, q2, "subtract");

        try {

            Quantity result = ((Quantity) q1).subtract((Quantity) q2);

            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().toString());
            dto.setResultMeasurementType(getMeasurementType(q1));
            dto.setError(false);

        } catch (Exception e) {
            dto.setErrorMessage(e.getMessage());
            dto.setError(true);
        }

        saveToRepository(dto);
        return dto;
    }

    // ================= DIVIDE =================
    @Override
    public QuantityMeasurementDTO divide(QuantityInputDTO input) {

        Quantity<?> q1 = convertDtoToModel(input.getThisQuantityDTO());
        Quantity<?> q2 = convertDtoToModel(input.getThatQuantityDTO());

        QuantityMeasurementDTO dto = buildBaseDTO(input, q1, q2, "divide");

        try {

            double result = ((Quantity) q1).divide((Quantity) q2);

            dto.setResultValue(result);
            dto.setResultMeasurementType(getMeasurementType(q1));
            dto.setError(false);

        } catch (Exception e) {
            dto.setErrorMessage("Divide Error: " + e.getMessage());
            dto.setError(true);
        }

        saveToRepository(dto);
        return dto;
    }

    // ================= HISTORY =================

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operation));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String type) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByThisMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByErrorTrue());
    }
}