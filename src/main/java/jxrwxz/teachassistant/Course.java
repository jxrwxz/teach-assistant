package jxrwxz.teachassistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    @NotNull
    private String name;

    @NotNull
    private String teacherName;

    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
    }




}
