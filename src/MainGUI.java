import cosmetics.ProgressStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Przemek on 2017-02-12.
 */
public class MainGUI {

    JFrame frame;
    
    ArrayList<String> actionLog;
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
    private JLabel selectSave_path_label;

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
        actionLog = new ArrayList<>();

        selectBOInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBOInput = new JFileChooser();
                selectBOInput.changeToParentDirectory();
                int returnVal = selectBOInput.showOpenDialog(BOInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    BOReport = selectBOInput.getSelectedFile();
                    logActivity("Opening BO: " + BOReport.getAbsolutePath());
                    BOInput_path_label.setText(BOReport.getName());
                    setProgressStatus(BO_progress_label, ProgressStatus.SELECTED);
                } else {
                    logActivity("Open command cancelled by user.");
                }
            }
        });

        selectAnagraficheInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAnagraficheInput = new JFileChooser();
                selectAnagraficheInput.changeToParentDirectory();
                int returnVal = selectAnagraficheInput.showOpenDialog(AnagraficheInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    anagraficheReport = selectAnagraficheInput.getSelectedFile();
                    logActivity("Opening Anagrafiche: " + anagraficheReport.getAbsolutePath());
                    AnagraficheInput_path_label.setText(anagraficheReport.getName());
                    setProgressStatus(anagrafiche_progress_label, ProgressStatus.SELECTED);
                } else {
                    logActivity("Open command cancelled by user.");
                }
            }
        });

        selectOtherInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOtherInput = new JFileChooser();
                selectOtherInput.changeToParentDirectory();
                int returnVal = selectOtherInput.showOpenDialog(otherInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    otherReport = selectOtherInput.getSelectedFile();
                    logActivity("Opening other: " + otherReport.getAbsolutePath());
                    otherInput_path_label.setText(otherReport.getName());
                    setProgressStatus(other_progress_label, ProgressStatus.SELECTED);
                } else {
                    logActivity("Open command cancelled by user.");
                }
            }
        });
        selectOutput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectSave = new JFileChooser();
                selectSave.changeToParentDirectory();
                selectSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = selectSave.showSaveDialog(save_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    saveFile = selectSave.getSelectedFile();
                    logActivity("Choosing save location: " + saveFile.getAbsolutePath());
                    selectSave_path_label.setText(saveFile.getPath());
                    setProgressStatus(saving_progress_label, ProgressStatus.SELECTED);
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

    private void setProgressStatus(JLabel label, ProgressStatus status) {
        label.setText(status.getStatus());
        label.setForeground(status.getColor());
    }

    private void saveResults() {
        logActivity("Saving the results in: " + saveFile.getAbsolutePath());
        printLogToFile();
    }

    private void printLogToFile() {
        try {
            Iterator iterator = actionLog.iterator();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date date = new Date();
            PrintWriter writer = new PrintWriter(saveFile.getPath() + "\\" + dateFormat.format(date) + " log.txt");
            writer.println("ACN Engie customer load tool log");
            while (iterator.hasNext()) {
                writer.println(iterator.next());
//                System.out.println(iterator.next());
            }
            writer.close();
        } catch(IOException ex) {
            System.out.println("File cannot be saved");
            logActivity("Log file cannot be saved...");
            ex.printStackTrace();
        }
    }

    private void logActivity(String log) {
        processingLog.append("\n" + log);
        actionLog.add(getTime() + log);
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        Date date = new Date();
        return dateFormat.format(date) + " - ";
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
