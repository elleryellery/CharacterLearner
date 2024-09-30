import java.awt.Graphics;
import java.util.ArrayList;

public class TextInterpreter {
    
    public TextInterpreter(){}

    public static void drawCenteredText(Graphics g2d, String text, int y, int width, int padding){
        int x = width/2 - g2d.getFontMetrics().stringWidth(text)/2 + padding;
        g2d.drawString(text, x, y);
    }

    public static void drawCenteredWrappedText(Graphics g2d, String inputText, int y, int charLim, int lineHeight, int width, int padding){ //Draws text with word wrapping
        ArrayList <String> lines = new ArrayList <String>();

        int thisLineStart = 0; //The index of the starting character of the line currently being processed
        //Word wrapping
        for(int i = 0; i<inputText.length(); i++){
            if(inputText.charAt(i) == '`' ){
                lines.add(inputText.substring(thisLineStart,i));
                thisLineStart = i + 1;
            } else if( i == inputText.length()-1){
                lines.add(inputText.substring(thisLineStart,i+1));
            } else if(i-thisLineStart>charLim && inputText.charAt(i) != ','){
                int stop = 0;
                for(int j = i; j>=thisLineStart; j--) {
                    if(inputText.charAt(j) == ' '){
                        stop = j;
                        break;
                    }
                }
                if(stop == 0){
                    lines.add(inputText.substring(thisLineStart,i));
                    thisLineStart = i;
                } else {
                    lines.add(inputText.substring(thisLineStart,stop));
                    thisLineStart = stop + 1;
                    i = stop + 1;
                }
            }
        }

        //Draws word-wrapped paragraph on the screen
        int lineWriter = y;
        for(String l: lines){
            drawCenteredText(g2d, l, lineWriter, width, padding);
            lineWriter += lineHeight;
        }
    }

    public void drawText(Graphics g2d, String inputText, int x, int y, int charLim, int lineHeight){ //Draws text with word wrapping
        ArrayList <String> lines = new ArrayList <String>();
        int thisLineStart = 0; //The index of the starting character of the line currently being processed
        //Word wrapping
        for(int i = 0; i<inputText.length(); i++){
            if(inputText.charAt(i) == '`' ){
                lines.add(inputText.substring(thisLineStart,i));
                thisLineStart = i + 1;
            } else if( i == inputText.length()-1){
                lines.add(inputText.substring(thisLineStart,i+1));
            } else if(i-thisLineStart>charLim){
                int stop = 0;
                for(int j = i; j>=thisLineStart; j--) {
                    if(inputText.charAt(j) == ' '){
                        stop = j;
                        break;
                    }
                }
                if(stop == 0){
                    lines.add(inputText.substring(thisLineStart,i));
                    thisLineStart = i;
                } else {
                    lines.add(inputText.substring(thisLineStart,stop));
                    thisLineStart = stop + 1;
                    i = stop + 1;
                }
            }
        }

        //Draws word-wrapped paragraph on the screen
        int lineWriter = y;
        for(String l: lines){
            g2d.drawString(l,x,lineWriter);
            lineWriter += lineHeight;
        }
    }

    public int simulateLines(String inputText, int lineCharLim){
        int lines = 0;
        int thisLineStart = 0; //The index of the starting character of the line currently being processed
        //Word wrapping
        for(int i = 0; i<inputText.length(); i++){
            if(inputText.charAt(i) == '`' ){
                lines++;
                thisLineStart = i + 1;
            } else if( i == inputText.length()-1){
                lines++;
            } else if(i-thisLineStart>lineCharLim){
                int stop = 0;
                for(int j = i; j>=thisLineStart; j--) {
                    if(inputText.charAt(j) == ' '){
                        stop = j;
                        break;
                    }
                }
                if(stop == 0){
                    lines++;
                    thisLineStart = i;
                } else {
                    lines++;
                    thisLineStart = stop + 1;
                    i = stop + 1;
                }
            }
        }

        return lines;
    }
}
