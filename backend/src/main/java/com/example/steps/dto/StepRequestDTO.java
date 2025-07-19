package com.example.steps.dto;

public class StepRequestDTO {
    private int stepNumber;
    private String instruction;

    public StepRequestDTO() {
    }
    public StepRequestDTO(int stepNumber, String instruction) {
        this.stepNumber = stepNumber;
        this.instruction = instruction;
    }

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


