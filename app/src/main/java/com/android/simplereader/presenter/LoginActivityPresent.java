package com.android.simplereader.presenter;

import android.view.View;

import com.android.simplereader.ui.view.ILoginActivityView;

/**
 * Created by Dragon丶Lz on 2016/3/25.
 */
public class LoginActivityPresent {

    private ILoginActivityView iLoginActivityViewl;

    public LoginActivityPresent(ILoginActivityView view){

        this.iLoginActivityViewl = view;

    }

    public void login(View view){
        iLoginActivityViewl.login(view);
    }

    public void IntentToRegister(){
        iLoginActivityViewl.toRegisterActivity();
    }

    public void IntentToTourist(){
        iLoginActivityViewl.toTourist();
    }

}
