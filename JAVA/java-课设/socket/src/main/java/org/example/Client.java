package org.example;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {
    private final JTextField hostField;
    private final JTextField portField;
    private final JProgressBar progressBar;
    private final JLabel statusLabel;

    private Socket socket;
    private OutputStream outputStream;

    public Client() {
        // 设置窗口属性
        setTitle("文件上传客户端");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 初始化组件
        hostField = new JTextField("localhost");
        portField = new JTextField("8888");
        JButton connectButton = new JButton("连接");
        JButton selectButton = new JButton("选择文件");
        progressBar = new JProgressBar();
        statusLabel = new JLabel("未连接");

        // 设置布局
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(1, 5));
        topPanel.add(new JLabel("服务器地址："));
        topPanel.add(hostField);
        topPanel.add(new JLabel("端口号："));
        topPanel.add(portField);
        topPanel.add(connectButton);
        add(topPanel, BorderLayout.NORTH);
        add(selectButton, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(progressBar);
        bottomPanel.add(statusLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // 连接按钮事件处理
        connectButton.addActionListener(e -> {
            String host = hostField.getText();
            int port = Integer.parseInt(portField.getText());
            try {
                socket = new Socket(host, port);
                outputStream = socket.getOutputStream();
                statusLabel.setText("已连接");
            } catch (IOException ex) {
                ex.printStackTrace();
                statusLabel.setText("连接失败");
            }
        });

        // 选择文件按钮事件处理
        selectButton.addActionListener(e -> {
            if (socket == null || socket.isClosed()) {
                statusLabel.setText("请先连接服务器");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showOpenDialog(Client.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 向服务器发送文件数量
                            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                            dataOutputStream.writeInt(files.length);

                            // 发送多个文件
                            for (File file : files) {
                                // 向服务器发送文件名和文件大小
                                dataOutputStream.writeUTF(file.getName());
                                dataOutputStream.writeLong(file.length());

                                // 发送文件内容
                                FileInputStream fileInputStream = new FileInputStream(file);
                                byte[] buffer = new byte[1024];
                                int len;
                                long sentSize = 0;
                                while ((len = fileInputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, len);
                                    outputStream.flush();
                                    sentSize += len;
                                    int progress = (int) (sentSize * 100 / file.length());
                                    progressBar.setValue(progress);
                                }
                                fileInputStream.close();
                            }
                            statusLabel.setText("文件上传成功");

                            // 接收服务器响应信息
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String response = bufferedReader.readLine();
                            System.out.println("服务器响应：" + response);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            statusLabel.setText("文件上传失败");
                        }
                    }
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setVisible(true);
    }
}