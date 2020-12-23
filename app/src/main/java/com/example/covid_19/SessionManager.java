package com.example.covid_19;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    //Variable
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session remember
    public static final String SESSION_REMEMBERME = "rememberMe";

    //Remember me variable
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context context, String sessionName){
        context = context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }


    public  void createRememberMeSession(String phoneNo, String password){
        editor.putBoolean(IS_REMEMBERME,true);
        editor.putString(KEY_SESSIONPHONENUMBER,phoneNo);
        editor.putString(KEY_SESSIONPASSWORD,password);

        editor.commit();
    }

    public HashMap<String, String>getRememberMeDetailsFromSession(){
        HashMap<String, String>userData = new HashMap<>();

        userData.put(KEY_SESSIONPHONENUMBER,usersSession.getString(KEY_SESSIONPHONENUMBER,null));
        userData.put(KEY_SESSIONPASSWORD,usersSession.getString(KEY_SESSIONPASSWORD,null));
        return userData;
    }

    public boolean checkRememberMe(){
        if(usersSession.getBoolean(IS_REMEMBERME,false))
            return  true;
        else
            return false;
    }
}

