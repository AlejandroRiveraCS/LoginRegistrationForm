package guis;

import javax.swing.*;
import java.awt.*;

public class SummaryGUI extends Form {

    public SummaryGUI(String departureCity, String arrivalCity, String departureDate,
                      String returnDate, String departureFlightTime, String returnFlightTime,
                      String departureSelectedClass, String returnSelectedClass,
                      double departureTotalAmount, double returnTotalAmount) {
        super("Flight Summary");

        setSize(1100, 800); // Increased height to accommodate two summaries
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1200, 100));
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel blue

        JLabel headerLabel = new JLabel("Flight Summary", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 72)); // Font size 72
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 20, 20)); // Two sections for departure and return
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel departurePanel = createSummaryPanel(
                "Departure Flight Summary",
                departureCity, arrivalCity, departureDate, departureFlightTime,
                departureSelectedClass, departureTotalAmount
        );
        mainPanel.add(departurePanel);

        JPanel returnPanel = createSummaryPanel(
                "Return Flight Summary",
                arrivalCity, departureCity, returnDate, returnFlightTime,
                returnSelectedClass, returnTotalAmount
        );
        mainPanel.add(returnPanel);

        add(mainPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        footerPanel.setPreferredSize(new Dimension(1200, 150));

        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.setPreferredSize(new Dimension(250, 55));
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Your booking is confirmed! Thank you!");
            System.exit(0); // Close the application
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.setPreferredSize(new Dimension(250, 55));
        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Booking canceled.");
            System.exit(0); // Close the application
        });

        footerPanel.add(confirmButton);
        footerPanel.add(cancelButton);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createSummaryPanel(String title, String fromCity, String toCity,
                                      String date, String flightTime,
                                      String selectedClass, double totalAmount) {
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(6, 2, 20, 10)); // 6 rows for details
        summaryPanel.setBorder(BorderFactory.createTitledBorder(title)); // Add title to section

        JLabel label1 = new JLabel("From:", SwingConstants.RIGHT);
        label1.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label1);

        JLabel value1 = new JLabel(fromCity, SwingConstants.LEFT);
        value1.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value1);

        JLabel label2 = new JLabel("To:", SwingConstants.RIGHT);
        label2.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label2);

        JLabel value2 = new JLabel(toCity, SwingConstants.LEFT);
        value2.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value2);

        JLabel label3 = new JLabel("Date:", SwingConstants.RIGHT);
        label3.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label3);

        JLabel value3 = new JLabel(date, SwingConstants.LEFT);
        value3.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value3);

        JLabel label4 = new JLabel("Flight Time:", SwingConstants.RIGHT);
        label4.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label4);

        JLabel value4 = new JLabel(flightTime, SwingConstants.LEFT);
        value4.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value4);

        JLabel label5 = new JLabel("Class:", SwingConstants.RIGHT);
        label5.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label5);

        JLabel value5 = new JLabel(selectedClass, SwingConstants.LEFT);
        value5.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value5);

        JLabel label6 = new JLabel("Total Amount:", SwingConstants.RIGHT);
        label6.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(label6);

        JLabel value6 = new JLabel("$" + String.format("%.2f", totalAmount), SwingConstants.LEFT);
        value6.setFont(new Font("Arial", Font.PLAIN, 25));
        summaryPanel.add(value6);

        return summaryPanel;
    }
}
