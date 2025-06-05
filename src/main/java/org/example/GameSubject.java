package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public interface GameSubject {
    void addObserver(GameObserver o);
    void notifyObservers(String resultaat);
}


class TimerPopup implements GameSubject {
    private JFrame frame;
    private JLabel timerLabel;
    private java.util.Timer timer;
    private int seconden;

    private final List<GameObserver> observers = new ArrayList<>();

    public TimerPopup(int seconden) {
        this.seconden = seconden;
        createUI();
        startTimer();
    }

    private void createUI() {
        frame = new JFrame("⏳ Tijd loopt...");
        frame.setSize(300, 140);
        frame.setUndecorated(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel("⏳", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        panel.add(iconLabel, BorderLayout.NORTH);

        timerLabel = new JLabel("Tijd over: " + seconden + "s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        timerLabel.setForeground(new Color(30, 144, 255));
        panel.add(timerLabel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    seconden--;
                    if (seconden >= 0) {
                        int min = seconden / 60;
                        int sec = seconden % 60;
                        timerLabel.setText(String.format("Tijd over: %02d:%02d", min, sec));
                    }
                    if (seconden <= 0) {
                        stop();
                        notifyObservers("fout");  // Notificeer de monsters
                        JOptionPane.showMessageDialog(null,
                                "⏰ De tijd is op!\n💀 TikTakulus, de Tijdeter verschijnt uit het niets!",
                                "Tijd Op", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
        }, 1000, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (frame != null) {
            frame.dispose();
        }
    }

    @Override
    public void addObserver(GameObserver o) {
        observers.add(o);
    }


    @Override
    public void notifyObservers(String resultaat) {
        for (GameObserver o : observers) {
            o.update(resultaat);
        }
    }
}

