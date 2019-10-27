import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FitnessUtils {

    public static double getTextFitness(List<Character> text,
                                        Map<String, Double> gramsProbabilitiesMap,
                                        int gramOrder) {
        double fitness = 0;
        for (int i = 0; i < text.size() - gramOrder - 1; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = i; j < i + gramOrder; j++) {
                builder.append(text.get(j));
            }
            //TODO опасное место, надо понять, какую вероятность прибалять
            fitness += gramsProbabilitiesMap.getOrDefault(builder.toString(), 0.0);
        }
        return fitness;
    }

    public static double getKeyFitness(List<Character> text,
                                       Map<String, Double> gramsProbabilitiesMap,
                                       int gramOrder,
                                       String key) {
        List<Character> resultedText = KeyFunctions.getReplacedText(text, key);
        return getTextFitness(resultedText, gramsProbabilitiesMap, gramOrder);
    }

    public static String findFittestKey(String key1,
                                        String key2,
                                        List<Character> text,
                                        Map<String, Double> gramsProbabilitiesMap,
                                        int gramOrder) {
        double key1Fitness = getKeyFitness(text, gramsProbabilitiesMap, gramOrder, key1);
        double key2Fitness = getKeyFitness(text, gramsProbabilitiesMap, gramOrder, key2);
        return key1Fitness > key2Fitness ? key1 : key2;
    }

    public static List<Character> getRussianSymbols(Stream<String> lines) {
        return lines.flatMapToInt(String::chars)
                .map(d -> d == 'ё' ? 'е' : d)
                .filter(d -> d >= 0x0430 && d <= 0x044F || d >= 0x0410 && d <= 0x042F)
                .mapToObj(d -> Character.toLowerCase((char) d))
                .collect(Collectors.toList());
    }

    public static List<Pair> getNGramsFrequency(List<Character> text, int gramNumber) {
        List<String> allNGrams = getAllNGrams(text, gramNumber);
        return allNGrams.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new Pair(String.valueOf(entry.getKey()), entry.getValue()))
                .sorted(Comparator.comparingLong(Pair::getFrequency).reversed())
                .collect(Collectors.toList());
    }

    public static List<String> getAllNGrams(List<Character> text, int gramOrder) {
        List<String> allNGrams = new ArrayList<>();
        for (int i = 0; i < text.size() - gramOrder - 1; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = i; j < i + gramOrder; j++) {
                builder.append(text.get(j));
            }
            allNGrams.add(builder.toString());
        }
        return allNGrams;
    }

    public static List<SpecialCount> getPairsWithProbabilities(List<Pair> grams, long gramsSize) {
        return grams.stream()
                .map(pair -> new SpecialCount(pair, 1.0 * pair.getFrequency() / gramsSize))
                .collect(Collectors.toList());
    }

    public static long getGramsCount(List<Pair> grams) {
        return grams.stream()
                .mapToLong(Pair::getFrequency)
                .sum();
    }
}
