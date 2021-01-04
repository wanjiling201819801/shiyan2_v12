package scr.shiyan4;

import com.csvreader.CsvWriter;
import scr.shiyan2.Exercise;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ExerciseFrame extends JFrame {
    static final int OP_COLUMN = 5;
    static final int OP_WIDTH = 65;
    static final int ANSWER_WIDTH = 35;
    static final int COMPONET_HEIGHT = 25;
    static int wrong = 0;
    static int right = 0;
    static int finish = 0;
    static int unfinish = 0;
    static double wrongRate = 0.0;
    static double rightRate = 0.0;

    private ArrayList<String> exerciseList = new ArrayList<String>();
    private ArrayList<Integer> valuelist = new ArrayList<Integer>();

    private JPanel conPanel;
    private JPanel exercisePanel;
    private JPanel functionPanel;

    private JLabel [] exerciseLabel;
    private JTextField [] exerciseText;
    private JLabel functionLabel;

    private JButton keepButton;//保存习题
    private JButton btnGenrate;//重新生成习题
    private JButton backButton;//返回选择类型按钮
    private JButton judgeButton;//批改习题按钮

    private Exercise exercise;

    private void initExerciseComponets(String type, int OP_AMOUNT){
        exercise= new Exercise();
        if(type.equals("add")){
            exercise.generateAddExercise(OP_AMOUNT);
        }else if(type.equals("sub")){
            exercise.generateSubExercise(OP_AMOUNT);
        }else if(type.equals("mix")){
            exercise.generateMixExercise(OP_AMOUNT);
        }

        exerciseLabel = new JLabel[OP_AMOUNT];
        exerciseText = new JTextField[OP_AMOUNT];
        for(int i=0; i<OP_AMOUNT; i++){
            exerciseLabel[i] = new JLabel();
            exerciseLabel[i].setBounds(20 + (i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
                    20 + (i/OP_COLUMN)*(COMPONET_HEIGHT+10),
                    OP_WIDTH,
                    COMPONET_HEIGHT);
            exerciseLabel[i].setHorizontalAlignment(JTextField.RIGHT);
            exercisePanel.add(exerciseLabel[i]);

            exerciseText[i] = new JTextField();
            exerciseText[i].setBounds(20+OP_WIDTH+(i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
                    20+(i/OP_COLUMN)*(COMPONET_HEIGHT+10),
                    ANSWER_WIDTH,
                    COMPONET_HEIGHT);
            exercisePanel.add(exerciseText[i]);
        }
    }
    private void updateExerciseComponets(int OP_AMOUNT){
        valuelist.clear();
        exerciseList.clear();
        for(int i=0; i<OP_AMOUNT; i++){
            exerciseLabel[i].setText(exercise.getOperation(i));
            exerciseList.add(exercise.getOperation(i));
            valuelist.add(exercise.getResult(i));
            exerciseText[i].setBackground(Color.WHITE);
            exerciseText[i].setText("");
        }
        exercise.clearList();
    }
    private void judgeExercise(int OP_AMOUNT){

        for(int i = 0; i < OP_AMOUNT; i++){
            if(exerciseText[i].getText().isEmpty()) unfinish++;
            else finish++;
            if(exerciseText[i].getText().trim().equals(valuelist.get(i)+"")){
                exerciseText[i].setBackground(Color.green);
                right++;
            }else{
                exerciseText[i].setBackground(Color.RED);
                wrong++;
            }
        }
        rightRate = (double)right/OP_AMOUNT*100;
        wrongRate = 100-rightRate;
        //rightRate = (double)right/OP_AMOUNT*100;
    }
    public ExerciseFrame(String type, int OP_AMOUNT) {
        setTitle("习题练习");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((OP_WIDTH+ANSWER_WIDTH+5)*OP_COLUMN+55, (COMPONET_HEIGHT+10)*(OP_AMOUNT/OP_COLUMN)+160);
        setLocationRelativeTo(null);

        conPanel = new JPanel();
        conPanel.setLayout(null);
        conPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        conPanel.setBounds(0, 0, 580, (COMPONET_HEIGHT+10)*(OP_AMOUNT/OP_COLUMN)+160);
        setContentPane(conPanel);

        exercisePanel = new JPanel();
        exercisePanel.setLayout(null);
        exercisePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        exercisePanel.setBounds(0, 0, 580, (COMPONET_HEIGHT+10)*(OP_AMOUNT/OP_COLUMN)+160);
        conPanel.add(exercisePanel);

        functionPanel = new JPanel();
        functionPanel.setLayout(null);
        functionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        functionPanel.setBounds(180, (COMPONET_HEIGHT+10)*(OP_AMOUNT/OP_COLUMN)+20, 200, 100);
        functionPanel.setBackground(new Color(173,173,173));
        exercisePanel.add(functionPanel);

        functionLabel = new JLabel("功 能 区:");
        functionLabel.setBounds(120, (COMPONET_HEIGHT+10)*(OP_AMOUNT/OP_COLUMN)+50, 60, 25);
        exercisePanel.add(functionLabel);

        initUI();

        btnGenrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(type.equals("add")){
                    exercise.generateAddExercise(OP_AMOUNT);
                    updateExerciseComponets(OP_AMOUNT);
                }else if(type.equals("sub")){
                    exercise.generateSubExercise(OP_AMOUNT);
                    updateExerciseComponets(OP_AMOUNT);
                }else if(type.equals("mix")){
                    exercise.generateMixExercise(OP_AMOUNT);
                    updateExerciseComponets(OP_AMOUNT);
                }
            }
        });
        keepButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SelectFrame();
            }
        });
        judgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                judgeExercise(OP_AMOUNT);
                JOptionPane.showMessageDialog(null, "完成数量:"+finish+",未完成数量:"+unfinish+"\n正确个数:"+right+",错误个数:"+wrong+"\n正确率:"+String.format("%.1f", rightRate)+"%,错误率:"+String.format("%.1f", wrongRate)+"%", "批改结果",JOptionPane.PLAIN_MESSAGE);
                wrong = 0;right = 0;finish = 0;unfinish = 0;
                wrongRate = 0.0;rightRate = 0.0;
            }
        });

        btnGenrate.setBounds(10, 20, 120, 25);
        keepButton.setBounds(132, 20, 60, 25);
        backButton.setBounds(10, 55, 120, 25);
        judgeButton.setBounds(132, 55, 60, 25);
        functionPanel.add(btnGenrate);
        functionPanel.add(keepButton);
        functionPanel.add(backButton);
        functionPanel.add(judgeButton);

        initExerciseComponets(type, OP_AMOUNT);
        updateExerciseComponets(OP_AMOUNT);
        setResizable(false);
        setVisible(true);
    }
    public void saveFile() {
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();

        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "文本文件(*.csv)", "csv");
        chooser.setFileFilter(filter);

        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if(option==JFileChooser.APPROVE_OPTION){	//假如用户选择了保存
            File file = chooser.getSelectedFile();
            String fname = chooser.getName(file);	//从文件名输入框中获取文件名

            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if(fname.indexOf(".csv")==-1){
                file = new File(chooser.getCurrentDirectory(),fname+".csv");
            }
            try {
                // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
                CsvWriter csvWriter = new CsvWriter(file.getAbsolutePath().replaceAll("\\\\", "/"), ',', Charset.forName("gbk"));
                for (int i = 0; i < exerciseList.size(); i++) {
                    String[] csvContent = {exerciseList.get(i), valuelist.get(i)+""};
                    csvWriter.writeRecord(csvContent);
                    //if((i+1)%5 == 0) csvWriter.endRecord();
                }
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void initUI() {
        keepButton = new JButton("保存");
        btnGenrate = new JButton("重新生成习题");
        backButton = new JButton("返回习题设置");
        judgeButton = new JButton("批改");
    }
    public ExerciseFrame() {
    }
    public static void main(String[] args) {
        ExerciseFrame  ef= new ExerciseFrame();
    }
}