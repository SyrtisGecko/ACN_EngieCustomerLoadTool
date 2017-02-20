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
    private String newOrderAdd;

    private int transactionCount;

    private ArrayList<String> XMLtemplate;

    public XMLgenerator(boolean newOrders) {
        this.newOrders = newOrders;
        transactionCount = 0;
        XMLtemplate = new ArrayList<>();
        newOrderCheck();
        setupEmptyXML();
    }

    private void newOrderCheck() {
        if(newOrders) {
            newOrderAdd = "-1";
        } else {
            newOrderAdd = "-2";
        }
    }

    private void setupEmptyXML() {
        XMLtemplate.add("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        XMLtemplate.add("<vip-customer-load create-date-time=\"" + getDate() + "\" origin=\"CGM\">");
        XMLtemplate.add("<data>");
        XMLtemplate.add("</data>");
        XMLtemplate.add("<control>");
        XMLtemplate.add("<customer country=\"IT\" service=\"ENE-DUAL1\">");
        XMLtemplate.add("<transaction-count>0</transaction-count>");
        XMLtemplate.add("<add-count>0</add-count>");
        XMLtemplate.add("<change-count>0</change-count>");
        XMLtemplate.add("</customer>");
        XMLtemplate.add("<customer country=\"IT\" service=\"ENE-ELEC\">");
        XMLtemplate.add("<transaction-count>0</transaction-count>");
        XMLtemplate.add("<add-count>0</add-count>");
        XMLtemplate.add("<change-count>0</change-count>");
        XMLtemplate.add("</customer>");
        XMLtemplate.add("<customer country=\"IT\" service=\"ENE-GAS\">");
        XMLtemplate.add("<transaction-count>0</transaction-count>");
        XMLtemplate.add("<add-count>0</add-count>");
        XMLtemplate.add("<change-count>0</change-count>");
        XMLtemplate.add("</customer>");
        XMLtemplate.add("<customer country=\"IT\" service=\"ENE-DUAL2\">");
        XMLtemplate.add("<transaction-count>0</transaction-count>");
        XMLtemplate.add("<add-count>0</add-count>");
        XMLtemplate.add("<change-count>0</change-count>");
        XMLtemplate.add("</customer>");
        XMLtemplate.add("</control>");
        XMLtemplate.add("</vip-customer-load>");
    }

    public void addTransaction(ReportGeneraleRecord record) {
        int n = (transactionCount * 44) + 3;
        XMLtemplate.add(n++, "<transaction id-type=\"VENDOR-CUSTOMER\" id=\"" + record.checkModuloWeb() + "\" seq=\"" + ++transactionCount + "\">");
        XMLtemplate.add(n++, "<customer service=\"" + record.checkServiceCode() + "\" country=\"IT\" action=\"CHANGE\">");
        XMLtemplate.add(n++, "<vendor-account-id>" + record.checkIdModulo() + "</vendor-account-id>");
        XMLtemplate.add(n++, "<customer-status>" + record.getStatus() + "</customer-status>");
        XMLtemplate.add(n++, "<credit-decision></credit-decision>");
        XMLtemplate.add(n++, "<teamid>" + record.getCodiceVenditore() + "</teamid>");
        XMLtemplate.add(n++, "<name>");
        XMLtemplate.add(n++, "<first>" + record.getNome() + "</first>");
        XMLtemplate.add(n++, "<middle></middle>");
        XMLtemplate.add(n++, "<last>" + record.getCognomeOrCompany() + "</last>");
        XMLtemplate.add(n++, "<suffix></suffix>");
        XMLtemplate.add(n++, "</name>");
        XMLtemplate.add(n++, "<language></language>");
        XMLtemplate.add(n++, "<currency></currency>");
        XMLtemplate.add(n++, "<startdate>" + record.getStartDate() + "</startdate>");
        XMLtemplate.add(n++, "<address>");
        XMLtemplate.add(n++, "<street>");
        XMLtemplate.add(n++, "<name>" + record.getIndirizzo() + "</name>");
        XMLtemplate.add(n++, "<number>" + record.getNumeroCivico() + "</number>");
        XMLtemplate.add(n++, "<type></type>");
        XMLtemplate.add(n++, "</street>");
        XMLtemplate.add(n++, "<double-locality></double-locality>");
        XMLtemplate.add(n++, "<city>" + record.getComune() + "</city>");
        XMLtemplate.add(n++, "<state></state>");
        XMLtemplate.add(n++, "<postal-code>" + record.getCap() + "</postal-code>");
        XMLtemplate.add(n++, "</address>");
        XMLtemplate.add(n++, "<residential></residential>");
        XMLtemplate.add(n++, "<entered-by>CGM</entered-by>");
        XMLtemplate.add(n++, "<loa-date>" + record.getStartDate() + "</loa-date>");
        XMLtemplate.add(n++, "<business-name></business-name>");
        XMLtemplate.add(n++, "<rate-plan>" + record.getNomeProdotto() + "</rate-plan>");
        XMLtemplate.add(n++, "<plan-duration>3</plan-duration>");
        XMLtemplate.add(n++, incompleteStatusCheck(record));
        XMLtemplate.add(n++, "<is-rep></is-rep>");
        XMLtemplate.add(n++, "<promotions>");
        XMLtemplate.add(n++, "<promo>");
        XMLtemplate.add(n++, "<code></code>");
        XMLtemplate.add(n++, "<type></type>");
        XMLtemplate.add(n++, "<referring-customer></referring-customer>");
        XMLtemplate.add(n++, "<referring-btn></referring-btn>");
        XMLtemplate.add(n++, "</promo>");
        XMLtemplate.add(n++, "</promotions>");
        XMLtemplate.add(n++, "</customer>");
        XMLtemplate.add(n++, "</transaction>");
    }

    private String incompleteStatusCheck(ReportGeneraleRecord record) {
        if(record.getStatus().equals("INCOMPLETE")) {
            return "<incomplete-fields><incomplete-field><field-name>NEOR</field-name><value></value></incomplete-field></incomplete-fields>";
        } else {
            return "<incomplete-fields />";
        }
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void saveXMLtemplate(String path) {
        try {
            Iterator iterator = XMLtemplate.iterator();

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();

            PrintWriter writer = new PrintWriter(path + "\\VIPCUST-CGMIT-" + dateFormat.format(date) + newOrderAdd + ".xml");

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
