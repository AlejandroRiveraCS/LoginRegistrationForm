package guis;

import constants.BaseFareCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class FlightOptionsGUI extends JFrame {

    public FlightOptionsGUI(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity, String season, Date travelDate) {
        super("Book Your Flight - Flight Options");

        // Set up the window
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Header Section
        JPanel headerPanel = new JPanel(new GridLayout(1, 1));
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel blue background
        JLabel titleLabel = new JLabel("Flight Options", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Combine both perks table and flight options
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));

        // Perks Table
        JTable perksTable = createPerksTable();
        JScrollPane perksScrollPane = new JScrollPane(perksTable);
        perksScrollPane.setBorder(BorderFactory.createTitledBorder("Perks"));
        combinedPanel.add(perksScrollPane);

        // Flight Options Table
        JTable flightOptionsTable = createFlightOptionsTable(baseFare, fuelSurcharge, departureCity, arrivalCity, season, travelDate);
        JScrollPane flightOptionsScrollPane = new JScrollPane(flightOptionsTable);
        flightOptionsScrollPane.setBorder(BorderFactory.createTitledBorder("Flight Options"));
        combinedPanel.add(flightOptionsScrollPane);

        JScrollPane combinedScrollPane = new JScrollPane(combinedPanel);
        add(combinedScrollPane, BorderLayout.CENTER);

        // Footer Section with Summary
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(new JLabel("Departure: " + departureCity));
        footerPanel.add(new JLabel("Arrival: " + arrivalCity));
        footerPanel.add(new JLabel("Date: " + new SimpleDateFormat("MM/dd/yyyy").format(travelDate)));
        footerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JTable createPerksTable() {
        String[] columnNames = {"Perk Details", "Go Savvy", "Go Comfy", "Go Big"};
        Object[][] data = {
                {"1 Personal Item (18\"x14\"x8\")", "✓", "✓", "✓"},
                {"1 Carry-On Bag (22\"x18\"x10\")", "✓", "✓", "✓"},
                {"1 Checked Bag (50 lbs, 62\")", "Extra $", "✓", "✓"},
                {"Seat Selection", "Standard Seat", "Middle Seat Blocked", "Big Front Seat"},
                {"Food & Beverage", "Extra $", "Snack and Drink Included", "Premium Snacks and Drinks"},
                {"Priority Boarding", "No", "Group 2", "Group 1"},
                {"Wi-Fi", "Extra $", "Extra $", "Included"},
                {"Shortcut Security", "Extra $", "Extra $", "Included"},
                {"Priority Check-In", "No", "No", "Included"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);

        // Customize column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(350); // Perk Details
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Go Savvy
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Go Comfy
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // Go Big

        applyTableStyling(table);

        return table;
    }

    private JTable createFlightOptionsTable(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity, String season, Date travelDate) {
        String[] columnNames = {"Depart/Arrive Times", "Go Savvy Price", "Go Comfy Price", "Go Big Price"};

        Object[][] data = generateFlightOptionsData(baseFare, fuelSurcharge, departureCity, arrivalCity, season, travelDate);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);

        // Customize column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(350); // Times
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Go Savvy
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Go Comfy
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // Go Big

        applyTableStyling(table);

        return table;
    }

    private Object[][] generateFlightOptionsData(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity, String season, Date travelDate) {
        String[] departureTimes = {"6:00 AM", "10:00 AM", "2:00 PM", "6:00 PM", "10:00 PM"};
        String[] arrivalTimes = {"8:30 AM", "12:30 PM", "4:30 PM", "8:30 PM", "12:30 AM"};
        double[] tierMultipliers = {1.0, 1.2, 1.5}; // Multipliers for Go Savvy, Go Comfy, Go Big

        Object[][] data = new Object[departureTimes.length][4];

        for (int i = 0; i < departureTimes.length; i++) {
            String timeDetails = departureTimes[i] + " → " + arrivalTimes[i] + " (" + departureCity + " → " + arrivalCity + ")";
            LocalTime departureTime = parseTime(departureTimes[i]);

            // Calculate prices for each tier
            double savvyPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[0], departureTime);
            double comfyPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[1], departureTime);
            double bigPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[2], departureTime);

            data[i][0] = timeDetails; // Time and route details
            data[i][1] = "$" + String.format("%.2f", savvyPrice);
            data[i][2] = "$" + String.format("%.2f", comfyPrice);
            data[i][3] = "$" + String.format("%.2f", bigPrice);
        }

        return data;
    }

    private LocalTime parseTime(String time) {
        String[] parts = time.split(" ");
        String[] hourMinute = parts[0].split(":");
        int hour = Integer.parseInt(hourMinute[0]);
        int minute = Integer.parseInt(hourMinute[1]);

        if (parts[1].equalsIgnoreCase("PM") && hour != 12) {
            hour += 12;
        } else if (parts[1].equalsIgnoreCase("AM") && hour == 12) {
            hour = 0;
        }

        return LocalTime.of(hour, minute);
    }

    private void applyTableStyling(JTable table) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Center-align text
                setHorizontalAlignment(SwingConstants.CENTER);

                // Color coding for columns
                if (column == 1) { // Go Savvy
                    cell.setBackground(Color.LIGHT_GRAY);
                    cell.setForeground(Color.BLACK);
                } else if (column == 2) { // Go Comfy
                    cell.setBackground(new Color(173, 216, 230)); // Light blue
                    cell.setForeground(Color.BLACK);
                } else if (column == 3) { // Go Big
                    cell.setBackground(new Color(70, 130, 180)); // Steel blue
                    cell.setForeground(Color.WHITE);
                } else { // Times or Perk Details
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
                return cell;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        table.setRowHeight(40); // Adjust row height
        table.setEnabled(false); // Disable editing
    }
}
