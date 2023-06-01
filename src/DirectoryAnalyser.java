import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class DirectoryAnalyser {

    public DirectoryAnalyser() {
    }

    /**
     * Recursively output the Information contained within all nodes
     */
    private void outputTree(Node node, int depth, long directorySize){
        for(Node n : node.children) {
            for (int i = 0; i <= depth; i++){
                System.out.print('\t');
            }
            //setGraphSize(n, directorySize);
            n.get_Data();
            if (n.children.size() > 0){
                outputTree(n, depth+1, n.size);
            }
        }
    }

    private void setGraphSize(Node node, long directorySize){
        long percentage = (node.size * 100 / directorySize);
        int blocks = (int) (percentage / 10);

        for (int i = 0; i < 10; i++){
            if (i <= blocks){
                System.out.print("==");
            } else{
                System.out.print("--");
            }
        }
    }

    /**
     * Analyse the currently selected relative path for folders, sub folders and files
     */
    public void analyse(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter relative path: ");
        String path = sc.nextLine();
        System.out.println("You entered: " + path);
        File pathFile = new File(path);
        DirectoryNode rootDirectory = new DirectoryNode();
        rootDirectory.path = pathFile.getAbsolutePath();
        rootDirectory.node_type = NodeType.DIRECTORY;
        recurse_Directory(pathFile, rootDirectory);
        //setGraphSize(rootDirectory, rootDirectory.size);
        rootDirectory.get_Data();
        outputTree(rootDirectory, 0, rootDirectory.size);
    }

    /**
     * Recursively scan the main directory and add every file and folder to a Tree Model
     * Check each File or Folder for file type and add a suitable identifier
     * @param currFile The current "parent" Java File object of the file data tree object
     * @param curr_Node The current "parent" node of the file data tree object
     */
    private void recurse_Directory(File currFile, DirectoryNode curr_Node){
        File[] files = currFile.listFiles();
        int folder_size = 0;
        int folder_compression = 0;
        if (files != null){for (File f : files) {
                if (f.isDirectory()){
                    DirectoryNode child_node = new DirectoryNode();
                    child_node.path = f.getAbsolutePath();
                    child_node.size = f.length();
                    curr_Node.addChild(child_node);
                    recurse_Directory(f, child_node);
                    folder_size += child_node.size;
                    folder_compression += child_node.compression;
                }
                else {
                    String suffix = Node.get_File_Suffix(f).toUpperCase();
                    if (!getEnumValues().contains(suffix.toUpperCase())){
                        FileNode child_node = new FileNode(f.getAbsolutePath());
                        child_node.size = f.length();
                        child_node.node_type = NodeType.UNKNOWN;
                        child_node.setSuffix(suffix);
                        curr_Node.addChild(child_node);
                        folder_size += child_node.size;
                        folder_compression += child_node.compression;
                        continue;
                    }
                    NodeType currType = NodeType.valueOf(suffix);
                    if (Arrays.asList(NodeType.values()).contains(currType)){
                        if (NodeType.getImageTypes().contains(suffix)){
                            ImageNode child_node = new ImageNode(suffix, f.getAbsolutePath());
                            child_node.size = f.length();
                            child_node.setSuffix(suffix);
                            curr_Node.addChild(child_node);
                            folder_size += child_node.size;
                            folder_compression += child_node.compression;
                        }
                        else {
                            FileNode child_node = new FileNode(f.getAbsolutePath());
                            child_node.size = f.length();
                            child_node.setSuffix(suffix);
                            curr_Node.addChild(child_node);
                            folder_size += child_node.size;
                            folder_compression += child_node.compression;
                        }
                    }
                }
            }
        }
        curr_Node.size = folder_size;
        curr_Node.compression = folder_compression;
    }
    /**
     * Creates a Hashset that links String name key and Enum NodeType values
     */
    private HashSet<String> getEnumValues(){
        HashSet<String> values = new HashSet<String>();
        for(NodeType c : NodeType.values()){
            values.add(c.name());
        }
        return values;
    }
}
