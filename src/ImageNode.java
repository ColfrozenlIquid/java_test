import java.io.*;
import java.util.Arrays;

public class ImageNode extends Node{
    private int width;
    private int height;
    private int bit_depth;
    private String suffix;

    public ImageNode(String nodeType, String path){
        this.node_type = NodeType.valueOf(nodeType);
        this.path = path;

        switch (this.node_type) {
            case GIF:
                set_GIF_Data(new File(this.path));
                break;
            case PNG:
                set_PNG_Data(new File(this.path));
                break;
            case JPG:
                set_JPEG_Data(new File(this.path));
                break;
            default:
                break;
        }
    }

    @Override
    public void get_Data() {
        if (this.node_type != NodeType.UNKNOWN){
            System.out.println(
                    "→ " + this.path + " | "
                    + this.node_type +  " | "
                    + "Size: " + this.size + " | "
                    + "Dimensions: " + this.width + " * " + this.height + " | "
                    + "Compression: " + this.compression
            );
        } else{
            System.out.println(
                    "→ " + this.path + " | "
                            + this.node_type
                            + " {" + this.suffix.toLowerCase() + "}" +  " | "
                            + "Size: " + this.size + " | "
                            + "Dimensions: " + this.width + " * " + this.height + " | "
                            + "Compression: " + this.compression);
        }
    }

    /**
     * Setter for reading data from input file stream as data stream and attempt to read PNG Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/PNG</p>
     * @param imgFile Java File Object that represents the currently examined file
     */
    private void set_PNG_Data(File imgFile){
        try (FileInputStream fis = new FileInputStream(imgFile)) {

            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //Magic number for PNG Files is 89:50:4e:47:0d:0a:1a:0a
            if (dis.readUnsignedByte() != 0x89) return;
            if (dis.readUnsignedByte() != 0x50) return;
            if (dis.readUnsignedByte() != 0x4e) return;
            if (dis.readUnsignedByte() != 0x47) return;
            if (dis.readUnsignedByte() != 0x0d) return;
            if (dis.readUnsignedByte() != 0x0a) return;
            if (dis.readUnsignedByte() != 0x1a) return;
            if (dis.readUnsignedByte() != 0x0a) return;

            dis.skip(8);

            //Get PNG width bytes and convert them to a readable int
            int width = 0;
            for (int i = 0; i < 4; i++){
                byte b = (byte)dis.readUnsignedByte();
                width = (width << 8) + (b & 0xFF);
            }

            //Get PNG height bytes and convert them to a readable int
            int height = 0;
            for (int i = 0; i < 4; i++){
                byte b = (byte)dis.readUnsignedByte();
                height = (height << 8) + (b & 0xFF);
            }

            //Get the bit depth of the PNG to be used in compression calculation
            //int bit_depth = dis.readUnsignedByte();

            if (dis != null) dis.close ();
            if (bis != null) bis.close ();
            if (fis != null) fis.close ();

            this.bit_depth = 3;
            this.height = height;
            this.width = width;
            this.setImageCompression();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Setter for reading data from input file stream as data stream and attempt to read GIF Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/GIF</p>
     * @param imgFile Java File Object that represents the currently examined file
     */
    private void set_GIF_Data(File imgFile){
        try (FileInputStream fis = new FileInputStream(imgFile)) {
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //Magic number for GIF Files is 47:49:46:38:39:61
            if (dis.readUnsignedByte() != 0x47) return;
            if (dis.readUnsignedByte() != 0x49) return;
            if (dis.readUnsignedByte() != 0x46) return;
            if (dis.readUnsignedByte() != 0x38) return;
            if (dis.readUnsignedByte() != 0x39) return;
            if (dis.readUnsignedByte() != 0x61) return;

            int width = 0;
            byte b = (byte)dis.readUnsignedByte();
            byte c = (byte)dis.readUnsignedByte();
            width = (width << 8) + (c & 0xFF);
            width = (b & 0xFF);

            int height = 0;
            byte d = (byte)dis.readUnsignedByte();
            byte e = (byte)dis.readUnsignedByte();
            height = (height << 8) + (e & 0xFF);
            height = (d & 0xFF);

            if (dis != null) dis.close ();
            if (bis != null) bis.close ();
            if (fis != null) fis.close ();

            this.bit_depth = 1;
            this.height = height;
            this.width = width;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for reading data from input file stream as data stream and attempt to read JPEG Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/JPEG, https://en.wikipedia.org/wiki/JPEG_File_Interchange_Format,
     * https://wiki.tcl-lang.org/page/Reading+JPEG+image+dimensions</p>
     * @param imgFile Java File Object that represents the currently examined file
     */
    private void set_JPEG_Data(File imgFile){
        try (FileInputStream fis = new FileInputStream(imgFile)) {
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //SOI for JPEG Files is ff:d8:ff
            if (dis.readUnsignedByte() != 0xff) return;
            if (dis.readUnsignedByte() != 0xd8) return;

            boolean found = false;

            while(!found){
                while (dis.readUnsignedByte() != 0xff) {}
                int sof = dis.readUnsignedByte();

                Integer[] SOF_markers = {0xc0,0xc1,0xc2,0xc3,0xc4,0xc5,0xc6,0xc7,0xc8,0xc9,0xca,0xcb,0xcd,0xce,0xcf};
                if (Arrays.asList(SOF_markers).contains(sof)){
                    found = true;
                    dis.skip(3);

                    int width = 0;
                    for (int i = 0; i < 2; i++){
                        byte b = (byte)dis.readUnsignedByte();
                        width = (width << 8) + (b & 0xFF);
                    }

                    int height = 0;
                    for (int i = 0; i < 2; i++){
                        byte b = (byte)dis.readUnsignedByte();
                        height = (height << 8) + (b & 0xFF);
                    }

                    this.bit_depth = 3;
                    this.height = height;
                    this.width = width;

                    if (dis != null) dis.close ();
                    if (bis != null) bis.close ();
                    if (fis != null) fis.close ();

                    return;

                } else {
                    int s1 = dis.readUnsignedByte();
                    int s2 = dis.readUnsignedByte();
                    int offset = 0;
                    offset = (offset << 8) + (s1 & 0xFF);
                    offset = (offset << 8) + (s2 & 0xFF);
                    dis.skip(offset-2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Setter calculating image compression based on Image dimensions and the bit depth
     * @param child_node The current node of the file data tree object
     */
    private void setImageCompression() {
        this.compression = (int)((this.size * 100) / (this.height * this.width * this.bit_depth));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBit_depth() {
        return bit_depth;
    }

    public void setBit_depth(int bit_depth) {
        this.bit_depth = bit_depth;
    }

    public String getSuffix() {
        return this.suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
