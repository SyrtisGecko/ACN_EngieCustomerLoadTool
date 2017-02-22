package reportRecords;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        regioneSociale = setName(record[12]);
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

    private String setName(String s) {
        return s.replace("&", "e");
    }

    // TODO remove once all is done
    public String getSomeStrings() {
        return idModulo + " " + moduloWeb + " " + codiceModulo + " " + dataInserimento + " " + consumoAnnuoPresunto + " " + causaleRettifica;
    }

    public String checkModuloWeb() {
        if(tipoCliente.equals("Business")) {
            if(tipoTicket.equals("Gas") || tipoTicket.equals("Dual Fuel [GM]")) {
                return moduloWeb + "G";
            } else {
                return moduloWeb + "E";
            }
        } else {
            return moduloWeb;
        }
    }

    public String checkIdModulo() {
        if(tipoCliente.equals("Business")) {
            if(tipoTicket.equals("Gas") || tipoTicket.equals("Dual Fuel [GM]")) {
                return idModulo + "G";
            } else {
                return idModulo + "E";
            }
        } else {
            return idModulo;
        }
    }

    public String checkServiceCode() {
        String addition = null;

        if(tipoCliente.equals("Business")) {
            if (!potenza.equals("")) {

                String s = potenza;
                s = s.replace("\"", "");
                s = s.replace(",", ".");

                float p = Float.parseFloat(s);

                if (p < 4.5) {
                    addition = "BS1";
                } else if (p > 16.5) {
                    addition = "BS3";
                } else {
                    addition = "BS2";
                }
            } else {
                String s = consumoAnnuoPresunto;
                s = s.replace("\"", "");
                s = s.replace(",", ".");

                float c = Float.parseFloat(s);

                if(c < 500.00) {
                    addition = "BS4";
                } else if(c > 5000) {
                    addition = "BS6";
                } else {
                    addition = "BS5";
                }
            }
        } else {
            if(tipoTicket.equals("Gas")) {
                addition = "GAS";
            } else if(tipoTicket.equals("Elettrica")) {
                addition = "ELE";
            } else {
                addition = "DUA";
            }
        }
        return "ENE-SW-" + addition;
    }

    public String getStatus() {
        if(statoTicket.equals("NUOVA")) {
            return "ACTIVE";
        } else if(statoTicket.equals("ANNULLATA")) {
            return "INCOMPLETE";
        } else if(statoTicket.equals("RETTIFICA")) {
            return "REVOKED";
        } else if(statoTicket.equals("IN LAVORAZIONE")) {
            return "ACTIVE";
        } else if(statoTicket.equals("N.D.")) {
            return "REVOKED";
        } else if(statoTicket.equals("COMPLETATA")) {
            return "ACTIVE";
        }
        return "";
    }

    public String getCodiceVenditore() {
        return codiceVenditore;
    }

    public String getNome() {
        return nome;
    }

    public String getCognomeOrCompany() {
        if(tipoCliente.equals("Business")) {
            String company = null;
            if(regioneSociale.length() > 20) {
                company = regioneSociale.substring(0, 20);
            } else {
                company = regioneSociale;
            }
            return company;
        } else {
            return cognome;
        }
    }

    public String getStartDate() {
        return dataInserimento.substring(6, 10) + "-" + dataInserimento.substring(3, 5) + "-" + dataInserimento.substring(0, 2);
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public String getComune() {
        return comune;
    }

    public String getCap() {
        return cap;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public boolean isValid() {
        if (!potenza.equals("")) {

            String s = potenza;
            s = s.replace("\"", "");
            s = s.replace(",", ".");

            float p = Float.parseFloat(s);

            if (p > 50) {
                return false;
            }
        } else {
            String s = consumoAnnuoPresunto;
            s = s.replace("\"", "");
            s = s.replace(",", ".");

            float c = Float.parseFloat(s);

            if(c > 30000) {
                return false;
            }
        }
        return true;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public String getTipoTicket() {
        return tipoTicket;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setStatoTicket(String statoTicket) {
        this.statoTicket = statoTicket;
    }
}
