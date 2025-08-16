package com.example.steps.dto;

import com.example.steps.model.Step;

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

    // convert Step model to StepDTO
    public StepRequestDTO(Step step) {
        this.stepNumber = step.getStepNumber();
        this.instruction = step.getInstruction();
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