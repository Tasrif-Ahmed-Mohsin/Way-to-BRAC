import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PathfinderGUI {
    static area a = new area("Mirpur 14");
    static area b = new area("Banani");
    static area c = new area("ECB");
    static area d = new area("Jahangirgate");
    static area e = new area("Gulshan");
    static area f = new area("Jamuna");
    static area g = new area("Notunbazar");
    static area h = new area("BRAC");
    static boolean priorik = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Way to Brac");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JPanel panel = new JPanel();
       panel.setLayout(new GridLayout(5, 2));


        JPanel startPanel = new JPanel();
        startPanel.setBorder(BorderFactory.createTitledBorder("Select Starting Point"));
        ButtonGroup startGroup = new ButtonGroup();
        String[] areaNames = {"Mirpur 14 ", "Banani", "ECB", "Jahangirgate", "Gulshan", "Jamuna", "Notunbazar"};
        JRadioButton[] startButtons = new JRadioButton[areaNames.length];

        for (int i = 0; i < areaNames.length; i++) {
            startButtons[i] = new JRadioButton(areaNames[i]);
            startButtons[i].setActionCommand(String.valueOf(i)); // index as string
            startGroup.add(startButtons[i]);
            startPanel.add(startButtons[i]);
        }

        panel.add(startPanel);


        JLabel transportLabel = new JLabel("Transport Preference:");
        String[] options = {"Rikshaw", "Bus"};
        JComboBox<String> transportBox = new JComboBox<>(options);
        transportBox.setPreferredSize(new Dimension(50, 1)); // width, height
        transportBox.setFont(new Font("Arial", Font.PLAIN, 20)); // smaller font


        panel.add(transportLabel);
        panel.add(transportBox);

        JButton computeButton = new JButton("Compute Paths");
        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);

        panel.add(computeButton);

        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);

        road x = new road(null, null, 0, 0);
        road[][] mat = {
                {x, new road(a, b, 10, 80), new road(a, c, 20, 60), new road(a, d, 0, 80), x, x, x, x},
                {x, x, x, x, new road(b, e, 10, 0), x, x, x},
                {x, x, x, x, x, new road(c, f, 10, 0), x, x},
                {x, x, x, x, x, x, x, new road(d, h, 0, 120)},
                {x, x, x, x, x, x, new road(e, g, 10, 0), x},
                {x, x, x, x, x, x, new road(f, g, 10, 0), x},
                {x, x, x, x, x, x, x, new road(g, h, 10, 60)},
                {x, x, x, x, x, x, x, x},
        };

        computeButton.addActionListener(e1 -> {
            output.setText("");
            int start;
            if (startGroup.getSelection() != null) {
                start = Integer.parseInt(startGroup.getSelection().getActionCommand());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a starting point.");
                return;
            }

            priorik = transportBox.getSelectedIndex() == 0;
            printAllPathsGUI(mat, x, start, 7, output);
        });

        frame.setVisible(true);
    }

    public static void printAllPathsGUI(road[][] mat, road x, int src, int dest, JTextArea output) {
        boolean[] visited = new boolean[mat.length];
        List<Integer> path = new ArrayList<>();
        List<road> roadlink = new ArrayList<>();
        dfsPathsGUI(mat, x, src, dest, visited, path, roadlink, output);
    }

    private static void dfsPathsGUI(road[][] mat, road x, int curr, int dest, boolean[] visited, List<Integer> path, List<road> roadlink, JTextArea output) {
        visited[curr] = true;
        path.add(curr);

        if (curr == dest) {
            output.append(formatPath(path, roadlink) + "\n");
        } else {
            for (int i = 0; i < mat.length; i++) {
                if (!mat[curr][i].equals(x) && !visited[i]) {
                    roadlink.add(mat[curr][i]);
                    dfsPathsGUI(mat, x, i, dest, visited, path, roadlink, output);
                    roadlink.remove(roadlink.size() - 1);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[curr] = false;
    }

    private static String formatPath(List<Integer> path, List<road> roadlink) {
        StringBuilder sb = new StringBuilder();
        area[] areas = {a, b, c, d, e, f, g, h};
        int cost = 0;
        for (int i = 0; i < path.size(); i++) {
            sb.append(areas[path.get(i)].n);
            if (i < roadlink.size()) {
                if (roadlink.get(i).bus != 0 && !priorik) {
                    sb.append(" --> (Bus :").append(roadlink.get(i).bus).append(") --> ");
                    cost += roadlink.get(i).bus;
                } else if (roadlink.get(i).rik == 0) {
                    sb.append(" --> (Bus :").append(roadlink.get(i).bus).append(") --> ");
                    cost += roadlink.get(i).bus;
                } else {
                    sb.append(" --> (Rikshaw :").append(roadlink.get(i).rik).append(") --> ");
                    cost += roadlink.get(i).rik;
                }
            }
        }
        sb.append("\nTotal cost: ").append(cost);
        return sb.toString();
    }
}

class road {
    area src;
    area dest;
    int bus;
    int rik;

    road(area src, area dest, int bus, int rik) {
        this.src = src;
        this.dest = dest;
        this.bus = bus;
        this.rik = rik;
    }
}

class area {
    String n;
    area(String n) {
        this.n = n;
    }
}
