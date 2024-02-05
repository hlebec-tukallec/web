package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postPageGet(@PathVariable("id") String tmpId, Model model) {
        long id = parse(tmpId);
        if (postService.findById(id) != null) {
            model.addAttribute("comment", new Comment());
            model.addAttribute("postPage", postService.findById(id));
            return "PostPage";
        } else {
            return "NoSuchPost";
        }
    }

    public long parse(String id) {
        long ans = 0;
        try {
            ans = Long.parseLong(id);
            return ans;
        } catch (NumberFormatException e) {
            return ans;
        }
    }

    @PostMapping("/post/{id}")
    public String postComment(@Valid @ModelAttribute("comment") Comment comment,
                                BindingResult bindingResult,
                                HttpSession httpSession, @PathVariable("id") String id, Model model) {
        if (id == null || id.equals("")) {
            return "NoSuchPost";
        }
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        User user = (User) model.getAttribute("user");
        Post post = postService.findById(Long.parseLong(id));
        comment.setPost(post);
        comment.setUser(user);
        postService.save(comment);
        putMessage(httpSession, "You published new comment");

        return "redirect:/post/{id}";
    }

}
