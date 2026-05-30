package com.laundry.entity;

import com.laundry.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {

    public Employee(
            Business business,
            String username,
            String password,
            String fullName
    ) {
        this.business = business;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = Role.EMPLOYEE;
    }
}