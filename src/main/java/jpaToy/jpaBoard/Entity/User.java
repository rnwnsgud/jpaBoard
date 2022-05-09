package jpaToy.jpaBoard.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    private String username;
    @JsonIgnore
    private String password;
    private Boolean enabled;

    //==연관관계 메서드==//
    public void addUserRoles(UserRole userRole) {
        userRoles.add(userRole);
        userRole.setUser(this);
    }
}
