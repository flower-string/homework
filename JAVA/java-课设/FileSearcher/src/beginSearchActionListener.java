import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//创建动作监听，并为动作添加相应的功能
public class beginSearchActionListener implements ActionListener {

    public  void actionPerformed(ActionEvent e) {
        //创建文件列表页面
        new fileListJFrame();
    }
}

