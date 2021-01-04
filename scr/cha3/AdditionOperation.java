package scr.cha3;
//
@SuppressWarnings("serial")
public class AdditionOperation extends BinaryOperation {
	public AdditionOperation() {	
		generateBinaryOperation('+');		
	}
	public boolean checkingCalculation(int anInteger){
		return anInteger <= UPPER;
	}
	int calculate(int left, int right){
		return left+right;
	}
}