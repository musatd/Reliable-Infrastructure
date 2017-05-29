package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;


public class PushNotificationTask extends Task{
	
    private final static String AUTH_KEY_FCM = "AAAAo8JQZUc:APA91bFWRrDSogQq45LJI6hrjFDGReDBhyMyrJyQScznfNE5iTQ1wXbQlNeNkhwKSLiigEn-6ZiYo3zGTRbkwwW6Ov1ER2fjnSSHeXTqMyUMJrK3foQbWIKl9cJQXaK5FcgKLCbQPrld";
    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	

	public void startTask() {
		// TODO Auto-generated method stub
		String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

	        URL url;
			try {
				url = new URL(FMCurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setUseCaches(false);
		        conn.setDoInput(true);
		        conn.setDoOutput(true);

		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Authorization", "key=" + authKey);
		        conn.setRequestProperty("Content-Type", "application/json");
		        
		        
		        /* Topic response
		         * Response Code : 200
					{"message_id":9149783501663843852}
		         */
		        List<String> registration_ids = notifications.getTokens();
		        //registration_ids.add("d2IHkqkbZJc:APA91bF6d5LZuy1W6Ozf4mNl3e86Kf5CT3Tu8law80J_2dBbpZoGPJK9zlmzoPxR6m7QyMimV6zKBzczwxaDJicpCIE1IWCat_bu_ImVKkuoIo1AlSr4yfU53oxyLKUqj612Hh1b3NCe");
		        //registration_ids.add("cHrGVJ-Hetc:APA91bHOblkp4gLyToq_m0cs87jPCO82gDty1lV12v3d4sZqmAT73U5ZIGVl02GWejUB4NtoGRa_z3GpAF9v4PBXPKztZ38fA4popWnTNhUanxICtjTjBENGKrFPxU3hT-R47wgJ-_T3");

		        JSONObject request = new JSONObject();
		        request.put("registration_ids", registration_ids);
		        
		        JSONObject data = new JSONObject();
		        data.put("title", "Reliable Infrastructure"); // Notification title
		        data.put("subTitle", "O noua alerta s-a produs"); // Notification body
		        data.put("click_action", "com.google.firebase.quickstart.fcm.SecondActivity");
		        data.put("body", notifications.getMessage());
		        request.put("data", data);

		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(request.toString());
		        wr.flush();

		        int responseCode = conn.getResponseCode();
		        System.out.println("Response Code : " + responseCode);

		        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();

		        while ((inputLine = in.readLine()) != null) {
		            response.append(inputLine);
		        }
		        in.close();
		        
		        System.out.println(response.toString());
		        wr.close();
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
	}

}
