import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Keypad extends JDialog {

    private JPanel kpad;
    private Container pane;
    private JButton one, two, three, four, five, six, seven, eight, nine, zero, star, pound, enter, clear;
    private JTextArea spaces, status;
    /*
    private ActionListener aL = new ActionListener() {
	    public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == one) {
		    
    */


    public Keypad() {
	setTitle("Keypad");
	setSize(650, 450);
	setLocation(100, 100);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pane = getContentPane();
	GroupLayout layout = new GroupLayout(pane);
	pane.setLayout(layout);
	spaces = new JTextArea("hello", 100, 20);
	pane.add(spaces);
	kpad = new JPanel();
	one = new JButton("1");
	two = new JButton("2");
	three = new JButton("3");
	four = new JButton("4");
	five = new JButton("5");
	six = new JButton("6");
	seven = new JButton("7");
	eight = new JButton("8");
	nine = new JButton("9");
	zero = new JButton("0");
	star = new JButton("*");
	pound = new JButton("#");
	one.setLocation(180, 100);
	one.setSize(75, 45);
	pane.add(one);
	two.setLocation(260, 100);
	two.setSize(75, 45);
	pane.add(two);
	three.setLocation(340, 100);
	three.setSize(75, 45);
	pane.add(three);
	four.setLocation(180, 150);
	four.setSize(75, 45);
	pane.add(four);
	five.setLocation(260, 150);
	five.setSize(75, 45);
	pane.add(five); /*
	pane.add(six);
	pane.add(seven);
	pane.add(eight);
	pane.add(nine);
	pane.add(zero);
	*/
    }

    public static void main(String[] args) {
	Keypad k = new Keypad();
	k.setVisible(true);
    }

}
