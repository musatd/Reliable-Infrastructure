package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

@Component
public class PushNotificationTask extends Task {
	
    private final static String AUTH_KEY_FCM = "AAAAGp4fPC0:APA91bFKJ0HI-CAw6n7YeE6wNOjn7z5qw1ElB-S9Rl-1V52YuIvNLzoobwOoWTKKOXJ7SvG0Jzat53YA4mFSYhQdUboEIWqatls9sOvjAPX8WJlax_1foA1knNd3E-1L8cOjNTaxNld6";
    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    
    private String SUBTITLE = "O noua alerta s-a produs";
	

	public void startTask() {
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
		        
		        
		        List<String> registration_ids = notifications.getTokens();
		        
		        JSONObject request = new JSONObject();
		        request.put("registration_ids", registration_ids);
		        
		        JSONObject data = new JSONObject();
		        data.put("title", "Reliable Infrastructure");
		        data.put("subTitle", SUBTITLE);
		        data.put("idalert", notifications.getIdalert());
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


	public String getSUBTITLE() {
		return SUBTITLE;
	}


	public void setSUBTITLE(String SUBTITLE) {
		this.SUBTITLE = SUBTITLE;
	}
	
	@Override
	public String toString() {
		return "PushNotificationTask [notifications=" + notifications + "]";
	}

}
