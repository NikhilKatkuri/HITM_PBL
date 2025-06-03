package App_ui.components;

import Models.WeatherData;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class WeatherInfoPanel extends JPanel {
    private String formatTime(long unixTime) {
        ZonedDateTime dateTime = Instant.ofEpochSecond(unixTime).atZone(ZoneId.systemDefault());
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String period = hour >= 12 ? "PM" : "AM";
        hour = hour % 12;
        if (hour == 0) hour = 12;
        return String.format("%02d:%02d %s", hour, minute, period);
    }

    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        private int thickness;

        public RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 51)); // 20% opacity black
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness / 2, y + thickness / 2,
                    width - thickness, height - thickness,
                    radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }
    }

    private JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        JLabel keyLabel = new JLabel(label);
        keyLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        keyLabel.setForeground(new Color(255,255,255,225));
        keyLabel.setPreferredSize(new Dimension(200, 36));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setForeground(Color.white);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setPreferredSize(new Dimension(100, 36));

        row.add(keyLabel);
        row.add(Box.createHorizontalGlue());
        row.add(valueLabel);

        return row;
    }

    public WeatherInfoPanel(ImageIcon weatherIcon, WeatherData data) {
        setOpaque(false);
        setLayout(new BorderLayout());

        String greeting = getGreeting(data.currentTime(), data.sunrise(), data.sunset());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel(scaleIcon(weatherIcon, 120, 120));
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(imageLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel temperatureLabel = new JLabel(String.format("%.1f°C", data.temp()));
        temperatureLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        temperatureLabel.setForeground(Color.white);
        temperatureLabel.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(temperatureLabel);

        JLabel greetingLabel = new JLabel(greeting);
        greetingLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        greetingLabel.setForeground(Color.white);
        greetingLabel.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(greetingLabel);

        JLabel locationLabel = new JLabel(data.cityName());
        locationLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        locationLabel.setForeground(Color.white);
        locationLabel.setAlignmentX(CENTER_ALIGNMENT);
        locationLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        centerPanel.add(locationLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel detailsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 51));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        detailsPanel.setOpaque(false);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setAlignmentX(CENTER_ALIGNMENT);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(24, Color.BLACK, 2),
                new EmptyBorder(16,16,16,16)
        ));

        detailsPanel.add(createDetailRow("Feels Like:", String.format("%.1f°C", data.feelsLike())));
        detailsPanel.add(createDetailRow("Min Temp:", String.format("%.1f°C", data.tempMin())));
        detailsPanel.add(createDetailRow("Max Temp:", String.format("%.1f°C", data.tempMax())));
        detailsPanel.add(createDetailRow("Pressure:", data.pressure() + " hPa"));
        detailsPanel.add(createDetailRow("Humidity:", data.humidity() + "%"));
        detailsPanel.add(createDetailRow("Sunrise:", formatTime(data.sunrise())));
        detailsPanel.add(createDetailRow("Sunset:", formatTime(data.sunset())));

        centerPanel.add(detailsPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private String getGreeting(long currentUnix, long sunriseUnix, long sunsetUnix) {
        ZonedDateTime current = Instant.ofEpochSecond(currentUnix).atZone(ZoneId.systemDefault());
        ZonedDateTime sunrise = Instant.ofEpochSecond(sunriseUnix).atZone(ZoneId.systemDefault());
        ZonedDateTime sunset = Instant.ofEpochSecond(sunsetUnix).atZone(ZoneId.systemDefault());

        if (current.isAfter(sunrise) && current.isBefore(sunrise.plusHours(6))) {
            return "Good Morning";
        } else if (current.isAfter(sunrise.plusHours(6)) && current.isBefore(sunset.minusHours(1))) {
            return "Good Afternoon";
        } else if (current.isAfter(sunset.minusHours(1)) && current.isBefore(sunset.plusHours(3))) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }
}
