import java.util.*;
import java.util.stream.Collectors;

public class KeyFunctions {
    public static final String RUSSIAN_ALPHABET;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 'а'; i <= 'я'; i++) {
            builder.append((char) i);
        }
        RUSSIAN_ALPHABET = builder.toString();
    }

    public static String getRandomizedKey() {
        List<String> letters = RUSSIAN_ALPHABET.chars()
                .mapToObj(value -> String.valueOf((char) value))
                .collect(Collectors.toList());
        Collections.shuffle(letters);
        return String.join("", letters);
    }

    //FIXME скорее всего тут что-то не так
    //старая версия, сейчас не нужна для правильной работы алгоритма
    public static String swapTwoCharsIfNeeded(String fittestKey, String notFittestKey, int position) {
        List<Character> fittestKeyLetters = fittestKey.chars()
                .mapToObj(value -> (char) value)
                .collect(Collectors.toList());
        List<Character> notFittestKeyLetters = notFittestKey.chars()
                .mapToObj(value -> (char) value)
                .collect(Collectors.toList());
        if (!fittestKeyLetters.get(position).equals(notFittestKeyLetters.get(position))) {
            int swapPosition = fittestKeyLetters.indexOf(notFittestKeyLetters.get(position));
            Collections.swap(fittestKeyLetters, position, swapPosition);
            return fittestKeyLetters.stream().map(String::valueOf).collect(Collectors.joining());
        }
        return fittestKey;
    }

    public static List<Character> getReplacedText(List<Character> text, String key) {
        Map<Character, Character> keyMap  = new HashMap<>();
        for (int i = 0; i < key.length(); i++) {
            keyMap.put(key.charAt(i), KeyFunctions.RUSSIAN_ALPHABET.charAt(i));
        }
        return text.stream().map(keyMap::get).collect(Collectors.toList());
    }

    public static String swapTwoChars(String key) {
        List<Character> charsKey = key.chars().mapToObj(value -> (char)value).collect(Collectors.toList());
        Random random = new Random();
        Collections.swap(charsKey, random.nextInt(RUSSIAN_ALPHABET.length()), random.nextInt(RUSSIAN_ALPHABET.length()));
        return charsKey.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
