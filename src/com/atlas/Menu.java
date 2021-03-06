package com.atlas;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

public class Menu extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton NEButton;
    private JButton MIAButton;
    private JButton IPVPNButton;
    private JButton comQueryBtn;
    private JButton sdwanBtn;
    private JButton mysteryBtn;

    void menu() {
        System.out.println("in menu");
        comQueryBtn.addActionListener(this);
        comQueryBtn.setActionCommand("comQuery");
        sdwanBtn.addActionListener(this);
        sdwanBtn.setActionCommand("sdwan");
        mysteryBtn.addActionListener(this);
        mysteryBtn.setActionCommand("mystery");

        this.setLocationRelativeTo(null);
        this.setTitle("1BEER");
        panel.setSize(3000, 3000);
        panel.setVisible(true);
        SwingUtilities.invokeLater(this::createWindow);

    }

    void createWindow() {
        this.add(panel);
        this.setVisible(true);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("comQuery")) {
            Boolean serialBool = false, ciscoBool = false, sdwanBool = false;
            System.out.println("Determining COM ports...");
            SerialPort[] ports = SerialPort.getCommPorts();
            ArrayList<String> portList = new ArrayList<>();
            for (SerialPort port : ports) {
                portList.add(port.getSystemPortName());
                String portInfo = " " + port.getPortDescription() + " " + port.getBaudRate() + "\n";
                portList.add(portInfo);
            }
            System.out.println(portList);
            JOptionPane.showMessageDialog(this, "List of available ports: \n " + portList);

            for (int i = 0; i < portList.size(); i++) {
                if (portList.get(i).contains("USB Serial Port Emulator")) {
                    System.out.println("Serial port detected");
                    serialBool = true;
                }
            }
            JOptionPane.showMessageDialog(this, "Based on the search results, the following is available: " + "\nNTUs: " + serialBool + "\nCisco: " + ciscoBool + "\nSDWAN: " + sdwanBool);
        }
        if (cmd.equals("sdwan")) {
            SDWAN sd = new SDWAN();
            sd.sdwanStart();
        }
        if (cmd.equals("mystery")) {
            JOptionPane.showConfirmDialog(this, "Congrats! You are retarded!");
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
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        NEButton = new JButton();
        NEButton.setText("NE");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(NEButton, gbc);
        MIAButton = new JButton();
        MIAButton.setText("MIA");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(MIAButton, gbc);
        IPVPNButton = new JButton();
        IPVPNButton.setText("IPVPN");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(IPVPNButton, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 72, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("1BEER");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label1, gbc);
        comQueryBtn = new JButton();
        comQueryBtn.setText("QUERY YOUR COMPORTS");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comQueryBtn, gbc);
        sdwanBtn = new JButton();
        sdwanBtn.setText("SDWAN");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(sdwanBtn, gbc);
        mysteryBtn = new JButton();
        mysteryBtn.setText("? Mystery ?");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(mysteryBtn, gbc);
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
        return panel;
    }

}
