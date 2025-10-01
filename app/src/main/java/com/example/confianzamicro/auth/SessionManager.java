package com.example.confianzamicro.auth;

import com.example.confianzamicro.domain.Advisor;

public class SessionManager {
    private static SessionManager I; private Advisor advisor;
    private SessionManager() {}
    public static synchronized SessionManager get(){ return I==null? (I=new
            SessionManager()):I; }
    public void login(Advisor a){ this.advisor = a; }
    public Advisor current(){ return advisor; }
    public boolean isLogged(){ return advisor != null; }
    public void logout(){ advisor = null; }
}