package scr.shiyan4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class SelectFrame extends JFrame{
    static private int preJpanel = 1;
    static private String csvFilePath = "";

    private JPanel ipJpanel;
    private JPanel insertJpanel;
    private JPanel produceJpanel;
    private JPanel conJpanel;
    private JPanel changeJpanel;
    private JLabel blankJpanel;

    private JLabel exerciseNumberLabel;
    private JTextField exerciseNumberText;
    private JLabel numberLabel;
    private JLabel produceLabel;

    private JRadioButton addButton;
    private JRadioButton subButton;
    private JRadioButton mixButton;
    private ButtonGroup group;
    private JLabel insertLabel;

    private JButton produceExerciseButton;
    private JButton insertExerciseButton;

    public SelectFrame() {
        setSize(350, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("习题生成类型的选择");

        initUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        ipJpanel.setVisible(true);
        produceJpanel.setVisible(true);

        produceLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                if(preJpanel != 1){
                    produceLabel.setOpaque(true);
                    produceLabel.setBackground(new Color(174,238,238));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                produceLabel.setBackground(new Color(92,172,238));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                changeJpanel.removeAll();
                insertJpanel.repaint();
                changeJpanel.add(produceJpanel);
                changeJpanel.repaint();
                preJpanel = 1;
                insertLabel.setBackground(new Color(174,238,238));
            }
        });

        insertLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(preJpanel != 2){
                    insertLabel.setOpaque(true);
                    insertLabel.setBackground(new Color(174,238,238));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                insertLabel.setBackground(new Color(92,172,238));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                changeJpanel.removeAll();
                changeJpanel.repaint();
                changeJpanel.add(insertJpanel);
                changeJpanel.repaint();
                preJpanel = 2;
                produceLabel.setBackground(new Color(174,238,238));
            }
        });
        produceExerciseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(exerciseNumberText.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "请输入生成习题的数量", "提示框",JOptionPane.PLAIN_MESSAGE);
                }else{
                    if(addButton.isSelected()){
                        dispose();
                        new ExerciseFrame("add", (new Integer(exerciseNumberText.getText()))*10);
                    }else if(subButton.isSelected()){
                        dispose();
                        new ExerciseFrame("sub", (new Integer(exerciseNumberText.getText()))*10);
                    }else if(mixButton.isSelected()){
                        dispose();
                        new ExerciseFrame("mix", (new Integer(exerciseNumberText.getText()))*10);
                    }
                }
            }
        });
        insertExerciseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                insertExercise();
                if(!csvFilePath.isEmpty()){
                    //new InsertExercise(csvFilePath);
                }
            }
        });
    }

    private void insertExercise(){
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(0);// 设定只能选择到文件
        int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            return;// 撤销则返回
        } else {
            File file = jfc.getSelectedFile();// file为选择到的文件
            csvFilePath = file.getAbsolutePath().replaceAll("\\\\", "/");
        }

    }

    private void initUI() {
        conJpanel = new JPanel();
        conJpanel.setLayout(null);
        conJpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        conJpanel.setBounds(0, 0, 350, 300);
        setContentPane(conJpanel);

        changeJpanel = new JPanel();
        changeJpanel.setLayout(null);
        changeJpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        changeJpanel.setBounds(80, 0, 270, 300);

        produceJpanel = new JPanel();
        produceJpanel.setLayout(null);
        produceJpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        produceJpanel.setBounds(0, 0, 270, 300);

        ipJpanel = new JPanel();
        ipJpanel.setLayout(null);
        ipJpanel.setBounds(0, 0, 80, 300);

        insertJpanel = new JPanel();
        insertJpanel.setLayout(null);
        insertJpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        insertJpanel.setBounds(0, 0, 270, 300);

        conJpanel.add(changeJpanel);
        changeJpanel.add(produceJpanel);
        conJpanel.add(ipJpanel);

        //实例化组件
        blankJpanel = new JLabel();
        produceLabel = new JLabel(" 生成习题");
        insertLabel = new JLabel(" 导入习题");
        exerciseNumberLabel = new JLabel("生成习题的数量：");
        exerciseNumberText = new JTextField();
        numberLabel = new JLabel("×10");

        addButton = new JRadioButton("加法", true);
        subButton = new JRadioButton("减法");
        mixButton = new JRadioButton("混合");
        group = new ButtonGroup();

        produceExerciseButton = new JButton("生成习题");
        insertExerciseButton = new JButton("导入习题");

        //设置组件位置
        blankJpanel.setBounds(0, 100, 60, 250);
        produceLabel.setBounds(0, 0, 60, 50);
        insertLabel.setBounds(0, 50, 60, 50);
        exerciseNumberLabel.setBounds(40, 10, 110, 60);
        exerciseNumberText.setBounds(145, 30, 30, 20);
        numberLabel.setBounds(180, 20, 40, 40);
        addButton.setBounds(40, 80, 55, 40);
        subButton.setBounds(95, 80, 55, 40);
        mixButton.setBounds(150, 80, 55, 40);
        produceExerciseButton.setBounds(70, 150, 100, 50);
        insertExerciseButton.setBounds(70, 90, 100, 50);

        //标签设置颜色
        insertLabel.setOpaque(true);
        insertLabel.setBackground(new Color(174,238,238));
        produceLabel.setOpaque(true);
        produceLabel.setBackground(new Color(92,172,238));
        blankJpanel.setOpaque(true);
        blankJpanel.setBackground(new Color(174,238,238));

        ipJpanel.add(insertLabel);
        ipJpanel.add(produceLabel);
        ipJpanel.add(blankJpanel);
        produceJpanel.add(exerciseNumberLabel);
        produceJpanel.add(exerciseNumberText);
        produceJpanel.add(numberLabel);
        insertJpanel.add(insertExerciseButton);
        group.add(addButton);
        group.add(subButton);
        group.add(mixButton);
        produceJpanel.add(addButton);
        produceJpanel.add(subButton);
        produceJpanel.add(mixButton);
        produceJpanel.add(produceExerciseButton);
    }
    public static void main(String[] args) {
        new SelectFrame();
    }
}