package com.upt.doccrypt.model.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notary")
@Data
public class Notary extends User{

}
