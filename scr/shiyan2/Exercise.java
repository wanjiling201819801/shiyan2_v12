package scr.shiyan2;

import java.util.*;

public class Exercise {
   private static final int SIGN_NUM = 2;
   private static final int EQUATION_NUM = 50;
   public static final int COLUMN_NUM = 5;
   private static final int EQUA_SPACING = 15;
   private List<Equation> equsList = new ArrayList<Equation>();
   public Exercise(){}
   public List<Equation> getList() {
      return equsList;
   }
   public boolean isEqual(Equation anEqu, Equation equi) {
      return anEqu.getEquOperand() == equi.getEquOperand() &&
              ((anEqu.getLeftOperand() == equi.getLeftOperand() && anEqu.getRightOperand() == equi.getRightOperand())
                      || (anEqu.getRightOperand() == equi.getLeftOperand() && anEqu.getLeftOperand() == equi.getRightOperand()));
   }
   public boolean occurIn(Equation anEqu) {
      for (Equation equi : equsList) {
         if (isEqual(anEqu, equi)) {
            return true;
         }
      }
      return false;
   }

   public Exercise generateAddExercise(int equsnum) {
      Exercise anExercise = new Exercise();
      Equation toExEqu;
      for (int i = 0; i < equsnum; i++) {
         toExEqu = new AddEquationFactory().generateEquation();
         if (!occurIn(toExEqu)) {
            anExercise.equsList.add(toExEqu);
         } else {
            i--;
         }
      }
      return anExercise;
   }
   public Exercise generateSubExercise(int euqsnum) {
      Exercise anExercise = new Exercise();
      Equation toExEqu;
      for (int i = 0; i < euqsnum; i++) {
         toExEqu = new SubEquationFactory().generateEquation();
         if (!occurIn(toExEqu)) {
            anExercise.equsList.add(toExEqu);
         } else {
            i--;
         }
      }
      return anExercise;
   }
   public Exercise generateMixExercise(int euqsnum) {
      Exercise anExercise = new Exercise();
      Equation binEqu;
      for (int i = 0; i < euqsnum; i++) {
         Random random = new Random();
         int sign = random.nextInt(SIGN_NUM);
         if (0 == sign) {
            binEqu = new AddEquationFactory().generateEquation();
         } else {
            binEqu = new SubEquationFactory().generateEquation();
         }
         anExercise.equsList.add(binEqu);
      }
      return anExercise;
   }

   //打印习题集
   public void printExercise() {
      //按一定要求打印
      System.out.println("*********************************习题*********************************");
      for (int i = 0; i < EQUATION_NUM; i++) {
         String string = equsList.get(i).equString();
         while (string.length() <= EQUA_SPACING) {
            string += " ";
         }
         System.out.print(string);
         if ((i + 1) % COLUMN_NUM == 0) {
            System.out.println();
         }
      }
   }
   //显示参考答案
   public void showAnswer() {
      System.out.println("*******************************习题答案********************************");
      for (int i = 0; i < EQUATION_NUM; i++) {
         String string = equsList.get(i).fullString();
         while (string.length() <= EQUA_SPACING) {
            string += " ";
         }
         System.out.print(string);
         if ((i + 1) % COLUMN_NUM == 0) {
            System.out.println();
         }
      }
   }
}