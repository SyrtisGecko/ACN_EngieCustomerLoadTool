package reportRecords;

/**
 * Created by Przemek on 2017-02-17.
 */
public class ReportGeneraleRecord {

    private String codiceModulo;
    private String codiceVenditore;
    private String idVenditore;
    private String stato;
    private String agenzia;
    private String idModulo;
    private String moduloWeb;
    private String nomeProdotto;
    private String dataInserimento;
    private String dataInserimentoAllegato;
    private String cognome;
    private String nome;
    private String regioneSociale;
    private String tipoCliente;
    private String tipoTicket;
    private String indirizzo;
    private String numeroCivico;
    private String estensioneCivico;
    private String piano;
    private String interno;
    private String scala;
    private String cap;
    private String comune;
    private String provincia;
    private String localita;
    private String unitoDiMisuraPotenza;
    private String livelloTensioneSiglia;
    private String potenza;
    private String consumoAnnuoPresunto;
    private String apparatoDiCura;
    private String sollevamentoPersone;
    private String adesioneBollettaOnline;
    private String postaElettronica;
    private String faseTicket;
    private String descrizioneFaseTicket;
    private String ticketMotivazione;
    private String statoTicket;
    private String esitoCreditCheck;
    private String praticaAnnullamento;
    private String esitoRecall;
    private String motivazioneRecall;
    private String creatoDaUtente;
    private String modalitoDiStipula;
    private String causaleRettifica;


    public ReportGeneraleRecord(String[] record) {

        codiceModulo = record[0];
        codiceVenditore = record[1];
        idVenditore = record[2];
        stato = record[3];
        agenzia = record[4];
        idModulo = record[5];
        moduloWeb = record[6];
        nomeProdotto = record[7];
        dataInserimento = record[8];
        dataInserimentoAllegato = record[9];
        cognome = record[10];
        nome = record[11];
        regioneSociale = record[12];
        tipoCliente = record[13];
        tipoTicket = record[14];
        indirizzo = record[15];
        numeroCivico = record[16];
        estensioneCivico = record[17];
        piano = record[18];
        interno = record[19];
        scala = record[20];
        cap = record[21];
        comune = record[22];
        provincia = record[23];
        localita = record[24];
        unitoDiMisuraPotenza = record[25];
        livelloTensioneSiglia = record[26];
        potenza = record[27];
        consumoAnnuoPresunto = record[28];
        apparatoDiCura = record[29];
        sollevamentoPersone = record[30];
        adesioneBollettaOnline = record[31];
        postaElettronica = record[32];
        faseTicket = record[33];
        descrizioneFaseTicket = record[34];
        ticketMotivazione = record[35];
        statoTicket = record[36];
        esitoCreditCheck = record[37];
        praticaAnnullamento = record[38];
        esitoRecall = record[39];
        motivazioneRecall = record[40];
        creatoDaUtente = record[41];
        modalitoDiStipula = record[42];
        if(record.length == 44) {
            causaleRettifica = record[43];
        } else {
            causaleRettifica = null;
        }

    }

    public String getIdModulo() {
        return idModulo;
    }

    public String getSomeStrings() {
        return idModulo + " " + moduloWeb + " " + codiceModulo + " " + dataInserimento + " " + consumoAnnuoPresunto + " " + causaleRettifica;
    }
}
