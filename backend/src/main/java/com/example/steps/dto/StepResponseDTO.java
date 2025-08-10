package com.example.steps.dto;

public class StepResponseDTO {
    // Declare fields
    private Long id;
    private String instruction;
    private int stepNumber;

    // Default constructor
    public StepResponseDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public StepResponseDTO(Long id, String instruction, Integer stepNumber) {
        this.id = id;
        this.instruction = instruction;
        this.stepNumber = stepNumber;
    }

    // Getters and setters
    // These methods are used to access and modify the fields of the StepResponseDTO class
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }
}
