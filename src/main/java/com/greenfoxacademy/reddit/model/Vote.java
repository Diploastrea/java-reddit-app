package com.greenfoxacademy.reddit.model;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    private String vote;

    public Vote() {}

    public Vote(User user, Post post, String vote) {
        this.user = user;
        this.post = post;
        this.vote = vote;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getVote() {
        return this.vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}