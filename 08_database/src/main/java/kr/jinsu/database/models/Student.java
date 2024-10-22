package kr.jinsu.database.models;

import lombok.Data;

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
    private int profno;
}
