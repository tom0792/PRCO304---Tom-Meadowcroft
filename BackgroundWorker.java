package com.example.parkhappy3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class
BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx) {
        context = ctx;


    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://34.142.36.87/login.php";
        String register_url = "http://34.142.36.87/register.php";
        if (type.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(type.equals("register")) {
            try {
                String user_name = params[1];
                String password = params[2];
                String password2 = params[3];
                String age = params[4];
                String email = params[5];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"
                        + URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8")+"&"
                        + URLEncoder.encode("password2", "UTF-8")+"="+URLEncoder.encode(password2, "UTF-8")+"&"
                        + URLEncoder.encode("age", "UTF-8")+"="+URLEncoder.encode(age, "UTF-8")+"&"
                        + URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }






    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Update");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        if (result.contains("success ")) {
            result = result.replaceAll("\\Wsuccess\\W|^success\\W|\\Wsuccess$", "");
            Toast toast = Toast.makeText(context, "Welcome back ", Toast.LENGTH_LONG);
            toast.show();
            context.startActivity(new Intent(context, UserActivity.class));

        } else if (result.contains("account ")) {
            Toast toast = Toast.makeText(context, "Account created ",  Toast.LENGTH_LONG);
            toast.show();
            context.startActivity(new Intent(context, UserActivity.class));

        } else if (result.contentEquals("match ")) {

            Toast toast = Toast.makeText(context, "both passwords entered must match", Toast.LENGTH_LONG);
            toast.show();

        } else if (result.contentEquals("empty ")) {

            Toast toast = Toast.makeText(context, "please enter a username and password", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("empty2 ")) {

            Toast toast = Toast.makeText(context, "please fill out all fields", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("empty3 ")) {

            Toast toast = Toast.makeText(context, "please enter a username", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("empty4 ")) {

            Toast toast = Toast.makeText(context, "please enter a password", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("user exists ")) {

            Toast toast = Toast.makeText(context, "an account using these details already exists please login", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("password ")) {

            Toast toast = Toast.makeText(context, "please include a special character in your password (?!%&*)", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("username ")) {

            Toast toast = Toast.makeText(context, "invalid characters entered please only input text/numbers", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("email ")) {

            Toast toast = Toast.makeText(context, "please enter a valid email address", Toast.LENGTH_LONG);
            toast.show();

        } else if (result.contentEquals("age ")) {

            Toast toast = Toast.makeText(context, "please only enter numbers for your age", Toast.LENGTH_LONG);
            toast.show();

        } else if (result.contentEquals("age2 ")) {

            Toast toast = Toast.makeText(context, "users must be at least 17 years old", Toast.LENGTH_LONG);
            toast.show();

        } else if (result.contentEquals("characters ")) {

            Toast toast = Toast.makeText(context, "username must be more than 5 characters", Toast.LENGTH_LONG);
            toast.show();
        } else if (result.contentEquals("characters2 ")) {

            Toast toast = Toast.makeText(context, "password must be more than 5 characters", Toast.LENGTH_LONG);
        } else {
            Toast toast = Toast.makeText(context, "Email or password is incorrect please try again", Toast.LENGTH_LONG);
            toast.show();

        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

}


