import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class PinyinInterpreter {

    public static ArrayList<Character> characterDictionary = new ArrayList <Character> ();
    
    public static ArrayList<Character> CreateDictionary(){

        ArrayList <Character> dictionary = new ArrayList <Character> ();

        try{
            File file = new File("pinyin_dict.txt");
    
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));

            String line;

            while((line = in.readLine()) != null){
                String[] properties = line.split("_");
                Character add = new Character("0x" + properties[0], properties[1]);
                add.addMeaning(properties[2]);
                dictionary.add(add);
                // String hexadecimal = "";
                // String pinyin = "";
                // String meaning = "";
                // String line = sc.nextLine();
                // for(int i = 0; i < line.length(); i++){
                //     hexadecimal = line.substring(0,line.indexOf(':'));

                //     if(line.contains("/")){
                //         pinyin = line.substring(line.indexOf(':') + 3,line.indexOf("/") - 2);
                //         meaning = line.substring(line.indexOf("/") +1);

                //     } else if (line.contains("//")){

                //     } else {
                //         pinyin = line.substring(line.indexOf(':') + 3,line.length()-2);
                //     }
                // }
                // Character add = new Character(hexadecimal, pinyin);
                // if(meaning.length()>0){
                //     add.addMeaning(meaning);
                // }
                // dictionary.add(add);
            }
            
            in.close();
            characterDictionary = dictionary;
        } catch(Exception e){
            e.printStackTrace();
        }

        return dictionary;
    }

    public static ArrayList<Character> Dictionary(){
        return characterDictionary;
    }

    public static Character LookUp(String character){
        try{
            String token = encodeAsUcs2(character);
            for(Character c: characterDictionary){
                if(c.encoded().equals(token)){
                    return c;
                }
            }
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeAsUcs2(String messageContent) throws UnsupportedEncodingException {
		byte[] bytes = messageContent.getBytes("UTF-16BE");
	
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
		sb.append(String.format("%02X", b));
		}
	
		return "0x" + sb.toString();
	}
 }
