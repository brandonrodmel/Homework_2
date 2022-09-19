import java.util.*;

public class Polynomial {

    // 3x + 7x^2 + 8x^3
    private LinkedList<Term> mPolynomial;

    public Polynomial() {
        mPolynomial = new LinkedList<>();
    }

    public Polynomial(Polynomial p) {
        mPolynomial = new LinkedList<>();
        for(int i=0; i<p.getNumTerms(); i++) {
            addTerm(p.getTerm(i));
        }
    }

    public Polynomial(String p) {
        mPolynomial = new LinkedList<>();
        String[] temp = p.split(" "); // splits string at every empty space and puts new strings in array
        for(int i=0; i<temp.length; i++) { // cycles through temp array, adding new terms to mPolynomial LinkedList
            if(!temp[i].equals("+") && !temp[i].equals("-")) { // skips any '+' or '-' signs in the array temp
                // STEP 1: ASSIGN A & B
                int indexOfX = temp[i].indexOf('x');
                int indexOfExponent = temp[i].indexOf('^');
                int indexOfNegativeSign = temp[i].indexOf('-');
                int a, b; // form of terms: ax^b, where a is the coefficient & b is the exponent

                if(indexOfX == -1) { // case: a, a^b (no 'x')
                    if(indexOfExponent == -1) // if term is in the form of a
                        a = Integer.parseInt(temp[i]);
                    else // if term is in the form a^b, b is equal to the value after '^'
                        a = (int) Math.pow(Integer.parseInt(temp[i].substring(0, indexOfExponent)), Integer.parseInt(temp[i].substring(indexOfExponent + 1)));
                    b = 0;
                } else if(indexOfExponent == -1) { // cases: x, -x, ax (no '^')
                    if(indexOfNegativeSign == 0 && indexOfX == 1) // if term is in form of -x (different than -1x), a = -1
                        a = -1;
                    else // if term is in form of x, a is equal to 1, else a is equal to the value before x
                        a = (indexOfX == 0) ? 1: Integer.parseInt(temp[i].substring(0, indexOfX));
                    b = 1;
                } else {// cases: x^b, -x^b, ax^b (both 'x' & '^' are present)
                    if(indexOfNegativeSign == 1 && indexOfX == 2) // if term is in form of -x^b (different than -1x^b), a = -1
                        a = -1;
                    else // if term is in form of x^b, a is equal to 1, else a is equal to the value before x
                        a = (indexOfX == 0) ? 1: Integer.parseInt(temp[i].substring(0, indexOfX));
                    b = Integer.parseInt(temp[i].substring(indexOfExponent+1)); // b is equal to the value after the '^'
                }

                // STEP 2: SIMPLIFY REDUNDANCIES (ex. - -3x == + 3x)
                if(i != 0) { // skip the first term to prevent out of index exceptions
                    boolean negativeSign = (temp[i-1].equals("-")); // if string is in the format - -3x, it negativeSign is true, else false
                    boolean negativeCoefficient = a < 0; // if a is less than 0, negativeCoefficient is true, else false

                    if(negativeSign && negativeCoefficient || (!negativeSign && !negativeCoefficient)) // cases: - -ax^b, + ax^b
                        a = Math.abs(a);
                    else if(negativeSign) // cases: - ax^b, + -ax^b
                        a = -Math.abs(a);
                }
                addTerm(a, b);
            }
        }
    }

    public void addTerm(String term) {
        int indexOfX = term.indexOf('x');
        int indexOfExponent = term.indexOf('^');
        int indexOfNegativeSign = term.indexOf('-');
        int a, b; // form of terms: ax^b, where a is the coefficient & b is the exponent

        if(indexOfX == -1) { // case: a, a^b (no 'x')
            a = Integer.parseInt(term);
            if(indexOfExponent == -1) // if term is in the form of a
                b = 0;
            else // if term is in the form a^b, b is equal to the value after '^'
                b = Integer.parseInt(term.substring(indexOfExponent+1));
        } else if(indexOfExponent == -1) { // cases: x, -x, ax (no '^')
            if(indexOfNegativeSign == 0 && indexOfX == 1) // if term is in form of -x (different than -1x), a = -1
                a = -1;
            else // if term is in form of x, a is equal to 1, else a is equal to the value before x
                a = (indexOfX == 0) ? 1: Integer.parseInt(term.substring(0, indexOfX));
            b = 1;
        } else {// cases: x^b, -x^b, ax^b (both 'x' & '^' are present)
            if(indexOfNegativeSign == 1 && indexOfX == 2) // if term is in form of -x^b (different than -1x^b), a = -1
                a = -1;
            else // if term is in form of x^b, a is equal to 1, else a is equal to the value before x
                a = (indexOfX == 0) ? 1: Integer.parseInt(term.substring(0, indexOfX));
            b = Integer.parseInt(term.substring(indexOfExponent+1)); // b is equal to the value after the '^'
        }
        addTerm(a, b);
    }

    public void addTerm(Term t) { addTerm(t.getCoefficient(), t.getExponent()); }

    public void addTerm(int coefficient, int exponent) {
        mPolynomial.add(new Term(coefficient, exponent));
        checkZeros();
        checkDuplicates();
        sortPolynomial();
    }



    private void checkZeros() {
        for(int i=0; i<mPolynomial.size(); i++)
            if(mPolynomial.get(i).getCoefficient() == 0) {
                mPolynomial.remove(i);
                i--;
            }
    }

    private void checkDuplicates() {
        for(int i=0; i<mPolynomial.size()-1; i++) {
            Term currentTerm = mPolynomial.get(i);
            for(int j=i+1; j<mPolynomial.size(); j++) {
                Term nextTerm = mPolynomial.get(j);
                if(currentTerm.getExponent() == nextTerm.getExponent())  { // IF EXPONENTS ARE EQUAL, THE COEFFICIENT OF nextTerm WILL BE ADDED TO currentTerm AND nextTerm WILL BE REMOVED FROM THE ARRAYLIST
                    currentTerm.setCoefficient(currentTerm.getCoefficient() + nextTerm.getCoefficient());
                    mPolynomial.remove(nextTerm);
                }
            }
        }
    }

    private void sortPolynomial() {
        boolean isSorted = false;
        while(!isSorted) {
            isSorted = true;
            for(int i=0; i<mPolynomial.size()-1; i++) {
                Term currentTerm = mPolynomial.get(i);
                Term nextTerm = mPolynomial.get(i+1);
                if(currentTerm.getExponent() < nextTerm.getExponent()) {
                    Term temp = currentTerm;
                    mPolynomial.set(i, nextTerm);
                    mPolynomial.set(i+1, temp);
                    isSorted = false;
                }
            }
        }
    }

    public int getNumTerms() { return mPolynomial.size(); }

    public Term getTerm(int i) { return mPolynomial.get(i); }

    public void add(Polynomial other) {
        for(int i=0; i<other.getNumTerms(); i++)
            addTerm(other.getTerm(i));
    }

    public void clear() { mPolynomial.clear(); }

    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        if(getNumTerms() != p.getNumTerms())
            return false;
        for(int i=0; i<getNumTerms(); i++)
            if(!p.getTerm(i).equals(p.getTerm(i)))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPolynomial);
    }

    @Override
    public String toString() {
        if(mPolynomial.size() == 0)
            return "0";
        StringBuilder stb = new StringBuilder();
        for(int i=0; i<mPolynomial.size(); i++) {
            if(mPolynomial.get(i).getCoefficient() != 0) { // if coefficient is 0, the term will be skipped and added to the StringBuilder
                String sign = (mPolynomial.get(i).getCoefficient() > 0) ? " + " : " - "; // Determines if coefficient is positive or negative
                String termWithoutSign = mPolynomial.get(i).toString().substring(1);
                if (stb.isEmpty() && mPolynomial.get(i).getCoefficient() > 0) { // will append the term without the sign ONLY IF it is the first term in the string or if it has a positive coefficient
                    stb.append(termWithoutSign); // skips the + or - and only appends term
                }
                else {
                    stb.append(sign).append(termWithoutSign); // puts a space between the + or - and the term
                }
            }
        }
        return stb.toString();
    }

    public String toStringImproved() {
        if(mPolynomial.size() == 0)
            return "0";
        StringBuilder stb = new StringBuilder();
        for(int i=0; i<mPolynomial.size(); i++) {
            Term term = mPolynomial.get(i);
            boolean negativeCoefficient = term.getCoefficient() < 0;
            String termWithoutSign = term.toString().substring(1); // if term is in form: -ax^b, it will put it will remove the negative sign
            String sign = (negativeCoefficient) ? "-" : "";
            if(i != 0) {
                if(negativeCoefficient)
                    stb.append(" - ").append(termWithoutSign);
                else
                    stb.append(" + ").append(termWithoutSign);
            } else
                stb.append(sign).append(termWithoutSign);
        }

        return stb.toString();
    }
}