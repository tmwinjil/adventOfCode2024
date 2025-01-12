package Day1;

import Utils.FileParser;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Day1 {

    public static int part1(List<List<Integer>> columns) {
        int total = 0;
        for (int i = 0; i < columns.getFirst().size(); i++) {
            total += Math.abs(columns.getFirst().get(i) - columns.getLast().get(i));
        }
        return total;
    }

    public static int part2(List<List<Integer>> columns) {
        int total = 0;
        List<Integer> left = columns.getFirst();
        List<Integer> right = columns.getLast();
        System.out.println(left + "\n" + right);
        for (int i = 0, j = 0; i < left.size() && j < right.size(); i++) {
            int multiplier = 1;
            while ((i < left.size() - 1) && left.get(i).intValue() == left.get(i+1).intValue()) {
                multiplier++;
                i++;
            }
            int similarity = 0;
            while (j < right.size() && left.get(i) >= right.get(j)) {
                if (left.get(i).intValue() == right.get(j).intValue()) {
                    similarity++;
                }
                j++;
            }
            //System.out.printf("Similarity for %d is: %d (multiplier %d)%n", left.get(i), similarity * multiplier, multiplier);
            total += left.get(i) * similarity * multiplier;
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        String filename = Objects.requireNonNull(Day1.class.getResource(FileParser.INPUT)).getPath();
        FileParser parser = new FileParser(filename);
        List<List<Integer>> columns = parser.readColumnFile(2,"").stream()
                .map(list -> list.stream().mapToInt(Integer::parseInt).sorted().boxed().toList())
                .toList();
        System.out.println("The answer to part 1 is: " + part1(columns));
        System.out.println("The answer to part 1 is: " + part2(columns));
    }
}
