package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {
    private Ewok ewok;

    @BeforeEach
    void setUp() {
        ewok=new Ewok();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void acquire() {
        ewok.acquire();
        assertFalse(ewok.available);
    }

    @Test
    void release() {
        ewok.release();
        assertTrue(ewok.available);
    }
}