package dev.antoniogrillo.prenotazioneposti.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.antoniogrillo.prenotazioneposti.model.*;


public class Utilities {
    public static void salvaToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String caricaToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sharedPref.getString("token", null);
    }

    private static List<ModelData3> getPrenotazioni(String s){
        Gson g=new Gson();
        ModelData3[] prenotazioniArray=g.fromJson(s, ModelData3[].class);
        return new ArrayList<>(Arrays.asList(prenotazioniArray));
    }

    public static List<Prenotazione> getPrenotazioniFormatted(String s){
        List<ModelData3> prenotazioni=getPrenotazioni(s);
        List<Prenotazione> prenotazioniDaRitornare=new ArrayList<>();
        for(ModelData3 p:prenotazioni){
            prenotazioniDaRitornare.addAll(p.toPrenotazioneList());
        }
        return prenotazioniDaRitornare;
    }

    private class ModelData1 {
        private long id;
        private String login;
        private String firstName;
        private String lastName;
        private String email;
        private String langKey;
        private long companyId;
        private ModelData2 userExtra;
        private String displayName;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLangKey() {
            return langKey;
        }

        public void setLangKey(String langKey) {
            this.langKey = langKey;
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        public ModelData2 getUserExtra() {
            return userExtra;
        }

        public void setUserExtra(ModelData2 userExtra) {
            this.userExtra = userExtra;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Utente toUtente(){
            Utente u=new Utente();
            u.setDisplayName(displayName);
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            return u;
        }
    }

    private class ModelData2 {
        private String sede;

        public String getSede() {
            return sede;
        }

        public void setSede(String sede) {
            this.sede = sede;
        }
    }


    private class ModelData3 {

        private ModelData1 user;
        private String stetes;
        private String booking;
        private String lastClock;
        private Map<String, ModelData4> statesInfo;

        public ModelData1 getUser() {
            return user;
        }

        public void setUser(ModelData1 user) {
            this.user = user;
        }

        public String getStetes() {
            return stetes;
        }

        public void setStetes(String stetes) {
            this.stetes = stetes;
        }

        public String getBooking() {
            return booking;
        }

        public void setBooking(String booking) {
            this.booking = booking;
        }

        public String getLastClock() {
            return lastClock;
        }

        public void setLastClock(String lastClock) {
            this.lastClock = lastClock;
        }

        public Map<String, ModelData4> getStatesInfo() {
            return statesInfo;
        }

        public void setStatesInfo(Map<String, ModelData4> statesInfo) {
            this.statesInfo = statesInfo;
        }

        public List<Prenotazione> toPrenotazioneList(){
            List<Prenotazione> prenotazioni=new ArrayList<>();
            for(String s:statesInfo.keySet()){
                Prenotazione p=new Prenotazione();
                LocalDate l=LocalDate.parse(s,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                p.setData(l);
                p.setUtente(user.toUtente());
                p.setPostazione(statesInfo.get(s).toPostazione());
                prenotazioni.add(p);
            }
            return prenotazioni;

        }
    }




    private class ModelData4 {
        private String state;
        private List<String> info;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<String> getInfo() {
            return info;
        }

        public void setInfo(List<String> info) {
            this.info = info;
        }

        public Postazione toPostazione(){
            Postazione p=new Postazione();
            Ufficio u=new Ufficio();
            info.sort((b,a)->a.length()-b.length());
            String s=info.get(0);
            p.setNomePostazione(info.get(2).trim());
            int indexOfParAp=s.indexOf('(');
            int indexOfParCh=s.indexOf(')');
            String s1=s.substring(indexOfParAp+1,indexOfParCh).trim();
            int val=Integer.parseInt(s1);
            u.setPiano(val);
            u.setCivico(s.substring(0,indexOfParAp).trim());
            u.setNome(s.substring(indexOfParCh+1).trim());
            p.setUfficio(u);
            return p;
        }

    }

}