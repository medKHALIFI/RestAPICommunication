package com.java2blog.androidrestjsonexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class PostActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText mEdit,passedit;
    public String nom = "" ;
    public String pass = "" ;
    TextView tvName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);
        btnSubmit = (Button) findViewById(R.id.BtnPostSubmit);
        mEdit   = (EditText)findViewById(R.id.editTextnom);
        passedit  = (EditText)findViewById(R.id.editTextpass);

        tvName = (TextView)findViewById(R.id.textViewResult);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //potos.clear();
                //Call WebService
               // new MainActivity.GetServerData().execute();
                new RequestAsync().execute();
                nom =mEdit.getText().toString() ;
                pass = passedit.getText().toString() ;
            }
        });
    }

    public class RequestAsync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nom", nom);
                postDataParams.put("password", pass);
            //    postDataParams.put("phone", "+1111111111");
                Log.d(TAG, "ServerData: " + postDataParams);

                Log.d(TAG, "ServerData: " + RequestHandler.sendPost("http://192.168.43.147:51252/home/MEPOSTval",postDataParams));

                return RequestHandler.sendPost("http://192.168.43.147:51252/home/MEPOSTval",postDataParams);

            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                tvName.setText(s);

            }else{
                Toast.makeText(getApplicationContext(), "s is null", Toast.LENGTH_LONG).show();
            }

        }


    }
}
