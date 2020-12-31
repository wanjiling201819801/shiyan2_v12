package scr.testShiyan2;

import org.junit.Before;
import scr.shiyan2.AddEquation;

import static org.junit.Assert.*;

public class AddEquationTest {
    AddEquation addequs;
    @Before
    public void init (){
        addequs = new AddEquation();
        addequs.generateEquation();
    }

    @org.junit.Test
    public void getLeftOperand() {
        int left = addequs.getLeftOperand();
        System.out.println(left);
    }

    @org.junit.Test
    public void getRightOperand() {
        int right = addequs.getRightOperand();
        System.out.println(right);
    }

    @org.junit.Test
    public void getOperator() {
        char op = addequs.getOperator();
        System.out.println(op);
    }

    @org.junit.Test
    public void getStandardAnswer() {
        int ans = addequs.getStandardAnswer();
        System.out.println(ans);
    }

    @org.junit.Test
    public void caculateResult() {
        int ans = addequs.caculateResult();
        System.out.println(ans);
    }

    @org.junit.Test
    public void getEquOperand() {
        int ops = addequs.getEquOperand();
        System.out.println(ops);
    }

    @org.junit.Test
    public void checkResultRange() {
        System.out.println(addequs.checkResultRange(100,99));
        System.out.println(addequs.checkResultRange(1,99));
    }

    @org.junit.Test
    public void construction() {
        addequs.construction(10,9,'+');
        System.out.println(addequs.fullString());
    }

    @org.junit.Test
    public void generateEquation() {
        addequs.generateEquation();
    }

    @org.junit.Test
    public void equString() {
        System.out.println(addequs.equString());
    }

    @org.junit.Test
    public void fullString() {
        System.out.println(addequs.fullString());
    }
}