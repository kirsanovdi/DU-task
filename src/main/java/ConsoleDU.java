import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleDU {
    @Option(name = "-h")
    private String type = "default";

    @Option(name = "-c")
    private boolean isSum;

    @Option(name = "--si")
    private boolean is1024;

    @Argument
    private List<String> files = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        new ConsoleDU().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e){
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        double marker = is1024?1024.0:1000.0;
        double sum = 0.0;
        double sectorSize = switch (type){
            case "B" -> 1.0;
            case "KB" -> marker;
            case "MB" -> marker * marker;
            case "GB" -> marker * marker * marker;
            default -> throw new IOException();
        };

        String strType = (type.equals("default")?"":type);

        for(String fileName: files){
            try {
                File file = new File(fileName);
                if(!file.exists()) throw new IOException();
                double size = file.length() / sectorSize;
                if(!isSum) System.out.println(size + strType); else sum+=size;
            } catch (NullPointerException e){ throw new IOException(); }
        }
        if(isSum) System.out.println(sum + strType);
    }
}
