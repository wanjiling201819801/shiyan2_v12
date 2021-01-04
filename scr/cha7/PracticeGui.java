package scr.cha7;

import scr.cha3.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PracticeGui extends JFrame {
	static final int WINDOW_WIDTH = 580;
	static final int WINDOW_HEIGHT = 300;
	static final int OP_AMOUNT = 20;
	static final int OP_COLUMN = 5;
	static final int OP_WIDTH = 65;
	static final int ANSWER_WIDTH = 35;
	static final int COMPONET_HEIGHT = 25;
	
	private JPanel contentPane;
	private JTextField [] tfOp;
	private JTextField [] tfAns;
	private JTextArea taStat;
	
	private Exercise_3_2_ch7 exercises;
	private int correctAmount;
	private int wrongAmount;
	private float correctRatio;
	private float wrongRatio;
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticeGui frame = new PracticeGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void initExerciseComponets(){
		exercises = new Exercise_3_2_ch7();
		exercises.generateAdditionExercise(OP_AMOUNT);
		
		tfOp = new JTextField[OP_AMOUNT];
		tfAns = new JTextField[OP_AMOUNT];
		for(int i=0; i<OP_AMOUNT; i++){
			tfOp[i] = new JTextField();
			tfOp[i].setBounds(20 + (i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
					20 + (i/OP_COLUMN)*(COMPONET_HEIGHT+10),
					OP_WIDTH,
					COMPONET_HEIGHT);
			tfOp[i].setHorizontalAlignment(JTextField.RIGHT);
			tfOp[i].setEditable(false);
			contentPane.add(tfOp[i]);
			
			tfAns[i] = new JTextField();
			tfAns[i].setBounds(20+OP_WIDTH+(i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
					20+(i/OP_COLUMN)*(COMPONET_HEIGHT+10),
					ANSWER_WIDTH,
					COMPONET_HEIGHT);
			contentPane.add(tfAns[i]);
		}
	}
	private void updateExerciseComponets(){
		BinaryOperation_3_2 op;
		for(int i=0; i<OP_AMOUNT; i++){
			op = exercises.getOperation(i);
			tfOp[i].setText(op.asString());
			tfAns[i].setBackground(Color.WHITE);
			tfAns[i].setText("");
		}
		taStat.setText("统计信息：\n总题数："+OP_AMOUNT+"\t正确题数：\t错误题数：\n\t正确率：\t错误率：");
	}
	private void judge(){
		BinaryOperation_3_2 op;
		correctAmount = wrongAmount = 0;
		for(int i=0; i<OP_AMOUNT; i++){
			op = exercises.getOperation(i);
			String result = String.valueOf(op.getResult());
			String answer = tfAns[i].getText().trim();
			if(result.equals(answer)){
				tfAns[i].setBackground(Color.GREEN);
				correctAmount++;
			}else{
				tfAns[i].setBackground(Color.RED);
				wrongAmount++;
			}
		}
		correctRatio = (float)correctAmount / OP_AMOUNT;
		wrongRatio = 1 - correctRatio;
		taStat.setText("统计信息：\n总题数：" + OP_AMOUNT 
				+ "\t正确题数：" + correctAmount
				+ "\t错误题数：" + wrongAmount
				+ "\n\t正确率：" + String.format("%.0f", correctRatio*100)
				+ "%\t错误率：" + String.format("%.0f", wrongRatio*100) + "%") ;
	}
	public PracticeGui() {
		setTitle("\u53E3\u7B97\u7EC3\u4E60");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGenrate = new JButton("\u91CD\u65B0\u751F\u6210\u9898\u76EE");
		btnGenrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exercises.generateAdditionExercise(OP_AMOUNT);
				updateExerciseComponets();
			}
		});
		btnGenrate.setBounds(32, 168, 123, 23);
		contentPane.add(btnGenrate);
		
		JButton btnSubmit = new JButton("\u63D0\u4EA4\u7B54\u6848");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				judge();
			}
		});
		btnSubmit.setBounds(32, 208, 123, 23);
		contentPane.add(btnSubmit);
		
		taStat = new JTextArea();
		taStat.setEditable(false);
		taStat.setBounds(200, 168, 293, 63);
		taStat.setText("统计信息：\n总题数："+OP_AMOUNT+"\t正确题数：\t错误题数：\n\t正确率：\t错误率：");
		contentPane.add(taStat);
		
		initExerciseComponets();
		updateExerciseComponets();

		
	}
}
