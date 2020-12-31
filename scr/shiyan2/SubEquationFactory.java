package scr.shiyan2;

public class SubEquationFactory implements EquationFactory {
   public Equation generateEquation() {
      Equation subEquation = new SubEquation();
      subEquation.generateEquation();
      return subEquation;
   }
}