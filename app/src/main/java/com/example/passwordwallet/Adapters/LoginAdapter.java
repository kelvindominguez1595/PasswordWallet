package com.example.passwordwallet.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.passwordwallet.Fragments.LoginTabFragment;
import com.example.passwordwallet.Fragments.SignupTabFragment;

public class LoginAdapter extends FragmentStateAdapter {

    public LoginAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle){
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return  loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment();
                return  signupTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}