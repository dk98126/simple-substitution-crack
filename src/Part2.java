import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {

    private static final String FIRST_KEY = "ужшктачовцдфыьяэнргйм.хз.е.л.щъю";

    private static final ArrayList<String> possibleKeys;

    static {
        possibleKeys = new ArrayList<>();
        possibleKeys.addAll(Arrays.asList(
                "ужшктачовцдфыьяэнргймбхзиеплсщъю",
                "ужшктачовцдфыьяэнргймбхзиеслпщъю",
                "ужшктачовцдфыьяэнргймбхзпеилсщъю",
                "ужшктачовцдфыьяэнргймбхзпеслищъю",
                "ужшктачовцдфыьяэнргймбхзсеилпщъю",
                "ужшктачовцдфыьяэнргймбхзсеплищъю",
                "ужшктачовцдфыьяэнргймихзбеплсщъю",
                "ужшктачовцдфыьяэнргймихзбеслпщъю",
                "ужшктачовцдфыьяэнргймихзпеблсщъю",
                "ужшктачовцдфыьяэнргймихзпеслбщъю",
                "ужшктачовцдфыьяэнргймихзсеблпщъю",
                "ужшктачовцдфыьяэнргймихзсеплбщъю",
                "ужшктачовцдфыьяэнргймпхзбеилсщъю",
                "ужшктачовцдфыьяэнргймпхзбеслищъю",
                "ужшктачовцдфыьяэнргймпхзиеблсщъю",
                "ужшктачовцдфыьяэнргймпхзиеслбщъю",
                "ужшктачовцдфыьяэнргймпхзсеблищъю",
                "ужшктачовцдфыьяэнргймпхзсеилбщъю",
                "ужшктачовцдфыьяэнргймсхзбеилпщъю",
                "ужшктачовцдфыьяэнргймсхзбеплищъю",
                "ужшктачовцдфыьяэнргймсхзиеблпщъю",
                "ужшктачовцдфыьяэнргймсхзиеплбщъю",
                "ужшктачовцдфыьяэнргймсхзпеблищъю",
                "ужшктачовцдфыьяэнргймсхзпеилбщъю"
        ));
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("ciphered_text.txt"));
        List<Character> text = FitnessUtils.getRussianSymbols(reader.lines());
        BufferedWriter writer = new BufferedWriter(new FileWriter("output_block2.txt"));
        for (String currentKey : possibleKeys) {
            for (int i = 5; i <= 13; i++) {
                String key = KeyFunctions.getReplacedText(text.subList(0, i), currentKey).stream().map(String::valueOf).collect(Collectors.joining());
                String sortedKey = Arrays.stream(key.split("")).sorted().map(String::valueOf).collect(Collectors.joining());
                StringBuilder builder = new StringBuilder();
                for (int j = key.length(); j < text.size() - key.length(); j += key.length()) {
                    List<Character> subList = text.subList(j, j + key.length());
                    for (int k = 0; k < key.length(); k++) {
                        builder.append(subList.get(sortedKey.indexOf(key.charAt(k))));
                    }
                }
                writer.write(builder.toString());
                writer.newLine();
                writer.newLine();
            }
            writer.newLine();
        }
        writer.close();
    }
}
