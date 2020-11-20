package it.tndigit.iot.costanti;

public enum TipoStatus {
    SENT("Il messaggio è stato spedito"),
    ACCEPTED("Il messaggio è stato accettato e verrà elaborato per la consegna; proveremo a archiviarne il contenuto nella posta in arrivo dell'utente e avvisarlo sui suoi canali preferiti"),
    THROTTLED("Un errore temporaneo ha causato un nuovo tentativo durante l'elaborazione del messaggio; qualsiasi notifica associata a questo messaggio verrà ritardata di un massimo di 7 giorni"),
    FAILED("Un errore permanente ha causato la chiusura del processo con un errore, non verrà inviata alcuna notifica per questo messaggio"),
    PROCESSED("Il messaggio è stato elaborato correttamente e ora è archiviato nella posta in arrivo dell'utente"),
    REJECTED("Il destinatario non esiste oppure il mittente è stato bloccato");


    String descrizioneStato;

    TipoStatus(String descrizioneStato) {
        this.descrizioneStato=descrizioneStato;
    }

    public String getDescrizioneStato() {
        return descrizioneStato;
    }
}
