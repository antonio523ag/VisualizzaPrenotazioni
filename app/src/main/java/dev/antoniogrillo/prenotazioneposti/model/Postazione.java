package dev.antoniogrillo.prenotazioneposti.model;

public class Postazione {
    private String nomePostazione;
    private Ufficio ufficio;

    public String getNomePostazione() {
        return nomePostazione;
    }

    public void setNomePostazione(String nomePostazione) {
        this.nomePostazione = nomePostazione;
    }

    public Ufficio getUfficio() {
        return ufficio;
    }

    public void setUfficio(Ufficio ufficio) {
        this.ufficio = ufficio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postazione that = (Postazione) o;
        if(!that.nomePostazione.equals(this.nomePostazione))return false;
        if(!that.ufficio.getCivico().equals(this.ufficio.getCivico()))return false;
        if(that.ufficio.getPiano()!=this.ufficio.getPiano())return false;
        return true;
    }
}
