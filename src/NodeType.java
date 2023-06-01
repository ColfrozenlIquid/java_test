import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NodeType {
    UNKNOWN,
    DIRECTORY,
    JPG,
    PNG,
    GIF,
    TXT;

    public static List<String> getImageTypes(){
        List<String> imageFormats = new ArrayList<String>();
        imageFormats.add(JPG.toString());
        imageFormats.add(PNG.toString());
        imageFormats.add(GIF.toString());
        return imageFormats;
    }
}
