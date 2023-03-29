package dev.antoniogrillo.prenotazioneposti.model;

import java.io.Serializable;
import java.time.LocalDate;

public class PrenotazioneRequest implements Serializable {
    private LocalDate data;
    private String ufficio;

    public PrenotazioneRequest(LocalDate data, String ufficio) {
        this.data = data;
        this.ufficio = ufficio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getUfficio() {
        return ufficio;
    }

    public void setUfficio(String ufficio) {
        this.ufficio = ufficio;
    }
}
