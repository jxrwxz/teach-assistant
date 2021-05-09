package jxrwxz.teachassistant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @NotNull
    @Column(name="COURSE_ID")
    private Long courseId;

    @NotNull
    private String name;

    private String answer;

    @NotNull
    private Long teacherId;

    @NotNull
    private Integer serialNumber;

    @NotNull
    @Column(name="CREATED_AT")
    private Date createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
    }

    @NotNull
    @Column(name="EXPIRE_AT")
    private Date expireDate;

    public void addSerialNumber(){
        serialNumber++;
    }

    public Assignment(){}

    public Assignment(String name,
                      String answer,
                      Date expireDate,
                      Long courseId,
                      Long teacherId,
                      Integer serialNumber){
        this.name=name;
        this.answer=answer;
        this.expireDate=expireDate;
        this.courseId=courseId;
        this.teacherId=teacherId;
        this.serialNumber=serialNumber;
    }

}

