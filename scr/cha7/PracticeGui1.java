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
    static final int WINDOW_WIDTH = 550; //���ڿ��
    static final int WINDOW_HEIGHT = 400;  //���ڸ߶�
    static final int OP_AMOUNT =20 ;  //��������ʾ����ʽ����
    static final int OP_COLUMN = 4; //��ʽ������
    static final int OP_WIDTH = 50; //��ʽ�Ŀ��
    static final int ANSWER_WIDTH = 35; //�𰸵Ŀ��
    static final int COMPONET_HEIGHT = 25; //��ʽ���𰸵ĸ߶�

    private JPanel contentPane;
    private JTextField [] tfOp; //��ʾ����ʽ�������
    private JTextField [] tfAns; //��ʾ�Ĵ��������
    private JLabel labelStatus; //״̬��ǩ
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
        setResizable(false);  //�����С���ɱ�

        setTitle("������ϰ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(1250, 70, WINDOW_WIDTH, WINDOW_HEIGHT);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JSeparator separator = new JSeparator();    //����ˮƽ�ָ���
        separator.setBounds(150, 270, 380, 2);
        contentPane.add(separator);

        //����Ϊ ״̬ �� ͳ����Ϣ ��ǩ
        labelStatus = new JLabel("��Ŀ���ͣ��ӷ�ϰ��   ��Ŀ������24");
        labelStatus.setFont(new Font("����", Font.PLAIN, 15));
        labelStatus.setBounds(10, 30, 401, 25);
        contentPane.add(labelStatus);

        taStat = new JTextArea();
        taStat.setEditable(false);
        taStat.setBounds(20, 280, 505, 63);
        taStat.setFont(new Font("����",Font.PLAIN,14));
        taStat.setBackground(Color.lightGray);
        taStat.setText("ͳ����Ϣ��\n"+"\t��ȷ������\t����������\n\t��ȷ�ʣ�"+"\t�����ʣ�");
        contentPane.add(taStat);


        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 564, 25);
        toolBar.setFloatable(false);
        contentPane.add(toolBar);
        JButton  btnin= new JButton("����");
        toolBar.add(btnin);
        JButton btnOut = new JButton("����");
        toolBar.add(btnOut);
        toolBar.addSeparator();
        JButton btnAdd = new JButton("�ӷ�ϰ��");
        toolBar.add(btnAdd);
        JButton btnSub = new JButton("����ϰ��");
        toolBar.add(btnSub);
        JButton btnBin = new JButton("���ϰ��");
        toolBar.add(btnBin);

        //����Ϊ�������Ķ���
        btnin.addActionListener(new ActionListener() { //������Ŀ
            public void actionPerformed(ActionEvent e) {
                impExercise();
            }
        });
        btnOut.addActionListener(new ActionListener() { //������Ŀ
            public void actionPerformed(ActionEvent e) {
                expExercise();
            }
        });
        btnAdd.addActionListener(new ActionListener() { //ѡ��ӷ�ϰ��
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateAdditionExercise(length);
                updateComponets();
            }
        });
        btnSub.addActionListener(new ActionListener() { //ѡ�����ϰ��
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateSubstractExercise(length);
                updateComponets();
            }
        });
        btnBin.addActionListener(new ActionListener() { //ѡ����ϰ��
            public void actionPerformed(ActionEvent e) {
                int length = exercise.length();
                exercise.generateBinaryExercise(length);
                updateComponets();
            }
        });

        //��ťѡ��
        JButton btnSubmit = new JButton("�ύ");
        btnSubmit.setBounds(30, 85, 90, 23);
        contentPane.add(btnSubmit);
        JButton btnGenrate = new JButton("��������");
        btnGenrate.setBounds(30, 115, 90, 23);
        contentPane.add(btnGenrate);
        JButton btnClear = new JButton("���");
        btnClear.setBounds(30, 145, 90, 23);
        contentPane.add(btnClear);

        //��ť����
        btnGenrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) { //����������Ŀ ��ť����
                generateExercise();
            }
        });
        btnClear.addActionListener(new ActionListener() { // ��մ�
            public void actionPerformed(ActionEvent e) {
                clearAnswers();
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //�ύ�� ��ť����
                judgeAnswer();
            }
        });

        initComponets();
        updateComponets();
    }
    private void impExercise(){  //������Ŀ
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file=jfc.getSelectedFile();
        try{
            exercise = exercise.loadObject(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "������Ŀ�ɹ���",
                    "��ʾ",JOptionPane.INFORMATION_MESSAGE);
            this.submitted = false;
            updateComponets();
        }catch(NullPointerException npe){

        }catch(ExerciseIOException e){
            JOptionPane.showMessageDialog(null, "������Ŀʧ�ܣ���������Ϊѡ���˴�����ļ�",
                    "����",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void expExercise(){  //������Ŀ
        JFileChooser jfc = new JFileChooser();
        jfc.showSaveDialog(null);
        File file=jfc.getSelectedFile();
        try{
            exercise.saveObject(file.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "������Ŀ�ɹ���",
                    "��ʾ",JOptionPane.INFORMATION_MESSAGE);
        }catch(NullPointerException npe){

        }catch(ExerciseIOException e){
            JOptionPane.showMessageDialog(null, "������Ŀʧ�ܣ���������Ϊ�����ļ�ʧ��",
                    "����",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void generateExercise(){ //����������Ŀ
        int length = exercise.length();
        exercise.generateWithFormerType(length);
        updateComponets();
    }
    private void judgeAnswer(){ //�ύ�𰸣�����
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
                    curType = "���ϰ��";
                    break;
                case ADD_ONLY:
                    curType = "�ӷ�ϰ��";
                    break;
                case SUB_ONLY:
                    curType = "����ϰ��";
            }
            labelStatus.setText("��Ŀ���ͣ�"
                    + curType  + "  ��Ŀ����:" + exercise.length());
            taStat.setText("ͳ����Ϣ��\n"+"\t��ȷ������\t����������\n\t��ȷ�ʣ�\t�����ʣ�");
            if(submitted){
                int total = exercise.length();
                int correct = exercise.Correct();
                int wrong = total - correct;
                int cratio = (int)((float)correct / total * 100);
                int wratio = 100 - cratio;
                taStat.setText("ͳ����Ϣ��\n" + "\t��ȷ������" + correct + "\t����������" + wrong
                        + "\n\t��ȷ�ʣ�" +cratio+"%" +"\t�����ʣ�" + wratio+"%") ;
            }
        }
    }
}