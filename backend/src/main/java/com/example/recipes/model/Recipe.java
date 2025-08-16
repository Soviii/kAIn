package com.example.recipes.model;

import com.example.ingredients.model.Ingredient;
import com.example.steps.model.Step;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "recipes")
public class Recipe {
    // Declaring columns for the recipe table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    private Long userId;

    private String title;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    @Column(name = "message_count", nullable = false) // TODO: consult with other devs if this is ok
    private Long messageCount = 0L;

    // Constructors
    public Recipe() {}

    public Recipe(Long userId, String title, String description, List<Ingredient> ingredients, List<Step> steps) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters and Setters
    public Long getRecipeId() {         // used to get the recipe id
        return id;
    }

    public void setRecipeId(Long recipeId) {       // used to set the recipe id 
        this.id = recipeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {      // used to get the ingredients of the recipe
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {      // used to set the ingredients of the recipe
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {      // used to get the steps of the recipe
        return steps;
    }

    public void setSteps(List<Step> steps) {    // used to set the steps of the recipe
        this.steps = steps;
    }

    public void increaseMessageCount(Long numOfMsgs) {
        this.messageCount += numOfMsgs;
    }

    public Long getMessageCount() {
        return this.messageCount;
    }
}
