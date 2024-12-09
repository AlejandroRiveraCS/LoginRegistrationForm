package guis;

import constants.BaseFareCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ReturnFlightOptionsGUI extends Form {
    private final String departureFlightTime;
    private final String departureClass;
    private final double departurePrice;
    private final String departureDate;

    public ReturnFlightOptionsGUI(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity, String season,
                                  Date departureDate, Date returnDate, String departureFlightTime, String departureClass, double departurePrice) {
        super("Book Your Flight - Return Flight Options");

        this.departureFlightTime = departureFlightTime;
        this.departureClass = departureClass;
        this.departurePrice = departurePrice;
        this.departureDate = new SimpleDateFormat("MM/dd/yyyy").format(departureDate);

        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridLayout(1, 1));
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel blue
        JLabel titleLabel = new JLabel("Return Flight Options", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));

        JTable perksTable = createPerksTable();
        JScrollPane perksScrollPane = new JScrollPane(perksTable);
        perksScrollPane.setBorder(BorderFactory.createTitledBorder("Perks"));
        combinedPanel.add(perksScrollPane);

        JTable returnFlightOptionsTable = createReturnFlightOptionsTable(baseFare, fuelSurcharge, departureCity, arrivalCity, season, returnDate);
        JScrollPane returnFlightOptionsScrollPane = new JScrollPane(returnFlightOptionsTable);
        returnFlightOptionsScrollPane.setBorder(BorderFactory.createTitledBorder("Return Flight Options"));
        combinedPanel.add(returnFlightOptionsScrollPane);

        JScrollPane combinedScrollPane = new JScrollPane(combinedPanel);
        add(combinedScrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(new JLabel("Departure: " + departureCity));
        footerPanel.add(new JLabel("Arrival: " + arrivalCity));
        footerPanel.add(new JLabel("Date: " + this.departureDate));
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

        table.getColumnModel().getColumn(0).setPreferredWidth(350);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        table.setRowHeight(42);
        applyTableStyling(table);

        return table;
    }

    private JTable createReturnFlightOptionsTable(double baseFare, double fuelSurcharge, String departureCity, String arrivalCity,
                                                  String season, Date returnDate) {
        String[] columnNames = {"Depart/Arrive Times", "Economy", "Basic", "First Class"};

        Object[][] data = generateReturnFlightOptionsData(baseFare, fuelSurcharge, departureCity, arrivalCity, season, returnDate);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(42);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int column = table.columnAtPoint(evt.getPoint());
                if (column > 0) {
                    String returnFlightTime = data[row][0].toString();
                    String selectedClass = column == 1 ? "Economy" : column == 2 ? "Basic" : "First Class";
                    double returnPrice = Double.parseDouble(data[row][column].toString().replace("$", ""));

                    new SummaryGUI(
                            departureCity,
                            arrivalCity,
                            departureDate,
                            new SimpleDateFormat("MM/dd/yyyy").format(returnDate),
                            departureFlightTime,
                            returnFlightTime,
                            departureClass,
                            selectedClass,
                            departurePrice,
                            returnPrice
                    ).setVisible(true);
                    dispose();
                }
            }
        });

        return table;
    }

    private Object[][] generateReturnFlightOptionsData(double baseFare, double fuelSurcharge, String departureCity,
                                                       String arrivalCity, String season, Date travelDate) {
        String[] departureTimes = {"6:00 AM", "10:00 AM", "2:00 PM", "6:00 PM", "10:00 PM"};
        String[] arrivalTimes = {"8:30 AM", "12:30 PM", "4:30 PM", "8:30 PM", "12:30 AM"};
        double[] tierMultipliers = {1.0, 1.2, 1.5};

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
