
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class MainTest {
    @Test
    void autoTest() throws IOException {
        int byteCount = new Random().nextInt(10000);
        PrintStream ps = new PrintStream("auxFile.txt");
        byte data[] = new byte[byteCount];
        Path file = Paths.get("testFile");
        Files.write(file, data);

        System.setOut(ps);
        ConsoleDU.main("-h B --si -c testFile".trim().split("\s+"));

        Assertions.assertEquals((double) byteCount + "B", new BufferedReader(new FileReader("auxFile.txt")).readLine());
    }
}
