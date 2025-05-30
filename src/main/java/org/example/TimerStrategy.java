
package org.example;

import java.util.Timer;
import java.util.TimerTask;

public interface TimerStrategy {
    void start();
    void stop();
}

class CliTimerStrategy implements TimerStrategy {
    private Timer timer;
    private int seconden;

    public CliTimerStrategy(int seconden) {
        this.seconden = seconden;
    }

    @Override
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int remaining = seconden;
            public void run() {
                remaining--;
                if (remaining <= 0) {
                    System.out.println("⏰ Tijd is op! TikTakulus verschijnt!");
                    stop();
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}

