package scr.cha3;
//设计二
//使用者（如Exercise）必须决定是选择加法运算还是减法运算，不能像之前一样，含糊其辞
//因为BinaryOperation是抽象类，不能实例化

import java.io.Serializable;
import java.util.Random;

public abstract class BinaryOperation implements Serializable{
	private static final long serialVersionUID = -47405034845094801L;
	static final int UPPER = 100;
	static final int LOWER = 0;
	private int left_operand=0, right_operand=0;
	private char operator='+';
	private int value=0;

	protected void generateBinaryOperation(char anOperator) {
	    int left, right, result;
	    Random random = new Random();
		left = random.nextInt(UPPER+1);
		do {
			right = random.nextInt(UPPER+1);
			result = calculate(left,right);
		} while (!(checkingCalculation(result)));
		left_operand = left;
		right_operand = right;
		operator = anOperator;
		value = result;	
	}
	
	// 2015-8-3. 只有当参数构成了有效的算式，才能调用
	// 目前仅支持加法和减法
	private void unsafeConstructor(int left,int right, char anOperator){
		left_operand = left;
		right_operand = right;
		operator = anOperator;
		value = anOperator == '+'?left+right:left-right;
	}
	public void unsafeConstructor(int left,int right, int result, char anOperator){
		left_operand = left;
		right_operand = right;
		operator = anOperator;
		value = result;	
	}
	public void unsafeConstructor(String eqString){
 	int opPos=0;
 	int length=eqString.length();
 	// try to locate the position of the operator either '+' or '-'
 	opPos=eqString.indexOf("+");
 	if (opPos <= 0){
 		opPos=eqString.indexOf("-");
 	}
 	unsafeConstructor(Integer.parseInt(eqString.substring(0,opPos)),
 			Integer.parseInt(eqString.substring(opPos+1,length)), 
 					eqString.charAt(opPos));
 }
	
 abstract boolean checkingCalculation(int anInteger);
 abstract int calculate(int left, int right);
 
	public int getLeftOperand(){return left_operand;}
	public int getRightOperand(){return right_operand;}
	public char getOperator(){return operator;}
	public int getResult(){return value;}

	public boolean equals (BinaryOperation anOperation) {  // 要使用 getOperator()
		return 	left_operand == anOperation.getLeftOperand() &
				right_operand == anOperation.getRightOperand() &
				operator == anOperation.getOperator();			
	}
	public String toString(){
		return ""+left_operand+getOperator()+right_operand;
	}
	public String asString(){
		return toString()+"=";
	}
	public String fullString(){
		return toString()+"="+getResult();
	}  
}

