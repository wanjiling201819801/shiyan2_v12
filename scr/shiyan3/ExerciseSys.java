package scr.shiyan3;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import scr.shiyan2.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExerciseSys {
    static final int COLUMN_NUMBER = 5;
    static int exerciseNumber=5;
    static int equationNumber=20;
    int savePointer = 0;

    private List<String> valuelist = new ArrayList<String>();           //用户答案
    private ArrayList<String> resultslist = new ArrayList<String>();         //标准答案
    private List<Equation> list = new ArrayList<Equation>();            //习题集（写入文件前）
    private List<String[]> listFile = new ArrayList<String[]>();        //习题集（以字符串形式读写文件的习题集）

    //将list数组中的习题集放进外存  路径1习题算式文件  路径2习题答案文件
    public  void writeCSV(String csvFilePath1, String csvFilePath2){
        try {
            CsvWriter csvWriter1 = new CsvWriter(csvFilePath1, ',', Charset.forName("gbk"));
            CsvWriter csvWriter2 = new CsvWriter(csvFilePath2, ',', Charset.forName("gbk"));
            String[] csvHeaders = { "第一列", "第二列", "第三列", "第四列", "第五列" };
            csvWriter1.writeRecord(csvHeaders);
            for (int i = 0; i < list.size(); i++) {
                Equation bo1 = list.get(i);
                String csvContent1 = bo1.getLeftOperand()+""+bo1.getOperator()+""+bo1.getRightOperand()+"=";
                String csvContent2 = bo1.getStandardAnswer()+"";
                csvWriter1.write(csvContent1);
                csvWriter2.write(csvContent2);
                if((i+1) % COLUMN_NUMBER == 0){
                    csvWriter1.endRecord();
                    csvWriter2.endRecord();
                }
            }
            csvWriter1.close();
            csvWriter2.close();
            list.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //生成多套加法习题保存到加法习题集文件夹中
    public  void writeCsvAdditionExercise(int exerciseNumber, int equationNumber){
        File file1 = new File("D://口算系统习题集//加法习题");
        if(!file1.exists())file1.mkdir();
        File file2 = new File("D://口算系统习题集//加法习题答案");
        if(!file2.exists())file2.mkdir();
        String csvFilePath1;
        String csvFilePath2;
        for(int j = exerciseNumber; j > 0; j--){
            Exercise addexe = new Exercise().generateAddExercise(equationNumber);
            list = addexe.getList();
            csvFilePath1 = "D://口算系统习题集//加法习题//第"+j+"套"+equationNumber+"道加法练习题.csv";
            csvFilePath2 = "D://口算系统习题集//加法习题答案//第"+j+"套"+equationNumber+"道加法练习题答案.csv";
            writeCSV(csvFilePath1, csvFilePath2);
        }
    }
    //生成多套减法习题保存到减法习题集文件夹中
    public  void writeCsvSubstractExercise(int exerciseNumber, int equationNumber){
        File file1 = new File("D://口算系统习题集//减法习题");
        if(!file1.exists())file1.mkdir();
        File file2 = new File("D://口算系统习题集//减法习题答案");
        if(!file2.exists())file2.mkdir();
        String csvFilePath1;
        String csvFilePath2;
        for(int j = exerciseNumber; j > 0; j--){
            Exercise subexe = new Exercise().generateSubExercise(equationNumber);
            list = subexe.getList();
            csvFilePath1 = "D://口算系统习题集//减法习题//第"+j+"套"+equationNumber+"道减法练习题.csv";
            csvFilePath2 = "D://口算系统习题集//减法习题答案//第"+j+"套"+equationNumber+"道减法练习题答案.csv";
            writeCSV(csvFilePath1,csvFilePath2);
        }
    }
    //生成多套混合习题保存到混合习题集文件夹中
    public  void writeCsvGenerateExercise(int exerciseNumber, int equationNumber){
        File file1 = new File("D://口算系统习题集//混合习题");
        if(!file1.exists())file1.mkdir();
        File file2 = new File("D://口算系统习题集//混合习题答案");
        if(!file2.exists())file2.mkdir();
        String csvFilePath1;
        String csvFilePath2;
        for(int j = exerciseNumber; j > 0; j--){
            Exercise binexe = new Exercise().generateMixExercise(equationNumber);
            list = binexe.getList();
            csvFilePath1 = "D://口算系统习题集//混合习题//第"+j+"套"+equationNumber+"道混合练习题.csv";
            csvFilePath2 = "D://口算系统习题集//混合习题答案//第"+j+"套"+equationNumber+"道混合练习题答案.csv";
            writeCSV(csvFilePath1, csvFilePath2);
        }
    }

    //判断生成习题集的类型
    public void typeWriteCsvExercise(String type,int exerciseNumber,int equationNumber){
        if(type.equals("加法")){
          writeCsvAdditionExercise(exerciseNumber,equationNumber);
        }else if(type.equals("减法")){
           writeCsvSubstractExercise(exerciseNumber,equationNumber);
        }else{
           writeCsvGenerateExercise(exerciseNumber,equationNumber);
        }
    }

    //读取习题集，用户写答案，并存入文件中 对练习题和练习用户答案进行操作
    //用户做选择的习题并保存答案
    @SuppressWarnings("null")
    public  void readCSV(String type, int number, int equationNumber){
        File file = new File("D://口算系统习题集//"+type+"习题用户答案");
        if(!file.exists()){
            file.mkdir();
        }
        String value;
        //文件夹路径
        String csvFilePath1 = "D://口算系统习题集//"+type+"习题//第"+number+"套"+equationNumber+"道"+type+"练习题.csv";
        String csvFilePath2 = "D://口算系统习题集//"+type+"习题用户答案//第"+number+"套"+equationNumber+"道"+type+"习题用户答案.csv";
        ArrayList<String[]> listFile = new ArrayList<String[]>();
        ArrayList<String> resultlist = new ArrayList<String>();
        @SuppressWarnings("resource")
        Scanner sc=new Scanner(System.in);
        try{
            //将文件夹中内容读到缓存中
            CsvReader reader = new CsvReader(csvFilePath1,',',Charset.forName("gbk"));
            reader.readHeaders();
            while(reader.readRecord()){
                String[] row = reader.getValues();
                listFile.add(row);
            }
            reader.close();
            //将缓存中的算式显示出来
            for(int i=0;i<listFile.size();i++){
                for(int j=0;j<listFile.get(i).length;j++){
                    String content = listFile.get(i)[j].toString();
                    resultlist.add(content);
                    System.out.print(content+" ");
                }
                System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0;i < equationNumber;i++){
            //用户输入答案，并存到缓存valueList数组中
            //输入save保存用户做一半的习题集
            System.out.print("第"+(i+1)+"道:");
            value = sc.next();
            if(value.equals("退出")) {
                System.out.println("是否保存");
                value=sc.next();
                if(value.equals("是")){
                    savePointer = i;    //记录写题的位置
                    break;
                }
            }else if(value != null || value.length() <= 0){
                valuelist.add(value);
            }else valuelist.add("");
        }
        try {
            //将用户答案写入文件2中
            CsvWriter csvWriter = new CsvWriter(csvFilePath2, ',', Charset.forName("gbk"));
            for (int i = 0; i < valuelist.size(); i++) {
                String csvContent = valuelist.get(i);
                csvWriter.write(csvContent);
                if((i+1) % COLUMN_NUMBER == 0)csvWriter.endRecord();
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CorrecteExercise(type, number, equationNumber);
    }

    //中途保存，下次就再做！ 读取习题集，用户写答案，并存入文件中 只是对用户答案文件操作
    public  void laterOpenCSV(String type, int number, int equationNumber){
        String value;
        String csvFilePath = "D://口算系统习题集//"+type+"习题//第"+number+"套"+equationNumber+"道"+type+"练习题.csv";
        String csvFilePath1 = "D://口算系统习题集//"+type+"习题用户答案//第"+number+"套"+equationNumber+"道"+type+"习题用户答案.csv";
        ArrayList<String> resultlist = new ArrayList<String>();
        @SuppressWarnings("resource")
        Scanner sc=new Scanner(System.in);
        try{
            listFile.clear();
            //读取路径文件下练习题算式到缓存listFile
            CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("gbk"));
            while(reader.readRecord()){
                String[] row = reader.getValues();
                listFile.add(row);
            }
            reader.close();

            //处理缓存文件，listFile存储习题答案
            for(int i=0;i<listFile.size();i++){
                for(int j=0;j<listFile.get(i).length;j++){
                    String content = listFile.get(i)[j].toString();
                    resultlist.add(content);
                    System.out.print(content+" ");
                }
                System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for(int i = savePointer;i < equationNumber;i++){
            //存储用户输入结果
            System.out.print("第"+(i+1)+"道:");
            value = sc.next();
            if(value != null || value.length() <= 0){
                valuelist.add(value);
            }else valuelist.add("");
        }
        try {
            //将用户文件写入用户答案文件中
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("gbk"));
            for (int i = 0; i < valuelist.size(); i++) {
                String csvContent = valuelist.get(i);
                csvWriter.write(csvContent);
                if((i+1) % COLUMN_NUMBER == 0)csvWriter.endRecord();
            }
            csvWriter.close();
            valuelist.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CorrecteExercise(type, number, equationNumber);
    }

    //批改文件，存放批改结果
    public void CorrecteExercise(String type, int number, int equationNumber){
        int wrong = 0;
        int right = 0;
        File file = new File("D://口算系统习题集//"+type+"习题批改结果");
        if(!file.exists()){
            file.mkdir();
        }
        String csvFilePath1 = "D://口算系统习题集//"+type+"习题答案//第"+number+"套"+equationNumber+"道"+type+"练习题答案.csv";
        String csvFilePath2 = "D://口算系统习题集//"+type+"习题批改结果//第"+number+"套"+equationNumber+"道"+type+"练习题批改结果.csv";
        try{
            //读取路径1的文件到缓存
            CsvReader reader = new CsvReader(csvFilePath1,',', Charset.forName("gbk"));
            while(reader.readRecord()){
                String[] row = reader.getValues();
                listFile.add(row);
            }
            reader.close();
            //对缓存文件进行处理，读到resultslist数组
            for(int i=0;i<listFile.size();i++){
                for(int j=0;j<listFile.get(i).length;j++){
                    String content = listFile.get(i)[j].toString();
                    resultslist.add(content);
                }
                System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i < valuelist.size(); i++){
            //标准答案和用户答案对比
            if(resultslist.get(i).equals(valuelist.get(i))){
                right++;
            }else{
                wrong++;
            }
        }
        try {
            //将批改结果放到文件2中保存
            CsvWriter csvWriter = new CsvWriter(csvFilePath2, ',', Charset.forName("gbk"));
            String[] csvHeaders = { "正确", "错误"};
            csvWriter.writeRecord(csvHeaders);
            for (int i = 0; i < valuelist.size(); i++) {
                String[] csvContent = { right+"", wrong+""};
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int number;
        String type;
        Scanner sc = new Scanner(System.in);
        ExerciseSys exerciseSys = new ExerciseSys();
        //生成不一样的多套套题，存放到外存
        //输入类型后，生成该类型的习题集
        System.out.print("请输入所选习题的类型：");//加法，减法，混合
        type = sc.next();
        exerciseSys.typeWriteCsvExercise(type,exerciseNumber,equationNumber);
        //选择想做的习题集编号
        System.out.print("请输入所选习题集的编号：");
        number = sc.nextInt();
        exerciseSys.readCSV(type,number,equationNumber);
        //中途退出保存后，重新打开做题
        System.out.println("是否选择继续上次的习题练习（是|否）");
        String str = sc.next();
        if(str.equals("是")){
            exerciseSys.laterOpenCSV(type,number,equationNumber);
        }
    }
}
