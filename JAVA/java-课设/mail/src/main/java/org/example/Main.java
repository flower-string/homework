package org.example;


import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/

public class Main extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    // 发件人邮件地址和密码
    private final String fromAddress = "example@example.com";
    private final String password = "password";

    // 收件人邮件地址
    private JTextField toField;
    // 邮件主题
    private JTextField subjectField;
    // 邮件正文
    private JTextArea contentArea;
    // 发送按钮
    private JButton sendButton;

    /**
     * 启动邮件客户端应用程序
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建邮件客户端
     */
    public Main() {
        setTitle("邮件客户端");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 320);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 设置收件人标签
        JLabel toLabel = new JLabel("收件人地址：");
        toLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        toLabel.setBounds(31, 26, 90, 24);
        contentPane.add(toLabel);

        // 设置收件人输入框
        toField = new JTextField();
        toField.setBounds(131, 26, 305, 24);
        contentPane.add(toField);
        toField.setColumns(10);

        // 设置邮件主题标签
        JLabel subjectLabel = new JLabel("邮件主题：");
        subjectLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        subjectLabel.setBounds(31, 60, 90, 24);
        contentPane.add(subjectLabel);

        // 设置邮件主题输入框
        subjectField = new JTextField();
        subjectField.setBounds(131, 60, 305, 24);
        contentPane.add(subjectField);
        subjectField.setColumns(10);

        // 设置邮件内容标签
        JLabel contentLabel = new JLabel("邮件内容：");
        contentLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        contentLabel.setBounds(31, 94, 90, 24);
        contentPane.add(contentLabel);

        // 设置邮件内容输入框
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(131, 94, 305, 119);
        contentPane.add(scrollPane);

        contentArea = new JTextArea();
        scrollPane.setViewportView(contentArea);

        // 设置发送按钮
        sendButton = new JButton("发送邮件");
        sendButton.addActionListener(this);
        sendButton.setFont(new Font("宋体", Font.PLAIN, 14));
        sendButton.setBounds(183, 232, 98, 30);
        contentPane.add(sendButton);
    }

    /**
     * 处理发送邮件请求
     */
    public void actionPerformed(ActionEvent e) {
        // 获取收件人地址、邮件主题和邮件内容
        String toAddress = toField.getText();
        String subject = subjectField.getText();
        String content = contentArea.getText();

        // 初始化SMTP配置
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // 设置邮件服务器主机名
        properties.put("mail.smtp.port", "587"); // 设置邮件服务器端口号
        properties.put("mail.smtp.auth", "true"); // 设置邮件服务器需要身份验证
        properties.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密连接

        // 初始化邮件会话
        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);

        try {
            // 设置发件人地址和收件人地址
            message.setFrom(new InternetAddress(fromAddress, "JavaMail邮件客户端"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            // 设置邮件主题
            message.setSubject(subject);
            // 设置邮件内容
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(content, "text/html;charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp1);
            message.setContent(multipart);
            // 设置邮件发送时间
            message.setSentDate(new Date());

            // SMTP身份验证
            Transport transport = session.getTransport("smtp");
            transport.connect(fromAddress, password);

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("邮件发送成功！");
            // 显示发送成功对话框
            JOptionPane.showMessageDialog(this, "邮件发送成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (AddressException e1) {
            // 显示错误信息对话框
            JOptionPane.showMessageDialog(this, "Email地址错误！", "错误", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        } catch (MessagingException e1) {
            if (e1 instanceof AuthenticationFailedException) {
                // 显示错误信息对话框
                JOptionPane.showMessageDialog(this, "用户名或密码不正确！", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                // 显示错误信息对话框
                JOptionPane.showMessageDialog(this, "邮件发送失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            JOptionPane.showMessageDialog(this, "不支持的编码格式！", "错误", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        }
    }
}
