package kr.jinsu.database.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private String dname; // (조인을 통해 조회된 값)

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
