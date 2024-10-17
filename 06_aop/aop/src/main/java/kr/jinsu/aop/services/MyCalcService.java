package kr.jinsu.aop.services;

public interface MyCalcService {
    /**
     * 두 개의 정수를 더하는 메서드
     * @param x 
     * @param y
     * @return int
     */
    public int plus(int x, int y);

    /**
     *  빼기
     * @param x
     * @param y
     * @return int
     */
    public int minus(int x, int y);
}
