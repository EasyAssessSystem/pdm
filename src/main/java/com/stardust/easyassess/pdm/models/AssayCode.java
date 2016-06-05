package com.stardust.easyassess.pdm.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="assay_codes")
public class AssayCode extends DataModel {

    private String codeNumber;

    private String status;

    private CodeGroup group;

    private String name;

    private List<AssayCategory> categories = new ArrayList<AssayCategory>();

    public AssayCode() {

    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "code_number")
    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName="id")
    public CodeGroup getGroup() {
        return group;
    }

    public void setGroup(CodeGroup group) {
        this.group = group;
    }


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "assay_codes_categories",
            joinColumns ={@JoinColumn(name = "code_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id")
            })
    public List<AssayCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<AssayCategory> categories) {
        this.categories = categories;
    }

    @Transient
    public String getGroupName() {
        if (getGroup() != null) {
            return getGroup().getName();
        }
        return null;
    }
}
