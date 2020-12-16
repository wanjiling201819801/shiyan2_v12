import java.util.*;

public class AddEquation implements Equation {
   private char operator;
   private int leftOperand;
   private int rightOperand;
   private int standardAnswer;

   @Override
   public int caculateResult() {
      return leftOperand+rightOperand;
   }

    //生成在[0,100]区间的操作数
   @Override
   public int getEquOperand() {
      Random random = new Random();
      int operand = random.nextInt(UPPER+1);
      return operand;
   }

   @Override
   public Boolean checkResultRange(int leftOperand,int rightOperand) {
      return leftOperand+rightOperand>100;
   }

   //赋值 并 计算参考答案
   @Override
   public void construction(int leftOperand, int rightOperand,char operator) {
      this.operator = operator;
      this.leftOperand = leftOperand;
      this.rightOperand = rightOperand;
      standardAnswer = caculateResult();
   }

   @Override
   public void generateEquation() {
      int left, right;
      left = getEquOperand();
      do{
         right = getEquOperand();
      }while(checkResultRange(left, right));
      construction(left, right, '+');
   }

   @Override
   public int getLeftOperand() {
      return leftOperand;
   }

   @Override
   public int getRightOperand() {
      return rightOperand;
   }

   @Override
   public String equString() {
      return ""+leftOperand+operator+rightOperand+"=";
   }

   @Override
   public String fullString() {
      return ""+leftOperand+operator+rightOperand+"="+standardAnswer;
   }

}