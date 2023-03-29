package dev.antoniogrillo.prenotazioneposti.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.antoniogrillo.prenotazioneposti.MainActivity;
import dev.antoniogrillo.prenotazioneposti.OnBackPressedPermitted;
import dev.antoniogrillo.prenotazioneposti.databinding.FragmentInserisciTokenBinding;
import dev.antoniogrillo.prenotazioneposti.util.Utilities;


public class InserisciTokenFragment extends Fragment implements OnBackPressedPermitted {

    private FragmentInserisciTokenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInserisciTokenBinding.inflate(inflater, container, false);
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
        binding.button.setOnClickListener(this::getToken);
        navigate(requireActivity());
    }

    public void getToken(View v){
        String token=binding.testo.getText().toString();
        if (!token.isEmpty()){
            Utilities.salvaToken(getContext(),token);
            Navigation.findNavController(binding.getRoot()).navigate(InserisciTokenFragmentDirections.caricaDati());
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}