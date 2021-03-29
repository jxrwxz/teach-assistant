package jxrwxz.teachassistant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE,force=true)
@RequiredArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private final String name;

    private final String password;

    private final String phoneNumber;

    private final String schoolName;

    private String profile;
}
