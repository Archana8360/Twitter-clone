package com.example.Twitter.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    public Role(Integer roleId, String authority) {
        super();
        this.roleId = roleId;
        this.authority = authority;
    }

    public Role() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", authority='" + authority + '\'' +
                '}';
    }
}
