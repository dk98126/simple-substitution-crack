import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarAndPiece {

    public static final int GRAM_NUMBER_FOR_FITNESS = 3;

    public static final int ITERATIONS_PER_KEY = 1000;

    public static final int OUTER_ITERATIONS = 100;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("war_and_piece.txt"));
        Stream<String> lines = reader.lines();

        List<Character> russianSymbols = FitnessUtils.getRussianSymbols(lines);

        List<Pair> charsFrequencies = FitnessUtils.getNGramsFrequency(russianSymbols, 1);

        List<Pair> gramsForAnalysisFrequencies =
                FitnessUtils.getNGramsFrequency(russianSymbols, GRAM_NUMBER_FOR_FITNESS);

        long gramsCount = FitnessUtils.getGramsCount(gramsForAnalysisFrequencies);

        List<SpecialCount> pairsWithProbabilities =
                FitnessUtils.getPairsWithProbabilities(gramsForAnalysisFrequencies, gramsCount);

        Map<String, Double> gramsProbabilitiesMap = new HashMap<>();
        pairsWithProbabilities.forEach(sc -> gramsProbabilitiesMap.put(sc.getPair().getGram(), sc.getLogProbability()));

        BufferedReader cipheredTextReader = new BufferedReader(new FileReader("text.txt"));

        List<Character> cipheredSymbols = FitnessUtils.getRussianSymbols(cipheredTextReader.lines());

        double bookFitness = FitnessUtils.getTextFitness(russianSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);

        double cipheredTextFitness = FitnessUtils.getTextFitness(cipheredSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);

        //List<Character> cipheredBook = KeyFunctions.getReplacedText(russianSymbols, KeyFunctions.getRandomizedKey());

        //double cipheredBookFitness = FitnessUtils.getTextFitness(cipheredBook, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);

        String bestKey = KeyFunctions.getRandomizedKey();

        for (int i = 0; i < OUTER_ITERATIONS; i++) {

            int counter = ITERATIONS_PER_KEY;
            String key = KeyFunctions.getRandomizedKey();
            String parentKey = key;
            while (counter > 0) {
                key = KeyFunctions.swapTwoChars(key);
                if (FitnessUtils.findFittestKey(key, parentKey, cipheredSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS).equals(parentKey)) {
                    key = parentKey;
                    counter--;
                } else {
                    parentKey = key;
                    counter = ITERATIONS_PER_KEY;
                }
            }
            bestKey = FitnessUtils.findFittestKey(key, bestKey, cipheredSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);
        }

        List<Character> resultedText = KeyFunctions.getReplacedText(cipheredSymbols, bestKey);
        double resultedTextFitness = FitnessUtils.getTextFitness(resultedText, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);
        System.out.println(resultedText.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
