package models;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class API {
    private String _URL_AUTH = "http://guarded-basin-88343.herokuapp.com/user/auth";
    private String _URL_STAT = "http://guarded-basin-88343.herokuapp.com/statistics";

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    private int sendGet() throws Exception {

        String url = "http://www.google.com";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result


        return responseCode;

    }

    // HTTP POST request
    private int sendPost(String params,String _url) throws Exception {

        //String url = "http://www.google.com";
        URL obj = new URL(_url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Cache-Control", "no-cache");
        con.setRequestProperty("Connection", " ");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "multipart/form-data");


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();


        return responseCode;

    }
    public boolean sendAuthenticationData (String user,String password){
        int responseCode = 0;

        try {
            responseCode = sendPost(
                            "user[email]=" + user +
                            "&user[password]=" + password ,
                            _URL_AUTH
            );
        }
        catch (Exception e1){
            System.out.println("Error");
            return false;

        }

        if (responseCode == 200)
            return true;
        return false;
    }
    public boolean sendEndOfGameStatistics(String user, String password, int score, int timeOfFirstDeath, int timeOfGame)  {
        int responseCode = 0;
        try {
            responseCode =sendPost(
                    "user[email]=" + user +
                    "&user[password]=" + password +
                    "&statistic[score]=" + score +
                    "&statistic[max_time_heart]=" + timeOfFirstDeath +
                    "&statistic[max_time_die]=" + timeOfGame,
                    _URL_STAT
            );
        }
        catch (Exception e1){
            return false;
        }
        if (responseCode == 200)
            return true;
        return false;
    }
    public void test() {


        try {
            System.out.println("Testing 1 - Send Http GET request");
            this.sendGet();
        }
        catch (Exception e1){
            System.out.println("Error");
        }

        //System.out.println("\nTesting 2 - Send Http POST request");
        //this.sendPost();

    }

}

