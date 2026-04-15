package com.qma_microservices.quantity_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
public class QuantityMeasurementEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double firstValue;
    private String firstUnit;
    private String firstMeasurementType;
    
    private double secondValue;
    private String secondUnit;
    private String secondMeasurementType;
    
    private String operation;
    
    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;
    private String resultString;
    
    private boolean error;
    private String errorMessage;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    // Default constructor (required by JPA)
    public QuantityMeasurementEntity() {}
    
    // Parameterized constructor (if needed)
    public QuantityMeasurementEntity(double firstValue, String firstUnit, String firstMeasurementType,
                                     double secondValue, String secondUnit, String secondMeasurementType,
                                     String operation, double resultValue, String resultUnit, 
                                     String resultMeasurementType) {
        this.firstValue = firstValue;
        this.firstUnit = firstUnit;
        this.firstMeasurementType = firstMeasurementType;
        this.secondValue = secondValue;
        this.secondUnit = secondUnit;
        this.secondMeasurementType = secondMeasurementType;
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters (manually written)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public double getFirstValue() { return firstValue; }
    public void setFirstValue(double firstValue) { this.firstValue = firstValue; }
    
    public String getFirstUnit() { return firstUnit; }
    public void setFirstUnit(String firstUnit) { this.firstUnit = firstUnit; }
    
    public String getFirstMeasurementType() { return firstMeasurementType; }
    public void setFirstMeasurementType(String firstMeasurementType) { this.firstMeasurementType = firstMeasurementType; }
    
    public double getSecondValue() { return secondValue; }
    public void setSecondValue(double secondValue) { this.secondValue = secondValue; }
    
    public String getSecondUnit() { return secondUnit; }
    public void setSecondUnit(String secondUnit) { this.secondUnit = secondUnit; }
    
    public String getSecondMeasurementType() { return secondMeasurementType; }
    public void setSecondMeasurementType(String secondMeasurementType) { this.secondMeasurementType = secondMeasurementType; }
    
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    
    public double getResultValue() { return resultValue; }
    public void setResultValue(double resultValue) { this.resultValue = resultValue; }
    
    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }
    
    public String getResultMeasurementType() { return resultMeasurementType; }
    public void setResultMeasurementType(String resultMeasurementType) { this.resultMeasurementType = resultMeasurementType; }
    
    public String getResultString() { return resultString; }
    public void setResultString(String resultString) { this.resultString = resultString; }
    
    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // PrePersist callback to set createdAt automatically
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", resultValue=" + resultValue +
                ", error=" + error +
                '}';
    }
}