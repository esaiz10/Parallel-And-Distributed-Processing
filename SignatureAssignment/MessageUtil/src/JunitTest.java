import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class JunitTest {

    @Test
    void countWords_shouldReturnEightForEightWords() {
        String s = "one two three four five six seven eight";
        assertEquals(8, MessageUtil.countWords(s));
    }

    @Test
    void countWords_shouldReturnZeroForBlank() {
        assertEquals(0, MessageUtil.countWords("   "));
        assertEquals(0, MessageUtil.countWords(""));
    }

    @Test
    void swapTwoRandomWords_shouldPreserveAllWords() {
        String original = "this is the day to test your software";
        String swapped = MessageUtil.swapTwoRandomWords(original, new Random(123));

        // Same number of words, same words (just different order)
        assertEquals(MessageUtil.countWords(original), MessageUtil.countWords(swapped));
        assertTrue(MessageUtil.sameWordsIgnoringOrder(original, swapped));
    }
}