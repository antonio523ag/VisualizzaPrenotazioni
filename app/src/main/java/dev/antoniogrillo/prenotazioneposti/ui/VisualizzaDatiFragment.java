package dev.antoniogrillo.prenotazioneposti.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import dev.antoniogrillo.prenotazioneposti.MainActivity;
import dev.antoniogrillo.prenotazioneposti.OnBackPressedPermitted;
import dev.antoniogrillo.prenotazioneposti.adapter.AdapterFiltroPostazione;
import dev.antoniogrillo.prenotazioneposti.data.Database;
import dev.antoniogrillo.prenotazioneposti.databinding.FragmentVisualizzaDatiBinding;
import dev.antoniogrillo.prenotazioneposti.model.Prenotazione;
import dev.antoniogrillo.prenotazioneposti.model.PrenotazioneRequest;


public class VisualizzaDatiFragment extends Fragment implements OnBackPressedPermitted {

    private FragmentVisualizzaDatiBinding binding;
    PrenotazioneRequest request;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVisualizzaDatiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).visualizzaToolbar();
        request=VisualizzaDatiFragmentArgs.fromBundle(getArguments()).getRequest();
        List<Prenotazione> prenotazioni= Database.getInstance().getPrenotazioniFiltrate(request);
        binding.recycler.setAdapter(new AdapterFiltroPostazione(prenotazioni));
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        navigate(requireActivity());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}