import javax.swing.*;

/**
 * Created by Przemek on 2017-02-12.
 */
public class MainGUI {

    JFrame frame;

    private JProgressBar BOprogressBar;
    private JProgressBar anagraficheProgressBar;
    private JProgressBar otherProgressBar;
    private JProgressBar savingProgressBar;
    private JProgressBar calculationProgressBar;
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
    private JLabel anagrafiche_progress_label;
    private JLabel other_progress_label;
    private JLabel saving_progress_label;
    private JLabel calculation_progress_label;
    private JTextPane processingLog;

    public MainGUI() {
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("MainGUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 650);
        frame.setResizable(false);
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("MainGUI");
//        frame.setContentPane(new MainGUI().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(500, 650);
//        frame.setResizable(false);
//        frame.setVisible(true);
//    }
}
