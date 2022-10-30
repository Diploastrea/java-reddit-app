package com.greenfoxacademy.reddit.repository;

import com.greenfoxacademy.reddit.model.Post;
import com.greenfoxacademy.reddit.model.User;
import com.greenfoxacademy.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserAndPost(User user, Post post);
}
