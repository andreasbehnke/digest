package de.andreasbehnke.digest.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordSequenceComparatorTest {

    @Test
    void sortingTest() {
        WordSequence seq1 = WordSequence.create("Just another brick in the wall");
        WordSequence seq2 = WordSequence.create("Hallo World from here");
        WordSequence seq3 = WordSequence.create("Hallo World from there");
        WordSequence seq4 = WordSequence.create("abc def ghi");

        List<WordSequence> sequences = Arrays.asList(seq1, seq2, seq3, seq4);
        List<WordSequence> sorted = sequences.stream().sorted(new WordSequenceComparator()).collect(Collectors.toList());
        System.out.println(sorted);
        assertEquals(seq4, sorted.get(0));
        assertEquals(seq2, sorted.get(1));
        assertEquals(seq3, sorted.get(2));
        assertEquals(seq1, sorted.get(3));
    }

}
