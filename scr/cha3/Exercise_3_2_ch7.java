package scr.cha3;
/*
 * 设计二
 * 方案3：让Exercise含ArayList，提供next和hasNext，让其客户实现formateAndDisplay
 */
// import cbsc.cha4.OperationBase;
/*
 * 2015-8-4: add()
 */

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class Exercise_3_2_ch7 implements Serializable{
	private static final long serialVersionUID = -1622536020144679558L;
	class Answers implements Serializable{
		private static final long serialVersionUID = -7833709422448085208L;
		String content;
		boolean correct;
		public Answers(){content = ""; correct = false;}
		public Answers(String ans, boolean cr){
			content = ans;
			correct = cr;
		}
	}
	private ArrayList<BinaryOperation_3_2> operationList = new ArrayList<BinaryOperation_3_2>();
//	private List <String> answers = new ArrayList<>(); //第7章增加：用户填写的所有题目的答案
	private List <Answers> answers = new ArrayList<>(); //第7章增加：用户填写的所有题目的答案
	private int current=0; // only used for iterator
	private ExerciseType currentType; //第7章新添加题目类型，为保存用
	public ExerciseType getCurrentType() {
		return currentType;
	}
	public void setAnswer(int index, String ans){
		BinaryOperation_3_2 op;
		op = operationList.get(index);
		String result = String.valueOf(op.getResult());
		String tans = ans.trim();
		answers.set(index, new Answers(tans,result.equals(tans)));
	}
	public String getAnswer(int index){
		return answers.get(index).content;
	}
	public void clearAnswers(){
		for(int i=0; i<answers.size(); i++)
			answers.set(i,new Answers("",false));
	}
	public int Correct(){
		int count=0;
		for(int i=0; i<answers.size(); i++){
			if(answers.get(i).correct) count++;
		}
		return count;
	}
	private void setCurrentType(ExerciseType type) {
		this.currentType = type;
	}
	public boolean getJudgement(int index){
		return answers.get(index).correct;
	}
	
	private BinaryOperation_3_2 generateOperation(){ 
		Random random = new Random();
		int opValue = random.nextInt(2);
		if (opValue == 1){ 
			return new AdditionOperation();
		}
		return new SubstractOperation();
	}
	public void saveObject(String filename) throws ExerciseIOException{ //串行化存储对象
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExerciseIOException("存储对象失败");
		}		
	}
	public static Exercise_3_2_ch7 loadObject(String filename) throws ExerciseIOException{  //串行化载入对象
		Exercise_3_2_ch7 exercise = null;
		try{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			exercise = (Exercise_3_2_ch7)ois.readObject();
			ois.close();
			fis.close();
		}catch(Exception e){
			throw new ExerciseIOException("载入对象失败");
		}
		return exercise;
	}
	//以下函数为第7章新添加，不改变原类型，但是可以改变数量，产生一批新的题目
	public void generateWithFormerType(int operationCount){
		switch(currentType){
		case ADD_AND_SUB:
			this.generateBinaryExercise(operationCount);
			break;
		case ADD_ONLY:
			this.generateAdditionExercise(operationCount);
			break;
		case SUB_ONLY:
			this.generateSubstractExercise(operationCount);
			break;
		}
	}
	public void generateAdditionExercise( int operationCount){
		BinaryOperation_3_2 anOperation;
/*
 * 根据第7章内容添加部分代码
 */		setCurrentType(ExerciseType.ADD_ONLY); //设置题目类型
		operationList.clear();    //先清空再生成
		answers.clear();
		while (operationCount > 0 ){
			do {anOperation = new AdditionOperation();
			}while (operationList.contains(anOperation));
			operationList.add(anOperation);
			answers.add(new Answers("",false));
			 //System.out.println("count="+ operationList.size());
			operationCount--;
		}
	}
	public void generateBinaryExercise(int operationCount){
		BinaryOperation_3_2 anOperation;
		/*
		 * 根据第7章内容添加部分代码
		 */
		setCurrentType(ExerciseType.ADD_AND_SUB); //设置题目类型
		operationList.clear();    //先清空再生成
		answers.clear();
		while (operationCount > 0 ){
			do{anOperation = generateOperation();
			}while (operationList.contains(anOperation));
			operationList.add(anOperation);
			answers.add(new Answers("",false));
			operationCount--;
		}
	}
	public void generateSubstractExercise(int operationCount){
		BinaryOperation_3_2 anOperation;
		/*
		 * 根据第7章内容添加部分代码
		 */
		setCurrentType(ExerciseType.SUB_ONLY); //设置题目类型
		operationList.clear();    //先清空再生成
		answers.clear();
		while (operationCount > 0 ){
			do{anOperation = new SubstractOperation();
			}while (operationList.contains(anOperation));
			operationList.add(anOperation);
			answers.add(new Answers("",false));
			operationCount--;
		}
	}
	// --- 2015-8-4: begin
	public void add(BinaryOperation_3_2 anOperation){
		operationList.add(anOperation);
	}
	public boolean contains(BinaryOperation_3_2 anOperation){
		return operationList.contains(anOperation);
	}
	public int length(){
		return operationList.size();
	}
	// write Exercise in a file, each Operation as "3+5"
	public void writeExercise(){
		File wfile = new File("eq2.txt");
		try{
			Writer out = new FileWriter(wfile, true);
			for (BinaryOperation_3_2 op: operationList){
				out.write(op.toString()+",");
			}
			out.flush();
			out.close();
		}
		catch(IOException e){
			System.out.println("ERROR: "+e);
		}
	}
	public void writeCSVExercise(File aFile){
		try{
			Writer out = new FileWriter(aFile, true);
			for (BinaryOperation_3_2 op: operationList){
				out.write(op.toString()+",");
			}
			out.flush();
			out.close();
		}
		catch(IOException e){
			System.out.println("ERROR: "+e);
		}
	}
	// read in a file each  as "3+5", and convert to Operation， add in Exercise
	public Exercise_3_2_ch7 readCSVExercise(){
		Exercise_3_2_ch7 exercise = new Exercise_3_2_ch7();
		String eqString;
		BinaryOperation_3_2 op;
		Scanner sc = null;
		File rfile = new File("eq2.txt");
		try{
        	sc = new Scanner(rfile);
        	sc.useDelimiter(",\\n");
        	
       	 	while(sc.hasNext()){
       	 		eqString = sc.next();
       	 		op = new AdditionOperation();
       	 		op.unsafeConstructor(eqString);
        		exercise.add(op);
        	}
        }
		catch(IOException e){
			System.out.println("ERROR: "+e);
		}
		
		return exercise;
	}
	public Exercise_3_2_ch7 readCSVExercise(File aFile){
		Exercise_3_2_ch7 exercise = new Exercise_3_2_ch7();
		String eqString;
		BinaryOperation_3_2 op;
		try{
			Scanner sc = new Scanner(aFile);
        	sc.useDelimiter(",");
        	
       	 	while(sc.hasNext()){
       	 		// 处理任意的\t，\f， \n等
       	 		eqString = sc.next().replaceAll("\\s", "");
       	 		op = new AdditionOperation();
       	 		op.unsafeConstructor(eqString);
        		exercise.add(op);
        	}
        }
		catch(IOException e){
			System.out.println("ERROR: "+e);
		}
		
		return exercise;
	}
	// ---- 2015-8-4: ----- end 
	
	// 下面两个方法用于实现遍历数据
	public boolean hasNext(){ 		// 若有元素返回true，否则返回false，
		return current <= operationList.size()-1;
	}
	public BinaryOperation_3_2 next(){		// 若有元素返回当前元素，移动到一个
		return operationList.get(current++);
	}
	public void printCurrent(){
		System.out.println("current="+current);
	}
	//根据第7章的需求新添加的一种获取元素的方法
	public BinaryOperation_3_2 getOperation(int index){
		if(index < operationList.size()) return operationList.get(index);
		else return null;
	}
	// for test
	public void all(){
		for (BinaryOperation_3_2 op:operationList){
			System.out.println(op.asString());
		}
	}
	// for test
	public void writeResults(File aFile){
		try{
			Writer out = new FileWriter(aFile, true);
			for (BinaryOperation_3_2 op: operationList){
				out.write(op.getResult()+",");
			}
			out.flush();
			out.close();
		}
		catch(IOException e){
			System.out.println("ERROR: "+e);
		}		
	}
	// 方式2：使用ArrayList自带的Iterator,这像是一个适配器模式！
	public Iterator<BinaryOperation_3_2> iterator(){
		return operationList.iterator();
	}
	/*
	public void generateAdditionExerciseFromBase(int operationCount){
		OperationBase base = new OperationBase();
		BinaryOperation_3_2 anOperation;
		while (operationCount > 0 ){
			do {anOperation = new AdditionOperation();
			}while (operationList.contains(anOperation));
			operationList.add(anOperation);
			// System.out.println("count="+ operationList.size());
			operationCount--;
		}
	}
	*/
}
