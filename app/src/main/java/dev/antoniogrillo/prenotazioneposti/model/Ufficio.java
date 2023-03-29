package dev.antoniogrillo.prenotazioneposti.model;

import java.util.Objects;

public class Ufficio {
    private String civico;
    private int piano;
    private String nome;

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public int getPiano() {
        return piano;
    }

    public void setPiano(int piano) {
        this.piano = piano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ufficio ufficio = (Ufficio) o;
        return piano == ufficio.piano && Objects.equals(civico, ufficio.civico) && Objects.equals(nome, ufficio.nome);
    }

}
