/**
 * Polynomial.java : JUnit test for Polynomial ADT implementation.
 * Tests are organized in the strategic development process for the class only,
 * running the entire JUnit Tester does not guarantee order!
 *
 * Assuming that Term works and has no errors (make sure all TermTest tests pass!),
 * testing relies on building Term constructors, equals(), etc.
 * Make sure to build getNumTerms() first (base implementation off of the
 * LinkedList size() method, will avoid more potential errors). Same idea for getTerm()
 *
 * DO NOT CHANGE ANY CODE HERE! THIS WILL BE USED FOR GRADING! Definitely use this
 * as a guide to help you program Polynomial and account for all situations. You can
 * comment code out (otherwise your code may not compile as you build the class!)
 *  Exception to changes: Extra Credit at bottom of this file
 *
 * @author  Nery Chapeton-Lamas <nchapetonLamas@miracosta.edu>
 * @version 1.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class PolynomialTest {

    //TERMS + POLYNOMIALS with unique terms (no duplicate terms = terms with same exponents)
    private static final Term[] UNIQUE_TERMS1 = {   new Term(5, 7),
            new Term(5, 9),
            new Term(-2, 2),
            new Term(-4, -3)};
    private static final String[] UNIQUE_POLY1 = {  "5x^7",
            "5x^9 + 5x^7",
            "5x^9 + 5x^7 - 2x^2",
            "5x^9 + 5x^7 - 2x^2 - 4x^-3"};
    private static final int[] UNIQUE_FINAL_ORDER1 = {1, 0, 2, 3};

    private static final Term[] UNIQUE_TERMS2 = {   new Term(15, 5),
            new Term(145, 9),
            new Term(-5, 7)};
    private static final String[] UNIQUE_POLY2 = {  "15x^5",
            "145x^9 + 15x^5",
            "145x^9 - 5x^7 + 15x^5"};
    private static final int[] UNIQUE_FINAL_ORDER2 = {1, 2, 0};

    private static final Term[] UNIQUE_ADD_ORDER = {
            new Term(150, 9),
            new Term(15, 5),
            new Term(-2, 2),
            new Term(-4, -3),
    };


    //TERMS + POLYNOMIALS with unique terms (no duplicate terms = terms with same exponents)
    private static final Term[] DUPLICATE_TERMS = {
            new Term(-12, 11),
            new Term(3, 13),
            new Term(-1, 4),
            new Term(9, 6),
            new Term(52, 8),
            new Term(-88, 10),
            new Term(11, 12),
            new Term(7, 2),
            new Term(-1, -1),
            new Term(4, 1),
            new Term(12, 0),
            new Term(100, 5),
            new Term(100, 10),  //duplicate
            new Term(-10, 0),   //duplicate
            new Term(-1, -1),   //duplicate
            new Term(-2, 13),   //duplicate
            new Term(-50, 8)    //duplicate
    };
    private static final int DUPLICATE_NUM = 5;
    private static final String[] DUPLICATE_POLY = {
            "- 12x^11",
            "3x^13 - 12x^11",
            "3x^13 - 12x^11 - x^4",
            "3x^13 - 12x^11 + 9x^6 - x^4",
            "3x^13 - 12x^11 + 52x^8 + 9x^6 - x^4",
            "3x^13 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4 + 7x^2",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4 + 7x^2 - x^-1",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4 + 7x^2 + 4x - x^-1",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 - x^4 + 7x^2 + 4x + 12 - x^-1",
            "3x^13 + 11x^12 - 12x^11 - 88x^10 + 52x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 12 - x^-1",
            "3x^13 + 11x^12 - 12x^11 + 12x^10 + 52x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 12 - x^-1",
            "3x^13 + 11x^12 - 12x^11 + 12x^10 + 52x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 2 - x^-1",
            "3x^13 + 11x^12 - 12x^11 + 12x^10 + 52x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 2 - 2x^-1",
            "x^13 + 11x^12 - 12x^11 + 12x^10 + 52x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 2 - 2x^-1",
            "x^13 + 11x^12 - 12x^11 + 12x^10 + 2x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 2 - 2x^-1",
    };
    private static final Term[] DUPLICATE_FINAL_ORDER = {
            new Term(1, 13),
            new Term(11, 12),
            new Term(-12, 11),
            new Term(12, 10),
            new Term(2, 8),
            new Term(9, 6),
            new Term(100, 5),
            new Term(-1, 4),
            new Term(7, 2),
            new Term(4, 1),
            new Term(2, 0),
            new Term(-2, -1),
    };


    @Test
    public void testUniqueAddTermTypeBySizeOfList1() {
        Polynomial test = new Polynomial();
        assertEquals("List NOT created as empty list", 0, test.getNumTerms());

        for(int i = 0; i < UNIQUE_TERMS1.length; i++) {
            test.addTerm(UNIQUE_TERMS1[i]);
            assertEquals("Term was NOT added to list of terms", i+1, test.getNumTerms());
        }
    }
    @Test
    public void testUniqueAddTermTypeBySizeOfList2() {
        Polynomial test = new Polynomial();
        assertEquals("List NOT created as empty list", 0, test.getNumTerms());

        for(int i = 0; i < UNIQUE_TERMS2.length; i++) {
            test.addTerm(UNIQUE_TERMS2[i]);
            assertEquals("Term was NOT added to list of terms", i+1, test.getNumTerms());
        }
    }


    @Test
    public void testToStringUnique1() {
        Polynomial test = new Polynomial();
        String expected, actual;
        assertEquals("Empty list of terms INCORRECT", "0", test.toString());

        for(int i = 0; i < UNIQUE_TERMS1.length; i++) {
            test.addTerm(UNIQUE_TERMS1[i]);

            //remove whitespace from Strings, more flexible testing (doesn't depend on whitespace matching)
            expected = UNIQUE_POLY1[i].replaceAll(" ", "");
            actual = test.toString().replaceAll(" ", "");

            assertEquals("Expected and actual polynomial toString DON'T match", expected, actual);
        }
    }

    @Test
    public void testToStringUnique2() {
        Polynomial test = new Polynomial();
        String expected, actual;
        assertEquals("Empty list of terms INCORRECT", "0", test.toString());

        for(int i = 0; i < UNIQUE_TERMS2.length; i++) {
            test.addTerm(UNIQUE_TERMS2[i]);

            //remove whitespace from Strings, more flexible testing (doesn't depend on whitespace matching)
            expected = UNIQUE_POLY2[i].replaceAll(" ", "");
            actual = test.toString().replaceAll(" ", "");

            assertEquals("Expected and actual polynomial toString DON'T match", expected, actual);
        }
    }

    @Test
    public void testUniqueAddTermTypeByFinalPositions1() {
        Term actualOrderedTerm, expectedOrderedTerm;
        int expectedTermIndex;
        //build full polynomial
        Polynomial test = new Polynomial();
        for(Term t : UNIQUE_TERMS1) {
            test.addTerm(t);
        }

        //test final polynomial, each term ending up in correct position
        for(int i = 0; i < UNIQUE_TERMS1.length; i++) {
            actualOrderedTerm = test.getTerm(i);
            expectedTermIndex = UNIQUE_FINAL_ORDER1[i];
            expectedOrderedTerm = UNIQUE_TERMS1[ expectedTermIndex ];

            assertEquals("Expected and actual term DON'T match", expectedOrderedTerm, actualOrderedTerm);
        }
    }

    @Test
    public void testUniqueAddTermTypeByFinalPositions2() {
        Term actualOrderedTerm, expectedOrderedTerm;
        int expectedTermIndex;
        //build full polynomial
        Polynomial test = new Polynomial();
        for(Term t : UNIQUE_TERMS2) {
            test.addTerm(t);
        }

        //test final polynomial, each term ending up in correct position
        for(int i = 0; i < UNIQUE_TERMS2.length; i++) {
            actualOrderedTerm = test.getTerm(i);
            expectedTermIndex = UNIQUE_FINAL_ORDER2[i];
            expectedOrderedTerm = UNIQUE_TERMS2[ expectedTermIndex ];

            assertEquals("Expected and actual term DON'T match", expectedOrderedTerm, actualOrderedTerm);
        }
    }

    @Test
    public void testDuplicateAddTermTypeBySizeOfList() {
        int finalSize = DUPLICATE_TERMS.length - DUPLICATE_NUM;
        Polynomial test = new Polynomial();
        assertEquals("List NOT created as empty list", 0, test.getNumTerms());

        //add unique terms first and test size of list grows
        for(int i = 0; i < finalSize; i++) {
            test.addTerm(DUPLICATE_TERMS[i]);
            assertEquals("Term was NOT added to list of terms", i+1, test.getNumTerms());
        }

        //add duplicate terms and test size does not grow
        for(int i = finalSize; i < DUPLICATE_TERMS.length; i++) {
            test.addTerm(DUPLICATE_TERMS[i]);
            assertEquals("Term was added but DIDN'T combine like terms (matching exponents",
                    finalSize, test.getNumTerms());
        }
    }

    @Test
    public void testDuplicateAddTermTypeByFinalPositions() {
        Term actualOrderedTerm, expectedOrderedTerm;
        //build full polynomial
        Polynomial test = new Polynomial();
        for(Term t : DUPLICATE_TERMS) {
            test.addTerm(t);
        }

        //test final polynomial, each term ending up in correct position
        for(int i = 0; i < DUPLICATE_FINAL_ORDER.length; i++) {
            actualOrderedTerm = test.getTerm(i);
            expectedOrderedTerm = DUPLICATE_FINAL_ORDER[i];

            assertEquals("Expected and actual term DON'T match", expectedOrderedTerm, actualOrderedTerm);
        }
    }

    @Test
    public void testClear() {
        Polynomial test = new Polynomial();

        for(Term t : UNIQUE_TERMS1) {
            test.addTerm(t);
        }
        assertEquals("Size of built polynomial does NOT match expected", UNIQUE_TERMS1.length, test.getNumTerms());

        test.clear();

        assertEquals("Size of built polynomial > 0 (should be empty list!)", 0, test.getNumTerms());
    }

    @Test
    public void testAddPolynomials() {
        Polynomial test1 = new Polynomial(), test2 = new Polynomial();
        Term expectedTerm, actualTerm;

        for(Term t : UNIQUE_TERMS1) {
            test1.addTerm(t);
        }
        for(Term t : UNIQUE_TERMS2) {
            test2.addTerm(t);
        }

        test1.add(test2);

        for(int i = 0; i < test1.getNumTerms(); i++) {
            actualTerm = test1.getTerm(i);
            expectedTerm = UNIQUE_ADD_ORDER[i];

            assertEquals("Expected and actual term DON'T match", expectedTerm, actualTerm);
        }
    }

    @Test
    public void testCopyConstructor() {
        Polynomial original = new Polynomial(), copy;
        Term originalTerm, copyTerm;

        for(Term t : UNIQUE_TERMS1) {
            original.addTerm(t);
        }

        copy = new Polynomial(original);

        assertTrue("Shallow copy of Polynomial object", original != copy);

        for(int i = 0; i < original.getNumTerms(); i++) {
            originalTerm = original.getTerm(i);
            copyTerm = copy.getTerm(i);


            assertTrue("Shallow copy of Term object in Polynomial", originalTerm != copyTerm);

        }
    }

    /** TODO only allowed to change this file for EXTRA CREDIT
     * add tests for:
     * - duplicate add terms by string (not Term objects),
     * - duplicate toString() tests
     * - anything else you can think of testing!
     */
}