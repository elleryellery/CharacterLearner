import javax.sound.sampled.*;

public class Sound {

	private String fileName;
    private Clip clip;
    private char type;
    private boolean repeat;
    private float volume;

    public Sound() {
        fileName = "No file";
        type = 'N';
        repeat = false;
    }

	public Sound (String inputFileName, char inputType, boolean inputRepeat) {
        fileName = inputFileName;
        type = inputType;
        repeat = inputRepeat;
        volume = 100.0f;
	}
    
    public Sound (String inputFileName, char inputType, boolean inputRepeat, float inputVolume) {
        fileName = inputFileName;
        type = inputType;
        repeat = inputRepeat;
        volume = inputVolume;
	}

    public String fileName(){
        return fileName;
    }

    public Clip clip() {
        return clip;
    }

    public char type(){
        return type;
    }

    public boolean repeat(){
        return repeat;
    }

    public float volume(){
        return volume;
    }
    
    public void setClip(Clip inputClip){
        clip = inputClip;
    }
}