package com.example.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Feedback {

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    @Size(max = 200)
    private String feedback;

    // Default constructor
    public Feedback() {
    }

    // Constructor with parameters
    public Feedback(int rating, String feedback) {
        this.rating = rating;
        this.feedback = feedback;
    }

    // Getters and Setters
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "rating=" + rating +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
