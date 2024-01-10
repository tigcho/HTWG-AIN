package aufgabe8;

import javax.swing.*;

public class Taschenrechner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Taschenrechner");
        JPanel panel = new GUI().mainPanel;
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(420, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
