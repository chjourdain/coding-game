import java.util.*;
import java.io.*;
import java.math.*;


class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int i = 0;
        String output = "";
        loop:   while(true) {
            String stat = in.next();

            switch (stat) {
                case "inc" : i = i +1; break;
                case "dec" : i = i -1; break;
                case "exit" : break loop;
                case "double" : i = 2* i; break;
                case "half" : i = i /2 ; break;
                case "print" : output = output + int;

            }

        }

        System.err.println(i);

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(i);
    }
}