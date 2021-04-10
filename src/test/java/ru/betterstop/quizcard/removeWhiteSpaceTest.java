package ru.betterstop.quizcard;

import junit.framework.TestCase;
import org.junit.Test;
import ru.betterstop.quizcard.listeners.CheckAnswerListener;

public class removeWhiteSpaceTest extends TestCase{

    @Test
    public void testRun() {
        String expResult = "test";
        String result = CheckAnswerListener.removeWhiteSpace("test");
        assertEquals(expResult, result);

        expResult = "";
        result = CheckAnswerListener.removeWhiteSpace("");
        assertEquals(expResult, result);

        expResult = "";
        result = CheckAnswerListener.removeWhiteSpace("  ");
        assertEquals(expResult, result);

        expResult = "test";
        result = CheckAnswerListener.removeWhiteSpace("    test");
        assertEquals(expResult, result);

        result = CheckAnswerListener.removeWhiteSpace("     test    ");
        assertEquals(expResult, result);

        result = CheckAnswerListener.removeWhiteSpace(" \t  test\n\n   ");
        assertEquals(expResult, result);

        expResult = "test test";
        result = CheckAnswerListener.removeWhiteSpace( "        test    \n test \n          ");
        assertEquals(expResult, result);

        result = CheckAnswerListener.removeWhiteSpace("   \n     test\n test\n");
        assertEquals(expResult, result);

        expResult = "test test 1";
        result = CheckAnswerListener.removeWhiteSpace( "        test    \n test \n       1   ");
        assertEquals(expResult, result);
    }
}
