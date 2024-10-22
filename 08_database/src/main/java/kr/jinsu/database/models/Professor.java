package kr.jinsu.database.models;

import lombok.Data;

@Data
public class Professor {
    private int profno;
    private String name;
    private String userid;
    private String position;
    private int sal;
    private String hiredate;
    private Integer comm;
    private int deptno;
}
