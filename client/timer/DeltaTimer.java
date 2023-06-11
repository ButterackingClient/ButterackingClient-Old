package client.timer;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DeltaTimer implements ActionListener {
    JFrame frame;
    JButton button;
    JButton rbutton;
    JLabel label;
    public int elapsedTime;
    public int seconds;
    public int minutes;
    public int hours;
    boolean started;
    String seconds_string;
    String minutes_string;
    String hours_string;
    public static DeltaTimer D;
    Timer timer;

    static {
        DeltaTimer.D = new DeltaTimer();
    }

    DeltaTimer() {
        this.frame = new JFrame();
        this.button = new JButton("Start");
        this.rbutton = new JButton("Reset");
        this.label = new JLabel();
        this.elapsedTime = 0;
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.started = false;
        this.seconds_string = String.format("%02d", this.seconds);
        this.minutes_string = String.format("%02d", this.minutes);
        this.hours_string = String.format("%02d", this.hours);
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final DeltaTimer this$2;
                final DeltaTimer this$0 = this$2 = DeltaTimer.this;
                this$2.elapsedTime += 1000;
                DeltaTimer.this.hours = DeltaTimer.this.elapsedTime / 3600000;
                DeltaTimer.this.minutes = DeltaTimer.this.elapsedTime / 60000 % 60;
                DeltaTimer.this.seconds = DeltaTimer.this.elapsedTime / 1000 % 60;
                DeltaTimer.this.seconds_string = String.format("%02d", DeltaTimer.this.seconds);
                DeltaTimer.this.minutes_string = String.format("%02d", DeltaTimer.this.minutes);
                DeltaTimer.this.hours_string = String.format("%02d", DeltaTimer.this.hours);
                DeltaTimer.this.label.setText(String.valueOf(String.valueOf(DeltaTimer.this.hours_string)) + ":" + DeltaTimer.this.minutes_string + ":" + DeltaTimer.this.seconds_string);
            }
        });
        this.label.setText(String.valueOf(String.valueOf(this.hours_string)) + ":" + this.minutes_string + ":" + this.seconds_string);
        this.label.setBounds(100, 100, 200, 100);
        this.label.setFont(new Font("Comic Sans MS", 0, 35));
        this.label.setBorder(BorderFactory.createBevelBorder(1));
        this.label.setOpaque(true);
        this.label.setHorizontalAlignment(0);
        this.button.setBounds(100, 200, 100, 50);
        this.button.setFont(new Font("Comic Sans MS", 0, 20));
        this.button.setFocusable(false);
        this.button.addActionListener(this);
        this.rbutton.setBounds(200, 200, 100, 50);
        this.rbutton.setFont(new Font("Comic Sans MS", 0, 20));
        this.rbutton.setFocusable(false);
        this.rbutton.addActionListener(this);
        this.frame.add(this.button);
        this.frame.add(this.rbutton);
        this.frame.add(this.label);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(400, 400);
        this.frame.setLayout(null);
        this.frame.setVisible(false);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.button) {
            this.start();
            if (!this.started) {
                this.started = true;
                this.button.setText("Stop");
                this.start();
            } else {
                this.started = false;
                this.button.setText("Start");
                this.stop();
            }
        }
        if (e.getSource() == this.rbutton) {
            this.started = false;
            this.button.setText("Start");
            this.reset();
        }
    }

    public void start() {
        this.timer.start();
    }

    public void stop() {
        this.timer.stop();
    }

    void reset() {
        this.timer.stop();
        this.elapsedTime = 0;
        this.seconds = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds_string = String.format("%02d", this.seconds);
        this.minutes_string = String.format("%02d", this.minutes);
        this.hours_string = String.format("%02d", this.hours);
        this.label.setText(String.valueOf(String.valueOf(this.hours_string)) + ":" + this.minutes_string + ":" + this.seconds_string);
    }
}
