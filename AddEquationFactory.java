public class AddEquationFactory implements EquationFactory {

   public AddEquation generateEquation() {
      AddEquation addEquation = new AddEquation();
      addEquation.generateEquation();
      return addEquation;
   }

}