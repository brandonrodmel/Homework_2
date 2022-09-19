import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.SortedMap;

public class Driver {

    public static Polynomial p1, p2;

    public static void main(String[] args) {

        Scanner kB = new Scanner(System.in);

        System.out.print("Enter Polynomial 1: ");
        String polynomial = kB.nextLine();
        p1 = new Polynomial(polynomial);

        System.out.print("Enter Polynomial 2: ");
        polynomial = kB.nextLine();
        p2 = new Polynomial(polynomial);

        int input;

        do {
            System.out.println("\n1. Edit Polynomial 1\n" +
                               "2. Edit Polynomial 2\n" +
                               "3. Add Polynomials\n" +
                               "4. Exit Program");

            System.out.print(">> ");
            input = kB.nextInt();

            if(input == 4) // 4. Exit Program
                System.exit(0);

            switch(input) {
                case 1:
                    editPolynomial(p1);
                    break;
                case 2:
                    editPolynomial(p2);
                    break;
                case 3:
                    addPolynomials();
                    break;
            }

        } while(true);

    }

    private static void editPolynomial(Polynomial p) {

        Scanner kB = new Scanner(System.in);

        do {

            System.out.println("\nPolynomial: " + p.toStringImproved());
            System.out.println("1. Clear\n" +
                    "2. Create\n" +
                    "3. Return");

            System.out.print(">> ");
            int choice = kB.nextInt();

            if(choice == 3) // 3. Return
                break;

            if(choice == 1)
                p.clear();
            else if(choice == 2) {
                System.out.print("Enter Coefficient: ");
                int coefficient = kB.nextInt();
                System.out.print("Enter Exponent: ");
                int exponent = kB.nextInt();
                p.addTerm(coefficient, exponent);
            }

        } while(true);

    }


    private static void addPolynomials() {

        Polynomial p = new Polynomial(p1);
        p.add(p2);
        System.out.println("\n"+p1 + " + " + p2 + " = " + p);

    }


}
