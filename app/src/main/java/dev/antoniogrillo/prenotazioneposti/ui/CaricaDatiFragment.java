package dev.antoniogrillo.prenotazioneposti.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import dev.antoniogrillo.prenotazioneposti.OnBackPressedPermitted;
import dev.antoniogrillo.prenotazioneposti.R;
import dev.antoniogrillo.prenotazioneposti.async.CallData;
import dev.antoniogrillo.prenotazioneposti.databinding.FragmentCaricaDatiBinding;
import dev.antoniogrillo.prenotazioneposti.util.Utilities;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CaricaDatiFragment extends Fragment implements OnBackPressedPermitted {

    private FragmentCaricaDatiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCaricaDatiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation pulse= AnimationUtils.loadAnimation(getContext(), R.anim.heart_pulse);
        binding.caricamento.startAnimation(pulse);
        navigate(requireActivity());
        String token= Utilities.caricaToken(getContext());
        if(token==null){
            recuperaToken(null);
            return;
        }
        CallData c=new CallData(token);
        Single.create(c).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                datiCaricati();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                recuperaToken(e);
            }
        });


    }

    public void recuperaToken(Throwable e){
        Utilities.salvaToken(getContext(),null);
        Navigation.findNavController(binding.getRoot()).navigate(CaricaDatiFragmentDirections.inserisciToken());
    }

    public void datiCaricati(){
        Navigation.findNavController(binding.getRoot()).navigate(CaricaDatiFragmentDirections.visualizzaDati(null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}