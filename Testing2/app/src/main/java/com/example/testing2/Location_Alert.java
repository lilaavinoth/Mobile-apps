package com.example.testing2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Telephony;
import android.util.Log;
import android.view.GestureDetector;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.logging.Logger;

import static android.content.Context.MODE_PRIVATE;

public class Location_Alert {

    public static String[] theirSplitedAddress = new String[0];
    public static int theirAddressLength = 0;
    public static String[] mySplitedAddress = new String[0];
    public static int myAddressLength = 0;
    public static String thspllitedState = "w";
    public static String myspllitedState = "q";

    public static int notifystate = 0;

    public static final String SHARED_PREFS = "sharedPrefs";

    public static void main(String theiraddress, String myaddress) {

//        String location = "26 5 th coross west, Lingam Nagar, Thillai Nagar, Tiruchirappalli, Tamil Nadu 600103, India";

        if (theiraddress.length() != 0)
        {
            theirSplitedAddress = theiraddress.split(",");
            theirAddressLength = theirSplitedAddress.length;
            String[] parta = theirSplitedAddress[theirAddressLength-2].split("(?<=\\D)(?=\\d)");
            thspllitedState = (parta[0]);
        }


        if (myaddress.length() != 0)
        {
            mySplitedAddress = myaddress.split(",");
            myAddressLength = mySplitedAddress.length;
            String[] partb = mySplitedAddress[myAddressLength-2].split("(?<=\\D)(?=\\d)");
            myspllitedState = (partb[0]);
        }




        if (thspllitedState.equals(myspllitedState))
        {
            if (theirSplitedAddress[theirAddressLength - 3].equals(mySplitedAddress[myAddressLength - 3]))
            {
                System.out.println("Both are in same city");
                notifystate = 1;
            }

        }
        else notifystate = 0;

//        Boolean stringExists = substringExistsInArray(splited[addressLength-2], State);
//        System.out.println(stringExists);

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean substringExistsInArray(String inputStr, String[] array) {
        return Arrays.stream(array).parallel().anyMatch(inputStr::contains);
    }

}
