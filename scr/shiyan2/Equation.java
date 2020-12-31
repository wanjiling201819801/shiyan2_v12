package scr.shiyan2;

public interface Equation {
   int UPPER = 100;
   int LOWER = 0;


   int getEquOperand();             //生成操作数
   Boolean checkResultRange(int leftOperand,int rightOperand);      //检查结果范围
   int caculateResult();            //计算答案
   void construction(int leftOperand, int rightOperand, char operator);
   String equString();              //算式字符串
   String fullString();              //算式+答案字符串
   void generateEquation();              //生成符合要求的算式
   int getLeftOperand();
   char getOperator();
   int getRightOperand();
   int getStandardAnswer();
}