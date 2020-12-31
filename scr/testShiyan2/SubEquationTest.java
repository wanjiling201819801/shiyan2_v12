package scr.testShiyan2;

import org.junit.Before;
import org.junit.Test;
import scr.shiyan2.SubEquation;

import static org.junit.Assert.*;

public class SubEquationTest {
    SubEquation subequs;
    @Before
    public void init(){
        subequs = new SubEquation();
        subequs.generateEquation();
    }

    @Test
    public void checkResultRange() {
        System.out.println(subequs.checkResultRange(10,9));
        System.out.println(subequs.checkResultRange(10,19));
    }

    @Test
    public void fullString() {
        System.out.println(subequs.fullString());
    }
}