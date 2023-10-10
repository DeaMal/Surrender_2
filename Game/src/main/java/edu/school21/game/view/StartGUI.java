package edu.school21.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartGUI extends JFrame {
    private final JTextField input = new JTextField("20", 5);
    private final JTextField inputWalls = new JTextField("10", 5);
    private final JTextField inputEnemy = new JTextField("10", 5);
    private final JRadioButton radio1 = new JRadioButton("Product mode");

    public StartGUI() {
        super("Surrender, You're Surrounded");
        if (System.getProperty("os.name").startsWith("Mac")) {
            setSize(720, 367);
//            setBounds(600, 300, 720, 367);
        } else {
            setSize(700, 378);
//            setBounds(600, 300, 700, 378);
        }
        setLocation(600, 300);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.BASELINE;
        constraints.weighty = 0;
        constraints.ipady = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 6;
        container.add(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Picture.jpg")))), constraints);

        constraints.gridheight = 1;
        constraints.gridy = 0;
        constraints.gridx = 1;
        container.add(Box.createVerticalStrut(5), constraints);

        constraints.weightx = 1;
        constraints.gridy = 1;
        constraints.gridx = 1;
        container.add(Box.createHorizontalStrut(5), constraints);

        constraints.weightx = 0;
        constraints.gridx = 2;
        JLabel label = new JLabel("Input Size:");
        container.add(label, constraints);

        constraints.gridx = 3;
        container.add(Box.createHorizontalStrut(5), constraints);

        constraints.gridx = 4;
        container.add(input, constraints);

        constraints.ipadx = 0;
        constraints.gridx = 5;
        container.add(Box.createHorizontalStrut(5), constraints);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        JRadioButton radio2 = new JRadioButton("Dev mode");
        group.add(radio2);
        radio1.setSelected(true);

        constraints.gridy = 2;
        constraints.gridx = 2;
        JLabel labelWalls = new JLabel("Input Count Walls:");
        container.add(labelWalls, constraints);

        constraints.gridx = 4;
        container.add(inputWalls, constraints);

        constraints.gridy = 3;
        constraints.gridx = 2;
        JLabel labelEnemy = new JLabel("Input Count Enemy:");
        container.add(labelEnemy, constraints);

        constraints.gridx = 4;
        container.add(inputEnemy, constraints);

        constraints.gridy = 4;
        constraints.gridx = 2;
        container.add(radio1, constraints);

        constraints.gridx = 4;
        container.add(radio2, constraints);

        constraints.gridy = 5;

        JButton button = new JButton("Start");
        button.addActionListener(new ButtonEventListener());

        constraints.gridx = 4;
        container.add(button, constraints);
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int size = -1, countWalls = -1, countEnemy = -1;
            try {
                size = Integer.parseInt(input.getText());
                countWalls = Integer.parseInt(inputWalls.getText());
                countEnemy = Integer.parseInt(inputEnemy.getText());
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
            if (size < 0 || countWalls < 0 || countEnemy < 0) {
                JOptionPane.showMessageDialog(null, "Invalid value!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (size < 6 || size > 50) {
                JOptionPane.showMessageDialog(null, "Size should be between 6 and 50!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (countWalls + countEnemy + 2 > size * size) {
                JOptionPane.showMessageDialog(null, "Too many walls or enemies!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                new GameFrame(size, countWalls, countEnemy, radio1.isSelected());
            }
        }
    }
}
