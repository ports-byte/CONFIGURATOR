package com.atlas;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;



import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SDWAN extends JFrame implements ActionListener, Network_iface {
    private JPanel panel1;
    private JButton liftOffBtn;
    private JLabel portReady;
    private static net.Network network;

    void sdwanStart() {
        this.setLocationRelativeTo(null);
        this.setTitle("1BEER");

        liftOffBtn.addActionListener(this);
        liftOffBtn.setActionCommand("LIFT OFF");

        panel1.setSize(3000, 3000);
        panel1.setVisible(true);
        SwingUtilities.invokeLater(this::createWindow);

        Runnable drawRunnable = () -> tryPort();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(drawRunnable, 0, 2, TimeUnit.SECONDS);

    }

    void createWindow() {
        this.add(panel1);
        this.setVisible(true);
        this.pack();
    }

    void open() {
        System.out.println("Here we go...!");
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM4");
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println(portIdentifier.getCurrentOwner());
                System.out.println("Port is ready to go!");
            } else {
                System.out.println("Can't find the port");
                JOptionPane.showMessageDialog(this, "Check the box is ON and plugged in");
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Oh shit something went wrong! Exception: " + e);
        }
    }

    void connect(String portName) throws Exception {
        System.out.println(System.getProperty("java.library.path"));
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialWriter(out))).start();

                serialPort.addEventListener(new SerialReader(in));
                serialPort.notifyOnDataAvailable(true);

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    void tryPort() {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM4");
            if (!portIdentifier.isCurrentlyOwned()) {
                System.out.println(portIdentifier.getCurrentOwner());

                System.out.println("Port is ready to go!");
                portReady.setText("PORT READY!");
                portReady.setBackground(Color.GREEN);
            } else {
                System.out.println("Can't find the port");
                portReady.setText("PORT NOT READY!");
                portReady.setBackground(Color.red);
                //JOptionPane.showMessageDialog(this, "Check the box is ON and plugged in");
            }
        } catch (Exception e) {
            System.out.println(e);
            //JOptionPane.showMessageDialog(this, "Oh shit something went wrong! Exception: " + e);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("LIFT OFF")) {
            try {
                connect("COM4");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example.
     */
    public static class SerialReader implements SerialPortEventListener {
        BufferedReader br;
        private InputStream in;
        private byte[] buffer = new byte[1024];

        public SerialReader(InputStream in) throws UnsupportedEncodingException {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            this.in = in;
        }

        public void serialEvent(SerialPortEvent arg0) {
            int data;
            try {
                int len = 0;
                while ((data = in.read()) > -1) {
                    if (data == '\n') {
                        //System.out.println('\n');
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                System.out.print(new String(buffer, 0, len, StandardCharsets.UTF_8) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    /**
     *
     */
    public static class SerialWriter implements Runnable {
        OutputStream out;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
                int c = 0;
                while ((c = System.in.read()) > -1) {
                    this.out.write(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setAutoscrolls(true);
        panel1.setBackground(new Color(-14737633));
        panel1.setForeground(new Color(-1050113));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 48, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1050113));
        label1.setText("Plug in the USB. Press the button.");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        liftOffBtn = new JButton();
        liftOffBtn.setActionCommand("");
        liftOffBtn.setBackground(new Color(-16777216));
        Font liftOffBtnFont = this.$$$getFont$$$(null, -1, 48, liftOffBtn.getFont());
        if (liftOffBtnFont != null) liftOffBtn.setFont(liftOffBtnFont);
        liftOffBtn.setForeground(new Color(-1050113));
        liftOffBtn.setHideActionText(true);
        liftOffBtn.setText("LIFT OFF");
        panel1.add(liftOffBtn, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        portReady = new JLabel();
        Font portReadyFont = this.$$$getFont$$$(null, -1, 48, portReady.getFont());
        if (portReadyFont != null) portReady.setFont(portReadyFont);
        portReady.setText("PORT READY?");
        panel1.add(portReady, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }


    @Override
    public void writeLog(int id, String text) {

    }

    @Override
    public void parseInput(int id, int numBytes, int[] message) {

    }

    @Override
    public void networkDisconnected(int id) {

    }
}
