import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Button {
    
    private String name;
    public DisplayAssets.Screens screen;
    private int x,y,w,h;
    private ImageIcon icon;
    private ImageIcon iconRegular;
    private ImageIcon iconHover;
    private Runnable action;
    private Condition condition;
    private ImageIcon conditionalAppearance;
    private ImageIcon conditionalAppearanceHover;
    private SoundPlayer sfx = new SoundPlayer();
    
    private ArrayList<Screen> screens = new ArrayList<Screen> ();

    public Button(){
        name = "Button.java: Empty Constructor";
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        icon = new ImageIcon("No Image");
    }

    public Button(String inputName, DisplayAssets.Screens inputScreen, int inputX, int inputY, int inputW, int inputH, Runnable inputAction){
        name = inputName;
        screen = inputScreen;
        iconRegular = new ImageIcon("Assets\\Buttons\\" + name + ".png");
        iconHover = new ImageIcon("Assets\\Buttons\\"  + name + "Hover.png");
        icon = iconRegular;
        x = inputX;
        y = inputY;
        w = inputW;
        h = inputH;
        action = inputAction;
    }

    public Button(String inputName, DisplayAssets.Screens inputScreen, int inputX, int inputY, int inputW, int inputH, Runnable inputAction, Condition inputCondition){
        name = inputName;
        screen = inputScreen;
        iconRegular = new ImageIcon("Assets\\Buttons\\" + name + ".png");
        iconHover = new ImageIcon("Assets\\Buttons\\"  + name + "Hover.png");
        icon = iconRegular;
        x = inputX;
        y = inputY;
        w = inputW;
        h = inputH;
        action = inputAction;
        condition = inputCondition;
        conditionalAppearance = new ImageIcon("Assets\\Buttons\\" + name + "Conditional.png");
        conditionalAppearanceHover = new ImageIcon("Assets\\Buttons\\"  + name + "ConditionalHover.png");
    }

    public interface Condition {
        boolean evaluate();
    }

    public void executeButtonAction(){
        action.run();
    }

    public void drawButton (Graphics g2d){
        g2d.drawImage(icon.getImage(),x,y,w,h,null);
    }

    public void check(int mouseX, int mouseY){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        //If the button has been pressed, executes the button's action, updates its appearance, and plays a sound effect
        if(mouse.intersects(me)){
            executeButtonAction();
            //if(!(this instanceof TextInput)){
                //sfx.playmusic(new Sound("SFX-SoundEffects/SOUNDEFFECT-ButtonPressed.wav", 'S', false,70.0f));
            //}
                icon = iconRegular;
            startAppearance();
        }
    }

    public void startAppearance(){
        //Checks the condition of conditional buttons and updates its appearance accordingly
        if(condition != null && condition.evaluate()){
                icon = conditionalAppearance;
        } else {
                icon = iconRegular;
        }
    }

    public void setRunnable(Runnable inputAction){
        action = inputAction;
    }
    public void setH(int inputH){
        h = inputH;
    }
    public void setW(int inputW){
        w = inputW;
    }
    public void addY(int inputY){
        y+=inputY;
    }
    public void checkHover(int mouseX, int mouseY){ //Changes the button's appearance if the user is hovering over the button
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        if(condition != null && condition.evaluate()){
            if(mouse.intersects(me)){
                icon = conditionalAppearanceHover;
            } else {
                icon = conditionalAppearance;
            }
        } else {
            if(mouse.intersects(me)){
                icon = iconHover;
            } else {
                icon = iconRegular;
            }
        }
    }

    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public int w(){
        return w;
    }
    public int h(){
        return h;
    }

    public void setAction(Runnable inputAction){
        action = inputAction;
    }
}
