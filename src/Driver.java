public class Driver {
    public static void main(String[] args) throws Exception {
        PinyinInterpreter.CreateDictionary();
        FileReader.UploadCharacters();
        FileReader.AddCharacters();
    }
}
