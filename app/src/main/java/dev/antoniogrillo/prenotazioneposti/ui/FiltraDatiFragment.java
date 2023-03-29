package dev.antoniogrillo.prenotazioneposti.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.antoniogrillo.prenotazioneposti.OnBackPressedPermitted;
import dev.antoniogrillo.prenotazioneposti.R;
import dev.antoniogrillo.prenotazioneposti.data.Database;
import dev.antoniogrillo.prenotazioneposti.databinding.FragmentFiltraDatiBinding;
import dev.antoniogrillo.prenotazioneposti.model.PrenotazioneRequest;

public class FiltraDatiFragment extends Fragment implements OnBackPressedPermitted {

    private FragmentFiltraDatiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFiltraDatiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCivici();
        navigate(requireActivity());
    }

    public void setCivici(){
        List<String> civiciTxt= Database.getInstance().getCivici();
        ArrayAdapter<String> civiciAdapter  =new ArrayAdapter<>(getContext(), R.layout.row,civiciTxt);
        AutoCompleteTextView civici= (AutoCompleteTextView) binding.civici.getEditText();

        civici.setAdapter(civiciAdapter);
        civici.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.uffici.setVisibility(View.VISIBLE);
            binding.data.setClickable(false);
            binding.button.setClickable(false);
            setUffici(civiciTxt.get(i));
        });
    }

    public void setUffici(String civico){
        List<String> ufficiTxt= Database.getInstance().getUffici(civico);
        AutoCompleteTextView uffici=(AutoCompleteTextView) binding.uffici.getEditText();
        binding.uffici.getEditText().setText("uffici");
        ArrayAdapter<String> ufficiAdapter =new ArrayAdapter<>(getContext(), R.layout.row,ufficiTxt);
        uffici.setAdapter(ufficiAdapter);
        uffici.setOnItemClickListener(((adapterView, view, i, l) -> {
            binding.data.setClickable(true);
            binding.button.setClickable(false);
            String ufficio=ufficiTxt.get(i);
            setData(ufficio);
        }));
    }

    public void setData(String ufficio){
        List<String> dateTxt= Database.getInstance().getDate(ufficio);
        AutoCompleteTextView date=(AutoCompleteTextView) binding.data.getEditText();
        binding.data.getEditText().setText("date");
        ArrayAdapter<String> dateAdapter =new ArrayAdapter<>(getContext(), R.layout.row,dateTxt);
        date.setAdapter(dateAdapter);
        date.setOnItemClickListener(((adapterView, view, i, l) -> {
            binding.button.setClickable(true);
            LocalDate data=LocalDate.parse(dateTxt.get(i), DateTimeFormatter.ofPattern("dd MM yyyy"));
            binding.button.setOnClickListener(view1 -> {

                PrenotazioneRequest p=new PrenotazioneRequest(data,ufficio);
                Navigation.findNavController(binding.getRoot()).navigate(FiltraDatiFragmentDirections.filtraDati(p));
            });
        }));
    }


    @Override
    public boolean onBackPressed() {
        if(binding!=null) {
            Navigation.findNavController(binding.getRoot()).navigate(FiltraDatiFragmentDirections.filtraDati(null));
        }
        return false;
    }
}