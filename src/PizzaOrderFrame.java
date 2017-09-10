import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Yisroel
 */
public class PizzaOrderFrame extends JFrame 
{
    private JPanel orderPnl, receiptPnl, buttonPnl, sizePnl, thicknessPnl, toppingsPnl, main;
    private JButton orderBtn, clearBtn, quitBtn;
    private JRadioButton thinRB, regularRB, deepDishRB;
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    private DefaultComboBoxModel sizes;
    private JComboBox pizzaSize;
    private ArrayList<JCheckBox> toppingsCB = new ArrayList<>();
    private JCheckBox peppersCB, pepperoniCB, sardinesCB, mushroomsCB, friedOnionCB, onionsCB, chickenCB, extraCheeseCB;
    private JTextArea receipt;
    private TitledBorder thicknessBdr, sizeBdr, toppingsBdr;
    private double sum;
    private String styleTxt, sizeTxt;
    
    PizzaOrderFrame()
    {
        super("Pizza Order GUI");
        createOrderPanel();
        createRecieptPanel();
        createButtonPanel();
        main = new JPanel();
      
        main.setLayout(new BorderLayout());
        main.add(orderPnl,BorderLayout.NORTH);
        main.add(receiptPnl,BorderLayout.CENTER);
        main.add(buttonPnl,BorderLayout.SOUTH);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3 * (screenSize.width / 4), 3 * (screenSize.height / 4));
        setLocationRelativeTo(null);
        
        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void createOrderPanel()
    {
        orderPnl = new JPanel();
        sizePnl = new JPanel();
        thicknessPnl = new JPanel();
        toppingsPnl = new JPanel();
        
        //creates the radio buttons for the crust style 
        thinRB = new JRadioButton("Thin Crust");
        regularRB = new JRadioButton("Regular Crust");
        deepDishRB = new JRadioButton("Deep-Dish");
        //set action commands 
        thinRB.setActionCommand("thin");
        regularRB.setActionCommand("regular");
        deepDishRB.setActionCommand("deep dish");
        //add to button group so only one button can clicked at once
        radioButtonGroup.add(thinRB);
        radioButtonGroup.add(regularRB);
        radioButtonGroup.add(deepDishRB);
        //add to the thickness panel
        thicknessPnl.add(thinRB);
        thicknessPnl.add(regularRB);
        thicknessPnl.add(deepDishRB);
        
        //create and add titled border for pizza thickness
        thicknessBdr = BorderFactory.createTitledBorder("Pizza Style");
        thicknessPnl.setBorder(thicknessBdr);
        
        //create the  box model (strings for the combobox) for the combo box  
        sizes = new DefaultComboBoxModel();
        sizes.addElement("Small Pizza: $8.00");
        sizes.addElement("Medium Pizza: $12.00");
        sizes.addElement("Large Pizza: $16.00");
        sizes.addElement("Super Size Pizza: $20.00");
        
        // create combobox with the default combobox model
        pizzaSize = new JComboBox(sizes);
        pizzaSize.setSelectedIndex(0);
        sizePnl.add(pizzaSize);
        
        //add a border to the pnl containing the combobox
        sizeBdr = BorderFactory.createTitledBorder("Pizza Size");
        sizeBdr.setTitleJustification(TitledBorder.CENTER);
        sizePnl.setBorder(sizeBdr);
        
        //create panel for the toppings--first intiate the check boxes
        toppingsPnl.setLayout(new GridLayout(4, 4));
        peppersCB = new JCheckBox("Peppers");
        pepperoniCB = new JCheckBox("Pepperoni");
        sardinesCB = new JCheckBox("Sardines");
        mushroomsCB = new JCheckBox("Mushrooms");
        friedOnionCB = new JCheckBox("Fried Onion");
        onionsCB = new JCheckBox("Onions");
        chickenCB = new JCheckBox("Chicken");
        extraCheeseCB = new JCheckBox("Extra Cheese");
        
        ToppingButtonListener toppingButtonListener = new ToppingButtonListener();
        peppersCB.addActionListener((ActionListener) toppingButtonListener);
        pepperoniCB.addActionListener(toppingButtonListener);
        sardinesCB.addActionListener(toppingButtonListener);
        mushroomsCB.addActionListener(toppingButtonListener);
        friedOnionCB.addActionListener(toppingButtonListener);
        onionsCB.addActionListener(toppingButtonListener);
        chickenCB.addActionListener(toppingButtonListener);
        extraCheeseCB.addActionListener(toppingButtonListener);
       
        //set action (and what will be printed) so we can tell when they are checked
        peppersCB.setActionCommand("Peppers");
        pepperoniCB.setActionCommand("Pepperoni");
        sardinesCB.setActionCommand("Sardines");
        mushroomsCB.setActionCommand("Mushrooms");
        friedOnionCB.setActionCommand("Fried Onions");
        onionsCB.setActionCommand("Onions");
        chickenCB.setActionCommand("Chicken");
        extraCheeseCB.setActionCommand("Extra Cheese");
        
//add check boxes to array for printing in receipt
        toppingsCB.add(mushroomsCB);
        toppingsCB.add(peppersCB);
        toppingsCB.add(pepperoniCB);
        toppingsCB.add(sardinesCB);
        toppingsCB.add(onionsCB);
        toppingsCB.add(friedOnionCB);
        toppingsCB.add(chickenCB);
        toppingsCB.add(extraCheeseCB);
        
        //add the checkboxes to the panel
        toppingsPnl.add(mushroomsCB);
        toppingsPnl.add(peppersCB);
        toppingsPnl.add(onionsCB);
        toppingsPnl.add(friedOnionCB);
        toppingsPnl.add(sardinesCB);
        toppingsPnl.add(chickenCB);
        toppingsPnl.add(pepperoniCB);
        toppingsPnl.add(extraCheeseCB);
        
        //create and add the titled border
        toppingsBdr = BorderFactory.createTitledBorder("Toppings");
        toppingsBdr.setTitleJustification(TitledBorder.RIGHT);
        toppingsPnl.setBorder(toppingsBdr);
        
        /*add the three subPanels into the  bigger order Panel
        that will hve all the pizza options */
        orderPnl.setLayout(new BorderLayout());
        orderPnl.add(thicknessPnl,BorderLayout.WEST);
        orderPnl.add(sizePnl,BorderLayout.CENTER);
        orderPnl.add(toppingsPnl,BorderLayout.EAST);
    }
    
    private void createRecieptPanel()
    {
        // create the text area and format the output, meaning the receipt with all the proper values
        receiptPnl = new JPanel();
        receipt = new JTextArea();
        receiptPnl.add(receipt);
    }
    
    private void createButtonPanel()
    {
        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1, 3));
        orderBtn = new JButton("Order");
        clearBtn = new JButton("Clear");
        quitBtn = new JButton("Quit");
        
        orderBtn.addActionListener((ActionEvent ae) -> {
            sum = 0.00;
            StringBuilder receiptString = new StringBuilder();

            sizeTxt = (String)pizzaSize.getSelectedItem();
            styleTxt = radioButtonGroup.getSelection().getActionCommand();

            receiptString.append("==============================");
            receiptString.append("\n");

            switch(styleTxt) {
                case "thin":
                    receiptString.append("Thin Crust");
                    break;
                case "regular":
                    receiptString.append("Regular Crust");
                    break;
                case "deep":
                    receiptString.append("Deep Dish Crust");
                    break;
            }

            receiptString.append(" - ");

            if(sizeTxt.toLowerCase().contains("small")) {
                receiptString.append("Small");
                receiptString.append("\t\t8.00");
                sum += 8.00;
            }
            else if(sizeTxt.toLowerCase().contains("medium")) {
                receiptString.append("Medium");
                receiptString.append("\t\t12.00");
                sum += 12.00;
            }
            else if(sizeTxt.toLowerCase().contains("super")) {
                receiptString.append("Super Size");
                if(styleTxt.equals("deep")) {
                    receiptString.append("\t20.00");
                }
                else {
                    receiptString.append("\t\t20.00");
                }
                sum += 20.00;
            }
            else if(sizeTxt.toLowerCase().contains("large")) {
                receiptString.append("Large");
                receiptString.append("\t\t16.00");
                sum += 16.00;
            }

            receiptString.append("\n");
            receiptString.append("Toppings:");
            receiptString.append("\n");

            for(JCheckBox jCheckBox : toppingsCB) {
                if(jCheckBox.isSelected()) {
                    receiptString.append("      ");
                    receiptString.append(jCheckBox.getActionCommand());
                    receiptString.append("\t\t1.00");
                    receiptString.append("\n");
                    sum += 1.00;
                }
            }

            receiptString.append("\n\n");
            receiptString.append("Subtotal:");
            receiptString.append("\t\t\t");
            receiptString.append(String.format("%.2f", sum));
            receiptString.append("\n");
            receiptString.append("Tax:");
            receiptString.append("\t\t\t");
            receiptString.append(String.format("%.2f", sum * 0.07));
            receiptString.append("\n");

            for(int i = 0; i < 75; i++) {
                receiptString.append("-");
            }

            receiptString.append("\n");
            receiptString.append("Total:");
            receiptString.append("\t\t\t");
            receiptString.append(String.format("%.2f", (sum * 0.07) + sum));
            receiptString.append("\n");
            receiptString.append("==============================");

            receiptString.trimToSize();
            receipt.setText(receiptString.toString());
          
        });
        
        clearBtn.addActionListener((ActionEvent ae) -> {
             int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to clear the current selections?",
                    "Confirm Clear", JOptionPane.YES_NO_OPTION);
                if(option == 0) {
                radioButtonGroup.clearSelection();
                pizzaSize.setSelectedIndex(0);
                for(JCheckBox toppingsButton : toppingsCB) {
                    toppingsButton.setSelected(false);
                }
                receipt.setText("");
            }
        });
        
        quitBtn.addActionListener((ActionEvent ae) -> {
             System.exit(0);
        });
        
        buttonPnl.add(orderBtn);
        buttonPnl.add(clearBtn);
        buttonPnl.add(quitBtn);
    }
    
    private class radioButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }

    private class ToppingButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
}
