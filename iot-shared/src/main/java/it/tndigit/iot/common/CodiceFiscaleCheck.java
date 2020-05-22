package it.tndigit.iot.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodiceFiscaleCheck {


    /**
     * controlla la lunghezza del codice fiscale passato.
     * se tutti i parametri sono valorizzati esegue anche il controllo
     * su checksum. Ritorna una stringa vuota se il CF è corretto altrimenti restituisce la descrizione dell'errore.
     *
     * @param codFis
     * @return String
     */

    public static String isValidCheckSumCF(String codFis) {
        String codiceFiscale = codFis.toUpperCase();

        int LUNGHEZZA_CODICEFISCALE = 16;

        // Valori per il calcolo del CF

        int[][] matricecod;

        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //*** Matrice per il calcolo del carattere di controllo.
        matricecod = new int[91][2];
        matricecod[0][1] = 1;
        matricecod[0][0] = 0;
        matricecod[1][1] = 0;
        matricecod[1][0] = 1;
        matricecod[2][1] = 5;
        matricecod[2][0] = 2;
        matricecod[3][1] = 7;
        matricecod[3][0] = 3;
        matricecod[4][1] = 9;
        matricecod[4][0] = 4;
        matricecod[5][1] = 13;
        matricecod[5][0] = 5;
        matricecod[6][1] = 15;
        matricecod[6][0] = 6;
        matricecod[7][1] = 17;
        matricecod[7][0] = 7;
        matricecod[8][1] = 19;
        matricecod[8][0] = 8;
        matricecod[9][1] = 21;
        matricecod[9][0] = 9;
        matricecod[10][1] = 1;
        matricecod[10][0] = 0;
        matricecod[11][1] = 0;
        matricecod[11][0] = 1;
        matricecod[12][1] = 5;
        matricecod[12][0] = 2;
        matricecod[13][1] = 7;
        matricecod[13][0] = 3;
        matricecod[14][1] = 9;
        matricecod[14][0] = 4;
        matricecod[15][1] = 13;
        matricecod[15][0] = 5;
        matricecod[16][1] = 15;
        matricecod[16][0] = 6;
        matricecod[17][1] = 17;
        matricecod[17][0] = 7;
        matricecod[18][1] = 19;
        matricecod[18][0] = 8;
        matricecod[19][1] = 21;
        matricecod[19][0] = 9;
        matricecod[20][1] = 2;
        matricecod[20][0] = 10;
        matricecod[21][1] = 4;
        matricecod[21][0] = 11;
        matricecod[22][1] = 18;
        matricecod[22][0] = 12;
        matricecod[23][1] = 20;
        matricecod[23][0] = 13;
        matricecod[24][1] = 11;
        matricecod[24][0] = 14;
        matricecod[25][1] = 3;
        matricecod[25][0] = 15;
        matricecod[26][1] = 6;
        matricecod[26][0] = 16;
        matricecod[27][1] = 8;
        matricecod[27][0] = 17;
        matricecod[28][1] = 12;
        matricecod[28][0] = 18;
        matricecod[29][1] = 14;
        matricecod[29][0] = 19;
        matricecod[30][1] = 16;
        matricecod[30][0] = 20;
        matricecod[31][1] = 10;
        matricecod[31][0] = 21;
        matricecod[32][1] = 22;
        matricecod[32][0] = 22;
        matricecod[33][1] = 25;
        matricecod[33][0] = 23;
        matricecod[34][1] = 24;
        matricecod[34][0] = 24;
        matricecod[35][1] = 23;
        matricecod[35][0] = 25;


        String result = "";
        int codcontrollo = 0;
        if (codiceFiscale.length() == LUNGHEZZA_CODICEFISCALE) {

            //*** Calcolare il CheckSum
            for (int i = 0; i < 15; i++) {
                // se � un carattere non valido il check sum � errato
                if (Character.getNumericValue(codiceFiscale.charAt(i)) < 0) {
                    String error = "Codice di controllo errato!";
                    return error;
                }

                codcontrollo +=
                        matricecod[Character.getNumericValue(
                                codiceFiscale.charAt(i))][(i + 1) % 2];
            }
            char checkCalcCF = alfabeto.charAt(codcontrollo % 26);
            char checkCF = codiceFiscale.charAt(LUNGHEZZA_CODICEFISCALE - 1);
            if (checkCF == checkCalcCF) {
                result = "";
            } else {
                String error = "Codice di controllo errato!";
                return error;
            }
        } else {
            String error = "lunghezza codice fiscale errata!";
            return error;
        }
        log.debug(CodiceFiscaleCheck.class.getName() + " - isValidCheckSumCF(" + codiceFiscale + ")=" + (result.equals("") ? "VALIDO" : "NON VALIDO"));
        return result;
    }
}


