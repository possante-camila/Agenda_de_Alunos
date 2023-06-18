package com.example.agenda.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguraBd {


    private static FirebaseAuth auth;

    public static FirebaseAuth Firebaseautenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
