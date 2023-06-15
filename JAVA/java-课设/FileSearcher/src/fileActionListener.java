import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class fileActionListener implements ActionListener {
    String filePath;

    //获得进行该动作的文件路径
    public fileActionListener(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(filePath);
        //获得该文件的父路径
        File parentFile = file.getParentFile();
        //捕获由于文件不存在导致的IO异常
        try {
            //在本地操作系统上打开该文件
            Desktop.getDesktop().open(parentFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
