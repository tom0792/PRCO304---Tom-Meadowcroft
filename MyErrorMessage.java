package com.example.parkhappy3;
import org.json.JSONException;
import org.json.JSONObject;


public class MyErrorMessage {

    private boolean status;
    private String error;

    public MyErrorMessage() {
    }

    public MyErrorMessage(String error) {
        this.status = false;
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("error", error);
            jsonObject.put("status", status);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}