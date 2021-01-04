package scr.cha7;

import scr.cha3.BinaryOperation_3_2;
import scr.cha3.ExerciseIOException;
import scr.cha3.Exercise_3_2_ch7;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.File;

public class PracticeGui1 extends JFrame {
    private static final long serialVersionUID = -639767039479761232L;
    static final int WINDOW_WIDTH = 550; //窗口宽度
    static final int WINDOW_HEIGHT = 400;  //窗口高度
    static final int OP_AMOUNT =20 ;  //窗口内显示的算式数量
    static final int OP_COLUMN = 4; //算式的列数
    static final int OP_WIDTH = 50; //算式的宽度
    static final int ANSWER_WIDTH = 35; //答案的宽度
    static final int COMPONET_HEIGHT = 25; //算式、答案的高度

    private JPanel contentPane;
    private JTextField [] tfOp; //显示的算式组件数组
    private JTextField [] tfAns; //显示的答案组件数组
    private JLabel labelStatus; //状态标签
    private Exercise_3_2_ch7 exercise;
    private boolean submitted;
    private JTextArea taStat;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PracticeGui1 frame = new PracticeGui1();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public PracticeGui1() {
        setResizable(false);  //窗体大小不可变

        setTitle("口算练习");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(1250, 70, WINDOW_WIDTH, WINDOW_HEIGHT);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JSeparator separator = new JSeparator();    //创建水平分隔符
        separator.setBounds(150, 270, 380, 2);
        contentPane.add(separator);

        //以下为 状态 和 统计信息 标签
        labelStatus = new JLabel("题目类型：加法习题   题目数量：24");
        labelStatus.setFont(new Font("宋体", Font.PLAIN, 15));
        labelStatus.setBounds(10, 30, 401, 25);
        contentPane.add(labelStatus);

        taStat = new JTextArea();
        taStat.setEditable(false);
        taStat.setBounds(20, 280, 505, 63);
        taStat.setFont(new Font("宋体",Font.PLAIN,14));
        taStat.setBackground(Color.lightGray);
        taStat.setText("统计信息：\n"+"\t正确题数：\t错误题数：\n\t正确率："+"\t错误率：");
        contentPane.add(taStat);


        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 564, 25);
        toolBar.setFloatable(false);
        contentPane.add(toolBar);
        JButton  btnin= new JButton("导入");
        toolBar.add(btnin);
        JButton btnOut = new JButton("导出");
        toolBar.add(btnOut);
        toolBar.addSeparator();
        JButton btnAdd = new JButton("加法习题");
        toolBar.add(btnAdd);
        JButton btnSub = new JButton("减法习题");
        toolBar.add(btnSub);
        JButton btnBin = new JButton("混合习题");
        toolBar.add(btnBin);

        //以下为工具栏的动作
        btnin.addActionListener(new ActionListener() { //导入题目
            public void actionPerformed(ActionEvent e) {
                impExercise();
            }
        });
        btnOut.addActionListener(new ActionListener() { //导出题目
            public void actionPerformed(ActionEvent e) {
                expExercise();
            }
        });
        btnAdd.addActionListener(new ActionListener() { //选择加法习题
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateAdditionExercise(length);
                updateComponets();
            }
        });
        btnSub.addActionListener(new ActionListener() { //选择减法习题
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateSubstractExercise(length);
                updateComponets();
            }
        });
        btnBin.addActionListener(new ActionListener() { //选择混合习题
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateBinaryExercise(length);
                updateComponets();
            }
        });

        //按钮选项
        JButton btnSubmit = new JButton("提交");
        btnSubmit.setBounds(30, 85, 90, 23);
        contentPane.add(btnSubmit);
        JButton btnGenrate = new JButton("重新生成");
        btnGenrate.setBounds(30, 115, 90, 23);
        contentPane.add(btnGenrate);
        JButton btnClear = new JButton("清空");
        btnClear.setBounds(30, 145, 90, 23);
        contentPane.add(btnClear);

        //按钮动作
        btnGenrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) { //重新生成题目 按钮动作
                generateExercise();
            }
        });
        btnClear.addActionListener(new ActionListener() { // 清空答案
            public void actionPerformed(ActionEvent e) {
                clearAnswers();
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //提交答案 按钮动作
                judgeAnswer();
            }
        });

        initComponets();
        updateComponets();
    }
    private void impExercise(){  //导入题目
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file=jfc.getSelectedFile();
        try{
            exercise = exercise.loadObject(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "导入题目成功！",
                    "提示",JOptionPane.INFORMATION_MESSAGE);
            this.submitted = false;
            updateComponets();
        }catch(NullPointerException npe){

        }catch(ExerciseIOException e){
            JOptionPane.showMessageDialog(null, "导入题目失败，可能是因为选择了错误的文件",
                    "错误",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void expExercise(){  //导出题目
        JFileChooser jfc = new JFileChooser();
        jfc.showSaveDialog(null);
        File file=jfc.getSelectedFile();
        try{
            exercise.saveObject(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "导出题目成功！",
                    "提示",JOptionPane.INFORMATION_MESSAGE);
        }catch(NullPointerException npe){

        }catch(ExerciseIOException e){
            JOptionPane.showMessageDialog(null, "导出题目失败，可能是因为创建文件失败",
                    "错误",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void generateExercise(){ //重新生成题目
        int length = exercise.length();
        exercise.generateWithFormerType(length);
        updateComponets();
    }
    private void judgeAnswer(){ //提交答案，判题
        this.submitted = true;
        getAnswers();
        updateComponets();
    }

    private void getAnswers(){
        for(int i=0; i<OP_AMOUNT; i++){
            exercise.setAnswer(i, tfAns[i].getText());
        }
    }
    private void clearAnswers(){
        exercise.clearAnswers();
        this.submitted = false;
        updateComponets();
    }
    private void initComponets(){
        this.submitted = false;
        exercise = new Exercise_3_2_ch7();
        exercise.generateAdditionExercise(OP_AMOUNT);

        tfOp = new JTextField[OP_AMOUNT];
        tfAns = new JTextField[OP_AMOUNT];
        for(int i=0; i<OP_AMOUNT; i++){
            tfOp[i] = new JTextField();
            tfOp[i].setBounds(150 + (i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
                    60 + (i/OP_COLUMN)*(COMPONET_HEIGHT+10),
                    OP_WIDTH,
                    COMPONET_HEIGHT);
            tfOp[i].setHorizontalAlignment(JTextField.RIGHT);
            tfOp[i].setEditable(false);
            contentPane.add(tfOp[i]);

            tfAns[i] = new JTextField();
            tfAns[i].setBounds(150+OP_WIDTH+(i%OP_COLUMN)*(OP_WIDTH+ANSWER_WIDTH+5),
                    60+(i/OP_COLUMN)*(COMPONET_HEIGHT+10),
                    ANSWER_WIDTH,
                    COMPONET_HEIGHT);
            contentPane.add(tfAns[i]);
        }
    }

    private void updateComponets(){
        BinaryOperation_3_2 op;
        for(int i=0; i<OP_AMOUNT; i++){
            op = exercise.getOperation(i);
            tfOp[i].setText(op.asString());
            if(!submitted){
                tfAns[i].setBackground(Color.WHITE);
            }else{
                if(exercise.getJudgement(i))
                    tfAns[i].setBackground(Color.green);
                else
                    tfAns[i].setBackground(Color.pink);
            }
            tfAns[i].setText(exercise.getAnswer(i));
            String curType="";
            switch(exercise.getCurrentType()){
                case ADD_AND_SUB:
                    curType = "混合习题";
                    break;
                case ADD_ONLY:
                    curType = "加法习题";
                    break;
                case SUB_ONLY:
                    curType = "减法习题";
            }
            labelStatus.setText("题目类型："
                    + curType  + "  题目数量:" + exercise.length());
            taStat.setText("统计信息：\n"+"\t正确题数：\t错误题数：\n\t正确率：\t错误率：");
            if(submitted){
                int total = exercise.length();
                int correct = exercise.Correct();
                int wrong = total - correct;
                int cratio = (int)((float)correct / total * 100);
                int wratio = 100 - cratio;
                taStat.setText("统计信息：\n" + "\t正确题数：" + correct + "\t错误题数：" + wrong
                        + "\n\t正确率：" +cratio+"%" +"\t错误率：" + wratio+"%") ;
            }
        }
    }
}