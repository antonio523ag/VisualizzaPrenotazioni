package dev.antoniogrillo.prenotazioneposti.async;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dev.antoniogrillo.prenotazioneposti.data.Database;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallData implements SingleOnSubscribe<String> {

    private String token;

    public CallData(String token) {
        this.token = token;
    }

    @Override
    public void subscribe(@NonNull SingleEmitter<String> emitter){

        try {

        String startDate= LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate= LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String url="https://easyhour.app/easyhour/api/v2/wallchart-report?withStateInfo=true&startDate="+startDate+"&endDate="+endDate;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Authorization", "Bearer "+token)
                    .addHeader("Accept", "*/*")
                    //.addHeader("Accept-Encoding", "gzip, deflate, br")
                    //.addHeader("Accept-Language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6,pt;q=0.5,co;q=0.4")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("content-type", "application/json")
                    .addHeader("Cookie", "__stripe_mid=ad1fd88f-48f0-40de-9139-022f365382d2251a54; _ga=GA1.1.1458071597.1678957875; __stripe_sid=49cf7307-4ef1-4f0e-94ef-0ae7e6a972b5b5deed; _ga_KMD523CBCW=GS1.1.1679404121.6.0.1679404127.0.0.0")
                    .addHeader("Host", "easyhour.app")
                    .addHeader("Referer", "https://easyhour.app/web/")
                    .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                    .build();
            Response response = client.newCall(request).execute();
                //System.out.println(response.body());
                //System.out.println(response.body().string().toString());
            Database.getInstance().setPrenotazioni(response.body().string());
                emitter.onSuccess("fatto");
        } catch (Exception e) {

           emitter.onError(e);
        }
    }
}