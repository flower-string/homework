package org.example;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class ShiftCipher extends JFrame implements ActionListener {
    private JButton encryptButton;
    private JButton decryptButton;
    private JTextArea textArea;
    private JTextField shiftField;
    private JFileChooser fileChooser;

    public ShiftCipher() {
        super("Shift Cipher");

        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        textArea = new JTextArea(10, 20);
        shiftField = new JTextField(3);
        fileChooser = new JFileChooser();

        JPanel panel = new JPanel();
        panel.add(new JLabel("Shift:"));
        panel.add(shiftField);
        panel.add(encryptButton);
        panel.add(decryptButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        encryptButton.addActionListener(this);
        decryptButton.addActionListener(this);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400,400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encryptButton) {
            int shift = Integer.parseInt(shiftField.getText());
            String text = textArea.getText();
            String encryptedText = encrypt(text, shift);
            textArea.setText(encryptedText);
            saveFile(encryptedText);
        } else if (e.getSource() == decryptButton) {
            int shift = Integer.parseInt(shiftField.getText());
            String text = textArea.getText();
            String decryptedText = decrypt(text, shift);
            textArea.setText(decryptedText);
            saveFile(decryptedText);
        }
    }

    private String encrypt(String text, int shift) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c += shift;
                if (c > 'z') {
                    c -= 26;
                }
            } else if (c >= 'A' && c <= 'Z') {
                c += shift;
                if (c > 'Z') {
                    c -= 26;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }

    private void saveFile(String text) {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(file)) {
                out.print(text);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ShiftCipher();
    }
}