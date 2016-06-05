package com.stardust.easyassess.pdm.models;

import javax.persistence.*;

@Entity
@Table(name="assay_code_groups")
public class CodeGroup extends DataModel {

    private String name;

    private String status;

    public CodeGroup() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
