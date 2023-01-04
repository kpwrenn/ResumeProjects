import java.util.Arrays;

/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */

public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int numSigDigits;

    /*
     * Default, no-argument constructor -- creates a BigInt that 
     * represents the number 0.
     */
    public BigInt() {
        this.digits = new int[SIZE];
        this.numSigDigits = 1;  // 0 has one sig. digit--the rightmost 0!
    }
    /* 
     *BigInt(int[] arr)
     * 
     * Creates a BigInt off an arr passed in 
     * 
     * returns: the this reference 
     * 
     */
    public BigInt(int[] arr) { 
        numSigDigits = 0;
        if (arr == null || arr.length > SIZE) { 
            throw new IllegalArgumentException();
        }
        digits = new int[SIZE]; 
        int digitsIndex = SIZE - 1;
        for (int i = arr.length - 1; i >= 0; i--) { 
            checkInput(arr[i]);
            digits[digitsIndex] = arr[i]; 
            // numSigDigits++; 
            digitsIndex--;
            // System.out.println(Arrays.toString(digits));
        } 
        // System.out.println(Arrays.toString(digits));
        // int k;
        // for (k = 0; k < digits.length; k++) { 
        //     if (digits[k] != 0) { 
        //         break;
        //     }
        // }
        // if (checkIfOneZero(arr)){
        // numSigDigits -= k;
        // }
        int l;
        for (l = 0; l < digits.length; l++) { 
            if (digits[l] != 0) { 
                break;
            }
        }
        numSigDigits = digits.length - l;
    }

     /* 
     *BigInt(int[] arr)
     * 
     * Creates a BigInt off an int passed in 
     * 
     * returns: the this reference 
     * 
     */
    public BigInt(int n) { 
        numSigDigits = 0;
        if (n < 0) { 
            throw new IllegalArgumentException();
        }
        if (n == 0) { 
            numSigDigits++;
        }
        digits = new int[SIZE]; 
        int digitsIndex = SIZE - 1;
        while (n != 0) { 
            int numToInput = n % 10;
            n = (n - numToInput) / 10;
            digits[digitsIndex] = numToInput; 
            numSigDigits++;
            digitsIndex--; 
        }
    }

     /* 
     *checkInput(int n)
     * 
     * Checks if the input for the individual slots in the digits array are valid
     * 
     * returns: void
     * 
     */
    private void checkInput(int n) { 
        if (n < 0 || n >= 10) { 
            throw new IllegalArgumentException();
        }
    }
     /* 
     *getNumSigDigits()
     * 
     *
     * 
     * returns: an int, the numSigDigits
     * 
     */
    public int getNumSigDigits() { 
        return numSigDigits;
    }

     /* 
     * toString()
     * 
     * Overrides the inherited toString method
     * 
     * returns: a String represented the integer BigInt is trying to represent
     * 
     */
    public String toString() { 
        if (checkIfZeroArray(digits)) { 
            return "0";
        }
        String str = "";
        for (int i = digits.length - numSigDigits; i < digits.length; i++) { 
            str += digits[i] + "";
        } 
        return str;
    }
     /* 
     * checkIfZeroArray(int[] arr)
     * 
     * Checks if the arr is filled with zeros
     * 
     * returns: boolean in regards to the condition above
     * 
     */
    private boolean checkIfZeroArray(int[] arr) { 
        boolean isZeroArray = true;
        for (int i = 0; i < arr.length; i++) { 
            if (arr[i] != 0) { 
                isZeroArray = false;
            }
        }
        return isZeroArray;
    }
     /* 
     * compareTo(BigInt other)
     * 
     * Compares two BigInts
     * 
     * returns: int, returns 1 if this is greater than other,  0 if they are equal and -1 if other is bigger than this
     * 
     */
    public int compareTo(BigInt other) { 
        if (other == null) {
            throw new IllegalArgumentException();
        }
        if (numSigDigits > other.getNumSigDigits()) { 
            return 1;
        }
        else if (numSigDigits < other.getNumSigDigits()) { 
            return -1;
        }
        else { 
            for (int i = digits.length - numSigDigits; i < digits.length; i++) { 
                if (digits[i] > other.digits[i]) { 
                    return 1;
                }
                else if (digits[i] < other.digits[i]) { 
                    return -1;
                }
            }
        }
        return 0;
    }
    /*
     * BigInt add(BigInt other)
     * 
     * Adds two contents of BigInt objects together
     * 
     * returns: a BigInt sum of this and other
     */
    public BigInt add(BigInt other) { 
        int[] added = new int[SIZE];
        if (other == null) { 
            throw new IllegalArgumentException();
        }
        boolean carry = false;
        for (int i = SIZE - 1; i >= 0; i--) { 
            int temp = (digits[i] + other.digits[i]) % 10;
            if (carry) { 
                temp = (digits[i] + other.digits[i] + 1) % 10;
                if (digits[i] + other.digits[i] + 1 >= 10)
                    carry = true;
                else
                    carry = false;
            }
            if (digits[i] + other.digits[i] >= 10) { 
                carry = true;
                // temp--;
            }
            if (carry && i == 0) { 
                throw new ArithmeticException();
            }
            // if (i == digits.length - numSigDigits - 1 && i == other.digits.length - other.numSigDigits - 1)
            // { 
            //     break;
            // }
            
            added[i] = temp;
        }
        // System.out.println(tempNumOfSigDigits);
        // int[] forBigInt = new int[tempNumOfSigDigits];
        // for (int i = added.length - tempNumOfSigDigits; i < added.length - 1; i++) {
        //     forBigInt[i] = added[i];
        // }
        
        
        // System.out.println("This is added: " + Arrays.toString(added));
        
        return new BigInt(added);
    }
     /*
     * BigInt mul(BigInt other)
     * 
     * Multiplies two contents of BigInt objects together
     * 
     * returns: a BigInt product of this and other
     */
    public BigInt mul(BigInt other) { 
        BigInt multiplied = new BigInt();
        int temp = 0;
        int forTheCarry = 0;
        int k = 1;
        if (other == null) { 
            throw new IllegalArgumentException(); 
        }
        for (int i = digits.length - 1; i >= digits.length - numSigDigits; i--) { 
            int[] a = new int[SIZE];
            // a.numSigDigits = 1;
            int aIndex = a.length - k;
            for (int j = other.digits.length - 1; j >= other.digits.length - other.numSigDigits; j--) { 
                // System.out.println("digits[i] is: " + digits[i]);
                // System.out.println("other.digits[j] is: " + other.digits[j]);
                // System.out.println("forTheCarry before making temp is: " + forTheCarry);
                
                temp = ((digits[i] * other.digits[j]) % 10) + forTheCarry; 
                
                // System.out.println("temp is: " + temp);
        
                forTheCarry = ((digits[i] * other.digits[j]) - ((digits[i] * other.digits[j]) % 10)) / 10;
                a[aIndex--] = temp;
                
                // System.out.println("a.digits looks like this: " + a.toString());
                // System.out.println("This iteration of the inner loop is over");
                // System.out.println();
             }
             if (forTheCarry != 0 && aIndex == -1) { 
                throw new ArithmeticException();
             }
             if (forTheCarry != 0) { 
                a[aIndex] = forTheCarry;
                
             }
             BigInt aBigInt = new BigInt(a);
            //  System.out.println("a looks as so: " + aBigInt); 
            //  System.out.println("a.digits looks as so: " + Arrays.toString(aBigInt.digits));
            //  System.out.println("a's numSigDigits is: " + aBigInt.numSigDigits);
             multiplied = multiplied.add(aBigInt);
            //  System.out.println("Multiplied looks as so: " + Arrays.toString(multiplied.digits));
             k++;
            //  System.out.println("The outer loop completed an iteration");
            //  System.out.println();
             forTheCarry = 0;
        }
    
        // for (int i = digits.length - numSigDigits; i < digits.length; i++) { 
        //     for (int j = other.digits.length - other.numSigDigits; j < other.digits.length; j++) { 
        //         temp = ((digits[i] * other.digits[j]) % 10) + forTheCarry; 
        //         multiplied[multipledIndex--] = temp;
                
        //         forTheCarry = ((digits[i] * other.digits[j]) - ((digits[i] * other.digits[j]) % 10)) / 10;
        //     }
        // }  
       return multiplied;
    }
    /* 
     * main(String[] args) 
     * 
     * This is where the program begins execution 
     * 
     * returns: void
     */
    public static void main(String [] args) {
        System.out.println("Unit tests for the BigInt class.");
        System.out.println();

        /* 
         * You should uncomment and run each test--one at a time--
         * after you build the corresponding methods of the class.
         */
        
        System.out.println("Test 1: result should be 7");
        int[] a1 = { 1,2,3,4,5,6,7 };
        BigInt b1 = new BigInt(a1);
        System.out.println(b1.getNumSigDigits());
        System.out.println();
        
        System.out.println("Test 2: result should be 1234567");
        b1 = new BigInt(a1);
        System.out.println(b1);
        System.out.println();
        
        System.out.println("Test 3: result should be 0");
        int[] a2 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        BigInt b2 = new BigInt(a2);
        System.out.println(b2);
        System.out.println();
        
        System.out.println("Test 4: should throw an IllegalArgumentException");
        try {
            int[] a3 = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
            BigInt b3 = new BigInt(a3);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();


        System.out.println("Test 5: result should be 1234567");
        b1 = new BigInt(1234567);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 6: result should be 0");
        b2 = new BigInt(0);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 7: should throw an IllegalArgumentException");
        try {
            BigInt b3 = new BigInt(-4);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 8: result should be 0");
        b1 = new BigInt(12375);
        b2 = new BigInt(12375);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 9: result should be -1");
        b2 = new BigInt(12378);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 10: result should be 1");
        System.out.println(b2.compareTo(b1));
        System.out.println();

        System.out.println("Test 11: result should be 0");
        b1 = new BigInt(0);
        b2 = new BigInt(0);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 12: result should be\n123456789123456789");
        int[] a4 = { 3,6,1,8,2,7,3,6,0,3,6,1,8,2,7,3,6 };
        int[] a5 = { 8,7,2,7,4,0,5,3,0,8,7,2,7,4,0,5,3 };
        BigInt b4 = new BigInt(a4);
        BigInt b5 = new BigInt(a5);
        BigInt sum = b4.add(b5);
        System.out.println(sum);
        // System.out.println(sum.numSigDigits);
        System.out.println();

        System.out.println("Test 13: result should be\n123456789123456789");
        System.out.println(b5.add(b4));
        System.out.println();

        System.out.println("Test 14: result should be\n3141592653598");
        b1 = new BigInt(0);
        int[] a6 = { 3,1,4,1,5,9,2,6,5,3,5,9,8 };
        b2 = new BigInt(a6);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15: result should be\n10000000000000000000");
        int[] a19 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };    // 19 nines!
        b1 = new BigInt(a19);
        // System.out.println(Arrays.toString(b1.digits));
        b2 = new BigInt(1);
        // System.out.println(Arrays.toString(b2.digits));
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 16: should throw an ArithmeticException");
        int[] a20 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };  // 20 nines!
        try {
            b1 = new BigInt(a20);
            System.out.println(b1.add(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 17: result should be 5670");
        b1 = new BigInt(135);
        b2 = new BigInt(42);
        BigInt product = b1.mul(b2);
        System.out.println(product);
        System.out.println();

        System.out.println("Test 18: result should be\n99999999999999999999");
        b1 = new BigInt(a20);   // 20 nines -- see above
        b2 = new BigInt(1);
        System.out.println(b1.mul(b2));
        System.out.println();

        System.out.println("Test 19: should throw an ArithmeticException");
        try {
            b1 = new BigInt(a20);
            b2 = new BigInt(2);
            System.out.println(b1.mul(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();
        
    }
}
