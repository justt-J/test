import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;

public class TrainFareCalculator extends JFrame {

    private JComboBox<String> terminalComboBox;
    private JComboBox<String> cardComboBox;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> stationFromComboBox;
    private JComboBox<String> stationToComboBox;
    private JButton calculateButton;
    private JLabel fareLabel;
    private JLabel paymentLabel;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final int[][] LRT1Fares = {
        {0, 20, 20, 20, 25, 25, 25, 25, 25, 25, 30, 30, 30, 30, 30, 35, 35, 35, 35, 35}, // FPJ
        {20, 0, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30, 30, 30, 30, 30, 35, 35, 35}, // Balintawak
        {20, 20, 0, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30, 30, 30, 30, 30}, // Monumento
        {20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 30, 30, 30, 30}, // 5th Ave
        {25, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 35, 35, 35}, // R. Papa
        {25, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30}, // Abad Santos
        {25, 25, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25}, // Blumentritt
        {25, 25, 25, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25}, // Tayuman
        {25, 25, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25}, // Bambang
        {25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 25, 25, 25, 25}, // D. Jose
        {30, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 20, 20, 20, 25, 25, 25}, // Carriedo
        {30, 25, 25, 20, 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 20, 20, 20, 25, 25}, // Central
        {30, 30, 25, 25, 25, 20, 20, 20, 20, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20}, // UN Avenue
        {30, 30, 25, 25, 25, 25, 20, 20, 20, 20, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20}, // Pedro Gil
        {30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 20, 20}, // Quirino
        {35, 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20}, // Vito Cruz
        {35, 30, 30, 30, 25, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 0, 15, 15, 20}, // Gil Puyat
        {35, 35, 30, 30, 30, 25, 25, 25, 25, 25, 25, 20, 20, 20, 20, 15, 15, 0, 15, 15}, // Libertad
        {35, 35, 30, 30, 30, 30, 25, 25, 25, 25, 25, 25, 20, 20, 20, 20, 15, 15, 0, 15}, // EDSA
        {35, 35, 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0} // Baclaran
    };

    private final int[][] LRT2Fares = {
        {0, 15, 20, 20, 20, 25, 25, 25, 25, 30, 30, 35, 35}, // Recto
        {15, 0, 15, 20, 20, 20, 25, 25, 25, 25, 30, 30, 35}, // Legarda
        {20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30, 30, 30}, // Pureza
        {20, 20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30, 30}, // V. Mapa
        {20, 20, 20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30}, // J. Ruiz
        {25, 20, 20, 20, 15, 0, 15, 20, 20, 20, 25, 25, 30}, // Gilmore
        {25, 25, 20, 20, 30, 15, 0, 15, 20, 20, 20, 25, 25}, // Betty Go
        {25, 25, 20, 20, 20, 20, 15, 0, 15, 20, 20, 25, 25}, // Araneta
        {25, 25, 25, 20, 20, 20, 20, 15, 0, 15, 20, 20, 25}, // Anonas
        {30, 25, 25, 25, 20, 20, 20, 20, 15, 0, 20, 20, 25}, // Katipunan
        {30, 30, 30, 25, 25, 25, 20, 20, 20, 20, 0, 15, 20}, // Santolan
        {35, 30, 30, 30, 25, 25, 25, 25, 20, 20, 15, 0, 20}, // Marikina
        {35, 35, 30, 30, 30, 30, 25, 25, 25, 25, 20, 20, 0}  // Antipolo
    };

    private final int[][] MRT3Fares = {
        {0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24, 28, 28}, // North Avenue
        {13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24, 28}, // Quezon Avenue
        {13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24}, // Kamuning
        {16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24}, // Cubao
        {16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24}, // Santolan-Annapolis
        {20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20}, // Ortigas
        {20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20}, // Shaw
        {20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20}, // Boni
        {24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16}, // Guadalupe
        {24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16}, // Buendia
        {24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13}, // Ayala
        {28, 24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13}, // Magallanes
        {28, 28, 24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0}  // Taft
    };

    public TrainFareCalculator() {
        setTitle("Train Fare Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        String[] terminals = {"MRT 3"};
        String[] cards = {"Regular", "Beep Card"};
        String[] statuses = {"Regular Passenger", "Student", "PWD"};
        String[] regStat = {"Regular Passenger"};
        String[] lrt1Stations = {"FPJ", "Balintawak", "Monumento", "5th Ave", "R. Papa", "Abad Santos", "Blumentritt", "Tayuman", "Bambang", "D. Jose", "Carriedo", "Central", "UN Avenue", "Pedro Gil", "Quirino", "Vito Cruz", "Gil Puyat", "Libertad", "EDSA", "Baclaran"};
        String[] lrt2Stations = {"Recto", "Legarda", "Pureza", "V. Mapa", "J. Ruiz", "Gilmore", "Betty Go", "Araneta", "Anonas", "Katipunan", "Santolan", "Marikina", "Antipolo"};
        String[] mrt3Stations = {"North Avenue", "Quezon Avenue", "Kamuning", "Cubao", "Santolan-Annapolis", "Ortigas", "Shaw", "Boni", "Guadalupe", "Buendia", "Ayala", "Magallanes", "Taft"};

        terminalComboBox = new JComboBox<>(terminals);
        cardComboBox = new JComboBox<>(cards);
        statusComboBox = new JComboBox<>(regStat);
        stationFromComboBox = new JComboBox<>(mrt3Stations);
        stationToComboBox = new JComboBox<>(mrt3Stations);

        terminalComboBox.addActionListener(e -> {
            String selectedTerminal = (String) terminalComboBox.getSelectedItem();
            if (selectedTerminal.equals("LRT 1")) {
                stationFromComboBox.setModel(new DefaultComboBoxModel<>(lrt1Stations));
                stationToComboBox.setModel(new DefaultComboBoxModel<>(lrt1Stations));
            } else if (selectedTerminal.equals("LRT 2")) {
                stationFromComboBox.setModel(new DefaultComboBoxModel<>(lrt2Stations));
                stationToComboBox.setModel(new DefaultComboBoxModel<>(lrt2Stations));
            } else if (selectedTerminal.equals("MRT 3")) {
                stationFromComboBox.setModel(new DefaultComboBoxModel<>(mrt3Stations));
                stationToComboBox.setModel(new DefaultComboBoxModel<>(mrt3Stations));
            }
        });

        cardComboBox.addActionListener(e -> {
            String selectedTerminal = (String) cardComboBox.getSelectedItem();
            if (selectedTerminal.equals("Regular")) {
                statusComboBox.setModel(new DefaultComboBoxModel<>(regStat));
            } else if (selectedTerminal.equals("Beep Card")) {
                statusComboBox.setModel(new DefaultComboBoxModel<>(statuses));
            }
        });

        calculateButton = new JButton("Calculate Fare");
        fareLabel = new JLabel("Fare: ");
        paymentLabel = new JLabel("");
        
        add(new JLabel("Terminal: "));
        add(terminalComboBox);
        add(new JLabel("Card: "));
        add(cardComboBox);
        add(new JLabel("Status: "));
        add(statusComboBox);
        add(new JLabel("From: "));
        add(stationFromComboBox);
        add(new JLabel("To: "));
        add(stationToComboBox);
        add(calculateButton);
        add(fareLabel);
        add(new JLabel("TOTAL PAYMENT: "));
        add(paymentLabel);
        

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFare();
            }
        });

        setVisible(true);
    }

    private void calculateFare() {
        String terminal = (String) terminalComboBox.getSelectedItem();
        String card = (String) cardComboBox.getSelectedItem();
        String status = (String) statusComboBox.getSelectedItem();
        String stationFrom = (String) stationFromComboBox.getSelectedItem();
        String stationTo = (String) stationToComboBox.getSelectedItem();
    
        System.out.println(stationFrom);
        System.out.println(stationTo);
    
        double totalFare = 0;
        double fare = 0;
        double payment = 0;
    
        if (terminal.equals("LRT 1")) {
            int startIndex = stationFromComboBox.getSelectedIndex();
            int endIndex = stationToComboBox.getSelectedIndex();
            int increment = startIndex < endIndex ? 1 : -1;
            for (int i = startIndex; i != endIndex; i += increment) {
                totalFare += LRT1Fares[i][i + increment];
            }
        } else if (terminal.equals("LRT 2")) {
            int startIndex = stationFromComboBox.getSelectedIndex();
            int endIndex = stationToComboBox.getSelectedIndex();
            int increment = startIndex < endIndex ? 1 : -1;
            for (int i = startIndex; i != endIndex; i += increment) {
                totalFare += LRT2Fares[i][i + increment];
            }
        } else if (terminal.equals("MRT 3")) {
            int startIndex = stationFromComboBox.getSelectedIndex();
            int endIndex = stationToComboBox.getSelectedIndex();
            fare = MRT3Fares[stationFromComboBox.getSelectedIndex()][stationToComboBox.getSelectedIndex()];

            int increment = startIndex < endIndex ? 1 : -1;
            for (int i = startIndex; i != endIndex; i += increment) {
                totalFare += MRT3Fares[i][i + increment];
            }
        }
    
        if (card.equals("Beep Card")) {
            if (status.equals("Regular Passenger")) {
                totalFare = totalFare - (totalFare * 0.10);// Apply 10% discount

                fare = fare - (fare * 0.10);
            } else if (status.equals("Student")) {
                totalFare = totalFare - (totalFare * 0.15);// Apply 15% discount
                fare = fare - (fare * 0.15);
            } else if (status.equals("PWD")) {
                totalFare = totalFare - (totalFare * 0.20);// Apply 20% discount
                fare = fare - (fare * 0.20);
                System.out.println(fare);
            }
        }
        System.out.println(fare);
        
        payment = totalFare;
        
        fareLabel.setText("Fare: " + fare + " PHP");
        paymentLabel.setText( df.format(payment) + " PHP");
    }
    

    public static void main(String[] args) {
        new TrainFareCalculator();
    }
}
