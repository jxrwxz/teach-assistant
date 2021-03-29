package jxrwxz.teachassistant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE,force=true)
public class Assignment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @NotNull
    private Long courseId;

    @NotNull
    private String name;

    private String answer;

    @NotNull
    private Integer serialNumber;

}

