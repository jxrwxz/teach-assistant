package jxrwxz.teachassistant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String schoolName;

    public Student(){}

    public Student(String name,String password,String phoneNumber,String schoolName){
        this.name=name;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.schoolName=schoolName;
    }
}
