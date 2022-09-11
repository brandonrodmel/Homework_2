/**
 * TermTest.java : JUnit test for Term class.  Tests are organized in the strategic
 * development process for the class only, running the entire JUnit Tester does not
 * guarantee order!
 *
 * DO NOT CHANGE ANY CODE HERE! THIS WILL BE USED FOR GRADING! Definitely use this
 * as a guide to help you program Term and account for all situations. You can
 * comment code out (otherwise your code may not compile as you build the class!)
 *
 * @author  Nery Chapeton-Lamas <nchapetonLamas@miracosta.edu>
 * @version 1.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class TermTest {

    //if TERMS array changed, make sure to update COEFFICIENTS and/or EXPONENTS array
    private static final String[] TERMS = {"", "+5", "-3", "+x", "-x",
            "+6x", "-9x", "+x^3", "-x^5", "+x^-2",
            "-x^-7", "+7x^4", "-2x^13", "+25x^-8", "-54x^-17"};
    private static final int[] COEFFICIENTS =   {0, 5, -3, 1, -1, 6, -9, 1, -1, 1, -1, 7, -2, 25, -54};
    private static final int[] EXPONENTS =      {3, 0,  0, 1,  1, 1,  1, 3, 5, -2, -7, 4, 13, -8, -17};

    private static int COMPARE_TO_VALUE = 3; //if this is changed, make sure to change array COMPARE_TO_VALUES
    private static final int[] COMPARE_TO_VALUES = {0, 1, 1, 1, 1, 1, 1, 0, -1, 1, 1, -1, -1, 1, 1};
    private static final int ORIGINAL_EQUALS_TEST = 0;
    private static final int ORIGINAL_BEFORE_TEST = -1;
    //private static final int ORIGINAL_AFTER_TEST = 1; //not used

    @Test
    public void testGetSetCoefficient() {
        Term test = new Term();

        for(int c : COEFFICIENTS) {
            test.setCoefficient(c);
            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
        }
    }

    @Test
    public void testGetSetExponent() {
        Term test = new Term();

        for(int e : EXPONENTS) {
            test.setExponent(e);
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    @Test
    public void testSetAll() {
        Term test = new Term();
        int c, e;

        for(int i = 0; i < EXPONENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test.setAll(c, e);
            assertTrue(test.getCoefficient() == c && test.getExponent() == e);
            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    @Test
    public void testToString() {
        Term test = new Term();
        String expected, actual;
        int c, e;

        for(int i = 0; i < TERMS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];
            expected = TERMS[i];

            test.setAll(c, e);
            actual = test.toString();
            actual = actual.replaceAll(" ", ""); //more flexible test doesnt depend on white space

            assertEquals("Expected and actual toStrings DON'T match", expected, actual);
        }
    }

    @Test
    public void testFullConstructor() {
        Term test;
        int c, e;
        String actual, expected;

        for(int i = 0; i < EXPONENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test = new Term(c, e);

            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    @Test
    public void testDefaultConstructor() {
        Term test = new Term();

        assertEquals("Expected and actual coefficients DON'T match", 1, test.getCoefficient());
        assertEquals("Expected and actual exponents DON'T match", 1, test.getExponent());
    }

    @Test
    public void testCopyConstructor() {
        Term original = new Term(-5, 10);
        Term copy = new Term(original);

        assertEquals("Coefficients in 'copy' does not match 'original'",
                original.getCoefficient(), copy.getCoefficient());
        assertEquals("Exponents in 'copy' does not match 'original'",
                original.getExponent(), copy.getExponent());
        assertTrue("Reference is not a unique memory address (shallow copy)",
                original != copy);
    }

    @Test
    public void testStringConstructor() {
        Term test;
        int c, e;
        String t;

        //omitting first test in array with "" since that will throw error!
        for(int i = 1; i < TERMS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];
            t = TERMS[i];

            test = new Term(t);

            assertEquals("Expected and actual coefficients DON'T match", c, test.getCoefficient());
            assertEquals("Expected and actual exponents DON'T match", e, test.getExponent());
        }
    }

    @Test
    public void testEqualsTrue() {
        Term test1, test2;
        int c, e;

        for(int i = 0; i < COEFFICIENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test1 = new Term(c, e);
            test2 = new Term(c, e);

            assertEquals("Expected and actual should be TRUE for equals", test1, test2);
        }
    }

    @Test
    public void testEqualsFalse() {
        Term test1, test2;
        int c, e;

        for(int i = 1; i < COEFFICIENTS.length; i++) {
            c = COEFFICIENTS[i];
            e = EXPONENTS[i];

            test1 = new Term(c, e);
            test2 = new Term(COEFFICIENTS[i-1], EXPONENTS[i-1]);

            assertNotEquals("Expected and actual should be FALSE for equals", test1, test2);
        }
    }


    @Test
    public void testClone() {
        Term original = new Term(10, -5);
        Term copy = (Term)original.clone();

        assertEquals("Coefficients in 'copy' does not match 'original'",
                original.getCoefficient(), copy.getCoefficient());
        assertEquals("Exponents in 'copy' does not match 'original'",
                original.getExponent(), copy.getExponent());
        assertTrue("Reference is not a unique memory address (shallow copy)",
                original != copy);
    }

    @Test
    public void testCompareTo() {
        Term original = new Term(1, COMPARE_TO_VALUE), test;
        int e, compareResult;

        for(int i = 0; i < EXPONENTS.length; i++) {
            e = EXPONENTS[i];

            test = new Term(1, e);
            compareResult = original.compareTo(test);

            if( COMPARE_TO_VALUES[i] == ORIGINAL_EQUALS_TEST) {
                assertEquals("Expected and actual compareTo result (original exponent EQUALS test exponent) DON'T match",
                        0, compareResult);
            }
            else if ( COMPARE_TO_VALUES[i] == ORIGINAL_BEFORE_TEST) {
                assertTrue("Expected and actual compareTo result (original exponent COMES BEFORE test exponent) DON'T match",
                        compareResult < 0);
            } else {
                assertTrue("Expected and actual compareTo result (original exponent COMES AFTER test exponent) DON'T match",
                        compareResult > 0);
            }

        }
    }

}