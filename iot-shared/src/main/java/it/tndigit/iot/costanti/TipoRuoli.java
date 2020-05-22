package it.tndigit.iot.costanti;

/**
 * @author Mirko Pianetti
 *
 *
 * Work in progress
 *
 */

public enum TipoRuoli {
    ROLE_ADMIN(TipoRuoli.ADMIN, "Utente amministratore, può fare tutto", 1L),
    ROLE_ENTE(TipoRuoli.ENTE, "Utente", 5L),

    ROLE_JOB(TipoRuoli.JOB, "Utente TIMER", 60L),
    ROLE_USERS(TipoRuoli.USERS, "Utente base, può modificare solamente i suoi dati", 90L);


    public static final String ADMIN = "ROLE_ADMIN";
    public static final String ENTE = "ROLE_ENTE";
    public static final String JOB = "ROLE_JOB";
    public static final String USERS = "ROLE_USERS";



    String nomeRuolo;
    Long livello;
    String descrizione;

    TipoRuoli(String nomeRuolo, String descrizione, Long livello) {
        this.nomeRuolo = nomeRuolo;
        this.livello = livello;
        this.descrizione = descrizione;
    }

    public static TipoRuoli getRuolo(Long livello) {
        for (TipoRuoli tipoRuoli : TipoRuoli.values()) {
            if (tipoRuoli.getLivello().equals(livello)) {
                return tipoRuoli;
            }
        }
        return null;
    }

    public String getNomeRuolo() {
        return nomeRuolo;
    }

    public Long getLivello() {
        return livello;
    }

    public String getDescrizione() {
        return descrizione;
    }


}
