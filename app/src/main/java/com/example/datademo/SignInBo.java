package com.example.datademo;

import java.util.ArrayList;

/**
 * Created by 丁思议 on 2016/7/30.
 */
public class SignInBo {
    private ArrayList<String> needSignInDays;
    private ArrayList<String> hasSignInDays;

    public ArrayList<String> getNeedSignInDays() {
        return needSignInDays;
    }

    public void setNeedSignInDays(ArrayList<String> needSignInDays) {
        this.needSignInDays = needSignInDays;
    }

    public ArrayList<String> getHasSignInDays() {
        return hasSignInDays;
    }

    public void setHasSignInDays(ArrayList<String> hasSignInDays) {
        this.hasSignInDays = hasSignInDays;
    }
}
