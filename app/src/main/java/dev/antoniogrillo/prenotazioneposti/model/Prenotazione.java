package dev.antoniogrillo.prenotazioneposti.model;

import java.time.LocalDate;

public class Prenotazione {
    private LocalDate data;
    private Postazione postazione;
    private Utente utente;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Postazione getPostazione() {
        return postazione;
    }

    public void setPostazione(Postazione postazione) {
        this.postazione = postazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
