import javax.swing.*;

/**
 * Created by Przemek on 2017-02-12.
 */
public class MainGUI {
    private JProgressBar BOprogressBar;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;
    private JProgressBar progressBar4;
    private JProgressBar progressBar5;
    private JButton selectInput1_button;
    private JButton selectInput2_button;
    private JButton selectInput3_button;
    private JButton selectOutput_button;
    private JButton calculate_button;
    private JPanel mainPanel;
    private JLabel selectInputFile1;
    private JLabel selectInputFile2;
    private JLabel selectInputFile3;
    private JLabel selectOutputFile;
    private JLabel BO_progress_label;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new MainGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 650);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
