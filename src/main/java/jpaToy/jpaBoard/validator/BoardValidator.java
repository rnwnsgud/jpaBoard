package jpaToy.jpaBoard.validator;

import jpaToy.jpaBoard.Entity.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.isAssignableFrom(clazz);
        //Board == clazz
        //Board == subBoard 자식 클래스까지 커버
    }

    @Override
    public void validate(Object target, Errors errors) {
        Board board = (Board) target;
        if(!StringUtils.hasText(board.getContent())) {
            errors.rejectValue("content","contentNull", "내용을 입력하세요");
        }
    }
}
