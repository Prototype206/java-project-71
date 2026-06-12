package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0.0",
        description = "Compares two configuration files (JSON or YAML) and shows a difference.")
public class App implements Runnable{

    @Parameters(index="0", description = "path to first file")
    String filepath1;

    @Parameters(index="1", description = "path to second file")
    String filepath2;

    @Option(names={"-f","--format"}, description="output format [default: stylish]")
    String format = "stylish";

    @Override
    public void run() {
        try {
            String result = Differ.generate(filepath1, filepath2);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
