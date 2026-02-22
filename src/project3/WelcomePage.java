package project3;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {

        setTitle("Smart Task Manager");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== LEFT SIDE PANEL =====
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33,37,41));
        leftPanel.setPreferredSize(new Dimension(250,0));
        leftPanel.setLayout(new GridLayout(6,1,15,15));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40,20,40,20));

        JLabel logo = new JLabel("TASK MANAGER", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leftPanel.add(logo);

        JButton dashboardBtn = createButton("ENTER DASHBOARD");
        JButton aboutBtn = createButton("ABOUT");
        JButton settingsBtn = createButton("SETTINGS");
        JButton exitBtn = createButton("EXIT");

        leftPanel.add(dashboardBtn);
        leftPanel.add(aboutBtn);
        leftPanel.add(settingsBtn);
        leftPanel.add(exitBtn);

        add(leftPanel, BorderLayout.WEST);

        // ===== RIGHT SIDE PANEL =====
        JPanel rightPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0,0,new Color(72,149,239),
                        getWidth(),getHeight(),
                        new Color(58,12,163));
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        rightPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("SMART TASK MANAGER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Organize • Track • Complete");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitle.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        rightPanel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20,0,0,0);
        rightPanel.add(subtitle, gbc);

        add(rightPanel, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        dashboardBtn.addActionListener(e -> {
            new TaskManagerUI().setVisible(true);
            dispose();
        });

        aboutBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Smart Task Manager\nVersion 2.0\nDeveloped in Java Swing",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        settingsBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Settings Coming Soon!",
                        "Settings",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        exitBtn.addActionListener(e -> System.exit(0));
    }

    private JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(52,58,64));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage().setVisible(true));
    }
}