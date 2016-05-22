package com.stardust.easyassess.pdm.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="User")
@Table(name="users")
public class User extends DataModel {

    private String name;

    private String status;

    private String username;

    private String password;

    private boolean canLaunchAssessment;

    private Set<Role> roles = new HashSet<Role>();

    public User() {

    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "can_launch_assessment")
    public boolean isCanLaunchAssessment() {
        return canLaunchAssessment;
    }

    public void setCanLaunchAssessment(boolean canLaunchAssessment) {
        this.canLaunchAssessment = canLaunchAssessment;
    }

    @ManyToMany(mappedBy = "users", fetch=FetchType.LAZY)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
