package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void get() {
        String str = "someResult";
        future.resolve(str);
        assertEquals(str, future.get());
    }

    @Test
    public void testIsDone() {
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        future = new Future<>();
        assertFalse(future.isDone());
    }

    @Test
    public void testGet() {
        String str = "someResult";
        long l =1000;
        assertNull(future.get(l,TimeUnit.MILLISECONDS));
        future.resolve(str);
        assertEquals(str, future.get(l,TimeUnit.MILLISECONDS));
    }
}
