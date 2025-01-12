package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * Class to aid in reading files
 */
public class FileParser {

    public static final String INPUT = "input.txt";
    public static final String TEST = "test.txt";
    private static final Logger LOG = Logger.getLogger(FileParser.class.getName());
    private static final int REASONABLE_MAX_SIZE = 10000;
    private final String filePath;

    public FileParser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return  Contents of file as list
     */
    public List<String> readFile() throws IOException{
        int i = 0;
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                i++;
                if (i > REASONABLE_MAX_SIZE) {
                    LOG.warning(String.format("File size is greater than %d. Consider reading file line by line",
                            REASONABLE_MAX_SIZE));
                    break;
                }
            }
        }
        return lines;
    }

    public List<List<String>> readColumnFile(int numColumns, String delimiter) throws IOException{
        List<List<String>> columnData = new ArrayList<>();
        List<String> lines = readFile();
        for (String line: lines) {
            StringTokenizer st = (delimiter.isEmpty()) ? new StringTokenizer(line) :  new StringTokenizer(line, delimiter);
            assert st.countTokens() == numColumns;
            for (int i = 0; i < numColumns; i++) {
                if (columnData.size() < i + 1) {
                    columnData.add(new ArrayList<>());
                }
                columnData.get(i).add(st.nextToken());
            }
        }
        return columnData;
    }

    public List<List<String>> readRowFile(String delimiter) throws IOException{
        List<List<String>> columnData = new ArrayList<>();
        List<String> lines = readFile();
        for (String line: lines) {
            columnData.add(new ArrayList<>());
            StringTokenizer st = (delimiter.isEmpty()) ? new StringTokenizer(line) :  new StringTokenizer(line, delimiter);
            while (st.hasMoreTokens()) {
                columnData.getLast().add(st.nextToken());
            }
        }
        return columnData;
    }

    public void displayFileContents() throws IOException{
        List<String> lines = readFile();
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
