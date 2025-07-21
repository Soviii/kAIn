package com.example.steps.dto;

public class StepResponseDTO {
    private Long id;
    private String instruction;
    private int step_number;

    public StepResponseDTO() {
    }

    public StepResponseDTO(Long id, String instruction, Integer step_number) {
        this.id = id;
        this.instruction = instruction;
        this.step_number = step_number;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getinstruction() {
        return instruction;
    }

    public void setinstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getstep_number() {
        return step_number;
    }

    public void setstep_number(Integer step_number) {
        this.step_number = step_number;
    }
}
