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

    private int rowsRSC;
    private int rowsRG;
    private int rowsBO;

    private ArrayList<BORecord> rawDataBO;
    private ArrayList<ReportGeneraleRecord> rawDataReportGenerale;
    private ArrayList<ReportStatoClientiRecord> rawDataReportStatoClienti;

    private XMLgenerator newOrdersLoad;
    private XMLgenerator changesOrdersLoad;

    public MainGUI() {
        initGUI();
        actionLog = new ArrayList<>();


        /***
         * Implementing ActionListeners for buttons in the GUI
         */

        selectBOInput_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBOInput = new JFileChooser("C:\\Studia WIZ\\sem_4\\ACNtools");
//                selectBOInput.changeToParentDirectory();
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
                selectReportGeneraleInput = new JFileChooser("C:\\Studia WIZ\\sem_4\\ACNtools");
//                selectReportGeneraleInput.changeToParentDirectory();
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
                selectReportStatoClientiInput = new JFileChooser("C:\\Studia WIZ\\sem_4\\ACNtools");
//                selectReportStatoClientiInput.changeToParentDirectory();
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
                selectSave = new JFileChooser("C:\\Studia WIZ\\sem_4\\ACNtools");
//                selectSave.changeToParentDirectory();
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
                setProgressStatus(calculation_progress_label, ProgressStatus.PROCESSING);
                if(BOReport != null && ReportGenerale != null) {
                    loadBO();
                    loadReportGenerale();
                    loadReportStatoClienti();
                    calculateReports();
                    saveResults();
                } else {
                    setProgressStatus(calculation_progress_label, ProgressStatus.ERROR);
                    logActivity("Calculation attempt failed ..............");
                }
            }
        });
    }

    private void calculateReports() {
        searchNew();
        searchChanges();
        reconciliation();
    }

    private void searchNew() {
        logActivity("Searching for new orders ......");
        newOrdersLoad = new XMLgenerator(true);

        Iterator iterator = rawDataReportGenerale.iterator();

        int step = rowsRG / 33;
        int progress = 0;
        int value = 0;

        while(iterator.hasNext()) {
            ReportGeneraleRecord record = (ReportGeneraleRecord) iterator.next();
            String id = record.checkModuloWeb();
            boolean recordFound = false;

            Iterator iter = rawDataBO.iterator();
            while(iter.hasNext()) {
                BORecord r = (BORecord) iter.next();
                if(r.getCstAccount().equals(id)) {
                    recordFound = true;
                    break;
                }
            }

            progress++;
            if(progress >= step) {
                progress = 0;
                value++;
                calculationProgressBar.setValue(value);
            }

            if(!recordFound) {
                if(record.isValid()) {
                    logActivity("New order (" + record.checkModuloWeb() + ") found ....");
                    newOrdersLoad.addTransaction(record);
                } else {
                    logActivity("New order (" + record.checkModuloWeb() + ") is invalid and cannot be added to the load.");
                }
            }
        }
    }

    private void searchChanges() {

        logActivity("Searching for orders changes ......");
        changesOrdersLoad = new XMLgenerator(false);

        Iterator iterator = rawDataBO.iterator();

        int step = rowsBO / 33;
        int progress = 0;
        int value = 33;

        while(iterator.hasNext()) {
            ReportGeneraleRecord record; // = (ReportGeneraleRecord) iterator.next();
            BORecord recordChecked = (BORecord) iterator.next();
            String id = recordChecked.getCstAccount();
            boolean recordFound = false;
            boolean rGas = false;
            boolean rEle = false;

            Iterator iter = rawDataReportGenerale.iterator();
            while(iter.hasNext()) {
                record = (ReportGeneraleRecord) iter.next();
                if(record.checkModuloWeb().equals(id)) {
                    if(!recordChecked.getCstProviderStatus().equals(record.getStatus())) {
                        if(record.getTipoCliente().equals("Residenziale") && record.getTipoTicket().equals("Dual Fuel [GM]")) {
                            //if()
                        } else {
                            if(recordChecked.getCstProviderStatus().equals("REVOKED") && record.getStatus().equals("INCOMPLETE")) {

                            } else {
                                recordFound = true;
                                if (record.isValid()) {
                                    logActivity("Change order (" + record.checkModuloWeb() + ") found ....");
                                    changesOrdersLoad.addTransaction(record);
                                } else {
                                    logActivity("Change order (" + record.checkModuloWeb() + ") is invalid and cannot be added to the load.");
                                }
                            }
                        }
                    }
                }
            }

            progress++;
            if(progress >= step) {
                progress = 0;
                value++;
                calculationProgressBar.setValue(value);
            }
//
//            if(!recordFound) {
//                if(record.isValid()) {
//                    logActivity("Change order (" + record.checkModuloWeb() + ") found ....");
//                    changesOrdersLoad.addTransaction(record);
//                } else {
//                    logActivity("Change order (" + record.checkModuloWeb() + ") is invalid and cannot be added to the load.");
//                }
//            }
        }
    }

    private void reconciliation() {
        if(ReportStatoClienti != null) {
            Iterator iterator = rawDataReportStatoClienti.iterator();
            // TODO once BO report is corrected
            logActivity("Reconciliation with Report Stato Clienti was skipped ....");
        } else {
            logActivity("Reconciliation with Report Stato Clienti was skipped ....");
        }
    }

    private void loadReportStatoClienti() {

        if(ReportStatoClienti != null) {

            logActivity("Loading file \"" + ReportStatoClienti.getName() + "\" ........");
            setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.LOADING);

            try {
                FileReader fileReader = new FileReader(ReportStatoClienti);
                BufferedReader reader = new BufferedReader(fileReader);

                rowsRSC = countRows(ReportStatoClienti) - 7;
                reportStatoClientiProgressBar.setMaximum(rowsRSC);
                int progress = 0;

                String line = "";
                rawDataReportStatoClienti = new ArrayList<>();

                System.out.println(rowsRSC);
                line = reader.readLine();
                reportStatoClientiProgressBar.setValue(progress++);

                if (line.equals(ReportHeaders.REPORT_STATO_CLIENTI_HEADER)) {
                    logActivity("........ File headers match: OK ........");

                    for (int i = 1; i <= rowsRSC - 1; i++) {
                        line = reader.readLine();
                        reportStatoClientiProgressBar.setValue(progress++);
                        String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        rawDataReportStatoClienti.add(new ReportStatoClientiRecord(record));
                    }

                    logActivity("Loaded << " + (progress - 1) + " >> records ........");
                    logActivity("Loading finished .........................");
                    setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.DONE);

                } else {
                    logActivity("........ File headers match: FAIL ........");
                    logActivity("Loading failed ...........................");
                    setProgressStatus(reportStatoClienti_progress_label, ProgressStatus.ERROR);
                }

                System.out.println(rawDataReportStatoClienti.get(0).getSomeStrings());
                System.out.println(rawDataReportStatoClienti.get(28583).getSomeStrings());

                reader.close();

            } catch (Exception ex) {
                logActivity("File Report Stato Clienti cannot be read ....");
                ex.printStackTrace();
            }
        } else {
            logActivity("Report Stato Clienti was not selected ....");
        }
    }

    private void loadReportGenerale() {
        logActivity("Loading file \"" + ReportGenerale.getName() + "\" ........");
        setProgressStatus(reportGenerale_progress_label, ProgressStatus.LOADING);

        try {
            FileReader fileReader = new FileReader(ReportGenerale);
            BufferedReader reader = new BufferedReader(fileReader);

            rowsRG = countRows(ReportGenerale) - 7;
            reportGeneraleProgressBar.setMaximum(rowsRG);
            int progress = 0;

            String line = "";
            rawDataReportGenerale = new ArrayList<>();

            System.out.println(rowsRG);
            line = reader.readLine();
            reportGeneraleProgressBar.setValue(progress++);

            if(line.equals(ReportHeaders.REPORT_GENERALE_HEADER)) {
                logActivity("........ File headers match: OK ........");

                for(int i = 1; i <= rowsRG-1; i++) {
                    line = reader.readLine();
                    reportGeneraleProgressBar.setValue(progress++);
                    String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    rawDataReportGenerale.add(new ReportGeneraleRecord(record));
                }

                logActivity("Loaded << " + (progress-1) + " >> records ........");
                logActivity("Loading finished .........................");
                setProgressStatus(reportGenerale_progress_label, ProgressStatus.DONE);

            } else {
                logActivity("........ File headers match: FAIL ........");
                logActivity("Loading failed ...........................");
                setProgressStatus(reportGenerale_progress_label, ProgressStatus.ERROR);
            }

            System.out.println(rawDataReportGenerale.get(0).getSomeStrings());
            System.out.println(rawDataReportGenerale.get(3578).getSomeStrings());

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

            rowsBO = countRows(BOReport);
            BOprogressBar.setMaximum(rowsBO);
            int progress = 0;

            String line = "";
            rawDataBO = new ArrayList<>();

            System.out.println(rowsBO);
            line = reader.readLine();
            BOprogressBar.setValue(progress++);

            if(line.equals(ReportHeaders.BO_REPORT_HEADER)) {
                logActivity("........ File headers match: OK ........");

                for(int i = 1; i <= rowsBO-1; i++) {
                    line = reader.readLine();
                    BOprogressBar.setValue(progress++);
                    String[] record = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    rawDataBO.add(new BORecord(record));
                }

                logActivity("Loaded << " + (progress-1) + " >> records ........");
                logActivity("Loading finished .........................");
                setProgressStatus(BO_progress_label, ProgressStatus.DONE);

            } else {
                logActivity("........ File headers match: FAIL ........");
                logActivity("Loading failed ...........................");
                setProgressStatus(BO_progress_label, ProgressStatus.ERROR);
            }

            System.out.println(rawDataBO.get(0).getSomeStrings());
            System.out.println(rawDataBO.get(48380).getSomeStrings());


            reader.close();


        } catch(Exception ex) {
            System.out.println("Selected file cannot be read");
            ex.printStackTrace();
        }
    }

    private int countRows(File report) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(report);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fileReader);

        int n = 0;
        try {
            while(reader.readLine() != null) {
                n++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    private void setProgressStatus(JLabel label, ProgressStatus status) {
        label.setText(status.getStatus());
        label.setForeground(status.getColor());
    }


    /***
     * Saving all results
     */

    private void saveResults() {
        if(saveFile != null) {
            setProgressStatus(saving_progress_label, ProgressStatus.SAVING);
            logActivity("Saving the results in: " + saveFile.getAbsolutePath());


            newOrdersLoad.saveXMLtemplate(saveFile.getPath());
            changesOrdersLoad.saveXMLtemplate(saveFile.getPath());

            printLogToFile();
        } else {
            setProgressStatus(saving_progress_label, ProgressStatus.ERROR);
        }
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
