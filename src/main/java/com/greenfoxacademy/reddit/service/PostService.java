package com.greenfoxacademy.reddit.service;

import com.greenfoxacademy.reddit.model.Post;
import com.greenfoxacademy.reddit.model.User;
import com.greenfoxacademy.reddit.model.Vote;
import com.greenfoxacademy.reddit.repository.PostRepository;
import com.greenfoxacademy.reddit.repository.UserRepository;
import com.greenfoxacademy.reddit.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private int currentPage = 1;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public void addPost(Post post, String username) {
        post.setUser(this.userRepository.findByUsername(username));
        this.postRepository.save(post);
    }

    public void upvotePost(Long id, String username) {
        User user = this.userRepository.findByUsername(username);
        Post post = this.postRepository.findById(id).get();
        Vote vote = this.voteRepository.findByUserAndPost(user, post);
        if (vote != null && vote.getVote().equals("upvote")) return;
        if (vote != null && vote.getVote().equals("notVoted")) {
            post.upvote();
            vote.setVote("upvote");
        } else if (vote != null && vote.getVote().equals("downvote")) {
            post.upvote();
            vote.setVote("notVoted");
        } else {
            vote = new Vote(user, post, "upvote");
            post.upvote();
        }
        this.voteRepository.save(vote);
        this.postRepository.save(post);
    }

    public void downvotePost(Long id, String username) {
        User user = this.userRepository.findByUsername(username);
        Post post = this.postRepository.findById(id).get();
        Vote vote = this.voteRepository.findByUserAndPost(user, post);
        if (vote != null && vote.getVote().equals("downvote")) return;
        if (vote != null && vote.getVote().equals("notVoted")) {
            post.downvote();
            vote.setVote("downvote");
        } else if (vote != null && vote.getVote().equals("upvote")) {
            post.downvote();
            vote.setVote("notVoted");
        } else {
            vote = new Vote(user, post, "downvote");
            post.downvote();
        }
        this.voteRepository.save(vote);
        this.postRepository.save(post);
    }

    public Page<Post> findPaginated(int pageNumber) {
        currentPage = pageNumber;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("votes").descending());
        return this.postRepository.findAll(pageable);
    }

    public int getCurrentPage() {
        return currentPage;
    }
}