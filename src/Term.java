import java.util.Objects;

public class Term implements Comparable<Term> {

    private int mExponent;
    private int mCoefficient;

    public Term() {
        mCoefficient = 1;
        mExponent = 1;
    }

    public Term(int coefficient, int exponent) {
        mCoefficient = coefficient;
        mExponent = exponent;
    }

    public Term(Term term) {
        mCoefficient = term.getCoefficient();
        mExponent = term.getExponent();
    }

    public Term(String term) {
        if(!term.contains("x")) { // case: a, +a, -a
            mCoefficient = Integer.parseInt(term);
            mExponent = 0;
        } else if(term.equals("x")) { // case: x
            mCoefficient = 1;
            mExponent = 1;
        } else if(!term.contains("^")) { // case: ax, +ax, -ax, +x, -x
            if(term.charAt(0) == '+' && term.charAt(1) == 'x')
                mCoefficient = 1;
            else if(term.charAt(0) == '-' && term.charAt(1) == 'x')
                mCoefficient = -1;
            else
                mCoefficient = Integer.parseInt(term.substring(0, term.indexOf("x")));
            mExponent = 1;
        } else if(term.charAt(0) == 'x') { // case: x^b
            mCoefficient = 1;
            mExponent = Integer.parseInt(term.substring(2));
        } else { // case: ax^b
            String[] temp = term.split("x");
            if(temp[0].equals("+")) //case: +x^b
                mCoefficient = 1;
            else if(temp[0].equals("-")) // case: -x^b
                mCoefficient = -1;
            else
                mCoefficient = Integer.parseInt(temp[0]);
            mExponent = Integer.parseInt(temp[1].substring(1));
        }
    }

    public int getExponent() {
        return mExponent;
    }

    public void setExponent(int exponent) {
        mExponent = exponent;
    }

    public int getCoefficient() {
        return mCoefficient;
    }

    public void setCoefficient(int coefficient) {
        mCoefficient = coefficient;
    }

    public void setAll(int coefficient, int exponent) {
        setCoefficient(coefficient);
        setExponent(exponent);
    }

    public Term clone() {
        return new Term(mCoefficient, mExponent);
    }

    @Override
    public int compareTo(Term other) { return Integer.compare(this.mExponent, other.getExponent()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return mExponent == term.mExponent && mCoefficient == term.mCoefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mExponent, mCoefficient);
    }

    @Override
    public String toString() {
        String coefficient;
        if(getCoefficient() == 0) {
            coefficient = "";
        } else if(getCoefficient() == 1)
            coefficient = "+";
        else if(getCoefficient() == -1)
            coefficient = "-";
        else
            coefficient = (getCoefficient() > 0) ? "+"+getCoefficient() : getCoefficient()+""; // if coefficient is greater than 0, it will set string to "+" + coefficient
        String exponent = (getExponent() == 1) ? "" : getExponent()+""; // if exponent is equal to 1, it will set the variable to an empty string
        if(getExponent() == 0 || getCoefficient() == 0) // case: a
            return coefficient;
        else if(getExponent() == 1) // case : ax
            return coefficient+"x";
        else // case ax^b
            return coefficient+"x^"+exponent;
    }
}