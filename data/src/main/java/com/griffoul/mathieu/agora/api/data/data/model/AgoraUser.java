package com.griffoul.mathieu.agora.api.data.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "AgoraUser",
        uniqueConstraints = {
        @UniqueConstraint(name = "username_uk", columnNames = {"username"}),
                @UniqueConstraint(name = "mail_pk",columnNames = {"mail"})
        })
public class AgoraUser implements Comparable<AgoraUser> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String username;
    @Column
    private String mail;
    @Column
    private String password;
    @Column
    private String sessionHash;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }

    @Override
    public int compareTo(AgoraUser o) {
        if (this.getUsername().equalsIgnoreCase(o.getUsername())
                && this.getMail().equalsIgnoreCase(o.getMail())) {
            return 1;
        } else {
            return 2;
        }
    }
}
