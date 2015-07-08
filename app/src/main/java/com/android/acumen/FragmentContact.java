package com.android.acumen;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FragmentContact extends Fragment {

    private EditText phoneField;
    private EditText emailField;
    private EditText nameField;
    private EditText messageField;
    private Button submitButton;

    public FragmentContact(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        submitButton = (Button)rootView.findViewById(R.id.contact_submit);
        phoneField = (EditText)rootView.findViewById(R.id.contact_phoneField);
        emailField = (EditText)rootView.findViewById(R.id.contact_emailField);
        nameField = (EditText)rootView.findViewById(R.id.contact_nameField);
        messageField = (EditText)rootView.findViewById(R.id.contact_messageField);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageField.getText().toString();
                String phone = phoneField.getText().toString();
                String email = emailField.getText().toString();
                String name = nameField.getText().toString();

                new PostData(name, phone, email, message).execute();

            }
        });

        return rootView;
    }
}

class PostData extends AsyncTask<String, Void, String> {

    String message, phone, email, name;

    public PostData(String name, String phone, String email, String message){
        this.message = message;
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    protected String doInBackground(String... urls) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("name",name));
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("phone",phone));
        nameValuePairs.add(new BasicNameValuePair("message",message));
        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new
                    HttpPost("http://pg18.comeze.com/apps/sample/contact.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            Log.i("postData", response.getStatusLine().toString());
        }

        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        return null;
    }

    protected void onPostExecute(String str) {

    }
}

