package scr.cha3;

public class SubstractOperation extends BinaryOperation_3_2 {	
	public SubstractOperation() {	
		generateBinaryOperation('-');		
	}	
	public boolean checkingCalculation(int anInteger){
		return anInteger >= LOWER;
	}
	int calculate(int left, int right){
		return left-right;
	}		
}