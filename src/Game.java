import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.*; 
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	//Management Variables
	private BufferedImage back; 
	private File file;
		
	//Numbers
	private int key;
	private int index;

    private Graphics2D g2d;

	private boolean finishedWriting = false;
	public static boolean assistedDragActive = false;
	private boolean front = true;
	private boolean developerMode = false;

	private double timer = 0.0;
	public static long startTime = System.currentTimeMillis();

	private Font englishFont = new Font("Times New Roman", Font.BOLD, 50);
	private Font chineseFont = new Font("", Font.BOLD, 50);


	public static ArrayList <Point> points = new ArrayList <Point> ();

	public static int currentCharacter = -1;
	TextInterpreter text = new TextInterpreter();

	public enum Mode {
		OFF,
		READING,
		WRITING,
		FLASHCARDS
	}

	private enum Properties{
		Character,
		Pinyin,
		StrokeOrder,
		Meaning
	}

	public static Mode LearningMode = Mode.OFF; 

	//Strings and Characters
	private static ArrayList <Character> characterSet = new ArrayList <Character> ();
	private Character randomCharacter;

	public Game() {
		
		//Create a new thread that handles input from the keyboard and mouse
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		RandomizeCharacterSet(100);
		RandomizeCharacter();
		Fonts.AddCustomFonts();

		chineseFont = Fonts.SourceHanSans_Light;
			
	}

	//Run the thread
	public void run(){
	   	try{
	   		while(true){
	   		   Thread.currentThread().sleep(5);
	        	repaint();
	        }
	    } catch(Exception e) {
	   			
	    }
	}
	
	public void paint(Graphics g){
		
		//Graphics setup
		Graphics2D twoDgraph = (Graphics2D) g; 
		if(back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
				
		g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.setFont( new Font("", Font.BOLD, 50));
		g2d.setColor(Color.WHITE);
		((Graphics2D) g2d).setStroke(new BasicStroke(10));

		DisplayAssets.DrawAssets(g2d, getWidth(), getHeight());

		switch(LearningMode){
			case READING:
				ReadingMode();
				break;
			case WRITING:
				WritingMode();
				break;
			case FLASHCARDS:
				FlashcardsMode();
				break;
		}
						
		twoDgraph.drawImage(back, null, 0, 0);
	}       

	public void SetChineseFontSize(int Size){
		g2d.setFont(new Font(chineseFont.getName(),chineseFont.getStyle(),Size));
	}
	public void SetEnglishFontSize(int Size){
		g2d.setFont(new Font(englishFont.getName(),englishFont.getStyle(),Size));
	}

	public void FlashcardsMode(){

		g2d.setColor(Color.BLACK);
		englishFont = Fonts.GrapeNuts_Regular;
		chineseFont = Fonts.YRDZST_Medium;

		ArrayList <Properties> Front = new ArrayList <Properties> ();
		ArrayList <Properties> Back = new ArrayList <Properties> ();

		if(Settings.characterFront){
			Front.add(Properties.Character);
		}
		if(Settings.pinyinFront){
			Front.add(Properties.Pinyin);
		}
		if(Settings.meaningFront){
			Front.add(Properties.Meaning);
		}

		if(Settings.characterBack){
			Back.add(Properties.Character);
		}
		if(Settings.pinyinBack){
			Back.add(Properties.Pinyin);
		}
		if(Settings.meaningBack){
			Back.add(Properties.Meaning);
		}
		
		if(front){
			DrawProperties(Front);
		} else {
			DrawProperties(Back);
		}


	}

	public void DrawProperties(ArrayList<Properties> PropertiesList){
		int yIncrement = 150;
		int y = getHeight()/2 - (PropertiesList.size() - 1)*(yIncrement - g2d.getFontMetrics().getHeight()) + 50;
		
		
		for(Properties p: PropertiesList){
			switch(p){
				case Character:
					SetChineseFontSize(80);
					TextInterpreter.drawCenteredText(g2d, randomCharacter.character, y, getWidth(),40);
					break;
				case Pinyin:
					SetEnglishFontSize(30);
					TextInterpreter.drawCenteredText(g2d, randomCharacter.pinyin, y, getWidth(),40);
					break;
				case Meaning:
					SetEnglishFontSize(40);
					TextInterpreter.drawCenteredText(g2d, randomCharacter.meaning, y, getWidth(),40);
					break;

			}
			y += yIncrement;
		}
	}

	public void RandomizeCharacter(){
		randomCharacter = FileReader.myCharacters.get(
			(int) (Math.random()*FileReader.myCharacters.size())
		);
		//randomCharacter = FileReader.myCharacters.get(index);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(randomCharacter.character + ""), null);
		index ++;
	}

	public void WritingMode(){
		ShowCharacterDetails(randomCharacter);

		if(finishedWriting){
			DrawCharacterStrokeOrder(randomCharacter, 140, 120);
		}

		g2d.drawLine(getWidth()/2,0,getWidth()/2,getHeight());
		DrawCharacterTemplate(randomCharacter, 750, 120);

		int brushSize = 10;

		g2d.setColor(Color.BLACK);
		for(Point p: points){
			g2d.fillOval((int)p.getX(),(int)p.getY(),brushSize,brushSize);
		}
		g2d.setColor(Color.WHITE);
	}

	public void ReadingMode(){
		CheckTimer();

		chineseFont = Fonts.SourceHanSans_Light;
		englishFont = Fonts.OpenSans_Regular;

		if(currentCharacter > characterSet.size()){
			RandomizeCharacterSet(100);
			currentCharacter = -1;
		}

		int x = 90;
		int y = 100;

		if(!developerMode){
			for(int i = 0; i < characterSet.size(); i++){

				Character c = characterSet.get(i);

				DrawCharacter(c, x, y);

				if(i <= currentCharacter){
					RevealCharacterInfo(c, x, y);
				}

				if(x + 170 > getWidth()){
					x = 40;
					y += 110;
				}
				
				x += 50;
			}
		} else {
			for(Character c: FileReader.undefinedCharacters){
				DrawCharacter(c, x, y);
				RevealMissingCharacterInfo(c, x, y);

				if(x + 170 > getWidth()){
					x = 40;
					y += 110;
				}
				
				x += 50;
			}
		}
	}

	public void CheckTimer(){
		timer = (System.currentTimeMillis()-startTime)/1000.0;

		if(timer >= 2*(1-Settings.speed/100.0)){
			startTime = System.currentTimeMillis();
			currentCharacter ++;
		}
	}

	public void DrawCharacterStrokeOrder(Character c, int x, int y){
		g2d.drawImage(c.strokeOrderFile.getImage(),x,y,350,350,this);
	}

	public void DrawCharacterTemplate(Character c, int x, int y){
		g2d.drawImage(new ImageIcon("WritingBackground.png").getImage(),x,y,350,350,this);
	}

	public void DrawCharacter(Character c, int x, int y){
		SetChineseFontSize(50);
		g2d.drawString(c.character,x,y);
	}

	public void ShowCharacterDetails(Character c){
		SetEnglishFontSize(50);

		TextInterpreter.drawCenteredText(g2d, c.getPinyin(), 100, getWidth(), -getWidth()/4);

		SetEnglishFontSize(30);
		g2d.setFont( new Font("Times New Roman", Font.BOLD, 30));

		TextInterpreter.drawCenteredText(g2d, c.meaning, 500, getWidth(), -getWidth()/4);

	}

	public void RevealCharacterInfo(Character c, int x, int y){
		SetEnglishFontSize(19);
		TextInterpreter.drawCenteredText(g2d, c.getPinyin(), y-50, getWidth(), -getWidth()/2+x+25);
		
		if(Settings.revealCharacterMeanings){
			SetEnglishFontSize(12);
			TextInterpreter.drawCenteredWrappedText(g2d, c.meaning, y+15, 6, 10, getWidth(), -getWidth()/2+x+25);
		}
	}

	public void RevealMissingCharacterInfo(Character c, int x, int y){
		SetEnglishFontSize(19);
		TextInterpreter.drawCenteredText(g2d, c.getPinyin(), y-50, getWidth(), -getWidth()/2+x+25);
		
		if(Settings.revealCharacterMeanings){
			SetEnglishFontSize(12);
			TextInterpreter.drawCenteredWrappedText(g2d, c.encoded, y+15, 6, 10, getWidth(), -getWidth()/2+x+25);
		}
	}
	
    public static void RandomizeCharacterSet(int size){
		ArrayList <Character> temp = new ArrayList <Character> ();
        for(int i = 0; i < size; i++){
            temp.add( 
                FileReader.myCharacters.get(
                    (int)(Math.random()* (FileReader.myCharacters.size()))
			));
        }
		characterSet = temp;
    }
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		key= e.getKeyCode();
		char keyChar = e.getKeyChar();

		switch(LearningMode){
			case WRITING:
		
				if(keyChar == ' '){
					//RandomizeCharacter();
					if(finishedWriting){
						RandomizeCharacter();
						points.clear();
						finishedWriting = false;
					} else {
						finishedWriting = true;
					}
				}

				break;
			
			case FLASHCARDS:
				if(keyChar == ' '){
					front = !front;
				}
				else if(key == 39){
					front = true;
					RandomizeCharacter();
				}

	}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		points.add(new Point(e.getX(), e.getY()));
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {

		switch(LearningMode){
			case WRITING:
				if(assistedDragActive && Settings.assistedDragging){
					points.add(new Point(e.getX(), e.getY()));
				}
				break;
		}

		DisplayAssets.CheckButtonsHover(e.getX(), e.getY());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX() + ", " + e.getY());

		switch(LearningMode){
			case WRITING:
				if(Settings.assistedDragging){
					assistedDragActive = !assistedDragActive;
				}
				break;
		}

		DisplayAssets.CheckButtons(e.getX(), e.getY());
		DisplayAssets.StartAppearance();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
