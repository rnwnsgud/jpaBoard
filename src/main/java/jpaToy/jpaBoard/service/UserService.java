package jpaToy.jpaBoard.service;

import jpaToy.jpaBoard.Entity.Role;
import jpaToy.jpaBoard.Entity.RoleType;
import jpaToy.jpaBoard.Entity.User;
import jpaToy.jpaBoard.Entity.UserRole;
import jpaToy.jpaBoard.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    @Transactional
    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1L);

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        em.persist(userRole);
        userRole.setUser(user);
//        user.addUserRoles(userRole);


        return userRepository.save(user);
    }
}
