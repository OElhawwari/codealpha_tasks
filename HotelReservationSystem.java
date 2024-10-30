
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class Room {
    private String roomNumber;
    private String category;
    private double price;

    public Room(String roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }
}


public class HotelReservationSystem {

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel homePanel;
    private JPanel searchPanel;
    private JPanel reservePanel;

    private JTextArea searchResultArea;
    private JComboBox<String> roomNumberDropdown;
    private List<Room> availableRooms;
    private List<Room> reservedRooms;
    private List<String> customerReservations;

    public HotelReservationSystem() {
        frame = new JFrame("Hotel Reservation System");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        availableRooms = new ArrayList<>();
        reservedRooms = new ArrayList<>();
        customerReservations = new ArrayList<>();

        createHomePanel();
        createSearchPanel();
        createReservePanel();

        mainPanel.add(homePanel, "Home");
        mainPanel.add(searchPanel, "Search");
        mainPanel.add(reservePanel, "Reserve");

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    private void createHomePanel() {
        homePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Hotel Reservation System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        homePanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton searchButton = new JButton("Search Rooms");
        JButton reserveButton = new JButton("Reserve Room");

        searchButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        reserveButton.addActionListener(e -> cardLayout.show(mainPanel, "Reserve"));

        buttonPanel.add(searchButton);
        buttonPanel.add(reserveButton);

        homePanel.add(buttonPanel, BorderLayout.CENTER);

        JTextArea customerListArea = new JTextArea(10, 30);
        customerListArea.setEditable(false);
        updateCustomerListArea(customerListArea);
        homePanel.add(new JScrollPane(customerListArea), BorderLayout.SOUTH);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search for Available Rooms", JLabel.CENTER);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchPanel.add(searchLabel, BorderLayout.NORTH);

        searchResultArea = new JTextArea(10, 30);
        searchResultArea.setEditable(false);
        searchPanel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");

        searchButton.addActionListener(e -> updateSearchResults());

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        controlPanel.add(searchButton);
        controlPanel.add(backButton);

        searchPanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private void createReservePanel() {
        reservePanel = new JPanel(new BorderLayout());
        JLabel reserveLabel = new JLabel("Reserve a Room", JLabel.CENTER);
        reserveLabel.setFont(new Font("Arial", Font.BOLD, 16));
        reservePanel.add(reserveLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Room Number:"));
        roomNumberDropdown = new JComboBox<>();
        formPanel.add(roomNumberDropdown);

        formPanel.add(new JLabel("Customer Name:"));
        JTextField customerNameField = new JTextField();
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Check-in Date:"));
        JTextField checkinDateField = new JTextField();
        checkinDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        checkinDateField.setEditable(false);
        formPanel.add(checkinDateField);

        formPanel.add(new JLabel("Check-out Date:"));
        JSpinner checkoutDateSpinner = createDateSpinner();
        formPanel.add(checkoutDateSpinner);

        reservePanel.add(formPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton reserveButton = new JButton("Reserve");
        JButton backButton = new JButton("Back");

        reserveButton.addActionListener(e -> {
            String selectedRoom = (String) roomNumberDropdown.getSelectedItem();
            String customerName = customerNameField.getText();
            Date checkinDate = new Date();
            Date checkoutDate = (Date) checkoutDateSpinner.getValue();

            if (selectedRoom != null && customerName != null && !customerName.isEmpty() && checkoutDate != null) {
                reserveRoom(selectedRoom, customerName, checkinDate, checkoutDate);
                JOptionPane.showMessageDialog(frame, "Room reserved successfully!");
                updateCustomerListArea((JTextArea) ((JScrollPane) homePanel.getComponent(2)).getViewport().getView());
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        controlPanel.add(reserveButton);
        controlPanel.add(backButton);

        reservePanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        return dateSpinner;
    }

    private void updateSearchResults() {
        availableRooms.clear();
        availableRooms.add(new Room("101", "Single", 100));
        availableRooms.add(new Room("102", "Double", 150));
        availableRooms.add(new Room("103", "Suite", 200));

        searchResultArea.setText("Available Rooms:\n");
        roomNumberDropdown.removeAllItems();
        for (Room room : availableRooms) {
            if (!reservedRooms.contains(room)) {
                searchResultArea.append(room + "\n");
                roomNumberDropdown.addItem(room.getRoomNumber());
            }
        }
    }

    private void reserveRoom(String roomNumber, String customerName, Date checkinDate, Date checkoutDate) {
        for (Room room : availableRooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                reservedRooms.add(room);
                customerReservations.add(customerName + " - Room " + roomNumber + " (Check-in: " + new SimpleDateFormat("yyyy-MM-dd").format(checkinDate) + ", Check-out: " + new SimpleDateFormat("yyyy-MM-dd").format(checkoutDate) + ")");
                break;
            }
        }
        updateSearchResults();
    }

    private void updateCustomerListArea(JTextArea customerListArea) {
        customerListArea.setText("Customer Reservations:\n");
        for (String reservation : customerReservations) {
            customerListArea.append(reservation + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelReservationSystem::new);
    }
}

