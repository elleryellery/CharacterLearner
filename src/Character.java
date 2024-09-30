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
        strokeOrderFile = new ImageIcon("StrokeOrder\\" + character + ".gif");
    }

    public String encoded(){
        return encoded;
    }

    public void addMeaning(String _meaning){
        meaning = _meaning;
    }

    public String getPinyin(){
        if (pinyin.contains(",")){
            return pinyin.substring(0, pinyin.indexOf(","));
        } else {
            return pinyin;
        }
    }
}
