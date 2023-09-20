package de.andreasbehnke.digest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class EllipsisRendererTest {

    @Test
    void testRender() {
        // The seven example input texts...
        List<String> input = Arrays.asList(
                "abc def ghi jkl xyz",
                "abc def ghi xyz 123 456",
                "xyz 123 abc def ghi",
                "xyz 123 456 abc ghi def",
                "xyz 123 ghi def abc",
                "999",
                "888");
        Iterator<String> output = new EllipsisRenderer(2,2).render(input).iterator();
        Assertions.assertEquals("abc def ghi jkl xyz", output.next());
        Assertions.assertEquals("... xyz 123 456", output.next());
        Assertions.assertEquals("...", output.next());
        Assertions.assertEquals("... 456 abc ghi def", output.next());
        Assertions.assertEquals("... abc", output.next());
        Assertions.assertEquals("999", output.next());
        Assertions.assertEquals("888", output.next());
    }
}
