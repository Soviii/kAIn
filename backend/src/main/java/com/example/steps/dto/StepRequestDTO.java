package com.example.steps.dto;

public class StepRequestDTO {
    // Declare fields
    private int stepNumber;
    private String instruction;

    // Default constructor
    public StepRequestDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public StepRequestDTO(int stepNumber, String instruction) {
        this.stepNumber = stepNumber;
        this.instruction = instruction;
    }

    // Getters and setters
    // These methods are used to access and modify the fields of the StepRequestDTO class
    public int getStepNumber() {
        return stepNumber;
    }
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}