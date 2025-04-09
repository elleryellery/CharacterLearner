import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.awt.*;

public abstract class DisplayAssets {

    private static long animStart;

    private static enum State {
        INIT,
        LOADING,
        TRANSITION
    }
    private static State s = State.INIT;

    public static enum Screens {
        ALL(null),
        INIT(new Screen("Transition.gif")),

        LOADING 
            (new Screen("LoadingScreen.gif")),
        TRANSITION
            (new Screen("Transition.gif")),
        HOME 
            (new Screen("Home.png")),
        READING 
            (new Screen("ReadingMode.png")),
        WRITING 
            (new Screen("WritingMode.png")),
        FLASHCARDS 
            (new Screen("FlashcardMode.png")),
        SETTINGS 
            (new Screen("Settings.png")),
        ;

        public final Screen screen;

        private Screens(Screen Screen) {
            this.screen = Screen;
        }
    }

    private static Button[] Buttons = {
        new Button("Reading", Screens.HOME, 340,380,150,150, () -> {
            Game.LearningMode = Game.Mode.READING;
            Game.RandomizeCharacterSet(100);
            Game.currentCharacter = -1;
            Game.startTime = System.currentTimeMillis();
            currentScreen = Screens.READING;
        }),
        new Button("Writing", Screens.HOME, 550,380,150,150, () -> {
            Game.LearningMode = Game.Mode.WRITING;
            currentScreen = Screens.WRITING;
        }),
        new Button("Flashcards", Screens.HOME, 760,380,150,150, () -> {
            Game.LearningMode = Game.Mode.FLASHCARDS;
            currentScreen = Screens.FLASHCARDS;
        }),
        new Button("HomeButton", Screens.ALL, 10,10,50,50, () -> {
            Game.LearningMode = Game.Mode.OFF;
            currentScreen = Screens.HOME;
        }),
        new Button("Clear", Screens.WRITING, 610,5,150,50, () -> {
            Game.points.clear();
            if(Settings.assistedDragging){
                Game.assistedDragActive = false;
            }
        }),

        new Button("SettingsButton", Screens.ALL, 1130,10,50,50, () -> {
            currentScreen = Screens.SETTINGS;
            Game.LearningMode = Game.Mode.OFF;
        }),

        new Button("Checkbox", Screens.SETTINGS, 355,148,25,25, () -> {
            Settings.soundEffects = !Settings.soundEffects;
        }, () -> 
            Settings.soundEffects
        ),
        new Button("Checkbox", Screens.SETTINGS, 355,204,25,25, () -> {
            Settings.music = !Settings.music;
        }, () -> 
            Settings.music
        ),
        new Button("Checkbox", Screens.SETTINGS, 1069,252,25,25, () -> {
            Settings.revealCharacterMeanings = !Settings.revealCharacterMeanings;
        }, () -> 
            Settings.revealCharacterMeanings
        ),
        new Button("Checkbox", Screens.SETTINGS, 380,403,25,25, () -> {
            Settings.assistedDragging = !Settings.assistedDragging;
        }, () -> 
            Settings.assistedDragging
        ),

        new Button("Checkbox", Screens.SETTINGS, 891,438,25,25, () -> {
            Settings.characterFront = !Settings.characterFront;
            if(Settings.characterBack){
                Settings.characterBack = false;
            }
        }, () -> 
            Settings.characterFront
        ),
        new Button("Checkbox", Screens.SETTINGS, 891,488,25,25, () -> {
            Settings.pinyinFront = !Settings.pinyinFront;
            if(Settings.pinyinBack){
                Settings.pinyinBack = false;
            }
        }, () -> 
            Settings.pinyinFront
        ),
        new Button("Checkbox", Screens.SETTINGS, 891,538,25,25, () -> {
            Settings.meaningFront = !Settings.meaningFront;
            if(Settings.meaningBack){
                Settings.meaningBack = false;
            }
        }, () -> 
            Settings.meaningFront
        ),
        new Button("Checkbox", Screens.SETTINGS, 1116,438,25,25, () -> {
            Settings.characterBack = !Settings.characterBack;
            if(Settings.characterFront){
                Settings.characterFront = false;
            }
        }, () -> 
            Settings.characterBack
        ),
        new Button("Checkbox", Screens.SETTINGS, 1116,488,25,25, () -> {
            Settings.pinyinBack = !Settings.pinyinBack;
            if(Settings.pinyinFront){
                Settings.pinyinFront = false;
            }
        }, () -> 
            Settings.pinyinBack
        ),
        new Button("Checkbox", Screens.SETTINGS, 1116,538,25,25, () -> {
            Settings.meaningBack = !Settings.meaningBack;
            if(Settings.meaningFront){
                Settings.meaningFront = false;
            }
        }, () -> 
            Settings.meaningBack
        ),
        

        new Button("Plus", Screens.SETTINGS, 995,185,25,25, () -> {
            Settings.speed += 5;
        }),
        new Button("Minus", Screens.SETTINGS, 885,185,25,25, () -> {
            Settings.speed -= 5;
        }),
    };

    private static Screens currentScreen = Screens.INIT;


    public static void DrawAssets(Graphics g2d, int Width, int Height){

        if(currentScreen.screen != null){
            currentScreen.screen.drawScreen(g2d, Width, Height);
        }

        if(currentScreen == Screens.INIT || currentScreen == Screens.LOADING || currentScreen == Screens.TRANSITION){
            HandleTransition();
        } else if (currentScreen == Screens.SETTINGS){
            TextInterpreter.drawCenteredText(g2d, Settings.speed + "", 215, Width, 355);
        }
        

        for(Button b: Buttons){
            if(b.screen.equals(currentScreen) || b.screen == Screens.ALL){
                b.drawButton(g2d);
            }
        }

    }

    public static void StartAppearance(){
        for(Button b: Buttons){
            b.startAppearance();
        }
    }

    public static void HandleTransition(){

        switch(s){
            case INIT:
                animStart = System.currentTimeMillis();
                s = State.LOADING;
                break;
            case LOADING:
                currentScreen = Screens.LOADING;
                if((System.currentTimeMillis()-animStart)/1000.0 >= 5.3){
                    s = State.TRANSITION;
                    animStart = System.currentTimeMillis();
                }
                break;
            case TRANSITION:
                currentScreen = Screens.TRANSITION;
                if((System.currentTimeMillis()-animStart)/1000.0 >= 2.2){
                    currentScreen = Screens.HOME;
                }
                break;
        }
    }

    public static void CheckButtonsHover(int x, int y){
        for(Button b: Buttons){
            if(b.screen == currentScreen || b.screen == Screens.ALL)
                b.checkHover(x,y);
        }
    }

    public static void CheckButtons(int x, int y){
        for(Button b: Buttons){
            if(b.screen == currentScreen || b.screen == Screens.ALL)
                b.check(x,y);
        }
    }
    
    
}
