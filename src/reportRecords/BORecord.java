package reportRecords;

/**
 * Created by Przemek on 2017-02-16.
 */
public class BORecord {

    private String cstAccount;
    private String cstName;
    private String cstServiceType;
    private String cstVIPStatus;
    private String cstProviderStatus;
    private String cstProductCode;
    private String cstAddressStreet;
    private String cstAddressCity;
    private String cstAddressPostalCode;
    private String cstCountry;
    private String cstVIPStartDate;
    private String cstOrderEntryDate;
    private String scheduledPurgeDate;
    private String cstPurgedDate;
    private String repTeamID;
    private String uplineTC1;
    private String uplineRVP;
    private String cstCUID;

    public BORecord(String[] record) {

        cstAccount = record[0];
        cstName = record[1];
        cstServiceType = record[2];
        cstVIPStatus = record[3];
        cstProviderStatus = record[4];
        cstProductCode = record[5];
        cstAddressStreet = record[6];
        cstAddressCity = record[7];
        cstAddressPostalCode = record[8];
        cstCountry = record[9];
        cstVIPStartDate = record[12];
        cstOrderEntryDate = record[13];
        scheduledPurgeDate = record[14];
        cstPurgedDate = record[15];
        repTeamID = record[16];
        uplineTC1 = record[17];
        uplineRVP = record[18];
        cstCUID = record[19];

    }

    public String getSomeStrings() {
        return cstAccount + " " + cstName + " " + cstVIPStatus + " " + cstVIPStartDate + " " + cstCUID;
    }

    public String getCstAccount() {
        return cstAccount;
    }
}
