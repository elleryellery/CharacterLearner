import javax.swing.ImageIcon;
import java.awt.*;

public class Screen {
    private ImageIcon background;

    public Screen(String BackgroundImagePath){
        background = new ImageIcon(BackgroundImagePath);
    }

    public void drawScreen(Graphics g2d, int width, int height){
        g2d.drawImage(background.getImage(),0,0,width,height, null);
    }
}
