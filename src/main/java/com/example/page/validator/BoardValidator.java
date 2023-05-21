package com.example.page.validator;

import com.example.page.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {

    // 어떤 class를 validation 하게 지원할건지 true, false 로 리턴
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board  b = (Board)obj;
        //게시글의 값이 비어있는지 체크
        if(StringUtils.isEmpty(b.getContent())){
//            rejactValue(잘못된 필드 값, 키 값, 키 값이 없을때 띄어줄 메세지)
            errors.rejectValue("content","key", "내용을 입력하세요");
        }

    }
}
