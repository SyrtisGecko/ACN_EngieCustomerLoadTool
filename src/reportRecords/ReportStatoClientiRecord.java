package reportRecords;

/**
 * Created by Przemek on 2017-02-17.
 */
public class ReportStatoClientiRecord {

    private String praticaID;
    private String numeroTicket;
    private String fase;
    private String esito;
    private String moduloAgenziaNome;
    private String moduloCodiceContratto;
    private String moduloTipoCliente;
    private String moduloRegioneSociale;
    private String moduloCognome;
    private String moduloNome;
    private String comune;
    private String provincia;
    private String codiceUtenza;
    private String tipoRichiesta;
    private String moduloCommodity;
    private String moduloVenditoreCodice;
    private String statoFornituraEE;
    private String statoFornituraGAS;
    private String dataAttivazione;
    private String dataCessazione;
    private String moduloModulitaDiStipula;

    public ReportStatoClientiRecord(String[] record) {

        praticaID = record[0];
        numeroTicket = record[1];
        fase = record[2];
        esito = record[3];
        moduloAgenziaNome = record[4];
        moduloCodiceContratto = record[5];
        moduloTipoCliente = record[6];
        moduloRegioneSociale = record[7];
        moduloCognome = record[8];
        moduloNome = record[9];
        comune = record[10];
        provincia = record[11];
        codiceUtenza = record[12];
        tipoRichiesta = record[13];
        moduloCommodity = record[14];
        moduloVenditoreCodice = record[15];
        statoFornituraEE = record[16];
        statoFornituraGAS = record[17];
        dataAttivazione = record[18];
        dataCessazione = record[19];
        if(record.length == 21) {
            moduloModulitaDiStipula = record[20];
        } else {
            moduloModulitaDiStipula = null;
        }

    }

    public String getSomeStrings() {
        return praticaID + " " + tipoRichiesta + " " + dataAttivazione + " " + moduloModulitaDiStipula;
    }
}
