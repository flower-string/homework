import javax.swing.*;
import java.awt.*;

public class appJFrame extends JFrame {
    //创建全局变量，便于其他相关类的调用
    static JTextField search;

    public appJFrame() {
        //初始化界面
        initJFrame();
        //在该界面中添加内容
        initView();
        //让当前界面显示出来
        this.setVisible(true);
    }

    public void initJFrame() {
        //设置宽高
        this.setSize(600, 680);
        //设置标题
        this.setTitle("App功能页面");
        //设置关闭模式,可以扩展写各个关闭模式的不同
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置置顶
        this.setAlwaysOnTop(true);
        //取消内部默认布局
        this.setLayout(null);
    }

    public void initView() {
        //添加搜索提示
        JLabel searchPrompt = new JLabel(new ImageIcon("src/image/searchPrompt.png"));
        searchPrompt.setBounds(130, 50, 350, 50);
        //将搜索提示添加到界面上
        this.getContentPane().add(searchPrompt);
        //添加搜索输入框
        search = new JTextField();
        search.setBounds(205, 100, 200, 80);
        //设置字体格式
        search.setFont((new Font("", Font.BOLD, 22)));
        this.getContentPane().add(search);
        //添加开始搜索按钮
        JButton beginSearch = new JButton();
        beginSearch.setBounds(250, 300, 120, 30);
        beginSearch.setIcon(new ImageIcon("src/image/search.png"));
        //去除开始按钮的默认边框
        beginSearch.setBorderPainted(false);
        //去除开始按钮的默认背景
        beginSearch.setContentAreaFilled(false);
        //给开始按钮添加动作监听
        //可以扩展写各个监听的不同
        beginSearch.addActionListener(new beginSearchActionListener());
        //将开始搜素按钮添加到界面上
        this.getContentPane().add(beginSearch);
    }
}