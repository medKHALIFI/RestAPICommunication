package com.java2blog.androidrestjsonexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmit,btnPost;
    String responseText;
    StringBuffer response;
    URL url;
    Activity activity;
    ArrayList<Poto> potos=new ArrayList<Poto>();
    private ProgressDialog progressDialog;
    ListView listView;

      // In case if you deploy rest web service, then use below link and replace below ip address with yours
    //http://192.168.2.22:8080/JAXRSJsonExample/rest/countries
    
    //Direct Web services URL
    private String path = "http://192.168.43.147/potos.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            activity = this;
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnPost =(Button) findViewById(R.id.btnPost);
            listView = (ListView) findViewById(android.R.id.list);
            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(MainActivity.this, PostActivity.class);
                    startActivity(myIntent);
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    potos.clear();
                    //Call WebService
                    new GetServerData().execute();
                }
            });
        }

        class GetServerData extends AsyncTask
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Fetching potos data");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return getWebServiceResponseData();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                // Dismiss the progress dialog
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // For populating list data
                /// your treaitement
                CustomCountryList customCountryList = new CustomCountryList(activity, potos);
                listView.setAdapter(customCountryList);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Toast.makeText(getApplicationContext(),"You Selected "+potos.get(position).getPotoName()+ "as poto",Toast.LENGTH_SHORT).show();        }
                });
            }
        }
        protected Void getWebServiceResponseData() {

            try {

                url = new URL(path);
                Log.d(TAG, "ServerData: " + path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                // soit get soit post
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                Log.d(TAG, "Response code: " + responseCode);
                // oky is 200
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Reading response from input Stream
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();

                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }
                    in.close();
                }}
             catch(Exception e){
                    e.printStackTrace();
            }

                responseText = response.toString();
                //Call ServerData() method to call webservice and store result in response
              //  response = service.ServerData(path, postDataParams);
                Log.d(TAG, "data:" + responseText);
                try {
                    JSONArray jsonarray = new JSONArray(responseText);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int id = jsonobject.getInt("id");
                        String poto = jsonobject.getString("PotoName");
                        Log.d(TAG, "id:" + id);
                        Log.d(TAG, "poto:" + poto);
                        Poto potoObj=new Poto(id,poto);
                        potos.add(potoObj);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
    }
