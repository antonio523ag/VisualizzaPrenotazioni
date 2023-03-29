package dev.antoniogrillo.prenotazioneposti;

import androidx.fragment.app.FragmentActivity;

public interface OnBackPressedPermitted {

    default void navigate(FragmentActivity m){
        if(m instanceof MainActivity){
            ((MainActivity)m).setOnBackPressedPolicy(this);
        }
    }

    boolean onBackPressed();
}
