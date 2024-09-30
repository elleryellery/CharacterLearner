import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.font.*;

public abstract class Fonts {
    public static Font GrapeNuts_Regular, YRDZST_Medium, SourceHanSans_Light, OpenSans_Regular;
    public static void AddCustomFonts(){

        try{
            GrapeNuts_Regular = Font.createFont(Font.TRUETYPE_FONT, new File("Assets\\Fonts\\GrapeNuts-Regular.ttf")).deriveFont(12f);
            YRDZST_Medium = Font.createFont(Font.TRUETYPE_FONT, new File("Assets\\Fonts\\YRDZST Medium.ttf")).deriveFont(12f);
            SourceHanSans_Light = Font.createFont(Font.TRUETYPE_FONT, new File("Assets\\Fonts\\NotoSansSC-Regular.ttf")).deriveFont(12f);
            Font OpenSans = Font.createFont(Font.TRUETYPE_FONT, new File("Assets\\Fonts\\NotoSans-VariableFont_wdth,wght.ttf")).deriveFont(12f);
            OpenSans_Regular = OpenSans.deriveFont(Map.of(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRABOLD));


            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(GrapeNuts_Regular);
            ge.registerFont(YRDZST_Medium);
            ge.registerFont(SourceHanSans_Light);
            ge.registerFont(OpenSans_Regular);


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
}
