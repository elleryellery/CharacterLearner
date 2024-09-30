import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

public abstract class FileReader {
    public static String uploadedCharacters = "";
    public static ArrayList <Character> myCharacters = new ArrayList <Character> ();
    public static ArrayList <Character> undefinedCharacters = new ArrayList <Character> ();

    public static void UploadCharacters(){
        try{
            File uploadsDirectory = new File("Uploads");

            for(File upload: uploadsDirectory.listFiles()){

                BufferedReader UTF_8_Reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(upload), "UTF-8"));

                Scanner sc = new Scanner(UTF_8_Reader);

                while(sc.hasNextLine()){
                    String fileContents = sc.nextLine();

                    for(int i = 0; i < fileContents.length(); i++){
                        String character = fileContents.substring(i,i+1);
                        if(!uploadedCharacters.contains(character)){
                            uploadedCharacters += character;
                        }
                    }
                }

                sc.close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void AddCharacters(){
        for(int i = 1; i < uploadedCharacters.length(); i++){
            String characterString = uploadedCharacters.substring(i, i+1);
            Character character = PinyinInterpreter.LookUp(characterString);

            if(character != null){
                character.addString(characterString);
                myCharacters.add(character);

            } else {
                try {
                    System.err.println("Unable to locate character \"" + PinyinInterpreter.encodeAsUcs2(characterString) + "\" in dictionary at position " + i + ".");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Character LookUp(String character){
        for(Character c: myCharacters){
            if(c.character.equals(character)){
                return c;
            }
        }
        return null;
    }
}
