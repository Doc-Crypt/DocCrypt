package com.upt.doccrypt.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "registration_notary")
@Data
public class NotaryCandidate extends BaseEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "prove_document")
    private byte[] proveDocument;

    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUpdated(new Date());
        user.setCreated(new Date());
        user.setStatus(Status.ACTIVE);
        return user;
        //ecaterina
    }
}
