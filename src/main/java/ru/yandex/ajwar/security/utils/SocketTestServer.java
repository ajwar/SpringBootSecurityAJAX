package ru.yandex.ajwar.security.utils;

import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


/**
 * Created by Ajwar on 14.04.2017.
 */
public class SocketTestServer {
    public static JSONObject sendCmd() throws IOException {
        Socket socket=null;
        try {
            socket=new Socket("127.0.0.1",Integer.parseInt("5505"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj=new JSONObject();
        obj.put("goal","get_list_servers");
        //obj.put("index","0");
        byte[] temp=null;
        try {
            temp=obj.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ObjectEncoderOutputStream sockOut=null;
        sockOut=new ObjectEncoderOutputStream(new BufferedOutputStream(socket.getOutputStream()));int sum=temp.length;
        System.out.println(sum);
        sockOut.writeInt(sum);
        sockOut.write(temp);
        //sockOut.writeUTF(file.getLogin());
        //sockOut.writeInt((int)file.length());
        sockOut.flush();
        return new JSONObject();
    }

    public static void main(String[] args) throws IOException {
        //sendRequestToServer();

        /*JSONObject obj=new JSONObject();
        obj.put("goal","get_list_servers");
        System.out.println(obj.toString());
        sendCmd();*/
    }
   /* public static void sendRequestToServer(String http,String host,String port,JSONObject obj) throws IOException {
        URL myURL=null;
        try {
            try {
                myURL=new URI("http",null,"127.0.0.1",5505,null,null,null).toURL();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            //myURL=new URL("HTTP","localhost",Integer.parseInt("5505"),null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con= (HttpURLConnection) myURL.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Accept", "application/json");
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //con.setRequestProperty("Access-Control-Allow-Origin: *");
        con.setDoOutput(true);
        con.setDoInput(true);
        //JSONObject obj=new JSONObject();
        //obj.put("goal","get_list_servers");
        OutputStream os = con.getOutputStream();
        os.write(obj.toString().getBytes("UTF-8"));
        os.flush();
        os.close();


        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(con.getResponseMessage());
        }
    }*/
}
