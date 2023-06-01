import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Node implements INode{
    String path;
    NodeType node_type;
    long size;
    int compression = 0;
    Node parent;
    List<Node> children = new ArrayList<Node>();

    public void addChild(Node child) {
        child.parent = this;
        this.children.add(child);
    }

    public void setNodeType(NodeType nodeType){
        this.node_type = nodeType;
    }

    /**
     * Getter for returning the file extension suffix from input File objects
     * @param file Java File Object that represents the currently examined file
     * @return String The files suffix as a String
     */
    public static String get_File_Suffix(File file) {
        int pos = file.getName().lastIndexOf(".");       //Get the last name of the path, then get the index of the last occurence of ".", this is to find the file extension type
        if (pos == -1)
            return "Unknown file extension";
        String suffix = file.getName().substring(pos+1);     //get the image name extension
        return suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public NodeType getNode_type() {
        return node_type;
    }

    public void setNode_type(NodeType node_type) {
        this.node_type = node_type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getCompression() {
        return compression;
    }

    public void setCompression(int compression) {
        this.compression = compression;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}