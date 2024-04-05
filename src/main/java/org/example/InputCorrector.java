package org.example;

import java.util.Scanner;

public class InputCorrector {
    public static double errorCheck(String q) {
        //method for checking if the input from user is a double which cannot be negative.
        Scanner reader = new Scanner(System.in);
        boolean n = true;
        double val = 0;
        double holder;
        while (n) {
            System.out.println(q);
            String l = reader.next();
            try {
                holder = Double.parseDouble(l);
                val = holder;
                if (val <= 0) {
                    System.out.println("Invalid input. Try again");
                } else {
                    n = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again");
            }
        }
        return val;
    }
    public static int convDouble(String q){
        //method for checking that the value is an integer, so it will be okay to convert from a double.
        double val=0;
        boolean n=true;
        while (n) {
            val = errorCheck(q);
            if (val % 2 == 0 || (val + 1) % 2 == 0) {
                n = false;
            } else {
                System.out.println("Value must be an integer. Try again.");
            }
        }
        return (int) val;
    }
    static int yN(String q){
        //method for changing a variable when the user has input 1 into the console when asked. If 2 is entered it doesn't change the value from the original value.
        boolean n = true;
        int yesNo=0;
        while(n){
            yesNo = convDouble(q);
            if (yesNo == 1 || yesNo==2){
                n = false;
            } else{
                System.out.println("Incorrect value entered. Try again.");
            }
        }
        return yesNo;
    }

}
