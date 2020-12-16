import java.util.*;

public class Exercise {
   private static final int SIGN_NUM = 2;
   private static final int EQUATION_NUM = 50;
   private static final int COLUMN_NUM = 4;
   private static final int EQUA_SPACING = 15;

   private List<Equation> equsList = new ArrayList<Equation>();

   public static void main(String[] args) {
      Exercise anAddExercise = new Exercise();
      Exercise anSubExercise = new Exercise();
      Exercise anBinExercise = new Exercise();

      System.out.println("屏幕显示50道加法题集：");
      anAddExercise.generateAddExercise();
      anAddExercise.printExercise();

      System.out.println("\n屏幕显示50道减法题集：");
      anSubExercise.generateSubExercise();
      anSubExercise.printExercise();

      System.out.println("\n屏幕显示50道加减法混合题集：");
      anBinExercise.generateMixExercise();
      anBinExercise.printExercise();
   }

   public boolean isEqual(Equation anEqu,Equation equi) {
      return anEqu.getEquOperand()==equi.getEquOperand()&&
              ((anEqu.getLeftOperand()==equi.getLeftOperand()&&anEqu.getRightOperand()==equi.getRightOperand())
                      ||(anEqu.getRightOperand()==equi.getLeftOperand()&&anEqu.getLeftOperand()==equi.getRightOperand()));
   }


   public boolean occurIn(Equation anEqu) {
      for (Equation equi: equsList) {
         if(isEqual(anEqu,equi)){
            return  true;
         }
      }
      return false;
   }

   public void generateAddExercise() {
      Equation toExEqu;
      for(int i = 0; i< EQUATION_NUM; i++){
         toExEqu = new AddEquationFactory().generateEquation();
         if(!occurIn(toExEqu)){
            equsList.add(toExEqu);
         }else {
            i--;
         }
      }
   }

   public void generateSubExercise() {
      Equation toExEqu;
      for(int i = 0; i< EQUATION_NUM; i++){
         toExEqu = new SubEquationFactory().generateEquation();
         if(!occurIn(toExEqu)){
            equsList.add(toExEqu);
         }else {
            i--;
         }
      }
   }

   public void generateMixExercise() {
      Equation binEqu;
      for (int i = 0; i < EQUATION_NUM; i++) {
         Random random = new Random();
         int sign = random.nextInt(SIGN_NUM);
         if(0 == sign){
            binEqu =  new AddEquationFactory().generateEquation();
         }else {
            binEqu = new SubEquationFactory().generateEquation();
         }
         equsList.add(binEqu);
      }
   }

   //打印习题集
   public void printExercise(){
      //按一定要求打印
      System.out.println("*********************************习题*********************************");
      for (int i = 0; i < EQUATION_NUM; i++) {
         String string = equsList.get(i).equString();
         while(string.length()<=EQUA_SPACING){
            string += " ";
         }
         System.out.print(string);
         if((i+1)%COLUMN_NUM==0){
            System.out.println();
         }
      }
   }

   //显示参考答案
   public void showAnswer(){
      System.out.println("*******************************习题答案********************************");
      for (int i = 0; i < EQUATION_NUM; i++) {
         String string = equsList.get(i).fullString();
         while(string.length()<=EQUA_SPACING){
            string += " ";
         }
         System.out.print(string);
         if((i+1)%COLUMN_NUM==0){
            System.out.println();
         }
      }
   }

}