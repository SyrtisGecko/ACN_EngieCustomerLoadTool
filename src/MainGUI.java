import cosmetics.ProgressStatus;
import cosmetics.ReportHeaders;
import reportRecords.BORecord;
import reportRecords.ReportGeneraleRecord;
import reportRecords.ReportStatoClientiRecord;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
    private JProgressBar reportGeneraleProgressBar;
    private JProgressBar reportStatoClientiProgressBar;
    private JProgressBar savingProgressBar;
    private JProgressBar calculationProgressBar;
    private JButton selectBOInput_button;
    private JButton selectReportGeneraleInput_button;
    private JButton selectReportStatoClientiInput_button;
    private JButton selectOutput_button;
    private JButton calculate_button;
    private JPanel mainPanel;
    private JLabel selectInputFile1;
    private JLabel selectInputFile2;
    private JLabel selectInputFile3;
    private JLabel selectOutputFile;
    private JLabel BO_progress_label;
    private JLabel reportGenerale_progress_label;
    private JLabel reportStatoClienti_progress_label;
    private JLabel saving_progress_label;
    private JLabel calculation_progress_label;
    private JTextArea processingLog;
    private JPanel BOInput_panel;
    private JLabel BOInput_path_label;
    private JPanel reportGeneraleInput_panel;
    private JLabel reportGeneraleInput_path_label;
    private JPanel reportStatoClientiInput_panel;
    private JLabel reportStatoClientiInput_path_label;
    private JPanel save_panel;
    private JLabel selectSave_path_label;

    private JFileChooser selectBOInput;
    private JFileChooser selectReportGeneraleInput;
    private JFileChooser selectReportStatoClientiInput;
    private JFileChooser selectSave;

    private File BOReport;
    private File ReportGenerale;
    private File ReportStatoClienti;
    private File saveFile;

    private ArrayList<BORecord> rawDataBO;
    private ArrayList<ReportGeneraleRecord> rawDataReportGenerale;
    private ArrayList<ReportStatoClientiRecord> rawDataReportStatoClienti;

    public MainGUI() {
        initGUI();
        actionLog = new ArrayList<>();


        /***
         * Implementing ActionListeners for buttons in the GUI
         */

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

        selectReportGeneraleInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectReportGeneraleInput = new JFileChooser();
                selectReportGeneraleInput.changeToParentDirectory();
                int returnVal = selectReportGeneraleInput.showOpenDialog(reportGeneraleInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    ReportGenerale = selectReportGeneraleInput.getSelectedFile();
                    logActivity("Opening Report Generale: " + ReportGenerale.getAbsolutePath());
                    reportGeneraleInput_path_label.setText(ReportGenerale.getName());
                    setProgressStatus(reportGenerale_progress_label, ProgressStatus.SELECTED);
                } else {
                    logActivity("Open command cancelled by user.");
                }
            }
        });

        selectReportStatoClientiInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectReportStatoClientiInput = new JFileChooser();
                selectReportStatoClientiInput.changeToParentDirectory();
                int returnVal = selectReportStatoClientiInput.showOpenDialog(reportStatoClientiInput_panel);

                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    ReportStatoClienti = selectReportStatoClientiInput.getSelectedFile();
                    logActivity("Opening Report Stato Clienti: " + ReportStatoClienti.getAbsolutePath());
                    reportStatoClientiInput_path_label.setText(ReportStatoClienti.getName());
                    setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.SELECTED);
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
                loadBO();
                loadReportGenerale();
                loadReportStatoClienti();
//                calculateReports();
                saveResults();
            }
        });
    }

    private void loadReportStatoClienti() {
        logActivity("Loading file \"" + ReportStatoClienti.getName() + "\" ........");
        setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.LOADING);

        try {
            FileReader fileReader = new FileReader(ReportStatoClienti);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = "";
            rawDataReportStatoClienti = new ArrayList<>();

            line = reader.readLine();

            if(line.equals(ReportHeaders.REPORT_STATO_CLIENTI_HEADER)) {
                logActivity("........ File headers match: OK ........");
            } else {
                logActivity("........ File headers match: FAIL ........");
                setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.ERROR);
            }


            reader.close();

        } catch(Exception ex) {
            System.out.println("Selected file cannot be read");
            ex.printStackTrace();
        }
    }

    private void loadReportGenerale() {
        logActivity("Loading file \"" + ReportGenerale.getName() + "\" ........");
        setProgressStatus(reportGenerale_progress_label, ProgressStatus.LOADING);

        try {
            FileReader fileReader = new FileReader(ReportGenerale);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = "";
            rawDataReportGenerale = new ArrayList<>();

            line = reader.readLine();

            if(line.equals(ReportHeaders.REPORT_GENERALE_HEADER)) {
                logActivity("........ File headers match: OK ........");

                line = reader.readLine();
                String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                rawDataReportGenerale.add(new ReportGeneraleRecord(record));

            } else {
                logActivity("........ File headers match: FAIL ........");
                setProgressStatus(reportGenerale_progress_label, ProgressStatus.ERROR);
            }

            System.out.println(rawDataReportGenerale.get(0).getSomeStrings());

            reader.close();

        } catch(Exception ex) {
            System.out.println("Selected file cannot be read");
            ex.printStackTrace();
        }
    }


    /***
     * Reading BO report from the file
     * File is being validated by comparing headers
     */

    private void loadBO() {
        logActivity("Loading file \"" + BOReport.getName() + "\" ........");
        setProgressStatus(BO_progress_label, ProgressStatus.LOADING);

        try {
            FileReader fileReader = new FileReader(BOReport);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = "";
            rawDataBO = new ArrayList<>();

            line = reader.readLine();

            if(line.equals(ReportHeaders.BO_REPORT_HEADER)) {
                logActivity("........ File headers match: OK ........");

                line = reader.readLine();
                String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                rawDataBO.add(new BORecord(record));

            } else {
                logActivity("........ File headers match: FAIL ........");
                setProgressStatus(BO_progress_label, ProgressStatus.ERROR);
            }

            System.out.println(rawDataBO.get(0).getSomeStrings());
            /*w = Integer.parseInt(reader.readLine());
            //System.out.println(w);
            k = Integer.parseInt(reader.readLine());
            //System.out.println(k);

            tab2d = new double[w][k];

            for(int i=0; i<w; i++) {
                line = reader.readLine(); //System.out.println(line);

                line = line.trim();
                String[] result = line.split("\\s+");
                for(int j=0; j<k; j++){

                    tab2d[i][j] = Double.parseDouble(result[j]);

                }

            }*/


            reader.close();

        } catch(Exception ex) {
            System.out.println("Selected file cannot be read");
            ex.printStackTrace();
        }
    }

    private void setProgressStatus(JLabel label, ProgressStatus status) {
        label.setText(status.getStatus());
        label.setForeground(status.getColor());
    }


    /***
     * Saving all results
     */

    private void saveResults() {
        setProgressStatus(saving_progress_label, ProgressStatus.SAVING);
        logActivity("Saving the results in: " + saveFile.getAbsolutePath());
        printLogToFile();
    }


    /***
     * Printing actionLog to file into location provided by user (saveFile)
     */

    private void printLogToFile() {
        try {
            Iterator iterator = actionLog.iterator();

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date date = new Date();

            PrintWriter writer = new PrintWriter(saveFile.getPath() + "\\" + dateFormat.format(date) + " log.txt");
            writer.println("ACN Engie customer load tool log");

            while (iterator.hasNext()) {
                writer.println(iterator.next());
            }

            writer.close();

        } catch(IOException ex) {
            System.out.println("File cannot be saved");
            logActivity("Log file cannot be saved...");
            ex.printStackTrace();
        }
    }


    /***
     * Logging activity to be displayed in the GUI JTextArea and to be saved to ArrayList actionLog
     * @param log
     */

    private void logActivity(String log) {
        processingLog.append("\n" + log);
        actionLog.add(getTime() + log);
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        Date date = new Date();
        return dateFormat.format(date) + " - ";
    }


    /***
     * Initializing GUI
     */

    private void initGUI() {
        frame = new JFrame("MainGUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(510, 650);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
