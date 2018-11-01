import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.*;

class SpellCheck 
{
	//Main Method
	public static void main(String[] args) throws IOException
	{
		//Calling the MyFrame Class
		new MyFrame();
	}
}
//Creating the GUI
class MyFrame extends JFrame implements ActionListener 
{
	private static final int FRAME_WIDTH = 500; //Width of the Frame
 	private static final int FRAME_HEIGHT = 500;//Height of the Frame
	public JButton b2,b;//initializing two JButton
	public TextField t1;//initializing one TextField
	public JLabel l1;//initializing one JLabel
	public JTextArea a1;//initializing one JTextArea
	public MyFrame()
	{
		JPanel p = new JPanel();
		setTitle("Spell Checker");//Set title of the Frame
		setSize(FRAME_WIDTH, FRAME_HEIGHT);//Set size of the Frame
		p.setLayout(null);//Null the layout of the panel
		p.setPreferredSize( new Dimension( FRAME_WIDTH, FRAME_HEIGHT) );//Size of the Panel
		p.setBackground(Color.BLACK);//Set Background of the panel
		l1 = new JLabel("Enter Word");//Set l1 Label
		l1.setBounds(180,0,150,50);//Location of the l1 Label
		l1.setForeground(Color.white);//Set the font color of the Label
		l1.setFont(new Font("Serif", Font.PLAIN, 30));//Set the font of the Label
		t1 = new TextField(20);//Set t1 TextField
		t1.setBounds(175,50,150,30);//Location of the t1 TextField
		t1.setFont(new Font("Serif", Font.PLAIN, 20));////Set the font of the TextField
		p.add(l1);//Add l1 Label to panel
		p.add(t1);//Add t1 Label to panel
		b2 = new JButton("Enter");//Set b2 JButton
		b2.setBounds(143,115,90,30);//Location of the b2 JButton
		b2.setBackground(Color.red);//Set Background of the JButton
		b2.setForeground(Color.white);//Set the font color of the JButton
		b = new JButton("Clear");//Set b JButton
		b.setBounds(253,115,90,30);//Location of the b JButton
		b.setBackground(Color.red);//Set Background of the JButton
		b.setForeground(Color.white);//Set the font color of the JButton
		p.add(b);//Add b Label to panel
		p.add(b2);//Add b2 Label to panel
		a1 = new JTextArea(10,30);//Set a1 JTextArea
		a1.setBounds(120,150,150,150);//Location of the a1 JTextArea
		a1.setEditable(false);//Set the a1 editable to False
		p.add(a1);//Add a1 Label to panel
		JScrollPane scrollPane = new JScrollPane(a1);//initializing one JScrollPane and add to a1
		scrollPane.setBounds(45,200,400,150);//Location of the scrollPane JScrollPane
		p.add(scrollPane);//Add scrollPane Label to panel
		add(p);//Add panel to the Frame
		b.addActionListener(this);//Set b to actionPerformed
		b2.addActionListener(this);//Set b2 to actionPerformed
		setVisible(true);//Set the frame visible to true
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Set the frame with DefaultCloseOperation
		
	}
	//Functions of the Buttons
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==b)//If button b is clicked
		{
			t1.setText("");//Clear the t1 TextField
			a1.setText("");//Clear the a1 JTextArea
		}
		if(e.getSource()==b2)//If button b2 is clicked
		{
			String inWord = t1.getText();//Set the inWord to the getText of t1 
			a1.setText("");//Clear the a1 JTextArea
			
			for(int w = 0;w < inWord.length();w++)//Loop that will check every letter inputted
			{
				if(Character.isDigit(inWord.charAt(w)))//If the inputted word has a digit
				{
					t1.setFont(new Font("Serif", Font.PLAIN, 20));//Set the font of the TextField
					t1.setText("Invalid Input");//Set t1 TextField output
				}
			}
			
			if(inWord.charAt(0) != 'a' || inWord.length() > 5 || inWord.length() == 1)//Check the word if its more than 5 letter 
			//or only 1 letter or the letter did not start at a
			{
				t1.setFont(new Font("Serif", Font.PLAIN, 20));//Set the font of the TextField
				t1.setText("Invalid Input");//Set t1 TextField output
			}
			else
			{
				try 
				{
					File file = new File("words.txt");//Initialize file
					Scanner in = new Scanner(file);//Read the file
					int suggest;//Initialize suggest to integer
					while(in.hasNextLine())//If file has next line
					{
						String lineWord = in.nextLine();//Initialize lineWord to every line
						if(inWord.toLowerCase().equals(lineWord.toLowerCase()))//If the inputted word is equal to the line word
						{
							a1.setFont(new Font("Serif", Font.PLAIN, 20));//Set the a1 Font
							a1.setText("Correct Spelling");//Set the a1 output
							break;//
						}
						if(!inWord.toLowerCase().equals(lineWord.toLowerCase()))//If the inputted word is not equal to the line word
						{
							twoStringDis dis = new twoStringDis(inWord,lineWord.toLowerCase());//Initialize the twoStringDis class
							suggest = dis.getSimilarity();//set suggest value
							if(suggest < 2)//If suggest is less than 2
							{
								a1.setFont(new Font("Serif", Font.PLAIN, 20));//Set the a1 Font
								a1.append(lineWord + "\n");//Set the a1 output
								}
						}
					}
				}
				catch(IOException x)//catch if the file is missing
				{
					t1.setText("File is Missing");//Set the t1 output
				}
			}
		}
	}
}
//Comparing the distance of the two Strings
class twoStringDis
{
    private String stringOne;// Initialize one String
    private String stringTwo;// Initialize one String
    private int[][] distance;// Initialize 2 dimensional array integer
    private Boolean calculated = false;// Initialize one boolean
 	//
    public twoStringDis(String one, String two)
    {
        stringOne = one;// Set stringOne to one
        stringTwo = two;// Set stringTwo to two
    }
 	//
    public int getSimilarity()
    {
        if (!calculated)//If the distance is not calculated
        {
            setupDistance();//Calculate the distance
        }
        return distance[stringOne.length()][stringTwo.length()];//Return the distance
    }
    //Set the distance
    public void setupDistance()
    {
        distance = new int[stringOne.length()+1][stringTwo.length()+1];//Initialize the distance
 
        for (int i = 0; i <= stringOne.length(); i++)//loop to input value to the distance array
        {
            distance[i][0] = i;//set row distance value
        }
 
        for (int j = 0; j <= stringTwo.length(); j++)////loop to input value to the distance array
        {
            distance[0][j] = j;//set column distance value
        }
 
        for (int i = 1; i < distance.length; i++)//loop to compare the letter of the 1st String to the 2nd String
        {
            for (int j = 1; j < distance[i].length; j++)//loop to compare one letter of the 1st String to all letter of 2nd String
            {
                if (stringOne.charAt(i-1) == stringTwo.charAt(j-1))//if the letter of 1st and 2nd String are same
                {
                    distance[i][j] = distance[i-1][j-1];//Set the distance value
                }
                else
                {
                    int minimum = Integer.MAX_VALUE;//set minimum to maximum
                    if ((distance[i-1][j])+1 < minimum)//If the value of distance is less than minimum
                    {
                        minimum = (distance[i-1][j])+1;//Set Minimum
                    }
 
                    if ((distance[i][j-1])+1 < minimum)//If the value of distance is less than minimum
                    {
                        minimum = (distance[i][j-1])+1;//Set Minimum
                    }
 
                    if ((distance[i-1][j-1])+1 < minimum)//If the value of distance is less than minimum
                    {
                        minimum = (distance[i-1][j-1])+1;//Set Minimum
                    }
 
                    distance[i][j] = minimum;//Set distance value to minimum
                }
            }
        }
        calculated = true;//Set calculated to True
    }
}