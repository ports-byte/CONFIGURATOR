package com.atlas;

import gnu.io.CommPortIdentifier;

import javax.swing.*;

public class QueryThread extends SDWAN {
    Boolean portLive = false;
    public void run() {
        System.out.println("In qt");
        while (!portLive) {

            try {
                Thread.sleep(2000);
            } catch (Exception f) {
                System.out.print(f);
            }
        }
    }
}
