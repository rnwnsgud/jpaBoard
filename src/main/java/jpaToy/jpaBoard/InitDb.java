package jpaToy.jpaBoard;

import jpaToy.jpaBoard.Entity.Board;
import jpaToy.jpaBoard.Entity.Role;
import jpaToy.jpaBoard.Entity.RoleType;

import jpaToy.jpaBoard.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit() {

//            Board board = new Board();
//            board.setTitle("예제 제목");
//            board.setContent("예제 내용");
//
//
//            em.persist(board);
//
//            for (int i = 0; i <21; i++) {
//                Board board2 = new Board();
//                board2.setTitle("title "+i);
//                board2.setContent("content " + i);
//                em.persist(board2);
//            }
//
//            Role role = new Role();
//            role.setRoleType(RoleType.ROLE_ADMIN);
//            em.persist(role);



        }
    }
}
