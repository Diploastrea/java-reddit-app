package com.greenfoxacademy.reddit.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Integer karma;
    private String postDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Vote> votes;

    public Post() {
        this.karma = 0;
        this.postDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.karma = 0;
        this.postDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public void upvote() {
        this.karma++;
    }

    public void downvote() {
        this.karma--;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getKarma() {
        return this.karma;
    }

    public void setKarma(Integer votes) {
        this.karma = votes;
    }

    public String getPostDate() {
        return this.postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
