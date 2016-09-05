package com.stardust.easyassess.pdm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "health_ministries")
public class HealthMinistry extends DataModel {
    private String name;

    private String type = "C";

    private String address;

    private String province;

    private String city;

    private String district;

    private String zipcode;

    private String status;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @JsonBackReference
    private HealthMinistry supervisor;

    @JsonManagedReference
    private List<HealthMinistry> ministries = new ArrayList<HealthMinistry>();

    private List<User> users = new ArrayList<User>();

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id", referencedColumnName="id")
    public HealthMinistry getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(HealthMinistry supervisor) {
        this.supervisor = supervisor;
    }

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy="supervisor",
               //orphanRemoval = true,
               cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    public List<HealthMinistry> getMinistries() {
        return ministries;
    }
    public void setMinistries(List<HealthMinistry> ministries) {
        this.ministries = ministries;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "ministries", fetch = FetchType.LAZY)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Transient
    public Long getSupervisorId() {
        if (getSupervisor() != null) {
            return getSupervisor().getId();
        }
        return new Long(-1);
    }

    @Transient
    public String getSupervisorName() {
        if (getSupervisor() != null) {
            return getSupervisor().getName();
        }
        return null;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
