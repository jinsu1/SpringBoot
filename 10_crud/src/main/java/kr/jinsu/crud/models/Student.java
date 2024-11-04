package kr.jinsu.crud.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Student {
    private int studno;
    private String name;
    private String userid;
    private int grade;
    private String idnum;
    private String birthdate;
    private String tel;
    private int height;
    private int weight;
    private int deptno;
    private Integer profno;

    private String dname;

    private String pname;

    
    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
    
}
