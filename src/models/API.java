package models;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class API {
    private String _URL = "http://jsonplaceholder.typicode.com/posts";

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://www.google.com";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private int sendPost(String params) throws Exception {

        //String url = "http://www.google.com";
        URL obj = new URL(_URL);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Accept", "application/json");

        String urlParameters = params;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        /*System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);*/

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(responseCode);
        return responseCode;

    }
    public boolean sendAuthenticationData (String user,String password){
        int responseCode = 0;
        try {
            responseCode = sendPost(
                            "user[name]=" + user +
                            "&user[password]=" + password
            );
        }
        catch (Exception e1){
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
                    "user[name]=" + user +
                    "&user[password]=" + password +
                    "&statistics[score]=" + score +
                    "&statistics[max_time_heart]=" + timeOfFirstDeath +
                    "&statistics[max_time_die]=" + timeOfGame
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

