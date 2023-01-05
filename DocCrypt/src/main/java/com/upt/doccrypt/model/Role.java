package com.upt.doccrypt.model;

import com.upt.doccrypt.model.user.Customer;
import com.upt.doccrypt.model.user.Notary;
import com.upt.doccrypt.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany
    private List<Notary> notaryList;

    @ManyToMany
    private List<Customer> customersList;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}
