import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Microwave {
    static Timer countdownTimer; 

    public static void main(String[] args) {
        JFrame frame = new JFrame("Microwave Oven");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.DARK_GRAY);

        JPanel displayPanel = new JPanel();
        final JLabel displayLabel = new JLabel("");
        displayLabel.setFont(new Font("Courier New", Font.BOLD, 30));
        displayLabel.setOpaque(true);
        displayLabel.setBackground(Color.BLACK);
        displayLabel.setForeground(Color.GRAY);
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        displayPanel.setBackground(Color.BLACK);
        displayPanel.add(displayLabel);

        JPanel numberPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        numberPanel.setBackground(Color.BLACK);

        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.DARK_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            String buttonValue = String.valueOf(i);

            button.addActionListener(e -> {
                Toolkit.getDefaultToolkit().beep();
                String currentText = displayLabel.getText();
                if (currentText.length() < 5) {
                    if (!currentText.contains(":")) {
                        if (currentText.length() < 2) {
                            displayLabel.setText(currentText + buttonValue);
                        } else if (currentText.length() == 2) {
                            displayLabel.setText(currentText + ":" + buttonValue);
                        }
                    } else {
                        String[] parts = currentText.split(":");
                        if (parts.length == 2 && parts[1].length() < 2) {
                            displayLabel.setText(currentText + buttonValue);
                        }
                    }
                }
            });
            numberPanel.add(button);
        }

        // Button: 0
        JButton zeroButton = new JButton("0");
        zeroButton.setFont(new Font("Arial", Font.BOLD, 18));
        zeroButton.setForeground(Color.WHITE);
        zeroButton.setBackground(Color.DARK_GRAY);
        zeroButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        zeroButton.addActionListener(e -> {
            String currentText = displayLabel.getText();
            if (currentText.length() < 5) {
                if (!currentText.contains(":")) {
                    if (currentText.length() < 2) {
                        displayLabel.setText(currentText + "0");
                    } else if (currentText.length() == 2) {
                        displayLabel.setText(currentText + ":" + "0");
                    }
                } else {
                    String[] parts = currentText.split(":");
                    if (parts.length == 2 && parts[1].length() < 2) {
                        displayLabel.setText(currentText + "0");
                    }
                }
            }
        });
        numberPanel.add(zeroButton);

        // Button: Clear
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 18));
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        clearButton.addActionListener(e -> {
            displayLabel.setText("00:00");
            if (countdownTimer != null) countdownTimer.stop();
        });
        numberPanel.add(clearButton);

        // Button: Start
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.BLACK);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        startButton.addActionListener(e -> {
            String time = displayLabel.getText();
            if (time.equals("") || time.equals("00:00")) {
                JOptionPane.showMessageDialog(frame, "Please enter the right time before start");
            } else {
                String[] parts = time.split(":");
                int[] timeLeft = new int[2]; 
                timeLeft[0] = Integer.parseInt(parts[0]);
                timeLeft[1] = Integer.parseInt(parts[1]);

                if (countdownTimer != null) countdownTimer.stop(); 

                countdownTimer = new Timer(1000, event -> {
                    if (timeLeft[1] == 0) {
                        if (timeLeft[0] == 0) {
                            countdownTimer.stop();
                            JOptionPane.showMessageDialog(frame, "Time's up!");
                            return;
                        } else {
                            timeLeft[0]--;
                            timeLeft[1] = 59;
                        }
                    } else {
                        timeLeft[1]--;
                    }

                    String minuteStr = String.format("%02d", timeLeft[0]);
                    String secondStr = String.format("%02d", timeLeft[1]);
                    displayLabel.setText(minuteStr + ":" + secondStr);
                });

                countdownTimer.start();
            }
        });
        numberPanel.add(startButton);

        // Button: Close
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setBackground(Color.BLUE);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        closeButton.addActionListener(e -> System.exit(0));
        numberPanel.add(closeButton);

        // Control panel with Stop button
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);
        JButton stopButton = new JButton("Stop");
        stopButton.setFont(new Font("Arial", Font.BOLD, 18));
        stopButton.setBackground(Color.ORANGE);
        stopButton.setForeground(Color.BLACK);

        stopButton.addActionListener(e -> {
            if (countdownTimer != null) countdownTimer.stop();
        });

        controlPanel.add(stopButton);

        // Add everything to frame
        frame.add(displayPanel, BorderLayout.NORTH);
        frame.add(numberPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
