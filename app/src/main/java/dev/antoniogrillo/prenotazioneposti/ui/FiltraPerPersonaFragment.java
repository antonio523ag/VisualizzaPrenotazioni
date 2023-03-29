package dev.antoniogrillo.prenotazioneposti.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.antoniogrillo.prenotazioneposti.OnBackPressedPermitted;
import dev.antoniogrillo.prenotazioneposti.adapter.AdapterFiltroPersona;
import dev.antoniogrillo.prenotazioneposti.databinding.FragmentFiltraPerPersonaBinding;

public class FiltraPerPersonaFragment extends Fragment implements OnBackPressedPermitted {

    FragmentFiltraPerPersonaBinding binding;
    AdapterFiltroPersona adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFiltraPerPersonaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigate(requireActivity());
        adapter=new AdapterFiltroPersona();
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ricerca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtra(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    public boolean onBackPressed() {
        return false;
    }
}