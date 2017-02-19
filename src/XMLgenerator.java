import reportRecords.ReportGeneraleRecord;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Przemek on 2017-02-19.
 */
public class XMLgenerator {

    private boolean newOrders;
    private int transactionCount;

    private ArrayList<String> XMLtemplate;

    public XMLgenerator(boolean newOrders) {
        this.newOrders = newOrders;
        transactionCount = 0;
        XMLtemplate = new ArrayList<>();
        setupEmptyXML();
    }

    private void setupEmptyXML() {
        XMLtemplate.add("<XMLtemplate>");
        XMLtemplate.add("</XMLtemplate>");
    }

    public void addTransaction(ReportGeneraleRecord record) {
        int n = (transactionCount * 5) + 1;
        XMLtemplate.add(n++, "<transaction id-type=\"VENDOR-CUSTOMER\" id=\"" + record.checkModuloWeb() + "\" seq=\"" + ++transactionCount + "\">");
        XMLtemplate.add(n++, "<customer service=\"" + record.checkServiceCode() + "\" country=\"IT\" action=\"CHANGE\">");
        XMLtemplate.add(n++, "<idModulo>" + record.getIdModulo() + "</idModulo>");
        XMLtemplate.add(n++, "</customer>");
        XMLtemplate.add(n++, "</transaction>");
    }

    public void saveXMLtemplate(String path) {
        try {
            Iterator iterator = XMLtemplate.iterator();

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date date = new Date();

            PrintWriter writer = new PrintWriter(path + "\\" + dateFormat.format(date) + ".xml");

            while (iterator.hasNext()) {
                writer.println(iterator.next());
            }

            writer.close();

        } catch(IOException ex) {
            System.out.println("File cannot be saved");
            ex.printStackTrace();
        }
    }
}
