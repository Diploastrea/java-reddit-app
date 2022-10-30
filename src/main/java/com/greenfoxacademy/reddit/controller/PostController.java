package com.greenfoxacademy.reddit.controller;

import com.greenfoxacademy.reddit.model.Post;
import com.greenfoxacademy.reddit.service.PostService;
import com.greenfoxacademy.reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/page/{pageNumber}")
    public String getPage(Model model, @PathVariable(value = "pageNumber") int pageNumber, @RequestParam(value = "username", required = false) String username) {
        if (!this.userService.isLoggedIn(username)) return "redirect:/login";
        Page<Post> page = this.postService.findPaginated(pageNumber);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("posts", page.getContent().stream().distinct().toList());
        model.addAttribute("user", this.userService.getLoggedIn());
        return "index";
    }

    @GetMapping("/{id}/upvote")
    public String upvotePost(Model model, @PathVariable("id") Long id, @RequestParam("username") String username) {
        if (!this.userService.isLoggedIn(username)) return "redirect:/login";
        this.postService.upvotePost(id, username);
        model.addAttribute("user", this.userService.getLoggedIn());
        return "redirect:/page/" + this.postService.getCurrentPage() + "?username=" + username;
    }

    @GetMapping("/{id}/downvote")
    public String downvotePost(Model model, @PathVariable("id") Long id, @RequestParam("username") String username) {
        if (!this.userService.isLoggedIn(username)) return "redirect:/login";
        this.postService.downvotePost(id, username);
        model.addAttribute("user", this.userService.getLoggedIn());
        return "redirect:/page/" + this.postService.getCurrentPage() + "?username=" + username;
    }

    @GetMapping("/submit")
    public String getSubmitForm(Model model, @ModelAttribute Post post, @RequestParam("username") String username) {
        if (!this.userService.isLoggedIn(username)) return "redirect:/login";
        model.addAttribute("user", this.userService.getLoggedIn());
        return "submit";
    }

    @PostMapping("/submit")
    public String submitPost(@ModelAttribute Post post, @RequestParam("username") String username) {
        this.postService.addPost(post, username);
        return "redirect:/page/1?username=" + username;
    }
}