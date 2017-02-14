import javax.swing.*;

/**
 * Created by Przemek on 2017-02-12.
 */
public class MainGUI {
    private JProgressBar BOprogressBar;
    private JProgressBar AnagraficheProgressBar;
    private JProgressBar OtherProgressBar;
    private JProgressBar SavingProgressBar;
    private JProgressBar CalculationProgressBar;
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
    private JLabel Anagrafiche_progress_label;
    private JLabel Other_progress_label;
    private JLabel Saving_progress_label;
    private JLabel Calculation_progress_label;
    private JTextPane textPane1;

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
