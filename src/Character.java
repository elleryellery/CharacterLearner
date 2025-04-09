import java.io.File;

import javax.swing.ImageIcon;

public class Character {
    String encoded;
    String character;
    String pinyin;
    ImageIcon strokeOrderFile;
    boolean isRevealed = false;
    String meaning = "";

    public Character(String enc, String pinyn){
        encoded = enc;
        pinyin = pinyn;
    }

    public String toString(){
        return character + "/" + encoded + "/" + pinyin;
    }

    public String getCharacter(){
        return character;
    }

    public void addString(String input){
        character = input;
        strokeOrderFile = new ImageIcon("stroke_order_gifs\\" + character.codePointAt(0) + ".gif");
    }

    public void hasGif(){
        if(!(new File("stroke_order_gifs\\" + character.codePointAt(0) + ".gif").isFile())){
            System.out.println(encoded);
        }
    }

    public String encoded(){
        return encoded;
    }

    public void addMeaning(String _meaning){
        meaning = _meaning;
    }

    public String getPinyin(){
        return pinyin;
    }
}
