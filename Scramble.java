import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*; 

public class Scramble extends JFrame{

    private Random rnd = new Random();
    private JFrame frame;
    private BufferedImage image;
    private Container pane;
    private JPanel options,grid;
    private JButton submit, clear;
    private JTextArea instructions,t;
    private Box[] boxList;
    private int[][] key;
    private int clicked, middle;


    private ActionListener aL = new ActionListener(){
	    public void actionPerformed(ActionEvent ae){
		middle = boxList.length/2;
		if(ae.getSource() == submit){
		    if(isCorrect(key,boxList)){
			t = new JTextArea(" Puzzle\n Complete!\n\n Close window\n to continue");
			t.setEditable(false);
			t.setBackground(Color.YELLOW);
			boxList[middle].add(t);
			boxList[middle].revalidate();
			unEnable(boxList);
		    }else {
			t = new JTextArea("\n Incorrect,\n Click 'Clear'\n to continue");
			t.setEditable(false);
			t.setBackground(Color.RED);
			boxList[middle].add(t);
			boxList[middle].revalidate();
		    }
		} else if (ae.getSource() == clear){    
		    try{
			boxList[middle].remove(t);
		    } catch(Exception e){}
		    boxList[middle].revalidate();
		}
		    
	    }
	};

    private  ActionListener actionListener = new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		Box b = (Box) actionEvent.getSource();
		if(clicked == -1){
		    b.setSelected(true);
		    b.setBorder(BorderFactory
				 .createLineBorder(Color.GREEN));
		     clicked = b.getBoxNum();
		} else if (b.getBoxNum() == clicked){
		    boxList[clicked].setSelected(false);
		    boxList[clicked].setBorder(BorderFactory
						 .createLineBorder(Color.BLUE));
		    clicked = -1;
		} else {
		    switchPlaces(boxList[clicked],b);
		    boxList[clicked].setSelected(false);
		    boxList[clicked].setBorder(BorderFactory
						 .createLineBorder(Color.BLUE));
		    clicked = -1;
		    b.setSelected(false);
		    b.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
	    }
	};

    public Scramble(){
	setTitle("Picture Scramble");
	setSize(600,1000);
	setLocation(100,100);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	//setResizable(false);
	pane = getContentPane();
	pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
	grid = new JPanel();
	grid.setMaximumSize(new Dimension(600,800));
	grid.setMinimumSize(new Dimension(600,800));
	grid.setLayout(new GridLayout(8,6));
	try {                
	    image = ImageIO.read(new File("pokecenter.jpg"));
	} catch (IOException ex) {
	    System.out.println("oops");
	}
	boxList = new Box[48];
	makeBoxes(boxList);
	makeKey();
       	shuffle(boxList);
	ColorBorders(boxList);
        for(int i = 0; i < boxList.length;i++){
	    boxList[i].setBoxNum(i);
	    grid.add(boxList[i]);
	}
	pane.add(grid);
	clicked = -1;
	String s = new String();
	s = " Complete the picture by reorganizing the frames.\n To switch a frame's location click on the frame and then click the frame you'd\n like to switch it with.\n Frames bordered in grey cannot be switched.\n Click 'Submit' when done.\n Click 'Clear' to clear on screen messages.";
	instructions = new JTextArea(s);
	instructions.setColumns(10);
	instructions.setRows(100);
	instructions.setBorder(BorderFactory.createLineBorder(Color.red,2));
	instructions.setEditable(false);
	pane.add(instructions);
	submit = new JButton("Submit");
	submit.setAlignmentX(Component.CENTER_ALIGNMENT);
	submit.addActionListener(aL);
	clear = new JButton("Clear");
	clear.setAlignmentX(Component.CENTER_ALIGNMENT);
	clear.addActionListener(aL);	
	pane.add(submit);
	pane.add(clear);
    }
    
   public void shuffle(Box[] buttons){

	for(int i = buttons.length -1; i > 0;i--){
	    if(i%3 == 0){
		int index = rnd.nextInt(i + 1);
      	        Box temp = buttons[index];
		buttons[index] = buttons[i];
		buttons[i] = temp;
		buttons[index].setEnabled(true);
		buttons[i].setEnabled(true);		
	    }
	}
	} 

    public void makeBoxes(JToggleButton[] buttons){
	int index = 0;
	for(int k = 0; k < 8;k++){
	    int y = k*100;
	    for(int j = 0; j < 6; j++){
		int x= j*100;
		buttons[index] = new Box(0,x,y);
		buttons[index].update(buttons[index].getGraphics());
		buttons[index].setEnabled(false);
		index += 1;
	    }
	}

    }
    
    public void switchPlaces(Box b1, Box b2){
	int x1 = b1.getx();
	int y1 = b1.gety();
	int x2 = b2.getx();
	int y2 = b2.gety();
	b1.setCoordinates(x2,y2);
	b2.setCoordinates(x1,y1);
	b1.repaint();
	b2.repaint();
    }
    
    public void ColorBorders(JToggleButton[] buttons){
	for(int i = 0;i< buttons.length;i++){
	    if(!buttons[i].isEnabled()){
		buttons[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    } else {
		buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLUE));
		buttons[i].addActionListener(actionListener);
	    }
	}
    }
    
    public void makeKey(){
	int y = 0;
	int x = 0;
	int index = 0;
	key = new int[48][2];
	for(int k=0;k<6;k++){
	    y = k*100;
	    for(int j=0;j<8;j++){
		x=j*100;
		key[index][0] = x;
		key[index][1] = y;
		index +=1;
	    }
	}

    }

    public boolean isCorrect(int[][] ans, Box[] submit){
      	for(int i = 0; i < ans.length; i ++){
	    if(submit[i].getx() != ans[i][0] && submit[i].gety() != ans[i][1]){
		return false;
	    }
	}
	return true;
    }

    public void unEnable(Box[] b){
	for(int i = 0; i < b.length;i++){
	    b[i].setEnabled(false);
	    b[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}
    }
    
    private class Box extends JToggleButton {
	int dstx1;
	int dsty1;
	int srcx1;
	int srcy1;
	int serialNum = 0;

	public Box(int dst, int srcx, int srcy){
	    dstx1 = dst;
	    dsty1 = dst;
	    srcx1 = srcx;
	    srcy1 = srcy;
	}

	public int getBoxNum(){
	    return serialNum;
	}

	public void setBoxNum(int n){
	    serialNum = n;
	}

	public void setCoordinates(int srcx, int srcy){
	    srcx1 = srcx;
	    srcy1 = srcy;
	}

	public int getx(){
	    return srcx1;
        }
	
	public int gety(){
	    return srcy1;
	}


	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(image, dstx1, dsty1, dstx1 + 100, dsty1 + 100, 
		        srcx1, srcy1, srcx1 + 100, srcy1 + 100, null);
	}
    }


    
    public static void main(String[] args){
	Scramble s = new Scramble();
	s.setVisible(true);
    }
}
