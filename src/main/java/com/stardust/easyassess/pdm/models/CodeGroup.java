package com.stardust.easyassess.pdm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="assay_code_groups")
public class CodeGroup extends DataModel {

    private String name;

    private String status;

    private List<AssayCode> codes = new ArrayList<AssayCode>();

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy="group",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    public List<AssayCode> getCodes() {
        return codes;
    }

    public void setCodes(List<AssayCode> codes) {
        this.codes = codes;
    }

}
