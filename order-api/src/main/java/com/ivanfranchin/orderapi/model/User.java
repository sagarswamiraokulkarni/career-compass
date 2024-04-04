package com.ivanfranchin.orderapi.model;

import com.ivanfranchin.orderapi.utils.CareerCompassUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
//, uniqueConstraints = {
//@UniqueConstraint(columnNames = "username"),
//@UniqueConstraint(columnNames = "email")
//}
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "verify_hash", nullable = false)
    private String verifyHash;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "phone_number")
    private String phoneNumber;


    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.createdAt= new Timestamp(System.currentTimeMillis());
        this.verifyHash= CareerCompassUtils.getInstance().generateUniqueHash();
        this.isVerified=true;
        this.role=role;
    }
}
