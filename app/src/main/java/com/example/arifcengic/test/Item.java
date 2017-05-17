package com.example.arifcengic.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arifcengic on 5/15/17.
 */

public class Item {
    public String Code; //ASSUMPTION Code is Unique in data set/file
    public String Description; //ASSUMPTION required field
    public String Ordinance; //ASSUMPTION required field
    public String Fine; //ASSUMPTION required field
    public String Pen1="";
    public String Pen2="";
    public String Image="";
    public String Markings="";
    public String Print="";
    public String ValidLoc="";
    public String Message="";
    public String Pen3="";
    public String Pen4="";
    public String Title="";


    public Item(String line){
        String[] values = line.split("\\|");

        //remove whitespace (could use regex but this is more obvious)
        for(int i=0; i < values.length; i++) {
            values[i] = values[i].trim();
        }

    try {
        this.Code = values[0];
        this.Description = values[1];
        this.Ordinance = values[2];
        this.Fine = values[3];
        this.Pen1 = values[4];
        this.Pen2 = values[5];
        this.Image = values[6];
        this.Markings = values[7];
        this.Print = values[8];
        this.ValidLoc = values[9];
        this.Message = values[10];
        this.Pen3 = values[11];
        this.Pen4 = values[12];
        this.Title = values[13];
    } catch (ArrayIndexOutOfBoundsException e)
    {
        // Simple way to finish if no values are available
    }

    }
}