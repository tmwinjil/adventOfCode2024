package Day2;

import Utils.FileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day2 {

    public static int safeLevelFinder(List<List<Integer>> rows, int tolerance) {
        int total = 0;
        for (List<Integer> originalRow: rows) {
            List<Integer> newRow = new ArrayList<>(originalRow);
            if (newRow.getFirst() > newRow.getLast()) {
                Collections.reverse(newRow);
            }
            int issuesFound = 0;
            for (int i = 0; i < newRow.size() - 1 && issuesFound <= tolerance; i++) {
                if (!areLevelsSafe(newRow.get(i), newRow.get(i+1))) {
                    issuesFound++;
                    // be more robust
                    if (i + 2 >= newRow.size() || areLevelsSafe(newRow.get(i), newRow.get(i+2))) {
                       // i+ 1 is level that makes the floor unsafe. skip i+1
                        i++;
                    } else if (i == 0 || areLevelsSafe(newRow.get(i-1), newRow.get(i+1))) {
                        // i is the level that makes the floor unsafe. skip i
                        continue;
                    } else {
                        issuesFound++;
                        break;
                    }
                }
            }
            if (issuesFound <= tolerance) {
                System.out.print("Safe row found: " + originalRow);
                if (issuesFound > 0) {
                    System.out.print(" with " + issuesFound + " issues");
                }
                System.out.println();
                total++;
            } else {
                System.out.println("Unsafe row found: " + originalRow);
            }
        }
        return total;
    }

    private static boolean areLevelsSafe(int level1, int level2) {
        return level1 < level2 && (level2 - level1) <= 3;
    }

    public static void main(String[] args) throws IOException {
        String filename = Objects.requireNonNull(Day2.class.getResource(FileParser.INPUT)).getPath();
        FileParser parser = new FileParser(filename);
        List<List<Integer>> rows = parser.readRowFile("").stream()
                .map(list -> list.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()))
                .toList();
        System.out.println("The answer to part 1 is: " + safeLevelFinder(rows, 0));
        System.out.println("The answer to part 2 is: " + safeLevelFinder(rows, 1));// 412 is too low. 439 is too high. 415 is too low
    }
}
