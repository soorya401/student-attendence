package com.example.feedbackapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
public class FeedbackAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedbackAppApplication.class, args);
    }

    @Entity
    @Table(name = "feedback")
    public static class Feedback {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        private String userName;

        @NotBlank
        private String message;

        public Feedback() {}

        public Feedback(String userName, String message) {
            this.userName = userName;
            this.message = message;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}

    @RestController
    @RequestMapping("/api/feedback")
    public static class FeedbackController {

        private final FeedbackRepository feedbackRepository;

        public FeedbackController(FeedbackRepository feedbackRepository) {
            this.feedbackRepository = feedbackRepository;
        }

        // Get all feedback
        @GetMapping
        public List<Feedback> getAllFeedback() {
            return feedbackRepository.findAll();
        }

        // Get feedback by ID
        @GetMapping("/{id}")
        public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
            Optional<Feedback> feedback = feedbackRepository.findById(id);
            return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        // Create new feedback
        @PostMapping
        public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
            Feedback savedFeedback = feedbackRepository.save(feedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
        }

        // Delete feedback by ID
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    // CommandLineRunner to load some initial data into the database (optional)
    @Bean
    public CommandLineRunner demo(FeedbackRepository repository) {
        return (args) -> {
            repository.save(new Feedback("John Doe", "Great app, loved it!"));
            repository.save(new Feedback("Jane Smith", "Needs improvement in UI."));
        };
    }
}

