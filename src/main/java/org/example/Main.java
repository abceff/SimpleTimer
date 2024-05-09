package org.example;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private final JButton startButton;
    private final JLabel timeLabel;
    private Timer timer;

    private long startTime;
    private long endTime;
    private boolean timerRunning;

    private int initialX;
    private int initialY;

    private long seconds;
    private long minutes;
    private long hours;

    public Main() {
        setSize(140, 35);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(new FlowLayout());

        startButton = new JButton("Start");
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 12));

        startButton.addActionListener(e -> {
            if (!timerRunning) {
                startTimer();
                startButton.setText("Stop");
            } else {
                stopTimer();
                startButton.setText("Start");
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int newX = getLocation().x + e.getX() - initialX;
                int newY = getLocation().y + e.getY() - initialY;
                setLocation(newX, newY);
            }
        });

        add(startButton);
        add(timeLabel);

        setVisible(true);
    }

    private void startTimer() {
        timerRunning = true;
        startTime = System.currentTimeMillis() - endTime;
        timer = new Timer(1000, e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
        });
        timer.start();
    }

    private void stopTimer() {
        timerRunning = false;
        endTime = System.currentTimeMillis() - startTime;
        timer.stop();
    }

    private void updateTimer(long elapsedTime) {
        if (seconds != 59) {
            ++seconds;
        } else {
            seconds = (elapsedTime / 1000) % 60;
            if (minutes != 59) {
                ++minutes;
            } else {
                minutes = 0;
                ++hours;
            }
        }

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(timeString);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}