package uk.ac.ncl.csc2022.team10.notificationmanager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertId {
    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP POST request
    public void sendPost(String param) throws Exception {

        String url = "http://zholdiyarov.com/insertid.php";
        URL obj = new URL(url);
        HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();
        con1.setRequestMethod("POST");
        con1.setRequestProperty("User-Agent", USER_AGENT);
        con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "id=" + param;
        System.out.println(urlParameters);
        con1.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con1.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con1.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con1.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }

}