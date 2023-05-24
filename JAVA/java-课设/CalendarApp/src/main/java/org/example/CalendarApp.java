package org.example;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class CalendarApp {
    private static Calendar calendar = Calendar.getInstance();
    private static JLabel label = new JLabel("", SwingConstants.CENTER);
    private static JTable table = new JTable(new DefaultTableModel(new Object[6][7], new String[]{"日", "一", "二", "三", "四", "五", "六"}) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });
    private static Map<String, List<String>> notes = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("万年历");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });
        topPanel.add(prevButton, BorderLayout.WEST);

        topPanel.add(label, BorderLayout.CENTER);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });
        topPanel.add(nextButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        table.setRowHeight(50);
        table.setCellSelectionEnabled(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                Object value = table.getValueAt(row, column);
                if (value != null) {
                    int day = Integer.parseInt(value.toString().split("<br>")[0].replaceAll("<[^>]*>", ""));
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    String key = year + "-" + month + "-" + day;
                    NoteDialog noteDialog = new NoteDialog(frame, notes.get(key));
                    noteDialog.setVisible(true);
                    List<String> noteList = noteDialog.getNoteList();
                    if (noteList != null) {
                        notes.put(key, noteList);
                        updateCalendar();
                    }
                }
            }
        });
        panel.add(table, BorderLayout.CENTER);

        updateCalendar();

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void updateCalendar() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        label.setText(year + "年" + (month + 1) + "月");

        calendar.set(year, month, 1);
        int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                table.setValueAt(null, i, j);
            }
        }

        for (int i = 1; i <= daysInMonth; i++) {
            int row = (i + startDayOfWeek - 2) / 7;
            int column = (i + startDayOfWeek - 2) % 7;
            String key = year + "-" + month + "-" + i;

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, i);
            LunarUtil lunar = new LunarUtil(cal);

            StringBuilder valueBuilder = new StringBuilder("<html>" + i + "<br>" + lunar.toString());
            if (notes.containsKey(key)) {
                for (String note : notes.get(key)) {
                    valueBuilder.append("<br>").append(note);
                }
            }
            valueBuilder.append("</html>");
            table.setValueAt(valueBuilder.toString(), row, column);
        }
    }

    private static class NoteDialog extends JDialog {
        private DefaultListModel<String> listModel;
        private boolean deleted;

        public NoteDialog(JFrame owner, List<String> noteList) {
            super(owner, "添加备注", true);
            setSize(300, 200);
            setLocationRelativeTo(owner);

            listModel = new DefaultListModel<>();
            if (noteList != null) {
                for (String note : noteList) {
                    listModel.addElement(note);
                }
            }
            JList<String> list = new JList<>(listModel);
            add(new JScrollPane(list));

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BorderLayout());
            JTextField textField = new JTextField();
            inputPanel.add(textField, BorderLayout.CENTER);
            JButton addButton = new JButton("添加");
            addButton.addActionListener(e -> {
                String note = textField.getText();
                if (!note.isEmpty()) {
                    listModel.addElement(note);
                    textField.setText("");
                }
            });
            inputPanel.add(addButton, BorderLayout.EAST);
            add(inputPanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            JButton deleteButton = new JButton("删除");
            deleteButton.addActionListener(e -> {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    listModel.remove(index);
                }
            });
            buttonPanel.add(deleteButton);

            JButton okButton = new JButton("确定");
            okButton.addActionListener(e -> setVisible(false));
            buttonPanel.add(okButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        public List<String> getNoteList() {
            List<String> noteList = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                noteList.add(listModel.get(i));
            }
            return noteList;
        }

        public boolean isDeleted() {
            return deleted;
        }
    }
}