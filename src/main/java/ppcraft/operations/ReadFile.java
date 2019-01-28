package ppcraft.operations;

import java.io.*;

import static ppcraft.main.Main.resurs;
import static ppcraft.main.Main.path;

public class ReadFile {
    public static void read(){
        try(FileInputStream fis = new FileInputStream(path)) {
            byte[] content = new byte[fis.available()];
            fis.read(content);
            resurs = new String(content).split("\n");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException :" + e);
        } catch (IOException e) {
            System.out.println("IOException :" + e);
        }
    }
}
