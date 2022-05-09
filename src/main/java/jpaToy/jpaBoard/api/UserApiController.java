package jpaToy.jpaBoard.api;

import jpaToy.jpaBoard.Entity.Board;
import jpaToy.jpaBoard.Entity.Role;
import jpaToy.jpaBoard.Entity.User;
import jpaToy.jpaBoard.Entity.UserRole;
import jpaToy.jpaBoard.Repository.BoardRepository;
import jpaToy.jpaBoard.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;


    @Getter
    static class UserDto {
        private Long user_id;
        private String username;
        private List<BoardDto> boards;

        public UserDto(User user) {
            user_id = user.getId();
            username = user.getUsername();
            boards = user.getBoards().stream()
                    .map(board -> new BoardDto(board))
                    .collect(Collectors.toList());


        }
    }

    @Getter
    static class BoardDto {

        private Long board_id;
        private String title;
        private String content;

        public BoardDto(Board board) {
            board_id = board.getId();
            title = board.getTitle();
            content = board.getContent();
        }
    }

    @GetMapping("/api/users")
    public List<UserDto> all(@RequestParam(required = false) String method) {
        List<User> users = userRepository.findAll();

        List<UserDto> collect = users.stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/users/query")
    public List<User> allQuery(@RequestParam String method, @RequestParam String text) {
        List<User> users = null;
        if("query".equals(method)) {
            users = userRepository.findByUsernameQuery(text);
        } else if("nativeQuery".equals(method)) {
            users = userRepository.findByUsernameNativeQuery(text);
        }
        return users;
    }

    @GetMapping("/api/users/{id}")
    public User one(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }






}
