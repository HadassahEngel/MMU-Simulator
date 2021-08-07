package com.hit.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;

/**
 * the panel for the interface that the user will see
 */
public class CacheUnitPanel extends JPanel implements ActionListener {
    private PropertyChangeSupport listener;
    private JButton buttonLoad;
    private JButton buttonStatistic;
    protected JTextArea textArea;
    private ButtonListener buttonListener;
    private JScrollPane scroll;
    private JPanel container;
    private JPanel panel1;
    private JPanel panel2;

    /**
     *
     * @param listener the listener to the changes in the class
     */
    public CacheUnitPanel(PropertyChangeSupport listener) {
        super();
        this.listener = listener;
        initializeUI();
    }

    /**
     *
     * @return all the panel
     */
    public JPanel getPanel() {
        return container;
    }

    /**
     * initialize the panel with Features
     * Initialize swing components
     */
    public void initializeUI() {
        container = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        ImageIcon loadImg = new ImageIcon("src/main/resources/icon/2.png");
        ImageIcon statisticImage = new ImageIcon("src/main/resources/icon/1.png");
        Image image1= loadImg.getImage();
        Image image2= statisticImage.getImage();
        Image goodSizeLoadImg= image1.getScaledInstance(20,20, Image.SCALE_SMOOTH);
        Image goodSizeStatisticImg= image2.getScaledInstance(20,20, Image.SCALE_SMOOTH);
        buttonLoad = new JButton("Load a Request", new ImageIcon(goodSizeLoadImg));
        buttonStatistic = new JButton("Show Statistics", new ImageIcon(goodSizeStatisticImg));
        System.out.println();
        buttonListener = new ButtonListener();
        textArea = new JTextArea(15, 32);
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setEditable(false);
        panel1.add(buttonLoad);
        panel1.add(buttonStatistic);
        panel2.add(scroll);
        container.add(panel1);
        container.add(panel2);
        buttonLoad.addActionListener(buttonListener);
        buttonStatistic.addActionListener(buttonListener);
    }


    /**
     *  Set button that will listen when press it
     *  if choose to load pages so opens file choose and file to load and read the file and send it to CacheUnitClientObserver
     *  if Show Statistics so send to the CacheUnitClientObserver that wash chosen the statics
     */
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "Load a Request" :
                {
                    String filePath = null;
                    JFileChooser chooser = new JFileChooser("src/main/resources/gson files");
                    int status = chooser.showOpenDialog(null);
                    if(status != JFileChooser.APPROVE_OPTION) {
                        textArea.setText("No file selected");
                    }
                    else {
                        File file = chooser.getSelectedFile();
                        filePath = file.getAbsolutePath();
                        try {
                            String json = readFileAsString(filePath);
                            listener.firePropertyChange(null, null, json);
                        }
                        catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    break;
                }

                case "Show Statistics" :
                {
                    listener.firePropertyChange(null, null, "Statistics");
                    break;
                }
                case "" :
                    textArea.setText("");
            }

        }

    }

    /**
     *
     * @param file the file that the user choose
     * @return the what was in the file as a string
     * @throws Exception
     */
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }}

