package uk.ac.ncl.csc2022.team10.databasemanager;

/**
 * Created by szholdiyarov on 4/17/15.
 * Purpose: Database Helper to be used by my colleagues.
 */

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseHelper {
    private final String USER_AGENT = "Mozilla/5.0";

    public void createAccount(String id, String name, String user_id) throws Exception {
        String url = "http://sprs1994.co.uk/createcontact.php";
        URL obj = new URL(url);
        HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();
        con1.setRequestMethod("POST");
        con1.setRequestProperty("User-Agent", USER_AGENT);
        con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "recipient_id=" + id + "&name=" + name + "&user_id=" + user_id;
        con1.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con1.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    public void createContact(String name, String recipient_id, String user_id) throws Exception {
        String url = "http://sprs1994.co.uk/createcontact.php";
        URL obj = new URL(url);
        HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();
        con1.setRequestMethod("POST");
        con1.setRequestProperty("User-Agent", USER_AGENT);
        con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "name=" + name + "&recipient_id=" + recipient_id + "&user_id=" + user_id;
        con1.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con1.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    public void createWallet(String balance_money, String balance_points, String user_id, String name) throws Exception {
        String url = "http://sprs1994.co.uk/createcontact.php";
        URL obj = new URL(url);
        HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();
        con1.setRequestMethod("POST");
        con1.setRequestProperty("User-Agent", USER_AGENT);
        con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "balance_money=" + balance_money + "&balance_points=" + balance_points + "&user_id=" + user_id + "$name=" + name;
        con1.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con1.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    public void createGame(String incompetition, String points, String user_id) throws Exception {
        String url = "http://sprs1994.co.uk/createcontact.php";
        URL obj = new URL(url);
        HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();
        con1.setRequestMethod("POST");
        con1.setRequestProperty("User-Agent", USER_AGENT);
        con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "incompetition=" + incompetition + "&points=" + points + "&user_id=" + user_id;
        con1.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con1.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

}
