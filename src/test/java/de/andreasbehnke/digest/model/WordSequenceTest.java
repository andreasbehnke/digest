package de.andreasbehnke.digest.model;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
    void testSuffixes() {
        WordSequence sequence = WordSequence.create("abc def ghi jkl");
        Set<WordSequence> suffixes = sequence.suffixes();
        assertEquals(4, suffixes.size());
        assertTrue(suffixes.contains(WordSequence.create("abc def ghi jkl")));
        assertTrue(suffixes.contains(WordSequence.create("def ghi jkl")));
        assertTrue(suffixes.contains(WordSequence.create("ghi jkl")));
        assertTrue(suffixes.contains(WordSequence.create("jkl")));

        sequence = WordSequence.create("abc");
        suffixes = sequence.suffixes();
        assertEquals(1, suffixes.size());
        assertTrue(suffixes.contains(WordSequence.create("abc")));
    }

    @Test
    void testMatch() {
        WordSequence sequence = WordSequence.create("abc def ghi jkl");
        assertEquals(Optional.empty(), sequence.match(WordSequence.create("xyz")));

        SequenceMatch match = sequence.match(WordSequence.create("def")).orElseThrow();
        assertEquals(1, match.getFromIndex());
        assertEquals(1, match.getToIndex());
        assertEquals(1, match.length());
        assertEquals("def", match.getPattern().toString());

        match = sequence.match(WordSequence.create("def ghi")).orElseThrow();
        assertEquals(1, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(2, match.length());
        assertEquals("def ghi", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi jkl mno")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(3, match.getToIndex());
        assertEquals(2, match.length());
        assertEquals("ghi jkl", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi 123")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(1, match.length());
        assertEquals("ghi", match.getPattern().toString());

        match = sequence.match(WordSequence.create("ghi 123 456 789")).orElseThrow();
        assertEquals(2, match.getFromIndex());
        assertEquals(2, match.getToIndex());
        assertEquals(1, match.length());
        assertEquals("ghi", match.getPattern().toString());

        match = sequence.match(Arrays.asList(WordSequence.create("x y z"), WordSequence.create("abc def"), WordSequence.create("ghi 123 456 789"))).orElseThrow();
        assertEquals("abc def", match.getPattern().toString());
    }

    @Test
    void testStartsWith() {
        WordSequence sequence = WordSequence.create("abc def ghi jkl");
        assertTrue(sequence.startsWith(WordSequence.create("abc")));
        assertTrue(sequence.startsWith(WordSequence.create("abc def")));
        assertTrue(sequence.startsWith(WordSequence.create("abc def ghi")));
        assertFalse(sequence.startsWith(WordSequence.create("def ghi")));
        assertFalse(sequence.startsWith(WordSequence.create("ghi")));
        assertFalse(sequence.startsWith(WordSequence.create("abc def ghi jkl 123")));
        assertFalse(sequence.startsWith(WordSequence.create("999 abc def ghi jkl")));
    }

    @Test
    void testMostFrequentCommonMatch() {
        WordSequence pattern = WordSequence.create("abc def ghi jkl kkk lll");

        PatternMatches match = pattern.mostFrequentCommonMatch(List.of(WordSequence.create("abc def ghi jkl")), 0, 0).orElseThrow();
        assertEquals(4, match.length());
        assertEquals(WordSequence.create("abc def ghi jkl"), match.getPattern());
        assertEquals(1, match.getHits());

        List<WordSequence> sources = List.of(
                WordSequence.create("abc def 123 567"),
                WordSequence.create("abc def 123 567 abc def 123 567"),
                WordSequence.create("123 456 xyz 777"),
                WordSequence.create("abc def ghi 777 ttt"),
                WordSequence.create("abc def ghi jkl"),
                WordSequence.create("abc def")
        );

        match = pattern.mostFrequentCommonMatch(sources, 0, 0).orElseThrow();
        assertEquals(2, match.length());
        assertEquals(5, match.getHits());
        assertEquals(WordSequence.create("abc def"), match.getPattern());

        match = pattern.mostFrequentCommonMatch(sources, 3, 0).orElseThrow();
        assertEquals(2, match.getHits());
        assertEquals(WordSequence.create("abc def ghi"), match.getPattern());

        match = pattern.mostFrequentCommonMatch(sources, 3, 2).orElseThrow();
        assertEquals(2, match.getHits());
        assertEquals(WordSequence.create("abc def ghi"), match.getPattern());

        assertFalse(pattern.mostFrequentCommonMatch(sources, 4, 2).isPresent());

        match = pattern.mostFrequentCommonMatch(sources, 4, 1).orElseThrow();
        assertEquals(1, match.getHits());
        assertEquals(WordSequence.create("abc def ghi jkl"), match.getPattern());

        pattern = WordSequence.create("cba fed ihg lkl");
        assertFalse(pattern.mostFrequentCommonMatch(sources, 0, 0).isPresent());
    }

    @Test
    void testContains() {
        WordSequence pattern = WordSequence.create("abc def ghi jkl kkk lll");
        SequenceMatch match = pattern.contains(WordSequence.create("abc def")).orElseThrow();
        assertEquals("abc def", match.getPattern().toString());
        assertEquals(0, match.getFromIndex());
        assertFalse(pattern.contains(WordSequence.create("abc def ghi jkl kkk lll xxx")).isPresent());
        match = pattern.contains(WordSequence.create("abc def ghi jkl kkk lll")).orElseThrow();
        assertEquals("abc def ghi jkl kkk lll", match.getPattern().toString());
        assertEquals(0, match.getFromIndex());
        match = pattern.contains(WordSequence.create("ghi jkl kkk lll")).orElseThrow();
        assertEquals("ghi jkl kkk lll", match.getPattern().toString());
        assertEquals(2, match.getFromIndex());
        match = pattern.contains(WordSequence.create("ghi jkl")).orElseThrow();
        assertEquals("ghi jkl", match.getPattern().toString());
        assertEquals(2, match.getFromIndex());
        match = pattern.contains(WordSequence.create("jkl")).orElseThrow();
        assertEquals("jkl", match.getPattern().toString());
        assertEquals(3, match.getFromIndex());

        Set<SequenceMatch> matches = pattern.contains(Arrays.asList(WordSequence.create("x y z"), WordSequence.create("abc def"), WordSequence.create("ghi 123 456 789")));
        assertEquals(1, matches.size());
        assertEquals("abc def", matches.iterator().next().getPattern().toString());

        assertEquals(0, pattern.contains(Arrays.asList(WordSequence.create("x y z"), WordSequence.create("ghi 123 456 789"))).size());

        matches = pattern.contains(Arrays.asList(WordSequence.create("x y z"), WordSequence.create("abc def"), WordSequence.create("def ghi")));
        assertEquals(2, matches.size());
        Set<String> matchesAsString = matches.stream().map(m -> m.getPattern().toString()).collect(Collectors.toSet());
        assertTrue(matchesAsString.contains("abc def"));
        assertTrue(matchesAsString.contains("def ghi"));
    }

    @Test
    void testToStringWithEllipsis() {
        WordSequence sequence = WordSequence.create("cba fed ihg lkl xyz");
        assertEquals("cba ... ihg ...", sequence.toString(Set.of(1, 3, 4), "..."));
        assertEquals("... fed ihg lkl xyz", sequence.toString(Set.of(0), "..."));
        assertEquals("...", sequence.toString(Set.of(0,1,2,3,4), "..."));
        assertEquals("cba fed --- lkl xyz", sequence.toString(Set.of(2), "---"));
    }
}
