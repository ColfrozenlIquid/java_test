import java.util.Locale;

public class FileNode extends Node{

    private String suffix;

    public FileNode(String path){
        this.node_type = NodeType.UNKNOWN;
        this.path = path;
    }

    public void get_Data(){
        if (this.node_type != NodeType.UNKNOWN){
            System.out.println(
                    "→ " + this.path + " | "
                    + this.node_type + " | "
                    + "Size: " + this.size
            );
        } else{
            System.out.println(
                    "→ " + this.path + " | "
                    + this.node_type + " "
                    + "{" + this.suffix.toLowerCase() + "}" + " | "
                    + "Size: " + this.size
            );
        }

    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
