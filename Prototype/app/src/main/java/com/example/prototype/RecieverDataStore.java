package com.example.prototype;

public class RecieverDataStore {


    String Patient_First_Name;
    String Patient_Last_Name;

    public String getPatient_First_Name() {
        return Patient_First_Name;
    }

    public void setPatient_First_Name(String patient_First_Name) {
        Patient_First_Name = patient_First_Name;
    }

    public String getPatient_Last_Name() {
        return Patient_Last_Name;
    }

    public void setPatient_Last_Name(String patient_Last_Name) {
        Patient_Last_Name = patient_Last_Name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZip_Code() {
        return Zip_Code;
    }

    public void setZip_Code(String zip_Code) {
        Zip_Code = zip_Code;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    String DOB;
    String Gender;
    String Address;
    String City;
    String Zip_Code;
    String Phone_Number;
    String eMail;

    public RecieverDataStore(String patient_First_Name, String patient_Last_Name, String DOB, String gender, String address, String city, String zip_Code, String phone_Number, String eMail) {
        Patient_First_Name = patient_First_Name;
        Patient_Last_Name = patient_Last_Name;
        this.DOB = DOB;
        Gender = gender;
        Address = address;
        City = city;
        Zip_Code = zip_Code;
        Phone_Number = phone_Number;
        this.eMail = eMail;
    }

    public RecieverDataStore() {
    }
}
