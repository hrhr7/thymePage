package com.example.page.controller;

import com.example.page.model.Board;
import com.example.page.repository.BoardRepository;
import com.example.page.validator.BoardValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board") //경로 지정
@AllArgsConstructor
public class BoardController {

    private BoardRepository boardRepository;
    private BoardValidator boardValidator;

    @GetMapping("/list")
    //데이터 값 넘겨주고 싶을때 파라미터 부분에 model 넘겨줌
    public String list(Model model,@PageableDefault(size=2) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
//        Page<Board> boardList= boardRepository.findAll(PageRequest.of(0,20)); 하드코딩
        Page<Board> boardList= boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int startPage = Math.max(1,boardList.getPageable().getPageNumber()-4);
        int endPage = Math.min(boardList.getTotalPages(),boardList.getPageable().getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList",boardList);

        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id ==null){
            model.addAttribute("board",new Board());
        }else{
//            값이 없을때
            Board board = boardRepository.findById(id).orElse(null);//조회된 값 없으면 null
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    // input 데이터 받아서 저장하는 컨트롤러
    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult){
        boardValidator.validate(board,bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list"; // 여기서 키값 넘기는게 아니라 list로 이동해서 새로고침
    }
}
