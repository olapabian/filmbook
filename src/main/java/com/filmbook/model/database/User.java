package com.filmbook.model.database;

import com.filmbook.model.database.ReviewInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "user_image")
    private byte[] userImage;

    @Column(name = "friends_ids")
    private String friendsIds; //oddzielone srednikiem czy cos


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ReviewInfo> reviews;

}
