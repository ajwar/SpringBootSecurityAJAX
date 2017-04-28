package ru.yandex.ajwar.security.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.yandex.ajwar.security.model.ServerInfo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ajwar on 21.04.2017.
 */
public class Util {
    //private static ExecutorService executorServiceLoad;

    public static ExecutorService createAndConfigureExecutorsLoadService() {
        ExecutorService executorServiceLoad = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        return executorServiceLoad;
    }

    public static String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    public static List<ServerInfo> parseResponseToList(Object object) {
        JSONArray array = new JSONArray(object.toString());
        List<ServerInfo> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = new JSONObject(array.get(i).toString());
            ServerInfo serverInfo = new ServerInfo();
            serverInfo.setName(obj.getString("name"));
            serverInfo.setIndex(obj.getString("index"));
            serverInfo.setStation(obj.getString("station"));
            serverInfo.setPort(obj.getString("port"));
            serverInfo.setAdminPort(obj.getString("adminPort"));
            serverInfo.setCount(obj.getString("count"));
            serverInfo.setVersion(obj.getString("version"));
            list.add(serverInfo);
        }
        return list;
    }

    //"http"  "127.0.0.1"   5505
    public static Object sendRequestToServer(String scheme, String host, int port, JSONObject obj) {
        URL myURL = null;
        int HttpResult = 0;
        StringBuilder sb = null;
        HttpURLConnection con = null;
        Object object = null;
        try {
            myURL = new URI(scheme, null, host, port, null, null, null).toURL();
            con = (HttpURLConnection) myURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setConnectTimeout(30000);
            con.setReadTimeout(60000);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            os.write(obj.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            sb = new StringBuilder();
            HttpResult = con.getResponseCode();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } catch (IOException e) {

            }
            object = sb.toString();
        } else {
            try {
                object = con.getResponseMessage();
            } catch (IOException e) {

            }
        }
        return object;
    }

    public static PrefixedProperty loadProperties(String nameProp) throws IOException {
        PrefixedProperty properties = new PrefixedProperty();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(nameProp)));
            properties.load(in);
        } catch (IOException e) {
            throw new IOException(nameProp + " file properties not found.");
        } finally {
            if (in != null) in.close();
        }
        if (properties.size() == 0) throw new IOException(nameProp + " properties is empty.");
        else return properties;
    }

    public static class PrefixedProperty extends Properties {

        public String getProperty(String group, int index, String key) {
            return getProperty(group + index + '.' + key);
        }

        public String getProperty(String group, int index, String key, String def) {
            return getProperty(group + index + '.' + key, def);
        }

        public int sizeGroup(Object object) throws Exception {
            if (object instanceof String) {
                String group = (String) object;
                final int[] i = {0};
                this.forEach((k, v) -> {
                    String str = (String) k;
                    if (str.startsWith(group)) i[0]++;
                });
                if (this.size() % i[0] == 0) return i[0];
                else throw new Exception("Incorrect data format.");
            } else {
                throw new Exception("Invalid parameter passed.");
            }
        }

    }

}
