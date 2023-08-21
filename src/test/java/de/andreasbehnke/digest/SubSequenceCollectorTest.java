package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.GroupOfWordSequences;
import de.andreasbehnke.digest.model.WordSequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubSequenceCollectorTest {

    @Test
    void testCollectSubSequences() {
        // The seven example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");
        WordSequence sequence6 = WordSequence.create("999");
        WordSequence sequence7 = WordSequence.create("888");

        //... contain this list of all possible sub-sequences
        List<WordSequence> subSequences = new SubSequenceCollector().collectSubSequences(List.of(sequence1, sequence2,
                sequence3, sequence4, sequence5, sequence6, sequence7));
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

        assertEquals(WordSequence.create("888"), subSequences.get(15));

        assertEquals(WordSequence.create("999"), subSequences.get(16));

        assertEquals(WordSequence.create("abc"), subSequences.get(17));
        assertEquals(WordSequence.create("abc def"), subSequences.get(18));
        assertEquals(WordSequence.create("abc def ghi"), subSequences.get(19));
        assertEquals(WordSequence.create("abc def ghi jkl"), subSequences.get(20));
        assertEquals(WordSequence.create("abc def ghi jkl xyz"), subSequences.get(21));

        assertEquals(WordSequence.create("abc def ghi xyz"), subSequences.get(22));
        assertEquals(WordSequence.create("abc def ghi xyz 123"), subSequences.get(23));
        assertEquals(WordSequence.create("abc def ghi xyz 123 456"), subSequences.get(24));

        assertEquals(WordSequence.create("abc ghi"), subSequences.get(25));
        assertEquals(WordSequence.create("abc ghi def"), subSequences.get(26));

        assertEquals(WordSequence.create("def"), subSequences.get(27));
        assertEquals(WordSequence.create("def abc"), subSequences.get(28));

        assertEquals(WordSequence.create("def ghi"), subSequences.get(29));
        assertEquals(WordSequence.create("def ghi jkl"), subSequences.get(30));
        assertEquals(WordSequence.create("def ghi jkl xyz"), subSequences.get(31));

        assertEquals(WordSequence.create("def ghi xyz"), subSequences.get(32));
        assertEquals(WordSequence.create("def ghi xyz 123"), subSequences.get(33));
        assertEquals(WordSequence.create("def ghi xyz 123 456"), subSequences.get(34));

        assertEquals(WordSequence.create("ghi"), subSequences.get(35));
        assertEquals(WordSequence.create("ghi def"), subSequences.get(36));
        assertEquals(WordSequence.create("ghi def abc"), subSequences.get(37));

        assertEquals(WordSequence.create("ghi jkl"), subSequences.get(38));
        assertEquals(WordSequence.create("ghi jkl xyz"), subSequences.get(39));

        assertEquals(WordSequence.create("ghi xyz"), subSequences.get(40));
        assertEquals(WordSequence.create("ghi xyz 123"), subSequences.get(41));
        assertEquals(WordSequence.create("ghi xyz 123 456"), subSequences.get(42));

        assertEquals(WordSequence.create("jkl"), subSequences.get(43));
        assertEquals(WordSequence.create("jkl xyz"), subSequences.get(44));

        assertEquals(WordSequence.create("xyz"), subSequences.get(45));
        assertEquals(WordSequence.create("xyz 123"), subSequences.get(46));
        assertEquals(WordSequence.create("xyz 123 456"), subSequences.get(47));
        assertEquals(WordSequence.create("xyz 123 456 abc"), subSequences.get(48));
        assertEquals(WordSequence.create("xyz 123 456 abc ghi"), subSequences.get(49));
        assertEquals(WordSequence.create("xyz 123 456 abc ghi def"), subSequences.get(50));

        assertEquals(WordSequence.create("xyz 123 abc"), subSequences.get(51));
        assertEquals(WordSequence.create("xyz 123 abc def"), subSequences.get(52));
        assertEquals(WordSequence.create("xyz 123 abc def ghi"), subSequences.get(53));

        assertEquals(WordSequence.create("xyz 123 ghi"), subSequences.get(54));
        assertEquals(WordSequence.create("xyz 123 ghi def"), subSequences.get(55));
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences.get(56));

        assertEquals(57, subSequences.size());
    }

    @Test
    void testGroupSequences() {
        // The seven example input texts...
        WordSequence sequence1 = WordSequence.create("abc def ghi jkl xyz");
        WordSequence sequence2 = WordSequence.create("abc def ghi xyz 123 456");
        WordSequence sequence3 = WordSequence.create("xyz 123 abc def ghi");
        WordSequence sequence4 = WordSequence.create("xyz 123 456 abc ghi def");
        WordSequence sequence5 = WordSequence.create("xyz 123 ghi def abc");
        WordSequence sequence6 = WordSequence.create("999");
        WordSequence sequence7 = WordSequence.create("888");

        //... contain this list of prefix groups
        List<GroupOfWordSequences> groups = new SubSequenceCollector().groupSequences(List.of(sequence1, sequence2,
                sequence3, sequence4, sequence5, sequence6, sequence7));
        assertEquals(19, groups.size());

        assertEquals(0, groups.get(0).getId());
        List<WordSequence> subSequences1 = groups.get(0).getSequences();
        assertEquals(WordSequence.create("123"), subSequences1.get(0));
        assertEquals(WordSequence.create("123 456"), subSequences1.get(1));
        assertEquals(WordSequence.create("123 456 abc"), subSequences1.get(2));
        assertEquals(WordSequence.create("123 456 abc ghi"), subSequences1.get(3));
        assertEquals(WordSequence.create("123 456 abc ghi def"), subSequences1.get(4));
        assertEquals(5, subSequences1.size());

        assertEquals(4, groups.get(4).getId());
        List<WordSequence> subSequences5 = groups.get(4).getSequences();
        assertEquals(WordSequence.create("888"), subSequences5.get(0));
        assertEquals(1, subSequences5.size());

        assertEquals(5, groups.get(5).getId());
        List<WordSequence> subSequences6 = groups.get(5).getSequences();
        assertEquals(WordSequence.create("999"), subSequences6.get(0));
        assertEquals(1, subSequences6.size());

        assertEquals(12, groups.get(12).getId());
        List<WordSequence> subSequences13 = groups.get(12).getSequences();
        assertEquals(WordSequence.create("ghi"), subSequences13.get(0));
        assertEquals(WordSequence.create("ghi def"), subSequences13.get(1));
        assertEquals(WordSequence.create("ghi def abc"), subSequences13.get(2));
        assertEquals(3, subSequences13.size());

        assertEquals(18, groups.get(18).getId());
        List<WordSequence> subSequences19 = groups.get(18).getSequences();
        assertEquals(WordSequence.create("xyz 123 ghi"), subSequences19.get(0));
        assertEquals(WordSequence.create("xyz 123 ghi def"), subSequences19.get(1));
        assertEquals(WordSequence.create("xyz 123 ghi def abc"), subSequences19.get(2));
        assertEquals(3, subSequences19.size());
    }
}
