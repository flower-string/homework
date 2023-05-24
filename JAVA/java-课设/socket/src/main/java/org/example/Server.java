package org.example;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // 创建ServerSocket对象，监听指定端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动，等待客户端连接...");

        while (true) {
            // 等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功：" + socket.getInetAddress().getHostAddress());

            // 创建新线程处理客户端请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取输入输出流
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();

                        // 读取客户端发送的文件数量
                        DataInputStream dataInputStream = new DataInputStream(inputStream);
                        int fileCount = dataInputStream.readInt();

                        // 接收多个文件并保存到本地
                        for (int i = 0; i < fileCount; i++) {
                            // 读取客户端发送的文件名和文件大小
                            String fileName = dataInputStream.readUTF();
                            long fileSize = dataInputStream.readLong();

                            // 接收文件并保存到本地
                            File dir = new File("data");
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(dir, fileName));
                            byte[] buffer = new byte[1024];
                            int len;
                            long receivedSize = 0;
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                                receivedSize += len;
                                if (receivedSize >= fileSize) {
                                    break;
                                }
                            }
                            fileOutputStream.close();
                            System.out.println("文件" + (i + 1) + "接收成功，保存到：" + fileName);
                        }

                        // 向客户端发送响应信息
                        outputStream.write("文件上传成功！".getBytes());

                        // 关闭资源
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}