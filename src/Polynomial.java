import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Polynomial {

    // 3x + 7x^2 + 8x^3
    private ArrayList<Term> mPolynomial;

    public Polynomial() {
        mPolynomial = new ArrayList<>();
    }

    public Polynomial(String p) {
        mPolynomial = new ArrayList<>();
        String[] temp = p.split(" "); // splits string at every empty space and puts new strings in array
        for(int i=0; i<temp.length; i++) { // cycles through temp array, adding new terms to mPolynomial arrayList
            if (!temp[i].equals("+") && !temp[i].equals("-")) { // form of a term: ax^b
                boolean negativeCoefficient = (i > 0) && temp[i-1].equals("-"); // checks to see if current Term has a negative coefficient (temp[i-1] refers to the + or - in front of the term)
                boolean negativeExponent = temp[i].charAt(temp[i].length()-2) == '-';
                int a, b;
                if(temp[i].indexOf('x') == -1) { // if term is in form of: a
                    a = Integer.parseInt(temp[i]);
                    b = 0;
                } else {// if term is in form of ax^b || ax || x
                    a = (temp[i].charAt(0) != 'x') ? Integer.parseInt(temp[i].substring(0,1)) : 1;
                    b = (temp[i].charAt(temp[i].length() - 1) != 'x') ? Integer.parseInt(temp[i].substring(temp[i].length() - 1)) : 1;
                }
                if(negativeCoefficient) a = -a;
                if(negativeExponent) b = -b;
                addTerm(a, b);
            }
        }
    }

    public Polynomial(Polynomial p) {
        mPolynomial = new ArrayList<>();
        for(int i=0; i<p.getNumTerms(); i++) {
            addTerm(p.getTerm(i));
        }
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
}