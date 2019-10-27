import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class WarAndPiece {

    public static final int GRAM_NUMBER_FOR_FITNESS = 3;

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

        BufferedReader cipheredTextReader = new BufferedReader(new FileReader("test.txt"));

        List<Character> cipheredSymbols = FitnessUtils.getRussianSymbols(cipheredTextReader.lines());

        double bookFitness = FitnessUtils.getTextFitness(russianSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);

        double maxFitness = 0;
        for (int j = 0; j < 1000; j++) {
            String firstKey = KeyFunctions.getRandomizedKey();
            String secondKey = KeyFunctions.getRandomizedKey();

            String fittestKey = null;
            for (int i = 0; i < KeyFunctions.RUSSIAN_ALPHABET.length(); i++) {
                fittestKey = FitnessUtils.findFittestKey(firstKey,
                        secondKey,
                        cipheredSymbols,
                        gramsProbabilitiesMap,
                        GRAM_NUMBER_FOR_FITNESS);
                String notFittestKey = !fittestKey.equals(firstKey) ? firstKey : secondKey;
                firstKey = fittestKey;
                secondKey = notFittestKey;
                fittestKey = KeyFunctions.swapTwoCharsIfNeeded(fittestKey, notFittestKey, i);
                if (FitnessUtils.getKeyFitness(cipheredSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS, fittestKey)
                        < FitnessUtils.getKeyFitness(cipheredSymbols, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS, firstKey)) {
                    fittestKey = firstKey;
                } else {
                    firstKey = fittestKey;
                }
            }

            List<Character> resultedText = KeyFunctions.getReplacedText(cipheredSymbols, fittestKey);
            double resultedTextFitness = FitnessUtils.getTextFitness(resultedText, gramsProbabilitiesMap, GRAM_NUMBER_FOR_FITNESS);
            maxFitness = Math.max(maxFitness, resultedTextFitness);
        }
        System.out.println();
    }
}
