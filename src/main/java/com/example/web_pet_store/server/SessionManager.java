package com.example.web_pet_store.server;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private static SessionManager sessionManager = new SessionManager();
    private SessionManager(){
    }
    public static SessionManager getInstance(){
        return sessionManager;
    }
    private static List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session){
        sessionList.add(session);
    }

    public void removeSession(Session session){
        sessionList.remove(session);
    }
}
