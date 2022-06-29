package com.example.messageviewer;

public class Recieverdatastore {

    public Recieverdatastore()
    {}

    String Sender;
    String Date;
    String Message;
    String Time;
    String Caller;
    String Recorded_Call;

    public String getCaller() {
        return Caller;
    }

    public void setCaller(String caller) {
        Caller = caller;
    }

    public String getRecorded_Call() {
        return Recorded_Call;
    }

    public void setRecorded_Call(String recorded_Call) {
        Recorded_Call = recorded_Call;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Recieverdatastore(String sender, String date, String message, String time, String caller, String recorded_Call) {
        Sender = sender;
        Date = date;
        Message = message;
        Time = time;
        Caller = caller;
        Recorded_Call = recorded_Call;
    }




}
