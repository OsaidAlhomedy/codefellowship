package com.d4.codefellowship.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String body;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    private ApplicationUser applicationUser;

    public Post() {
    }

    public Post(String body, ApplicationUser applicationUser) {
        this.body = body;
        this.applicationUser = applicationUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
