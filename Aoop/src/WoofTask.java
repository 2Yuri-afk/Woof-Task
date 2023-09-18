import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.List;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class WoofTask {
    private static final String DATABASE_FILE = "tasks.txt";

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        RoundedPanel panelOne = new RoundedPanel(20);
        RoundedPanel panelTwo = new RoundedPanel(20);
        RoundedPanel panelThree = new RoundedPanel(20);

        JLabel logo = new JLabel();
        JLabel taskNumber = new JLabel();
        JLabel emptyTask = new JLabel();

        RoundedButton clear = new RoundedButton("Clear");
        JButton create = new JButton();
        JButton update = new JButton();
        JButton delete = new JButton();

        ImageIcon image = new ImageIcon("wooftasklogo1.png");
        ImageIcon createimg = new ImageIcon("createimg.png");
        ImageIcon updateimg = new ImageIcon("updateimg.png");
        ImageIcon deleteimg = new ImageIcon("deleteimg.png");
        ImageIcon notdoneimg = new ImageIcon("notdoneimg.png");
        ImageIcon doneimg = new ImageIcon("doneimg.png");
        ImageIcon logoimg = new ImageIcon("wooftasklogo.png");
        ImageIcon todoimg = new ImageIcon("todoimg.png");
        ImageIcon clearimg = new ImageIcon("clearimg.png");
        ImageIcon emptytaskimg = new ImageIcon("emptytaskimg.png");
        List<String> taskList = new ArrayList<>();
        loadTasksFromDatabase(taskList, panelTwo, doneimg, notdoneimg);

        logo.setIcon(logoimg);
        logo.setBounds(0, 350, 300, 300);

        emptyTask.setText("You have no tasks right now");
        emptyTask.setIcon(emptytaskimg);
        emptyTask.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        emptyTask.setHorizontalTextPosition(JLabel.LEFT);
        emptyTask.setVerticalTextPosition(JLabel.BOTTOM);

        panelOne.setBackground(new Color(0x394867));
        panelOne.setBounds(13, 10, 295, 635);
        panelOne.setLayout(null);
        panelOne.add(create);
        panelOne.add(update);
        panelOne.add(delete);
        panelOne.add(logo);

        panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.Y_AXIS));
        panelTwo.setBackground(new Color(0x567189));
        panelTwo.setBounds(320, 40, 650, 605);
        panelTwo.setBorder(new EmptyBorder(20, 20, 20, 20));

        if (taskList.size() == 0) {
            panelTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 250));
            panelTwo.add(emptyTask);
        }

        panelThree.setBounds(900, 5, 70, 30);
        panelThree.setBackground(new Color(0x607099));
        panelThree.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelThree.setForeground(new Color(0x212A3E));

        clear.setBounds(790, 5, 105, 30);
        clear.setIcon(clearimg);
        clear.setFont(new Font("Century Gothic", Font.BOLD, 15));
        clear.setCornerRadius(20);
        clear.setBackgroundColor(new Color(0x607099));
        clear.setFocusable(false);
        clear.setForeground(new Color(0x212A3E));
        clear.setIconTextGap(10);

        taskNumber.setText((String.valueOf(taskList.size())));
        taskNumber.setIcon(todoimg);
        taskNumber.setFont(new Font("Century Gothic", Font.BOLD, 15));
        taskNumber.setIconTextGap(10);
        panelThree.add(taskNumber);

        create.setBounds(20, 30, 250, 85);
        create.setText("Create a task");
        create.setFont(new Font("Century Gothic", Font.BOLD, 20));
        create.setIcon(createimg);
        create.setFocusable(false);
        create.setForeground(new Color(0x95c8d8));
        create.setBackground(new Color(0x394867));
        create.setBorder(BorderFactory.createRaisedBevelBorder());
        create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = JOptionPane.showInputDialog("What do you need to do?");
                String subject = JOptionPane.showInputDialog("Assign subject for this task: ");
                String todo = task + " - " + subject;
                JLabel label = new JLabel(todo);
                panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.Y_AXIS));
                panelTwo.remove(emptyTask);
                panelTwo.revalidate();
                panelTwo.repaint();
                label.setIcon(notdoneimg);
                label.setHorizontalTextPosition(JLabel.LEFT);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                label.setFont(new Font("Century Gothic", Font.PLAIN, 25));
                label.setForeground(Color.WHITE);
                label.setIconTextGap(20);
                panelTwo.add(label);
                taskList.add(todo);
                frame.revalidate();
                taskNumber.setText((String.valueOf(taskList.size())));

            }
        });

        update.setBounds(20, 150, 250, 85);
        update.setText("Update a task");
        update.setIcon(updateimg);
        update.setFont(new Font("Century Gothic", Font.BOLD, 20));
        update.setIconTextGap(10);
        update.setFocusable(false);
        update.setForeground(new Color(0x95c8d8));
        update.setBackground(new Color(0x394867));
        update.setBorder(BorderFactory.createRaisedBevelBorder());
        update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No tasks available to update.");
                } else {
                    String[] taskArray = taskList.toArray(new String[0]);
                    JComboBox<String> taskComboBox = new JComboBox<>(taskArray);
                    taskComboBox.setBackground(Color.WHITE);
                    taskComboBox.setPreferredSize(new Dimension(300, 30));

                    Object[] message = {
                            "Select a task to update:", taskComboBox
                    };

                    int option = JOptionPane.showConfirmDialog(frame, message, "Update a Task",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        int selectedIndex = taskComboBox.getSelectedIndex();
                        String[] options = { "Done", "Not Done" };
                        int choice = JOptionPane.showOptionDialog(
                                frame,
                                "Mark task as:",
                                "Update Task Status",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                options,
                                options[0]);

                        ImageIcon statusIcon = (choice == 0) ? doneimg : notdoneimg;
                        JLabel selectedLabel = (JLabel) panelTwo.getComponent(selectedIndex);
                        selectedLabel.setIcon(statusIcon);
                        frame.revalidate();
                    }
                }
            }
        });

        delete.setBounds(20, 270, 250, 85);
        delete.setText("Delete a task");
        delete.setIcon(deleteimg);
        delete.setFont(new Font("Century Gothic", Font.BOLD, 20));
        delete.setIconTextGap(15);
        delete.setFocusable(false);
        delete.setForeground(new Color(0x95c8d8));
        delete.setBackground(new Color(0x394867));
        delete.setBorder(BorderFactory.createRaisedBevelBorder());
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No tasks available to delete.");
                } else {
                    String[] taskArray = taskList.toArray(new String[0]);
                    JComboBox<String> taskComboBox = new JComboBox<>(taskArray);
                    taskComboBox.setBackground(Color.WHITE);
                    taskComboBox.setPreferredSize(new Dimension(300, 30));

                    Object[] message = {
                            "Select a task to delete:", taskComboBox
                    };

                    int option = JOptionPane.showConfirmDialog(frame, message, "Delete a Task",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String selectedTask = (String) taskComboBox.getSelectedItem();
                        Component[] components = panelTwo.getComponents();

                        for (Component component : components) {
                            if (component instanceof JLabel) {
                                JLabel label = (JLabel) component;
                                if (label.getText().equals(selectedTask)) {
                                    panelTwo.remove(label);
                                    panelTwo.revalidate();
                                    panelTwo.repaint();
                                    taskList.remove(selectedTask);
                                    writeTasksToDatabase(taskList);
                                    break;
                                }
                            }
                        }
                    }
                }
                taskNumber.setText((String.valueOf(taskList.size())));
                if (taskList.size() == 0) {
                    panelTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 250));
                    panelTwo.add(emptyTask);
                }

            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskList.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "There are no task to clear.");
                } else {
                    int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all tasks?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {

                        panelTwo.removeAll();
                        panelTwo.revalidate();
                        panelTwo.repaint();

                        taskList.clear();

                        writeTasksToDatabase(taskList);
                        taskNumber.setText(String.valueOf(taskList.size()));
                        if (taskList.size() == 0) {
                            panelTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 250));
                            panelTwo.add(emptyTask);

                        }
                    }
                }
            }
        });

        frame.setTitle("WoofTask!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(panelOne);
        frame.add(panelTwo);
        frame.add(panelThree);
        frame.add(clear);
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(0x212A3E));
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                writeTasksToDatabase(taskList);
            }
        });
    }

    private static void loadTasksFromDatabase(List<String> taskList, JPanel panelTwo, ImageIcon doneIcon,
            ImageIcon notDoneIcon) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String todo = line.substring(0);
                ImageIcon icon = (todo.equals("Done")) ? doneIcon : notDoneIcon;
                JLabel label = createTaskLabel(todo, icon);
                label.setFont(new Font("Century Gothic", Font.PLAIN, 25));
                label.setForeground(Color.WHITE);
                panelTwo.add(label);
                taskList.add(line);

            }
            panelTwo.revalidate();
            panelTwo.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JLabel createTaskLabel(String task, ImageIcon icon) {
        JLabel label = new JLabel(task);
        label.setIcon(icon);
        label.setHorizontalTextPosition(JLabel.LEFT);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(new Font("Century Gothic", Font.ITALIC, 30));
        label.setForeground(Color.WHITE);
        label.setIconTextGap(20);
        return label;
    }

    private static void writeTasksToDatabase(List<String> taskList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (String task : taskList) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
