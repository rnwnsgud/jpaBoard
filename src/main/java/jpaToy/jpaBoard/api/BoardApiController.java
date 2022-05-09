package jpaToy.jpaBoard.api;

import jpaToy.jpaBoard.Entity.Board;
import jpaToy.jpaBoard.Repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository boardRepository;


    @Data
    @AllArgsConstructor
    static class UpdateBoardResponse {
        private Long id;
        private String title;
    }

    @Data
    static class BoardRequest {
        @NotNull
        @Size(min = 2, max = 30)
        private String title;

        @NotNull
        private String content;
    }

    @Data
    static class CreateBoardResponse {
        private Long id;
        public CreateBoardResponse(Long id) {
            this.id = id;
        }
    }


    @GetMapping("/boards")
    public List<Board> all(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String content) {
        if(!StringUtils.hasText(title) && !StringUtils.hasText(content)) {
            return boardRepository.findAll();
        } else {
            return boardRepository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    public CreateBoardResponse createBoard(@RequestBody @Valid BoardRequest request) {

        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());

        Board save = boardRepository.save(board);
        Long id = save.getId();

        return new CreateBoardResponse(id);

    }

    //single item

    @GetMapping("/boards/{id}")
    public Board findBoard(@PathVariable Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    @Transactional
    public UpdateBoardResponse updateBoard(@RequestBody @Valid BoardRequest request, @PathVariable Long id) {
        Board findOne = boardRepository.findById(id).get();
        findOne.setTitle(request.getTitle());
        findOne.setContent(request.getContent());
//        Board save = boardRepository.save(findOne);
        Board updateOne = boardRepository.findById(id).get();
        return new UpdateBoardResponse(updateOne.getId(), updateOne.getTitle());
//        return new UpdateBoardResponse(save.getId(), save.getTitle());

    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}
