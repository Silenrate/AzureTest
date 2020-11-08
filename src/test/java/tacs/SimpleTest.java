package tacs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleTest {

    @Test
    public void shouldAssert() {
        assertTrue(true);
    }

    @Test
    public void shouldEmpty() {
        List<Integer> integers = new ArrayList<>();
        assertEquals(0, integers.size());
    }
}
