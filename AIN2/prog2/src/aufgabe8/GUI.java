package aufgabe8;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    JPanel mainPanel;
    private JTextField operandXField;
    private JTextField operandYField;
    private JTextField resultatField;
    private JRadioButton degRadioButton;
    private JRadioButton radRadioButton;
    private JButton clearButton;
    private JButton plusButton;
    private JButton sinButton;
    private JButton malButton;
    private JButton cosButton;
    private JButton minusButton;
    private JButton hochButton;
    private JButton log2Button;
    private JButton divButton;
    private JLabel operandXLabel;
    private JLabel operandYLabel;
    private JLabel resultatLabel;
    private JPanel operanden;
    private JPanel operationen;

    public GUI() {
        plusButton.addActionListener(e -> {
            double x;
            double y;
            String result = "0";
            try {
                x = Double.parseDouble(operandXField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                return;
            }
            try {
                y = Double.parseDouble(operandYField.getText());
                result = String.valueOf(x + y);
                resultatField.setText(result);
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandYField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        malButton.addActionListener(e -> {
            double x;
            double y;
            String result = "0";
            try {
                x = Double.parseDouble(operandXField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                return;
            }
            try {
                y = Double.parseDouble(operandYField.getText());
                result = String.valueOf(x * y);
                resultatField.setText(result);
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandYField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        minusButton.addActionListener(e -> {
            double x;
            double y;
            String result = "0";
            try {
                x = Double.parseDouble(operandXField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                return;
            }
            try {
                y = Double.parseDouble(operandYField.getText());
                result = String.valueOf(x - y);
                resultatField.setText(result);
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandYField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        divButton.addActionListener(e -> {
            double x;
            double y;
            String result = "0";
            try {
                x = Double.parseDouble(operandXField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                return;
            }
            try {
                y = Double.parseDouble(operandYField.getText());
                result = String.valueOf(x / y);
                resultatField.setText(result);
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandYField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        sinButton.addActionListener(e -> {
            String x = operandXField.getText();
            String result = "0";
            try {
                if (radRadioButton.isSelected()) {
                    result = String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(x))));
                } else {
                    result = String.valueOf(Math.sin(Double.parseDouble(x)));
                }
                resultatField.setText(result);
                operandYField.setText("0");
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        cosButton.addActionListener(e -> {
            String x = operandXField.getText();
            String result = "0";
            try {
                if (radRadioButton.isSelected()) {
                    result = String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(x))));
                } else {
                    result = String.valueOf(Math.cos(Double.parseDouble(x)));
                }
                resultatField.setText(result);
                operandYField.setText("0");
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        hochButton.addActionListener(e -> {
            double x;
            double y;
            String result = "0";
            try {
                x = Double.parseDouble(operandXField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                return;
            }
            try {
                y = Double.parseDouble(operandYField.getText());
                result = String.valueOf(Math.pow(x, y));
                resultatField.setText(result);
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandYField.setForeground(Color.RED);
                resultatField.setText(result);
            }
        });
        log2Button.addActionListener(e -> {
            String x = operandXField.getText();
            String result = "0";
            try {
                result = String.valueOf(Math.log(Double.parseDouble(x)) / Math.log(2));
                resultatField.setText(result);
                operandYField.setText("0");
            } catch (NumberFormatException ex) {
                System.out.println("Falsche Eingabe: " + ex);
                operandXField.setForeground(Color.RED);
                resultatField.setText(result);
                operandYField.setText("0");
            }
        });
        clearButton.addActionListener(e -> {
            operandXField.setText("0");
            operandYField.setText("0");
            resultatField.setText("0");
        });
    }

}
