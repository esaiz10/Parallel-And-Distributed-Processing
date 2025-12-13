import java.util.*;

public class MessageUtil {

    public static int countWords(String sentence) {
        if (sentence == null) return 0;
        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) return 0;
        return trimmed.split("\\s+").length;
    }

    public static String swapTwoRandomWords(String sentence, Random rng) {
        if (sentence == null) return null;

        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) return sentence;

        String[] words = trimmed.split("\\s+");
        if (words.length < 2) return sentence;

        int i = rng.nextInt(words.length);
        int j = rng.nextInt(words.length);
        while (j == i) {
            j = rng.nextInt(words.length);
        }

        String temp = words[i];
        words[i] = words[j];
        words[j] = temp;

        return String.join(" ", words);
    }
    public static boolean sameWordsIgnoringOrder(String a, String b) {
        if (a == null || b == null) return a == b;

        String[] wa = a.trim().isEmpty() ? new String[0] : a.trim().split("\\s+");
        String[] wb = b.trim().isEmpty() ? new String[0] : b.trim().split("\\s+");

        if (wa.length != wb.length) return false;

        Map<String, Integer> freq = new HashMap<>();
        for (String w : wa) freq.put(w, freq.getOrDefault(w, 0) + 1);
        for (String w : wb) {
            if (!freq.containsKey(w)) return false;
            freq.put(w, freq.get(w) - 1);
            if (freq.get(w) == 0) freq.remove(w);
        }
        return freq.isEmpty();
    }
}