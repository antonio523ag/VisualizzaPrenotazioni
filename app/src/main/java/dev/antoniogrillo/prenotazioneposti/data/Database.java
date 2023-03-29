package dev.antoniogrillo.prenotazioneposti.data;

import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import dev.antoniogrillo.prenotazioneposti.model.*;
import dev.antoniogrillo.prenotazioneposti.util.Utilities;

public class Database {
    private final static Database instance=new Database();

    private Database(){}

    public static Database getInstance() {
        return instance;
    }

    private List<Prenotazione> prenotazioni;
    private List<Postazione> postazioni;
    private List<String> civici;
    private List<Ufficio> uffici;

    public void setPrenotazioni(String s){
        this.prenotazioni= Utilities.getPrenotazioniFormatted(s);
        postazioni=prenotazioni.stream().map(Prenotazione::getPostazione).distinct().collect(Collectors.toList());
        uffici=postazioni.stream().map(Postazione::getUfficio).distinct().collect(Collectors.toList());
        civici=uffici.stream().map(u->u.getCivico()+" "+u.getPiano()).distinct().sorted().collect(Collectors.toList());
        ordina();
    }

    public List<String> getCivici(){
        return civici;
    }

    public List<String> getUffici(String civico){
        return uffici.stream().filter(u -> (u.getCivico()+" "+ u.getPiano()).equals(civico)).map(Ufficio::getNome).distinct().sorted().collect(Collectors.toList());
    }

    public List<String> getDate(String ufficio){
        return prenotazioni.stream().filter(p->p.getPostazione().getUfficio().getNome().equals(ufficio)).map(Prenotazione::getData).filter(d->d.isAfter(LocalDate.now())||d.isEqual(LocalDate.now())).sorted().map(d->d.format(DateTimeFormatter.ofPattern("dd MM yyyy"))).distinct().collect(Collectors.toList());
    }

   public List<Prenotazione> getPrenotazioniFiltrate(PrenotazioneRequest request){
        if(request==null)return prenotazioni;
        return prenotazioni.stream().filter(p->p.getPostazione().getUfficio().getNome().equals(request.getUfficio())).filter(p->p.getData().isEqual(request.getData())).collect(Collectors.toList());
   }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    private void ordina(){
        prenotazioni.removeIf(p->p.getData().isBefore(LocalDate.now())&&!p.getData().isEqual(LocalDate.now()));
        prenotazioni.sort((a,b)->{
            int compare=a.getData().compareTo(b.getData());
            if(compare!=0)return compare;
            compare=a.getPostazione().getUfficio().getCivico().compareToIgnoreCase(b.getPostazione().getUfficio().getCivico());
            if(compare!=0)return compare;
            compare=a.getPostazione().getUfficio().getPiano()-b.getPostazione().getUfficio().getPiano();
            if(compare!=0)return compare;
            compare=a.getPostazione().getUfficio().getNome().compareToIgnoreCase(b.getPostazione().getUfficio().getNome());
            if(compare!=0)return compare;
            compare=a.getPostazione().getNomePostazione().compareToIgnoreCase(b.getPostazione().getNomePostazione());
            return compare;
        });
    }

    public boolean datiCaricati(){
        return prenotazioni!=null&&prenotazioni.size()!=0;
    }

    public List<Prenotazione> getPrenotazioniFiltrate(String text) {
        return prenotazioni.stream().sorted((a,b)->a.getUtente().getDisplayName().compareToIgnoreCase(b.getUtente().getDisplayName())).filter(p->isCompreso(p.getUtente().getDisplayName(),text)).collect(Collectors.toList());
    }

    private static boolean isCompreso(String testoDaControllare,String testoCompreso){
        if(testoCompreso==null||testoCompreso.replace(" ","").isEmpty())return true;
        List<Character> testoDaControllareList=testoDaControllare.toLowerCase().replace(" ","").chars().mapToObj(c->(char)c).collect(Collectors.toList()),
                        testoCompresoList=testoCompreso.toLowerCase().replace(" ","").chars().mapToObj(c->(char)c).collect(Collectors.toList());

        boolean b=false;

        for(int i=0;i<testoDaControllareList.size();){
            char c=testoCompresoList.get(0);
            char c1=testoDaControllareList.get(i);
            if(i==3&&testoCompreso.equalsIgnoreCase("elisabS")){
                Log.d("BLOCK","block");
            }
            if(c1==c){
                testoCompresoList.remove(0);
                if(testoCompresoList.size()==0){
                    b=true;
                    break;
                }
                testoDaControllareList.remove(i);
                i=0;
            }else{
                i++;
            }
        }
        if(testoCompreso.length()>4){
            Log.d("DEBUGGING",testoDaControllare);
            Log.d("DEBUGGING",testoCompreso);
            Log.d("DEBUGGING",""+b);
        }
        return b;

    }
}
