package de.andreasbehnke.digest.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordSequenceTest {

    @Test
    void testCreate() {
        WordSequence sequence = WordSequence.create("This\n is a small \t\n\t    test");
        List<String> words = sequence.getWords();
        assertEquals(5, words.size());
        assertEquals("This", words.get(0));
        assertEquals("is", words.get(1));
        assertEquals("a", words.get(2));
        assertEquals("small", words.get(3));
        assertEquals("test", words.get(4));
    }

    @Test
    void testSubSequence() {
        List<String> words = WordSequence.create("This\n is a small \t\n\t    test")
                .subSequence(2,5).getWords();
        assertEquals(3, words.size());
        assertEquals("a", words.get(0));
        assertEquals("small", words.get(1));
        assertEquals("test", words.get(2));
    }

    @Test
    void testAllSubSequences() {
        WordSequence sequence = WordSequence.create("abc def ghi jkl");
        Set<WordSequence> subSequences = sequence.allSubSequences();
        assertEquals(10, subSequences.size());
        assertTrue(subSequences.contains(WordSequence.create("abc")));
        assertTrue(subSequences.contains(WordSequence.create("abc def")));
        assertTrue(subSequences.contains(WordSequence.create("abc def ghi")));
        assertTrue(subSequences.contains(WordSequence.create("abc def ghi jkl")));
        assertTrue(subSequences.contains(WordSequence.create("def")));
        assertTrue(subSequences.contains(WordSequence.create("def ghi")));
        assertTrue(subSequences.contains(WordSequence.create("def ghi jkl")));
        assertTrue(subSequences.contains(WordSequence.create("ghi")));
        assertTrue(subSequences.contains(WordSequence.create("ghi jkl")));
        assertTrue(subSequences.contains(WordSequence.create("jkl")));
    }
}
