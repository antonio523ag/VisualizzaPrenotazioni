package dev.antoniogrillo.prenotazioneposti.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dev.antoniogrillo.prenotazioneposti.databinding.CustomRowBinding;
import dev.antoniogrillo.prenotazioneposti.model.Prenotazione;


public class CustomViewHolder extends RecyclerView.ViewHolder {

    CustomRowBinding binding;
    public CustomViewHolder(View v) {
        super(v);
        binding=CustomRowBinding.bind(v);
    }

    public void setAll(String nomePostazione, String nomePrenotato, String nomeUfficio, LocalDate data){
        binding.postazione.setText(nomePostazione);
        binding.nome.setText(nomePrenotato);
        binding.ufficio.setText(nomeUfficio);
        binding.data.setText(data.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }

    public void setAll(Prenotazione prenotazione) {
        String nomePostazione,nomePrenotato,nomeUfficio;
        LocalDate data=prenotazione.getData();
        nomeUfficio=prenotazione.getPostazione().getUfficio().getNome()+" "+prenotazione.getPostazione().getUfficio().getCivico();
        nomePrenotato=prenotazione.getUtente().getDisplayName();
        nomePostazione=prenotazione.getPostazione().getNomePostazione();
        setAll(nomePostazione,nomePrenotato,nomeUfficio,data);
    }
}
