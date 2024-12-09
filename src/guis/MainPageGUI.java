package guis;

import constants.CommonConstants;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class MainPageGUI extends Form {

    public MainPageGUI() {
        super("Flight Search");
        addScrollableContent();
    }

    private void addScrollableContent() {
        // Create a panel to hold all components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Use absolute positioning
        contentPanel.setBackground(CommonConstants.PRIMARY_COLOR);

        int formWidth = getWidth();
        int componentWidth = 400;
        int startX = (formWidth - componentWidth) / 2; // Center horizontally
        int currentY = 50; // Start from top margin

        // Title Label
        JLabel titleLabel = new JLabel("Find Your Flight");
        titleLabel.setBounds(startX, currentY, componentWidth, 50);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel);

        currentY += 70; // Move down

        // Trip Type Radio Buttons
        JLabel tripLabel = new JLabel("Trip Type:");
        tripLabel.setBounds(startX, currentY, componentWidth, 30);
        tripLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        tripLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(tripLabel);

        currentY += 40;

        JRadioButton roundTripButton = new JRadioButton("Round Trip");
        roundTripButton.setBounds(startX, currentY, 150, 30);
        roundTripButton.setBackground(CommonConstants.PRIMARY_COLOR);
        roundTripButton.setForeground(CommonConstants.TEXT_COLOR);
        roundTripButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        roundTripButton.setSelected(true); // Default selection

        JRadioButton oneWayButton = new JRadioButton("One Way");
        oneWayButton.setBounds(startX + 200, currentY, 150, 30);
        oneWayButton.setBackground(CommonConstants.PRIMARY_COLOR);
        oneWayButton.setForeground(CommonConstants.TEXT_COLOR);
        oneWayButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        ButtonGroup tripTypeGroup = new ButtonGroup();
        tripTypeGroup.add(roundTripButton);
        tripTypeGroup.add(oneWayButton);

        contentPanel.add(roundTripButton);
        contentPanel.add(oneWayButton);

        currentY += 50;

        // From/To Fields with Scrollable Airports
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(startX, currentY, componentWidth, 30);
        fromLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        fromLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(fromLabel);

        currentY += 40;

        JComboBox<String> fromDropdown = createScrollableComboBox();
        fromDropdown.setBounds(startX, currentY, componentWidth, 40);
        contentPanel.add(fromDropdown);

        currentY += 60;

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(startX, currentY, componentWidth, 30);
        toLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        toLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(toLabel);

        currentY += 40;

        JComboBox<String> toDropdown = createScrollableComboBox();
        toDropdown.setBounds(startX, currentY, componentWidth, 40);
        contentPanel.add(toDropdown);

        currentY += 60;

        // Dates with JDateChooser
        JLabel departLabel = new JLabel("Departure Date:");
        departLabel.setBounds(startX, currentY, componentWidth, 30);
        departLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        departLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(departLabel);

        currentY += 40;

        JDateChooser departDateChooser = new JDateChooser();
        departDateChooser.setBounds(startX, currentY, componentWidth, 40);
        contentPanel.add(departDateChooser);

        currentY += 60;

        JLabel returnLabel = new JLabel("Return Date:");
        returnLabel.setBounds(startX, currentY, componentWidth, 30);
        returnLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        returnLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(returnLabel);

        currentY += 40;

        JDateChooser returnDateChooser = new JDateChooser();
        returnDateChooser.setBounds(startX, currentY, componentWidth, 40);
        returnDateChooser.setEnabled(roundTripButton.isSelected()); // Enable only for round trips
        contentPanel.add(returnDateChooser);

        // Enable/disable return date based on trip type
        roundTripButton.addActionListener(e -> returnDateChooser.setEnabled(true));
        oneWayButton.addActionListener(e -> returnDateChooser.setEnabled(false));

        currentY += 60;

        // Passengers
        JLabel passengersLabel = new JLabel("Passengers:");
        passengersLabel.setBounds(startX, currentY, componentWidth, 30);
        passengersLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        passengersLabel.setForeground(CommonConstants.TEXT_COLOR);
        contentPanel.add(passengersLabel);

        currentY += 40;

        JPanel passengersPanel = new JPanel();
        passengersPanel.setBounds(startX, currentY, componentWidth, 40);
        passengersPanel.setLayout(new GridLayout(1, 4));
        passengersPanel.setBackground(CommonConstants.PRIMARY_COLOR);

        JLabel adultsLabel = new JLabel("Adults:");
        adultsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JComboBox<String> adultsDropdown = createPassengerDropdown();

        JLabel childrenLabel = new JLabel("Children:");
        childrenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JComboBox<String> childrenDropdown = createPassengerDropdown();

        passengersPanel.add(adultsLabel);
        passengersPanel.add(adultsDropdown);
        passengersPanel.add(childrenLabel);
        passengersPanel.add(childrenDropdown);
        contentPanel.add(passengersPanel);
        adultsLabel.setForeground(CommonConstants.TEXT_COLOR);
        childrenLabel.setForeground(CommonConstants.TEXT_COLOR);

        currentY += 60;

        // Search Button
        JButton searchButton = new JButton("Search Flights");
        searchButton.setFont(new Font("Dialog", Font.BOLD, 18));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBackground(CommonConstants.TEXT_COLOR);
        searchButton.setBounds(startX, currentY, componentWidth, 50);
        searchButton.addActionListener(e -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String departDate = departDateChooser.getDate() != null ? sdf.format(departDateChooser.getDate()) : null;
                String returnDate = returnDateChooser.getDate() != null ? sdf.format(returnDateChooser.getDate()) : null;

                if (departDate == null) {
                    JOptionPane.showMessageDialog(this, "Please select a departure date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (roundTripButton.isSelected() && returnDate == null) {
                    JOptionPane.showMessageDialog(this, "Please select a return date for a round trip.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String fromAirport = (String) fromDropdown.getSelectedItem();
                String toAirport = (String) toDropdown.getSelectedItem();
                String adults = (String) adultsDropdown.getSelectedItem();
                String children = (String) childrenDropdown.getSelectedItem();

                double baseFare = 200; // Example base fare
                double fuelSurcharge = 50; // Example surcharge
                String season = "summer"; // Example season

                new FlightOptionsGUI(baseFare, fuelSurcharge, fromAirport, toAirport, season,
                        departDateChooser.getDate(), returnDateChooser.getDate()).setVisible(true);
                MainPageGUI.this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        contentPanel.add(searchButton);

        // Adjust the height of the content panel dynamically
        contentPanel.setPreferredSize(new Dimension(formWidth, currentY + 100));

        // Add the content panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(0, 0, formWidth, getHeight());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);

        getContentPane().add(scrollPane);
    }

    private JComboBox<String> createScrollableComboBox() {
        String[] airports = {
                "Atlanta (ATL)", "Boston (BOS)", "Charlotte (CLT)", "Chicago (ORD)", "Dallas (DFW)",
                "Denver (DEN)", "Detroit (DTW)", "Houston (IAH)", "Las Vegas (LAS)", "Los Angeles (LAX)",
                "Miami (MIA)", "Minneapolis (MSP)", "New York (JFK)", "Newark (EWR)", "Orlando (MCO)",
                "Philadelphia (PHL)", "Phoenix (PHX)", "Salt Lake City (SLC)", "San Diego (SAN)",
                "San Francisco (SFO)", "Seattle (SEA)", "Tampa (TPA)", "Washington, D.C. (IAD)"
        };
        JComboBox<String> dropdown = new JComboBox<>(airports);
        dropdown.setMaximumRowCount(10);
        return dropdown;
    }

    private JComboBox<String> createPassengerDropdown() {
        return new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5", "6"});
    }
}
