package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flendger.demo.store.demo.store.dto.CommentDto;
import ru.flendger.demo.store.demo.store.exeptions.UnauthorizedException;
import ru.flendger.demo.store.demo.store.services.CommentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public List<CommentDto> findAllByProductId(@PathVariable Long id) {
        return commentService.findAllByProductId(id);
    }

    @PostMapping
    public void save(Principal principal, @RequestBody CommentDto commentDto) {
        if (principal == null) {
            throw new UnauthorizedException("You must login");
        }

        commentService.save(commentDto, principal.getName());
    }
}
