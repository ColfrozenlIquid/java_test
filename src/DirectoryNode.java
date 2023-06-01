public class DirectoryNode extends Node {

    public DirectoryNode(){
        this.node_type = NodeType.DIRECTORY;
    }

    public void get_Data(){
        System.out.println(
                "→ " + this.path + " | "
                + this.node_type + " | "
                + "Size: " + this.size + " | "
                + "Average Compression: " + this.compression
        );
    }
}
