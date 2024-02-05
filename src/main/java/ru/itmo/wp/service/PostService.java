package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.*;
import ru.itmo.wp.repository.*;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    /** @noinspection FieldCanBeLocal, unused */
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;

        for (Tag tag : tagRepository.findAllByOrderByIdDesc()) {
            if (tagRepository.countByName(tag.getName()) == 0) {
                tagRepository.save(new Tag(tag.getName()));
            }
        }
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
