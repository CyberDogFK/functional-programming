package chapter3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface Saveable {
    public Object readFile(String fileName);

    public default void saveFile(String fileName, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(fileName));
        fileWriter.write(content);
        fileWriter.close();
    }

    enum FileType { executable, readable, writeable, readWrite };

    public static FileType standardFileType() {
        return FileType.readWrite;
    }

    public static void main(String[] args) {
        var ft = Saveable.standardFileType();
        System.out.println(ft);
    }
}

