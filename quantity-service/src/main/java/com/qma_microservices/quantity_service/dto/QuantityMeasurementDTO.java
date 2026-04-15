package com.qma_microservices.quantity_service.dto;

import com.qma_microservices.quantity_service.entity.QuantityMeasurementEntity;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuantityMeasurementDTO {
    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private String resultString;
    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String errorMessage;

    @JsonProperty("error")
    private boolean error;

    public QuantityMeasurementDTO() {
    }

    public double getThisValue() {
        return thisValue;
    }

    public void setThisValue(double thisValue) {
        this.thisValue = thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public void setThisUnit(String thisUnit) {
        this.thisUnit = thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public void setThisMeasurementType(String thisMeasurementType) {
        this.thisMeasurementType = thisMeasurementType;
    }

    public double getThatValue() {
        return thatValue;
    }

    public void setThatValue(double thatValue) {
        this.thatValue = thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public void setThatUnit(String thatUnit) {
        this.thatUnit = thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public void setThatMeasurementType(String thatMeasurementType) {
        this.thatMeasurementType = thatMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public void setResultMeasurementType(String resultMeasurementType) {
        this.resultMeasurementType = resultMeasurementType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    // Convert Entity to DTO
    public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
        if (entity == null)
            return null;

        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.thisValue = entity.getFirstValue();
        dto.thisUnit = entity.getFirstUnit();
        dto.thisMeasurementType = entity.getFirstMeasurementType();

        dto.thatValue = entity.getSecondValue();
        dto.thatUnit = entity.getSecondUnit();
        dto.thatMeasurementType = entity.getSecondMeasurementType();

        dto.operation = entity.getOperation();

        dto.resultString = entity.getResultString();
        dto.resultValue = entity.getResultValue();
        dto.resultUnit = entity.getResultUnit();
        dto.resultMeasurementType = entity.getResultMeasurementType();

        dto.errorMessage = entity.getErrorMessage();
        dto.error = entity.isError();

        return dto;
    }

    // Convert DTO to Entity
    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setFirstValue(this.thisValue);
        entity.setFirstUnit(this.thisUnit);
        entity.setFirstMeasurementType(this.thisMeasurementType);

        entity.setSecondValue(this.thatValue);
        entity.setSecondUnit(this.thatUnit);
        entity.setSecondMeasurementType(this.thatMeasurementType);

        entity.setOperation(this.operation);

        entity.setResultString(this.resultString);
        entity.setResultValue(this.resultValue);
        entity.setResultUnit(this.resultUnit);
        entity.setResultMeasurementType(this.resultMeasurementType);

        entity.setErrorMessage(this.errorMessage);
        entity.setError(this.error);

        return entity;
    }

    // Convert list of entities to list of DTOs
    public static List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::from)
                .collect(Collectors.toList());
    }

    // Convert list of DTOs to list of entities
    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
    }
}