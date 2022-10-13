package company;
import java.math.*;
import java.util.Timer;

public class RSA {
    
    void generateKey(String input1, String input2){
        BigInteger one = new BigInteger("1"); //hardcode 1 into biginteger...
        BigInteger p = new BigInteger(input1);
        BigInteger q = new BigInteger(input2);
        BigInteger n = new BigInteger(p.multiply(q).toString());
        BigInteger t = new BigInteger(p.subtract(one).multiply(q.subtract(one)).toString());
        BigInteger m = new BigInteger("77");

        
        BigInteger e = new BigInteger("2");
        BigInteger d = new BigInteger("0");

        Timer timer = new Timer();
        long startTime = System.currentTimeMillis();
        System.out.println("Calculating...");
        while(e.compareTo(t) < 0){
            if(t.gcd(e).compareTo(one) == 0){
                while(d.compareTo(t) < 0){
                    if(e.multiply(d).mod(t).compareTo(one) == 0){
                        break;
                    }
                    else{
                    d = d.add(one);
                    }
                }
                break;
            }
            e = e.add(one);
        }
        System.out.println("Finished.");
        System.out.println("e = " + e + ", d = " + d);
        System.out.println((double)(System.currentTimeMillis() - startTime) / 1000 + " seconds.");
        System.out.println(p.multiply(q));
        System.out.println("EDmodT=1 >>> (" + e + " x " + d + ") % " + t + " = " + e.multiply(d).mod(t));
    }

    void generatePrime(int num, int digits){
        System.out.println(num + " numbers " + digits + " digits");
    }





}
