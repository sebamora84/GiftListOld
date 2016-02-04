package com.mlstuff.apps.giftlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sebastian on 9/28/2015.
 */
public class WebConnection {

    public String GetJsonItems(String urlRequested, String params)
    {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlRequested);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            byte[] outputInBytes = params.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes );
            os.close();
            conn.connect();
            int status = conn.getResponseCode();

            switch (status) {
                case HttpURLConnection.HTTP_OK:
                case 201:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
