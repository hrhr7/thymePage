package com.example.page.controller;

import java.util.List;
import com.example.page.model.Board;
import com.example.page.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BoardApiController {

    private final BoardRepository repository;

    // 제목으로 검색
    @GetMapping("/Boards")
    List<Board> all(@RequestParam(required = false,defaultValue = "") String title,
                    @RequestParam(required = false,defaultValue = "") String content) {
        // 제목과 내용 찾는게 없으면 모두 조회
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll();
        } else{
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/Boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/Boards/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/Boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/Boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}