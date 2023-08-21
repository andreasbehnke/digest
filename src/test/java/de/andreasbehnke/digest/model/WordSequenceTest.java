package de.andreasbehnke.digest.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
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

    @Test
    void testMatch() {
        WordSequence sequence = WordSequence.create("abc def ghi jkl");
        assertEquals(Optional.empty(), sequence.match(WordSequence.create("xyz")));

        SequenceMatch match = sequence.match(WordSequence.create("def")).orElseThrow();
        assertEquals(1, match.getFromIndex());
        assertEquals(1, match.getToIndex());
        assertEquals(1, match.getCount());
        assertEquals("def", match.getPattern().toString());

        match = sequence.match(WordSequence.create("def ghi")).orElseThrow();
        assertEquals(1, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(2, match.getCount());
        assertEquals("def ghi", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi jkl mno")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(3, match.getToIndex());
        assertEquals(2, match.getCount());
        assertEquals("ghi jkl", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi 123")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(1, match.getCount());
        assertEquals("ghi", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi 123 456 789")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(1, match.getCount());
        assertEquals("ghi", match.getPattern().toString());
    }
}
