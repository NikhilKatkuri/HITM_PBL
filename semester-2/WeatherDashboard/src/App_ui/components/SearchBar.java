package App_ui.components;

import assets.helpers.appAssets;
import assets.helpers.appTheme;
import Models.WeatherData;
import weatherApi.WeatherDataApi;
import weatherApi.WeatherParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchBar extends JPanel {

    private final JTextField searchField;
    private final JButton searchButton;

    // Callback interface to notify on weather data update
    public interface WeatherUpdateListener {
        void onWeatherUpdate(WeatherData data);
    }

    private final WeatherUpdateListener listener;

    public SearchBar(WeatherUpdateListener listener) {
        this.listener = listener;

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 20, 10, 20)); // padding

        JPanel searchContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 77)); // ~30% opacity white
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        searchContainer.setOpaque(false);
        searchContainer.setBorder(new EmptyBorder(5, 15, 5, 10));

        searchField = new JTextField("Search city or place");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search city or place")) {
                    searchField.setText("");
                    searchField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search city or place");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        ImageIcon icon = new ImageIcon(appAssets.IconSearch.getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        searchButton = new JButton(icon);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add search action
        searchButton.addActionListener(e -> {
            String city = searchField.getText().trim();
            if (city.isEmpty() || city.equals("Search city or place")) {
                JOptionPane.showMessageDialog(this, "Please enter a city name.");
                return;
            }
            searchButton.setEnabled(false);
            searchField.setEnabled(false);

            new Thread(() -> {
                try {
                    String json = WeatherDataApi.getWeatherData(city);
                    WeatherData weatherData = WeatherParser.parseCurrentWeather(json);

                    SwingUtilities.invokeLater(() -> {
                        listener.onWeatherUpdate(weatherData);
                        searchButton.setEnabled(true);
                        searchField.setEnabled(true);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Failed to fetch weather data: " + ex.getMessage());
                        searchButton.setEnabled(true);
                        searchField.setEnabled(true);
                    });
                }
            }).start();
        });

        searchContainer.add(searchField, BorderLayout.CENTER);
        searchContainer.add(searchButton, BorderLayout.EAST);
        searchContainer.setPreferredSize(new Dimension(appTheme.appWidth - 12, 40));

        add(searchContainer, BorderLayout.CENTER);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
