import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    JButton button1;
    JButton button3;
    JButton button4;
    JButton button5;
    JLabel timeDisplay;
    JLabel pausePlayDisplay;
    Timer timer;
    int seconds = 0;
    int minutes = 0;

    Main() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.setSize(570, 90);
        this.setTitle("DJ's Timer Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(true);

        timeDisplay = new JLabel();
        timeDisplay.setText("00:00");
        timeDisplay.setFont(new Font("Serif", Font.PLAIN, 40));
        timeDisplay.setForeground(Color.green);
        timeDisplay.setBackground(Color.black);
        timeDisplay.setOpaque(true);

        pausePlayDisplay = new JLabel();
        pausePlayDisplay = new JLabel();
        pausePlayDisplay.setText("Stopped");
        pausePlayDisplay.setFont(new Font("Serif", Font.PLAIN, 20));
        pausePlayDisplay.setForeground(Color.green);
        pausePlayDisplay.setBackground(Color.black);
        pausePlayDisplay.setOpaque(true);
        this.add(pausePlayDisplay);

        button1 = new JButton("Start");
        button1.addActionListener(this);
        button1.setPreferredSize(new Dimension(75, 50));

        button3 = new JButton("Restart");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartTimer();
            }
        });
        button3.setPreferredSize(new Dimension(75, 50));

        button4 = new JButton("+15");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incrementTimer();
            }
        });
        button4.setPreferredSize(new Dimension(75, 50));

        button5 = new JButton("-15");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrementTimer();
            }
        });
        button5.setPreferredSize(new Dimension(75, 50));

        this.add(timeDisplay);
        this.add(button1);
        this.add(button3);
        this.add(button4);
        this.add(button5); // Add the button to the frame

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });

        this.setVisible(true);
    }

    public void decrementTimer() {
        if (seconds >= 15) {
            seconds -= 15;
            minutes = seconds / 60;
            int displaySeconds = seconds % 60;
            timeDisplay.setText(String.format("%02d:%02d", minutes, displaySeconds));
        } else {
            seconds = 0;
            minutes = seconds / 60;
            int displaySeconds = seconds % 60;
            timeDisplay.setText(String.format("%02d:%02d", minutes, displaySeconds));
        }
    }

    public void incrementTimer() {
        seconds += 15;
        minutes = seconds / 60;
        int displaySeconds = seconds % 60;
        timeDisplay.setText(String.format("%02d:%02d", minutes, displaySeconds));

    }

    public void restartTimer() {
        seconds = 0;
        minutes = 0;
        timeDisplay.setText(String.format("%02d:%02d", 0, 0));
    }

    public void updateTimer() {
        seconds++;
        minutes = seconds / 60;
        int displaySeconds = seconds % 60;
        timeDisplay.setText(String.format("%02d:%02d", minutes, displaySeconds));
    }

    public void stopTimer() {
        if (timer.isRunning()) {
            timer.stop();
            pausePlayDisplay.setText("Stopped");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!timer.isRunning()) {
            timer.start();
            pausePlayDisplay.setText("Running");
            button1.setText("Stop");
        } else {
            timer.stop();
            pausePlayDisplay.setText("Stopped");
            button1.setText("Start");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}