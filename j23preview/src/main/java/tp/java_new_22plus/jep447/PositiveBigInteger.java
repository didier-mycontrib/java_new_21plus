package tp.java_new_22plus.jep447;

import java.math.BigInteger;

public class PositiveBigInteger extends BigInteger {

    public PositiveBigInteger(long value) {
        if (value <= 0L) {
            throw new IllegalArgumentException("non-positive value");
        }
       super(String.valueOf(value));
    }

    static void main(){
        System.out.println("test statement before super in constructor (jep447)");
        System.out.println("PositiveBigInteger inherit builtin BigInteger");
        PositiveBigInteger pbi1 = new PositiveBigInteger(12L); //ok positive
        System.out.println("pbi1="+pbi1);

        try {
            PositiveBigInteger pbi2 = new PositiveBigInteger(-12L); //not accepted negative value
            System.out.println("pbi2=" + pbi2);
        } catch (Exception e) {
            //normal expected exception here
            System.err.println(e.getMessage());
        }
    }
}