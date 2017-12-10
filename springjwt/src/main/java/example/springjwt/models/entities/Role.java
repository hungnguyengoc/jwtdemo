package example.springjwt.models.entities;

import example.springjwt.models.entities.commons.BaseEntity;
import example.springjwt.models.entities.commons.RoleName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Column(name = "NAME")
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    public RoleName getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}