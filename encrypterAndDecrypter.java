/*
Name: Rowan Noel-Rickert
This program encrypts an numeric message using RSA and also decrypts numeric messages.
*/
import java.io.*;
import java.util.*;
import java.math.*;
import java.math.BigInteger;

public class encrypterAndDecrypter {
	private static Scanner in;
	public static void main(String[] args) throws Exception{
		Scanner scanner = new Scanner(System.in);
		String prime1s;
		String prime2s;
		BigInteger n= new BigInteger("1");
		String es;
		String Ms;
		String M2s;
		String ds;
		BigInteger phi= new BigInteger("1");
		BigInteger one = BigInteger.valueOf(1);

		//Gets two primes from user inpur
		System.out.println("Please enter your two prime numbers: ");
		prime1s = scanner.nextLine();
		prime2s = scanner.nextLine();

		//Converts the strings into BigIntegers
		BigInteger prime1= new BigInteger(prime1s);
		BigInteger prime2= new BigInteger(prime2s);

		//Calculates n and phi to be used later
		n = prime1.multiply(prime2);
		phi = (prime1.subtract(one).multiply(prime2.subtract(one)));

		//obtains the encryption exponent from the user and converts the string into BigInts (BigIntegers)
		System.out.print("Please enter encryption exponent: ");
		es = scanner.nextLine();
		BigInteger e= new BigInteger(es);

		//obtains the message to encrypt from user and converts it into BigInts
		System.out.print("Please enter a numeric message: ");
		Ms = scanner.nextLine();
		BigInteger M= new BigInteger(Ms);

		//Encrypts the message and converts it into BigInts
		String enMs = encrypt(M, e, n);
		BigInteger enM= new BigInteger(enMs);

		System.out.println("This is your encrypted message: "+enM);

		//Obtains encrypted message from user as a string and then converts it into BigInts
		System.out.print("Please enter a numeric encrypted message: ");
		M2s = scanner.nextLine();
		BigInteger M2= new BigInteger(M2s);

		//calculates the d value using previously calculated phi and entered e then converts it to BigInt
		ds = calcd(phi, e);
		BigInteger d= new BigInteger(ds);

		//Decrypts the encrypted message using calculated d
		String deMs = encrypt(M2, d, n);
		BigInteger deM = new BigInteger(deMs);

		System.out.println("This is your decrypted encoded message: "+deM);



	}
	//Encrypts string messages by converting them into BigInts and then back to string to return
	public static String encrypt(BigInteger m, BigInteger e, BigInteger n){
		BigInteger result = BigInteger.ONE; // Initialize result as 1
		    BigInteger zero = BigInteger.ZERO;
		    BigInteger two = BigInteger.valueOf(2);

		    // Modular exponentiation loop
		    while (e.compareTo(zero) > 0) {
		        if (e.mod(two).equals(BigInteger.ONE)) {
		            // If the exponent is odd, multiply base with the result
		            result = result.multiply(m).mod(n);
		        }
		        // Square the base and reduce mod n
		        m = m.multiply(m).mod(n);
		        // Divide the exponent by 2
		        e = e.divide(two);
		    }

    return result.toString();
	}

	//calculates the d value for decryption methods
	public static String calcd(BigInteger phi, BigInteger e){
		BigInteger t = BigInteger.ZERO, newT = BigInteger.ONE;
		    BigInteger r = phi, newR = e;
		    BigInteger temp;

		    while (!newR.equals(BigInteger.ZERO)) {
		        BigInteger quotient = r.divide(newR);

		        // Update r and newR
		        temp = newR;
		        newR = r.subtract(quotient.multiply(newR));
		        r = temp;

		        // Update t and newT
		        temp = newT;
		        newT = t.subtract(quotient.multiply(newT));
		        t = temp;
		    }

		    // Ensure t is positive
		    if (t.compareTo(BigInteger.ZERO) < 0) {
		        t = t.add(phi);
		    }

    	return t.toString(); // Return the modular inverse

	}


}