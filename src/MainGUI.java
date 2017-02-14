import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Przemek on 2017-02-12.
 */
public class MainGUI {

    JFrame frame;
    int n;

    private JProgressBar BOprogressBar;
    private JProgressBar anagraficheProgressBar;
    private JProgressBar otherProgressBar;
    private JProgressBar savingProgressBar;
    private JProgressBar calculationProgressBar;
    private JButton selectBOInput_button;
    private JButton selectAnagraficheInput_button;
    private JButton selectOtherInput_button;
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
    private JTextArea processingLog;

    public MainGUI() {
        initGUI();

        selectBOInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n++;
                System.out.println("Button was clicked");
                processingLog.append("\nButton was clicked " +n+ " times.");
            }
        });
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
