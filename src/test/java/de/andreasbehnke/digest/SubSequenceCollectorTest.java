package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.GroupOfWordSequences;
import de.andreasbehnke.digest.model.WordSequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubSequenceCollectorTest {

    @Test
    void testCollectSubSequences() {
        // The five example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");

        //... contain this list of all possible sub-sequences
        List<WordSequence> subSequences = new SubSequenceCollector().collectSubSequences(List.of(sequence1, sequence2, sequence3, sequence4, sequence5));
        assertEquals(WordSequence.create("123"), subSequences.get(0));
        assertEquals(WordSequence.create("123 456"), subSequences.get(1));
        assertEquals(WordSequence.create("123 456 abc"), subSequences.get(2));
        assertEquals(WordSequence.create("123 456 abc ghi"), subSequences.get(3));
        assertEquals(WordSequence.create("123 456 abc ghi def"), subSequences.get(4));

        assertEquals(WordSequence.create("123 abc"), subSequences.get(5));
        assertEquals(WordSequence.create("123 abc def"), subSequences.get(6));
        assertEquals(WordSequence.create("123 abc def ghi"), subSequences.get(7));

        assertEquals(WordSequence.create("123 ghi"), subSequences.get(8));
        assertEquals(WordSequence.create("123 ghi def"), subSequences.get(9));
        assertEquals(WordSequence.create("123 ghi def abc"), subSequences.get(10));

        assertEquals(WordSequence.create("456"), subSequences.get(11));
        assertEquals(WordSequence.create("456 abc"), subSequences.get(12));
        assertEquals(WordSequence.create("456 abc ghi"), subSequences.get(13));
        assertEquals(WordSequence.create("456 abc ghi def"), subSequences.get(14));

        assertEquals(WordSequence.create("abc"), subSequences.get(15));
        assertEquals(WordSequence.create("abc def"), subSequences.get(16));
        assertEquals(WordSequence.create("abc def ghi"), subSequences.get(17));
        assertEquals(WordSequence.create("abc def ghi jkl"), subSequences.get(18));
        assertEquals(WordSequence.create("abc def ghi jkl xyz"), subSequences.get(19));

        assertEquals(WordSequence.create("abc def ghi xyz"), subSequences.get(20));
        assertEquals(WordSequence.create("abc def ghi xyz 123"), subSequences.get(21));
        assertEquals(WordSequence.create("abc def ghi xyz 123 456"), subSequences.get(22));

        assertEquals(WordSequence.create("abc ghi"), subSequences.get(23));
        assertEquals(WordSequence.create("abc ghi def"), subSequences.get(24));

        assertEquals(WordSequence.create("def"), subSequences.get(25));
        assertEquals(WordSequence.create("def abc"), subSequences.get(26));

        assertEquals(WordSequence.create("def ghi"), subSequences.get(27));
        assertEquals(WordSequence.create("def ghi jkl"), subSequences.get(28));
        assertEquals(WordSequence.create("def ghi jkl xyz"), subSequences.get(29));

        assertEquals(WordSequence.create("def ghi xyz"), subSequences.get(30));
        assertEquals(WordSequence.create("def ghi xyz 123"), subSequences.get(31));
        assertEquals(WordSequence.create("def ghi xyz 123 456"), subSequences.get(32));

        assertEquals(WordSequence.create("ghi"), subSequences.get(33));
        assertEquals(WordSequence.create("ghi def"), subSequences.get(34));
        assertEquals(WordSequence.create("ghi def abc"), subSequences.get(35));

        assertEquals(WordSequence.create("ghi jkl"), subSequences.get(36));
        assertEquals(WordSequence.create("ghi jkl xyz"), subSequences.get(37));

        assertEquals(WordSequence.create("ghi xyz"), subSequences.get(38));
        assertEquals(WordSequence.create("ghi xyz 123"), subSequences.get(39));
        assertEquals(WordSequence.create("ghi xyz 123 456"), subSequences.get(40));

        assertEquals(WordSequence.create("jkl"), subSequences.get(41));
        assertEquals(WordSequence.create("jkl xyz"), subSequences.get(42));

        assertEquals(WordSequence.create("xyz"), subSequences.get(43));
        assertEquals(WordSequence.create("xyz 123"), subSequences.get(44));
        assertEquals(WordSequence.create("xyz 123 456"), subSequences.get(45));
        assertEquals(WordSequence.create("xyz 123 456 abc"), subSequences.get(46));
        assertEquals(WordSequence.create("xyz 123 456 abc ghi"), subSequences.get(47));
        assertEquals(WordSequence.create("xyz 123 456 abc ghi def"), subSequences.get(48));

        assertEquals(WordSequence.create("xyz 123 abc"), subSequences.get(49));
        assertEquals(WordSequence.create("xyz 123 abc def"), subSequences.get(50));
        assertEquals(WordSequence.create("xyz 123 abc def ghi"), subSequences.get(51));

        assertEquals(WordSequence.create("xyz 123 ghi"), subSequences.get(52));
        assertEquals(WordSequence.create("xyz 123 ghi def"), subSequences.get(53));
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences.get(54));

        assertEquals(55, subSequences.size());
    }

    @Test
    void testGroupSequences() {
        // The five example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");

        //... contain this list of prefix groups
        List<GroupOfWordSequences> groups = new SubSequenceCollector().groupSequences(List.of(sequence1, sequence2, sequence3, sequence4, sequence5));
        assertEquals(17, groups.size());

        assertEquals(0, groups.get(0).getId());
        List<WordSequence> subSequences1 = groups.get(0).getSequences();
        assertEquals(WordSequence.create("123"), subSequences1.get(0));
        assertEquals(WordSequence.create("123 456"), subSequences1.get(1));
        assertEquals(WordSequence.create("123 456 abc"), subSequences1.get(2));
        assertEquals(WordSequence.create("123 456 abc ghi"), subSequences1.get(3));
        assertEquals(WordSequence.create("123 456 abc ghi def"), subSequences1.get(4));
        assertEquals(5, subSequences1.size());

        assertEquals(10, groups.get(10).getId());
        List<WordSequence> subSequences11 = groups.get(10).getSequences();
        assertEquals(WordSequence.create("ghi"), subSequences11.get(0));
        assertEquals(WordSequence.create("ghi def"), subSequences11.get(1));
        assertEquals(WordSequence.create("ghi def abc"), subSequences11.get(2));
        assertEquals(3, subSequences11.size());

        assertEquals(16, groups.get(16).getId());
        List<WordSequence> subSequences17 = groups.get(16).getSequences();
        assertEquals(WordSequence.create("xyz 123 ghi"), subSequences17.get(0));
        assertEquals(WordSequence.create("xyz 123 ghi def"), subSequences17.get(1));
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences17.get(2));
        assertEquals(3, subSequences17.size());
    }
}
