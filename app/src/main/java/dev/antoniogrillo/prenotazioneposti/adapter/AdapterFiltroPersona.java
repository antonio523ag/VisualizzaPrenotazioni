package dev.antoniogrillo.prenotazioneposti.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.antoniogrillo.prenotazioneposti.R;
import dev.antoniogrillo.prenotazioneposti.data.Database;
import dev.antoniogrillo.prenotazioneposti.model.Prenotazione;

public class AdapterFiltroPersona extends RecyclerView.Adapter<CustomViewHolder> {
        private List<Prenotazione> prenotazioni;

        public AdapterFiltroPersona(){
                prenotazioni= Database.getInstance().getPrenotazioni();
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
                holder.setAll(prenotazioni.get(position));
        }

        @Override
        public int getItemCount() {
                return prenotazioni.size();
        }

        public void filtra(String text){
                prenotazioni=Database.getInstance().getPrenotazioniFiltrate(text);
                notifyDataSetChanged();
        }
}
