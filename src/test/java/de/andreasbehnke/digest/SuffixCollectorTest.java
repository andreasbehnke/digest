package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.WordSequence;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SuffixCollectorTest {

    @Test
    void testCollectSuffixes() {
        // The seven example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");
        WordSequence sequence6 = WordSequence.create("999");
        WordSequence sequence7 = WordSequence.create("888");

        //... contain this list of all possible sub-sequences
        Iterator<WordSequence> subSequences = new SuffixCollector().collectSuffixes(List.of(sequence1, sequence2,
                sequence3, sequence4, sequence5, sequence6, sequence7)).iterator();
        assertEquals(WordSequence.create("123 456"), subSequences.next());
        assertEquals(WordSequence.create("123 456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("123 abc def ghi"), subSequences.next());
        assertEquals(WordSequence.create("123 ghi def abc"), subSequences.next());
        assertEquals(WordSequence.create("456"), subSequences.next());
        assertEquals(WordSequence.create("456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("888"), subSequences.next());
        assertEquals(WordSequence.create("999"), subSequences.next());
        assertEquals(WordSequence.create("abc"), subSequences.next());
        assertEquals(WordSequence.create("abc def ghi"), subSequences.next());
        assertEquals(WordSequence.create("abc def ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("abc def ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("def"), subSequences.next());
        assertEquals(WordSequence.create("def abc"), subSequences.next());
        assertEquals(WordSequence.create("def ghi"), subSequences.next());
        assertEquals(WordSequence.create("def ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("def ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("ghi"), subSequences.next());
        assertEquals(WordSequence.create("ghi def"), subSequences.next());
        assertEquals(WordSequence.create("ghi def abc"), subSequences.next());
        assertEquals(WordSequence.create("ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("xyz"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 abc def ghi"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences.next());
        assertFalse(subSequences.hasNext());
    }

    @Test
    void testGroupSuffixes() {
        // The seven example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");
        WordSequence sequence6 = WordSequence.create("999");
        WordSequence sequence7 = WordSequence.create("888");

        //... contain this list of prefix groups
        List<WordSequence> groups = new SuffixCollector().groupSuffixes(List.of(sequence1, sequence2,
                sequence3, sequence4, sequence5, sequence6, sequence7));
        assertEquals(19, groups.size());

        Iterator<WordSequence> subSequences = groups.iterator();
        assertEquals(WordSequence.create("123 456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("123 abc def ghi"), subSequences.next());
        assertEquals(WordSequence.create("123 ghi def abc"), subSequences.next());
        assertEquals(WordSequence.create("456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("888"), subSequences.next());
        assertEquals(WordSequence.create("999"), subSequences.next());
        assertEquals(WordSequence.create("abc def ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("abc def ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("def abc"), subSequences.next());
        assertEquals(WordSequence.create("def ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("def ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("ghi def abc"), subSequences.next());
        assertEquals(WordSequence.create("ghi jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("ghi xyz 123 456"), subSequences.next());
        assertEquals(WordSequence.create("jkl xyz"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 456 abc ghi def"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 abc def ghi"), subSequences.next());
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences.next());
        assertFalse(subSequences.hasNext());
    }
}
