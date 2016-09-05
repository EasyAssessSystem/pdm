package com.stardust.easyassess.pdm.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="User")
@Table(name="users")
public class User extends DataModel {

    private String name;

    private String phone;

    private String status;

    private String username;

    private String password;

    private boolean canLaunchAssessment;

    private List<Role> roles = new ArrayList<Role>();

    private List<HealthMinistry> ministries = new ArrayList<HealthMinistry>();

    public User() {

    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns ={@JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id")
            })
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "users_ministries",
            joinColumns ={@JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "ministry_id")
            })
    public List<HealthMinistry> getMinistries() {
        return ministries;
    }

    public void setMinistries(List<HealthMinistry> ministries) {
        this.ministries = ministries;
    }
}
