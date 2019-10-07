import com.sun.rowset.internal.Row;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame {
    private final int height = 600;
    private final int width = 800;
    private final String name = "Random Seat Generator";
    private final String version = "1.0.4";
    private final String defaultAllowList = "allowList.txt";
    private final String defaultBanList = "banList.txt";

    private JPanel menuPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;



    private JPanel buttonPanel;
    private JButton importButton;
    private JButton clearButton;
    private JButton startButton;
    private JLabel fileInfoLabel;

    private JComboBox rowComboBox;
    private JComboBox columnComboBox;
    private JComboBox boysComboBox;
    private JComboBox girlsComboBox;

    private JPanel seatPanel;

    private final int narrowHeight = 50;
    private final int separateLength = 10;
    private final int seatPanelHeight = 40;

    private final int MIN_ROW = 3;
    private final int MIN_COLUMN = 6;
    private final int MAX_ROW = 7;
    private final int MAX_COLUMN = 10;

    private JPanel[] rowPanels;
    private JLabel[][] seatLabels;

    private int numStudents;
    List<String> nameStudents;

    class myActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = rowComboBox.getSelectedIndex() + 3;
            int column = columnComboBox.getSelectedIndex() + 6;
            for(int i = 0; i < MAX_ROW; i ++){
                for(int j = 0; j < MAX_COLUMN; j ++){
                    seatLabels[i][j].setText(String.valueOf(i * column + j));
                    if(i < row && j < column)
                        seatLabels[i][j].setVisible(true);
                    else
                        seatLabels[i][j].setVisible(false);
                }
            }
        }
    }

    public GUI(){
        setTitle(name + " " + version);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numStudents = -1;

        setLayout(null);

        addComboBoxes();
        addMenuBar();
        addButtons();
        addButtonsLogic();

        addSeatPanel();

        setVisible(true);


    }

    private void addMenuBar(){
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
    }

    private void addButtons(){

        importButton = new JButton("Import");
        clearButton = new JButton("Clear");
        startButton = new JButton("Start");

        buttonPanel.add(importButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(startButton);
        add(buttonPanel);
    }

    private void addComboBoxes(){
        buttonPanel = new JPanel();

        buttonPanel.setBounds(separateLength, separateLength, width, narrowHeight);

        JLabel rowLabel = new JLabel("row: ");
        rowComboBox = new JComboBox();
        JLabel columnLabel = new JLabel("column: ");
        columnComboBox = new JComboBox();
        JLabel boysLabel = new JLabel("Boys: ");
        boysComboBox = new JComboBox();
        JLabel girlsLabel = new JLabel("Girls ");
        girlsComboBox = new JComboBox();

        for(int i = 3; i <= 7; i++){
            rowComboBox.addItem(i);
        }

        for(int i = 6; i <= 10; i++){
            columnComboBox.addItem(i);
        }

        for(int i = 0; i < 60; i++){
            boysComboBox.addItem(i);
            girlsComboBox.addItem(i);
        }



        buttonPanel.add(rowLabel);
        buttonPanel.add(rowComboBox);
        rowComboBox.addActionListener(new myActionListener());
        buttonPanel.add(columnLabel);
        buttonPanel.add(columnComboBox);
        columnComboBox.addActionListener(new myActionListener());
        buttonPanel.add(boysLabel);
        buttonPanel.add(boysComboBox);
        buttonPanel.add(girlsLabel);
        buttonPanel.add(girlsComboBox);


    }

    private void addButtonsLogic(){
        addImportLogic();
        addStartLogic();
        addClearLogic();
    }

    private void addImportLogic(){
        importButton.addActionListener(e -> {

            JFileChooser jf = new JFileChooser();
            int status = jf.showOpenDialog(this);

            if(status == JFileChooser.APPROVE_OPTION){
                try {
                    File fp = jf.getSelectedFile();
                    List<String> content = FileReader.readFileByLines(fp);

                    if( null == content ){
                        JOptionPane.showMessageDialog(this, "Failed to read file " + fp.getAbsolutePath() + ".");
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Read " + content.size() + " line(s).");
                        numStudents = content.size();
                        nameStudents = content;
                        fileInfoLabel.setText("File: " + fp.getName());
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addClearLogic(){
        clearButton.addActionListener(e -> {
            try{
                numStudents = -1;
                nameStudents.clear();
                fileInfoLabel.setText("Info: No file chosen.");

                JOptionPane.showMessageDialog(this, "Cleared Successfully.");
            }

            catch (Exception ex){
                System.out.println("Exception Caught.");

                JOptionPane.showMessageDialog(this, "Failed to clear the information, please try again.");

                ex.printStackTrace();
            }
        });
    }


    private void addStartLogic(){
        addStartListener();
    }

    private void addSeatPanel(){
        seatPanel = new JPanel();
        fileInfoLabel = new JLabel("Info: No file chosen.");

        seatPanel.setBounds(0, 60,  800, narrowHeight);
        seatPanel.add(fileInfoLabel);
        add(seatPanel);

        rowPanels = new JPanel[7];
        seatLabels = new JLabel[7][10];

        final int startX = 120;
        final int rowHeight = 50;

        for(int i = 0; i < MAX_ROW; i ++){
            rowPanels[i] = new JPanel();
            rowPanels[i].setBounds(0, startX + i * rowHeight, width, 30);
            for(int j = 0; j < MAX_COLUMN; j++){
                seatLabels[i][j] = new JLabel(String.valueOf(i * MIN_COLUMN + j));
                seatLabels[i][j].setPreferredSize(new Dimension(60,30));
                rowPanels[i].add(seatLabels[i][j]);

                if(i < MIN_ROW && j < MIN_COLUMN)
                    seatLabels[i][j].setVisible(true);
                else
                    seatLabels[i][j].setVisible(false);
            }

            add(rowPanels[i]);
        }
    }

    private int getRow(){
        return MIN_ROW + rowComboBox.getSelectedIndex();
    }

    private int getColumn(){
        return MIN_COLUMN + columnComboBox.getSelectedIndex();
    }

    private void addStartListener() {
        startButton.addActionListener(e -> {

            int column = getColumn();
            int row = getRow();
            int numSeat = row * column;

            if (numStudents == -1) {
                JOptionPane.showMessageDialog(null, "ERROR: No file chosen.");
                return;
            } else if (numStudents > numSeat) {
                int val = JOptionPane.showConfirmDialog(this, "There are " + numStudents + " student(s) but only " + numSeat + " seat(s) offered. Continue?");
                if (val != JOptionPane.YES_OPTION)
                    return;
            }


            File allowListFile, banListFile;
            allowListFile = new File(defaultAllowList);
            banListFile = new File(defaultBanList);

            int[][] allowList = FileReader.readRules(allowListFile, numStudents);
            int[][] banList = FileReader.readRules(banListFile, numStudents);

            int boys = Integer.parseInt(boysComboBox.getSelectedItem().toString());
            int girls = Integer.parseInt(girlsComboBox.getSelectedItem().toString());

            System.out.println("Student: " + numStudents);
            System.out.println("Boys: " + boys);
            System.out.println("Girls: " + girls);

            final int max_tries = 50;
            int cur = 0;

            int[] permutation = null;
            while(cur < max_tries) {
                permutation = RandomGenerator.randomPermutation(numStudents, row, column, true, true, boys, girls, allowList, banList);
                if(permutation != null) break;
                cur++;
            }

            if(permutation != null) {
                updateSeat(row, column, permutation);
            }

            else{
                JOptionPane.showMessageDialog(this, "Failed to generate seats due to too much constraints.");
            }
        });
    }

    void disableAll(){
        rowComboBox.setEnabled(false);
        columnComboBox.setEnabled(false);
        startButton.setEnabled(false);
        importButton.setEnabled(false);
        clearButton.setEnabled(false);
    }

    void enableAll(){
        rowComboBox.setEnabled(true);
        columnComboBox.setEnabled(true);
        startButton.setEnabled(true);
        importButton.setEnabled(true);
        clearButton.setEnabled(true);
    }

    private void updateSeat(int row, int column, int[] permutation){

        Thread animationThread = new Thread () {
            @Override
            public void run() {
                disableAll();

                for(int i = 0; i < row; i++)
                    for(int j=0; j < column; j++){
                        int index = i * column + j;
                        if(null != permutation && index < numStudents) {
                            seatLabels[i][j].setText(nameStudents.get(permutation[index]));
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        else break;
                    }

                enableAll();
            }
        };
        animationThread.start();

    }


    public static void main(String[] args){
        new GUI();
    }

}
