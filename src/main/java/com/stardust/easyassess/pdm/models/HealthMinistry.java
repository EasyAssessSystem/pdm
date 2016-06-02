package com.stardust.easyassess.pdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "health_ministries")
public class HealthMinistry extends DataModel {
    private String name;

    private String type = "C";

    private String status;

    private HealthMinistry supervisor;

    private List<HealthMinistry> ministries = new ArrayList<HealthMinistry>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName="id")
    public HealthMinistry getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(HealthMinistry supervisor) {
        this.supervisor = supervisor;
    }

    @OneToMany(mappedBy="supervisor")
    public List<HealthMinistry> getMinistries() {
        return ministries;
    }

    public void setMinistries(List<HealthMinistry> ministries) {
        this.ministries = ministries;
    }
}
