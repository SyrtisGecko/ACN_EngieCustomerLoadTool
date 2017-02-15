import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private JPanel BOInput_panel;
    private JLabel BOInput_path_label;
    private JPanel AnagraficheInput_panel;
    private JLabel AnagraficheInput_path_label;
    private JPanel otherInput_panel;
    private JLabel otherInput_path_label;
    private JPanel save_panel;

    private JFileChooser selectBOInput;
    private JFileChooser selectAnagraficheInput;
    private JFileChooser selectOtherInput;
    private JFileChooser selectSave;

    private File BOReport;
    private File anagraficheReport;
    private File otherReport;
    private File saveFile;

    public MainGUI() {
        initGUI();

        selectBOInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBOInput = new JFileChooser();
                int returnVal = selectBOInput.showOpenDialog(BOInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    BOReport = selectBOInput.getSelectedFile();
                    processingLog.append("\nOpening BO: " + BOReport.getAbsolutePath());
                    BOInput_path_label.setText(BOReport.getName());
                } else {
                    processingLog.append("\nOpen command cancelled by user.");
                }
            }
        });

        selectAnagraficheInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAnagraficheInput = new JFileChooser();
                int returnVal = selectAnagraficheInput.showOpenDialog(AnagraficheInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    anagraficheReport = selectAnagraficheInput.getSelectedFile();
                    processingLog.append("\nOpening Anagrafiche: " + anagraficheReport.getAbsolutePath());
                    AnagraficheInput_path_label.setText(anagraficheReport.getName());
                } else {
                    processingLog.append("\nOpen command cancelled by user.");
                }
            }
        });

        selectOtherInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOtherInput = new JFileChooser();
                int returnVal = selectOtherInput.showOpenDialog(otherInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    otherReport = selectOtherInput.getSelectedFile();
                    processingLog.append("\nOpening other: " + otherReport.getAbsolutePath());
                    otherInput_path_label.setText(otherReport.getName());
                } else {
                    processingLog.append("\nOpen command cancelled by user.");
                }
            }
        });
        selectOutput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectSave = new JFileChooser();
                selectSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = selectSave.showSaveDialog(save_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    saveFile = selectSave.getSelectedFile();
                    processingLog.append("\nChoosing save location: " + saveFile.getAbsolutePath());
                }
            }
        });
        calculate_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                loadBO();
//                loadAnagrafiche();
//                loadOther();
//                calculateReports();
                saveResults();
            }
        });
    }

    private void saveResults() {
        processingLog.append("\nSaving the results in: " + saveFile.getAbsolutePath());
    }

    private void initGUI() {
        frame = new JFrame("MainGUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(510, 650);
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
