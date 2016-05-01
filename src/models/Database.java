package models;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Database {

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
    private void sendPost(String params) throws Exception {

        String url = "http://www.google.com";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = params;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
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
    public int sendAuthenticationData (String user,String password){
        try {
            sendPost("user=" + user + "&password=" + password);
        }
        catch (Exception e1){
            return -1;
        }
        return 0;
    }
    public int sendEndOfGameStatistics(String user, int score, int timeOfFirstDeath, int timeOfGame)  {
        try {
            sendPost("user=" + user + "&score=" + score + "&timeOfFirstDeath=" + timeOfFirstDeath + "&timeOfGame=" + timeOfGame);
        }
        catch (Exception e1){
            return -1;
        }
        return 0;
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

