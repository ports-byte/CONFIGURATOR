package com.atlas;

import gnu.io.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {

    public static void main(String[] s) {
        SerialPort serialPort = null;
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM3");
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Port in use!");
            } else {
                System.out.println(portIdentifier.getName());

                serialPort = (SerialPort) portIdentifier.open(
                        "ListPortClass", 300);
                int b = serialPort.getBaudRate();
                System.out.println(Integer.toString(b));
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                serialPort.setInputBufferSize(65536);
                serialPort.setOutputBufferSize(4096);

                System.out.println("Opened " + portIdentifier.getName());
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();
            }
        } catch (IOException | PortInUseException ex) {
            System.out.println("IOException : " + ex.getMessage());
        } catch (UnsupportedCommOperationException ex) {
            System.out.println("UnsupportedCommOperationException : " + ex.getMessage());
        } catch (NoSuchPortException ex) {
            System.out.println("NoSuchPortException : " + ex.getMessage());
        } finally {
            if (serialPort != null) {
                serialPort.close();
            }
        }

    }

    public static class SerialReader implements Runnable {
        InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int len = -1;
            try {
                while ((len = this.in.read(buffer)) > -1) {
                    //System.out.println("Received a signal.");
                    System.out.print(new String(buffer, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SerialWriter implements Runnable {
        OutputStream out;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
                    String s = "ipconfig";
                    byte[] array = s.getBytes();

                    out.write(array);
                    out.flush();
                    Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
