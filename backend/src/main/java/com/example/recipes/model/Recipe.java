// TODO REVIEW MODEL

package com.example.recipes.model;

import com.example.ingredients.model.Ingredient;
import com.example.steps.model.Step;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "recipes")
public class Recipe {

    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID recipe_uuid;

    private Long userId; // optional, if you're associating recipes with users

    private String title;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

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

    public UUID getRecipeUuid() {
        return recipe_uuid;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
