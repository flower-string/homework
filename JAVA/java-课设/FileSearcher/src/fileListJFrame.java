import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class fileListJFrame extends JFrame {
    public fileListJFrame() {
        //初始化界面
        initJFrame();
        //在该界面中添加内容
        initView();
        //让当前界面显示出来
        this.setVisible(true);
    }

    public void initJFrame() {
        //设置宽高
        this.setSize(1600, 1600);
        //设置标题
        this.setTitle("文件展示页面");
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置置顶
        this.setAlwaysOnTop(true);
        //取消内部默认布局
        this.setLayout(null);
    }

    private void initView() {
        //得到输入框输入的内容内容
        String context = appJFrame.search.getText();
        //创建集合接收所有符合搜索条件文件汇总的集合
        ArrayList<String> printList = new ArrayList<>();
        printList = fileSearch.fileSearch(context, printList);
        int i = 0;
        if (printList.size() == 1) {
            //创建文件进入到文件父目录的按钮
            JButton fileName = new JButton(printList.get(0));
            fileName.setBounds(400, 400, 800, 50);
            //去除按钮的默认边框
            fileName.setBorderPainted(false);
            fileName.setFont((new Font("", Font.BOLD, 22)));
            //去除按钮的默认背景
            fileName.setContentAreaFilled(false);
            //将按钮在界面上显示
            this.getContentPane().add(fileName);
        }
        //若查找不到该文件，给用户进行提示
        else if (printList.size() == 0) {
            //查询不到相关的文件给出相应的提示
            JLabel fileName = new JLabel("无相关文件");
            fileName.setBounds(800, 400, 1400, 50);
            fileName.setFont((new Font("", Font.BOLD, 22)));
            //将文本在界面上显示
            this.getContentPane().add(fileName);
        } else {
            //遍历集合将所有相关文件展示到界面上
            for (String filePath : printList) {
                {
                    //创建文件进入到文件父目录的按钮
                    JButton fileName = new JButton(filePath);
                    fileName.setBounds(0, i, 800, 50);
                    //去除按钮的默认边框
                    fileName.setBorderPainted(false);
                    fileName.setFont((new Font("", Font.BOLD, 22)));
                    //去除按钮的默认背景
                    fileName.setContentAreaFilled(false);
                    //给按钮添加动作监听
                    fileName.addActionListener(new fileActionListener(filePath));
                    //将按钮在界面上显示
                    this.getContentPane().add(fileName);
                }
                //动态管理按钮的纵坐标
                i = i + 50;
            }
        }
    }
}

