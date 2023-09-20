package de.andreasbehnke.digest.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class SequenceMatchTest {

    @Test
    void testRange() {
        SequenceMatch sequenceMatch = new SequenceMatch(WordSequence.create("abc def ghi"), 3);
        Set<Integer> range = sequenceMatch.range();
        Assertions.assertFalse(range.contains(0));
        Assertions.assertFalse(range.contains(1));
        Assertions.assertFalse(range.contains(2));
        Assertions.assertTrue(range.contains(3));
        Assertions.assertTrue(range.contains(4));
        Assertions.assertTrue(range.contains(5));
        Assertions.assertFalse(range.contains(6));
    }
}
