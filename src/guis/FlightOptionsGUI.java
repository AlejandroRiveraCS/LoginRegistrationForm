package guis;

import constants.BaseFareCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class FlightOptionsGUI extends Form {

    public FlightOptionsGUI() {
        super("Flight Options");
        setTitleBarIcon("C:\\Users\\alexr\\IdeaProjects\\LoginRegistrationFormkmk\\src\\A_realistic_image_of_an_airplane_flying_in_a_clear.jpeg");
    }

    public FlightOptionsGUI(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity, String season,
                            Date travelDate, Date returnDate) {
        super("Book Your Flight - Flight Options");

        // Set up the window
        setSize(1100, 800);
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

        // Combine perks table and flight options
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));

        // Perks Table
        JTable perksTable = createPerksTable();
        JScrollPane perksScrollPane = new JScrollPane(perksTable);
        perksScrollPane.setBorder(BorderFactory.createTitledBorder("Perks"));
        combinedPanel.add(perksScrollPane);

        // Flight Options Table
        JTable flightOptionsTable = createFlightOptionsTable(baseFare, fuelSurcharge, departureCity, arrivalCity, season, travelDate, returnDate);
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
        String[] columnNames = {"Perk Details", "Economy", "Basic", "First Class"};
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
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Economy
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Basic
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // First Class

        table.setRowHeight(42);

        applyTableStyling(table);

        return table;
    }

    private JTable createFlightOptionsTable(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity,
                                            String season, Date travelDate, Date returnDate) {
        String[] columnNames = {"Depart/Arrive Times", "Economy", "Basic", "First Class"};

        Object[][] data = generateFlightOptionsData(baseFare, fuelSurcharge, departureCity, arrivalCity, season, travelDate);

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(40);

        // Add custom button click behavior
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int column = table.columnAtPoint(evt.getPoint());
                if (column > 0) { // Price columns
                    String flightTime = data[row][0].toString();
                    String selectedClass = column == 1 ? "Economy" : column == 2 ? "Business" : "First Class";
                    double price = Double.parseDouble(data[row][column].toString().replace("$", ""));

                    // Navigate to ReturnFlightOptionsGUI
                    new ReturnFlightOptionsGUI(baseFare, fuelSurcharge, departureCity, arrivalCity, season, travelDate, returnDate, flightTime, selectedClass, price).setVisible(true);
                    dispose(); // Close current window
                }
            }
        });

        return table;
    }

    private Object[][] generateFlightOptionsData(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity,
                                                 String season, Date travelDate) {
        String[] departureTimes = {"6:00 AM", "10:00 AM", "2:00 PM", "6:00 PM", "10:00 PM"};
        String[] arrivalTimes = {"8:30 AM", "12:30 PM", "4:30 PM", "8:30 PM", "12:30 AM"};
        double[] tierMultipliers = {1.0, 1.2, 1.5}; // Multipliers for Go Savvy, Go Comfy, Go Big

        Object[][] data = new Object[departureTimes.length][4];

        for (int i = 0; i < departureTimes.length; i++) {
            String timeDetails = departureTimes[i] + " → " + arrivalTimes[i] + " (" + departureCity + " → " + arrivalCity + ")";
            LocalTime departureTime = parseTime(departureTimes[i]);

            double savvyPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[0], departureTime);
            double comfyPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[1], departureTime);
            double bigPrice = BaseFareCalculator.calculatePrice(baseFare, fuelSurcharge, departureCity, tierMultipliers[2], departureTime);

            data[i][0] = timeDetails;
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

                setHorizontalAlignment(SwingConstants.CENTER);

                if (column == 1) {
                    cell.setBackground(Color.LIGHT_GRAY);
                } else if (column == 2) {
                    cell.setBackground(new Color(173, 216, 230)); // Light blue
                } else if (column == 3) {
                    cell.setBackground(new Color(70, 130, 180)); // Steel blue
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
}
