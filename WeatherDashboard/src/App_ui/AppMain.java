package App_ui;

import App_ui.components.SearchBar;
import App_ui.components.WeatherInfoPanel;
import Models.WeatherData;
import assets.helpers.appAssets;
import assets.helpers.appTheme;

import javax.swing.*;
import java.awt.*;

public class AppMain extends JFrame {

    private final JPanel weatherPanelWrapper;
    private WeatherInfoPanel weatherPanel;

    public AppMain() {
        setTitle(appTheme.appTitle);
        setIconImage(appAssets.appIcon.getImage());
        setSize(appTheme.appWidth, appTheme.appHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Background Panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(appAssets.appBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        setContentPane(backgroundPanel);

        // Create SearchBar with callback for weather update
        SearchBar searchBar = new SearchBar(weatherData -> {
            // Update weather panel on UI thread
            SwingUtilities.invokeLater(() -> updateWeatherInfo(weatherData));
        });
        backgroundPanel.add(searchBar, BorderLayout.NORTH);

        // Initial dummy weather data
        WeatherData data = new WeatherData(
                32.23f, 33.2f, 30.73f, 32.23f,
                1007, 43, "Hyderabad, India",
                1746836164L, 1746882541L, 1746888483L
        );

        // Initialize WeatherInfoPanel with dummy data
        weatherPanel = new WeatherInfoPanel(appAssets.appIcon, data);
        weatherPanel.setPreferredSize(new Dimension(appTheme.appWidth - 24, 500));

        weatherPanelWrapper = new JPanel(new BorderLayout());
        weatherPanelWrapper.setOpaque(false);
        weatherPanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        weatherPanelWrapper.add(weatherPanel, BorderLayout.CENTER);

        backgroundPanel.add(weatherPanelWrapper, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateWeatherInfo(WeatherData data) {
        weatherPanelWrapper.removeAll();

        // You can decide how to pick the icon here based on weather data
        ImageIcon icon = appAssets.appIcon; // Replace with dynamic icon if you want

        weatherPanel = new WeatherInfoPanel(icon, data);
        weatherPanel.setPreferredSize(new Dimension(appTheme.appWidth - 24, 500));
        weatherPanelWrapper.add(weatherPanel, BorderLayout.CENTER);

        weatherPanelWrapper.revalidate();
        weatherPanelWrapper.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppMain::new);
    }
}
