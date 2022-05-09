package jpaToy.jpaBoard.service;

import jpaToy.jpaBoard.Entity.Board;
import jpaToy.jpaBoard.Entity.User;
import jpaToy.jpaBoard.Repository.BoardRepository;
import jpaToy.jpaBoard.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
