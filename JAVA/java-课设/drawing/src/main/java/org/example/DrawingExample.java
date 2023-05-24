package org.example;


/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.Border;

public class DrawingExample extends JFrame {
    public static void main(String[] args) {
        new DrawingExample();
    }
    private JRadioButton brushButton;
    private JRadioButton lineButton;
    private JRadioButton rectButton;
    private JRadioButton ovalButton;
    private JCheckBox fillBox;
    private JButton color1Button;
    private JButton color2Button;
    private DrawPanel drawPanel;

    public DrawingExample() {
        // 初始化应用
        // 创建窗口，设置窗口标题
        super("绘图软件");

        // 在窗口中添加组件并进行布局
        initLayout();

        // 为组件绑定事件，用于切换绘图工具
        brushButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                brushButtonActionPerformed(e);
            }
        });

        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lineButtonActionPerformed(e);
            }
        });
        rectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rectButtonActionPerformed(e);
            }
        });
        ovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ovalButtonActionPerformed(e);
            }
        });
    }

    private void brushButtonActionPerformed(ActionEvent e) {
        drawPanel.setTool(DrawPanel.Tool.BRUSH);
    }

    private void lineButtonActionPerformed(ActionEvent e) {
        drawPanel.setTool(DrawPanel.Tool.LINE);
    }

    private void rectButtonActionPerformed(ActionEvent e) {
        drawPanel.setTool(DrawPanel.Tool.RECTANGLE);
    }

    private void ovalButtonActionPerformed(ActionEvent e) {
        drawPanel.setTool(DrawPanel.Tool.OVAL);
    }

    // 布局，将所有的组件展示在窗口中
    private void initLayout() {
        brushButton = new JRadioButton("画笔");
        lineButton = new JRadioButton("直线");
        rectButton = new JRadioButton("矩形");
        ovalButton = new JRadioButton("圆形");
        fillBox = new JCheckBox("填充");
        color1Button = new JButton();
        color2Button = new JButton();
        drawPanel = new DrawPanel();

        ButtonGroup group = new ButtonGroup();
        group.add(brushButton);
        group.add(lineButton);
        group.add(rectButton);
        group.add(ovalButton);

        brushButton.setSelected(true);
        color1Button.setBackground(Color.BLACK);
        color2Button.setBackground(Color.WHITE);

        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(0, 1));
        toolPanel.add(brushButton);
        toolPanel.add(lineButton);
        toolPanel.add(rectButton);
        toolPanel.add(ovalButton);
        toolPanel.add(fillBox);
        toolPanel.add(new JLabel("边框色"));
        toolPanel.add(color1Button);
        toolPanel.add(new JLabel("填充色"));
        toolPanel.add(color2Button);

        add(toolPanel, BorderLayout.WEST);
        add(drawPanel, BorderLayout.CENTER);

        color1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(DrawingExample.this, "Choose a color", color1Button.getBackground());
                if (color != null) {
                    color1Button.setBackground(color);
                    drawPanel.setColor1(color);
                }
            }
        });
        color2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(DrawingExample.this, "Choose a color", color2Button.getBackground());
                if (color != null) {
                    color2Button.setBackground(color);
                    drawPanel.setColor2(color);
                }
            }
        });
        fillBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPanel.setFill(fillBox.isSelected());
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setSize(600, 400);
        setVisible(true);
    }
}

class DrawPanel extends JPanel {
    enum Tool {
        BRUSH, LINE, RECTANGLE, OVAL
    }

    private Tool tool;
    private Color color1;
    private Color color2;
    private boolean fill;
    private Point startPoint;
    private Point endPoint;
    private BufferedImage image;

    public DrawPanel() {
        tool = Tool.BRUSH;
        color1 = Color.BLACK;
        color2 = Color.WHITE;
        fill = false;

        this.setBorder(BorderFactory.createLineBorder(Color.red, 3));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = startPoint;
                if (tool == Tool.BRUSH) {
                    Graphics2D g = image.createGraphics();
                    g.setColor(color1);
                    g.fillOval(startPoint.x - 2, startPoint.y - 2, 4, 4);
                    g.dispose();
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                Graphics2D g = image.createGraphics();
                g.setColor(fill ? color2 : color1);
                switch (tool) {
                    case LINE:
                        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                        break;
                    case RECTANGLE:
                        int x = Math.min(startPoint.x, endPoint.x);
                        int y = Math.min(startPoint.y, endPoint.y);
                        int width = Math.abs(startPoint.x - endPoint.x);
                        int height = Math.abs(startPoint.y - endPoint.y);
                        if (fill) {
                            g.fillRect(x, y, width, height);
                        } else {
                            g.drawRect(x, y, width, height);
                        }
                        break;
                    case OVAL:
                        x = Math.min(startPoint.x, endPoint.x);
                        y = Math.min(startPoint.y, endPoint.y);
                        width = Math.abs(startPoint.x - endPoint.x);
                        height = Math.abs(startPoint.y - endPoint.y);
                        if (fill) {
                            g.fillOval(x, y, width, height);
                        } else {
                            g.drawOval(x, y, width, height);
                        }
                        break;
                }
                g.dispose();
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                if (tool == Tool.BRUSH) {
                    Graphics2D g = image.createGraphics();
                    g.setColor(color1);
                    g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                    g.dispose();
                    startPoint = endPoint;
                    repaint();
                } else {
                    repaint();
                }
            }
        });
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setColor1(Color color) {
        this.color1 = color;
    }

    public void setColor2(Color color) {
        this.color2 = color;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        g.drawImage(image, 0, 0, null);

        if (startPoint != null && endPoint != null) {
            g.setColor(fill ? color2 : color1);
            switch (tool) {
                case LINE:
                    g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                    break;
                case RECTANGLE:
                    int x = Math.min(startPoint.x, endPoint.x);
                    int y = Math.min(startPoint.y, endPoint.y);
                    int width = Math.abs(startPoint.x - endPoint.x);
                    int height = Math.abs(startPoint.y - endPoint.y);
                    if (fill) {
                        g.fillRect(x, y, width, height);
                    } else {
                        g.drawRect(x, y, width, height);
                    }
                    break;
                case OVAL:
                    x = Math.min(startPoint.x, endPoint.x);
                    y = Math.min(startPoint.y, endPoint.y);
                    width = Math.abs(startPoint.x - endPoint.x);
                    height = Math.abs(startPoint.y - endPoint.y);
                    if (fill) {
                        g.fillOval(x, y, width, height);
                    } else {
                        g.drawOval(x, y, width, height);
                    }
                    break;
            }
        }
    }
}