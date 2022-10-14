package company;
import java.math.*;


public class RSA {
    
    BigInteger[] generateKey(String input1, String input2){
        BigInteger array[] = new BigInteger[6];
        BigInteger one = new BigInteger("1"); //hardcode 1 into biginteger...

        BigInteger p = new BigInteger(input1);
        BigInteger q = new BigInteger(input2);
        BigInteger n = new BigInteger(p.multiply(q).toString());
        BigInteger t = new BigInteger(p.subtract(one).multiply(q.subtract(one)).toString());
        BigInteger e = new BigInteger("2"); //Must be greater than 1
        BigInteger d = new BigInteger("0");

        long startTime = System.currentTimeMillis();
        System.out.println("Calculating...");
        while(e.compareTo(t) < 0){
            if(t.gcd(e).compareTo(one) == 0){
                d = e.modInverse(t);
                break;
            }
            e = e.add(one);
        }

        array[0] = p;
        array[1] = q;
        array[2] = n;
        array[3] = t;
        array[4] = e;
        array[5] = d;

        return array;
    }

    void generatePrime(int num, int digits){
        System.out.println(num + " numbers " + digits + " digits");
    }
}
