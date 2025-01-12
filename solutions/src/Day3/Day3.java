package Day3;

import Utils.FileParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day3 {

    private static final String MUL_STRING = "mul(";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private static long part1(List<String> lines) {
        long total = 0;
        int lastMulIndex;
        // Determines how many times the mul string was found. Should be equivalent to the string search.
        int hits = 0;
        for (String line : lines) {
            lastMulIndex = 0;
            while (lastMulIndex != -1) {
                int mulIndex = line.indexOf(MUL_STRING, lastMulIndex);
                if (mulIndex == -1) {
                    break;
                }
                hits++;
                int closingParenthesisIndex = line.indexOf(")", mulIndex);
                if (closingParenthesisIndex == -1) {
                    break;
                }
                String parameters = line.substring(mulIndex + MUL_STRING.length(), closingParenthesisIndex);
                List<String> tokens = Arrays.stream(parameters.split(",")).toList();
                if (tokens.size() != 2) {
                    printError(String.format("%d - BAD number of tokens in mul %d tokens. Opening token = %s", hits, tokens.size(), tokens));
                } else {
                    try {
                        String token1 = tokens.get(0);
                        String token2 = tokens.get(1);
                        long x = Integer.parseInt(token1);
                        long y = Integer.parseInt(token2);
                        total += (x * y);
                        System.out.printf("%d - GOOD mul command found: %s%n", hits, line.substring(mulIndex, closingParenthesisIndex + 1));
                    } catch (NumberFormatException e) {
                        printError(String.format("%d - BAD mul command found: %s", hits, line.substring(mulIndex, closingParenthesisIndex + 1)));
                    }
                }
                lastMulIndex = mulIndex +1;
            }
        }
        System.out.println("hits: " + hits);
        return total;
    }

    private static void printError(String error) {
        System.out.println(ANSI_RED + "BAD mul command found: " + error + ANSI_RESET);
    }

    public static void main(String[] args) throws IOException {
        String filename = Objects.requireNonNull(Day3.class.getResource(FileParser.INPUT)).getPath();
        FileParser parser = new FileParser(filename);
        List<String> lines = parser.readFile();
        System.out.println("The answer to part 1 is: " + part1(lines)); // 31103311 is too low, 32601251 is too low
        System.out.println("File length: " + lines.stream().mapToInt(String::length).sum());
    }
}
