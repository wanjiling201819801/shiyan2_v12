package scr.shiyan3;import org.junit.Before;import org.junit.Test;import static org.junit.Assert.*;public class ExerciseSysTest {    ExerciseSys exesys = new ExerciseSys();    @Test    public void writeCSV() {        String csvFilePath1 = "D://口算系统习题集1.csv";        String csvFilePath2 = "D://口算系统习题集2.csv";        exesys.writeCSV(csvFilePath1,csvFilePath2);    }    @Test    public void writeCsvAdditionExercise() {        exesys.writeCsvAdditionExercise(3,9);    }    @Test    public void writeCsvSubstractExercise() {        exesys.writeCsvSubstractExercise(3,9);    }    @Test    public void writeCsvGenerateExercise() {        exesys.writeCsvGenerateExercise(3,9);    }    @Test    public void typeWriteCsvExercise() {        exesys.typeWriteCsvExercise("加法",5,10);    }    @Test    public void correcteExercise() {        exesys.CorrecteExercise("加法",3,9);    }}