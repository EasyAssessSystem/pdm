package com.stardust.easyassess.pdm.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role extends DataModel {


    private long id;

    private String name;

    private Set<User> users = new HashSet<User>();

    public Role() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
                joinColumns ={@JoinColumn(name = "role_id", referencedColumnName = "id") },
                    inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")
                })
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
