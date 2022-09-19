package controller;
import com.sun.deploy.util.StringUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class UserInterface {
    private JFrame jf;
    DefaultTableModel model;
    private int peoNum;
    private int groNum;
    private int groLimit;
    List groAllocate;
    String path= "E:\\new.txt";
    //顶层窗口的创建和初始化

    protected void frameIni(){
        jf=new JFrame();
        jf.setSize(550, 500);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序

        windowIni();

        jf.setVisible(true);
    }

    //容器组件放置
    protected void setJf(JComponent component){
        jf.setContentPane(component);
    }
    //
    protected void windowIni(){
        Box box=Box.createVerticalBox();
        box.add(peoGroNumEnterPannel());
        box.add(showIni());
        setJf(box);
    }
    //创建一个JLable
    protected JLabel createJLable(String title){
        JLabel jla=new JLabel(title);
        jla.setPreferredSize(new Dimension(50, 50));//设置标签大小
        jla.setHorizontalAlignment(JLabel.CENTER);//设置内容居中对齐
        jla.setFont(new Font("宋体", Font.BOLD, 20));//设置字体大小
        jla.setBorder(BorderFactory.createLineBorder(Color.blue));//设置边框
        return jla;
    }

    //创建一个JTextField
    protected JTextField createJTextField(){
        JTextField jTextField=new JTextField();

        jTextField.setBackground(new Color(255, 255, 255));
        jTextField.setPreferredSize(new Dimension(150, 50));
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192,
                192));
        jTextField.setBorder(border);
        return jTextField;
    }

    //创建一个JButten
    protected JButton createJButton(String title){
        JButton button=new JButton(title);
        button.setPreferredSize(new Dimension(80,50));
        button.setFont(new Font("宋体", Font.BOLD, 20));//设置字体大小
        return button;
    }

    //中间容器——人数组数输入器
    protected Box peoGroNumEnterPannel(){
        JPanel jPanelPeoNum=new JPanel();
        jPanelPeoNum.add(createJLable("人数"));
        JTextField peo = createJTextField();
        jPanelPeoNum.add(peo);

        JPanel jPanelGroNum=new JPanel();
        jPanelGroNum.add(createJLable("组数"));
        JTextField gro=createJTextField();
        jPanelGroNum.add(gro);

        JButton button=createJButton("确认");

        //button监听事件
        button.addActionListener((actionEvent->{
            System.out.println(1);
            peoNum=Integer.parseInt(peo.getText());
            groNum=Integer.parseInt(gro.getText());
            try {
                groupAllocate();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAllocate();
        }));
        jPanelGroNum.add(button);

        //水平BOX
        Box hBox01 = Box.createHorizontalBox();
        hBox01.add(jPanelPeoNum);
        hBox01.add(jPanelGroNum);
        showIni();
        return hBox01;
    }

    //随机分组
    protected void groupAllocate() throws IOException {
        Random random=new Random();
        groAllocate=new ArrayList();
        groLimit=(peoNum/groNum);
        if(peoNum%groNum!=0){
            groLimit+=1;
        }
        for (int i=0;i<peoNum;i++)
        {
            int n= random.nextInt(groNum);
            while(Collections.frequency(groAllocate,n)>groLimit){
                n= random.nextInt(groNum);
            }
            groAllocate.add(n);
        }
        printWriterMethod(path,groAllocate.toString().replace(","," ").replace('[',' ').replace(']',' '));
    }

    //显示区域初始化
    protected JScrollPane showIni(){
        model = new DefaultTableModel();
        JTable table=new JTable(model);
        JScrollPane jsp = new JScrollPane(table); // 用列表创建可滚动的Panel，把这个Panel添加到窗口中
        return jsp;
    }

    //输出到文件
    public static void printWriterMethod(String filepath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filepath,true)) {
            fileWriter.append(content);
            fileWriter.append("\n");
        }
    }

    //显示分组信息
    protected void showAllocate(){

        Vector data = new Vector();//所有数据
        Vector label = new Vector();//列标
        label.add("组号");
        for(int i=0;i<groLimit;i++)label.add("组员");
        for (int i=0;i<groNum;i++)
        {
            Vector row = new Vector();
            row.add(i+1);
            for (int j=0;j<groAllocate.size();j++)
            {
                if (groAllocate.get(j).equals(i)){
                    row.add(j);
                }
            }
            data.add(row);
        }
        model.setDataVector(data, label);
    }
    public static void main(String[] args) {
        UserInterface userInterface=new UserInterface();
        userInterface.frameIni();
    }
}



