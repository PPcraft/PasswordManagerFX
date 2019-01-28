package ppcraft.operations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static ppcraft.main.Main.path;

public class WriteFile {
    public static void write(String data){
        try(FileOutputStream fos = new FileOutputStream(path)) {
            byte[] buffer = data.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println("IOException :" + e);
        }
    }

    public static void writeAdd(String data){
        try {
            Files.write(Paths.get(path), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("IOException :" + e);
        }
    }
}
