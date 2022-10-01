package com.company;
import java.util.HashMap;
import java.util.Scanner;

//64-BIT ENCRYPTION/DECRYPTION VIA DATA ENCRYPTION STANDARD

public class Main {

    //Toggle print operations
    private static boolean printOp = false;

    private static class DES {

        //initial permutation
        int IP[] = { 58, 50, 42, 34, 26, 18, 10, 2,
                     60, 52, 44, 36, 28, 20, 12, 4,
                     62, 54, 46, 38, 30, 22, 14, 6,
                     64, 56, 48, 40, 32, 24, 16, 8,
                     57, 49, 41, 33, 25, 17, 9,  1,
                     59, 51, 43, 35, 27, 19, 11, 3,
                     61, 53, 45, 37, 29, 21, 13, 5,
                     63, 55, 47, 39, 31, 23, 15, 7 };

        //expansion permutation
        int E[] = { 32, 1,  2,  3,  4,  5,  4,  5,  6,  7,  8,  9,
                    8,  9,  10, 11, 12, 13, 12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };

        //permuted choice 1
        int PC1[] = { 57, 49, 41, 33, 25, 17, 9,
                      1,  58, 50, 42, 34, 26, 18,
                      10, 2,  59, 51, 43, 35, 27,
                      19, 11, 3,  60, 52, 44, 36,
                      63, 55, 47, 39, 31, 23, 15,
                      7,  62, 54, 46, 38, 30, 22,
                      14, 6,  61, 53, 45, 37, 29,
                      21, 13, 5,  28, 20, 12, 4  };


        //shift table
        int shift[] = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

        //permuted choice 2
        int PC2[] = { 14, 17, 11, 24, 1, 5, 3, 28,
                      15, 6, 21, 10, 23, 19, 12, 4,
                      26, 8, 16, 7, 27, 20, 13, 2,
                      41, 52, 31, 37, 47, 55, 30, 40,
                      51, 45, 33, 48, 44, 49, 39, 56,
                      34, 53, 46, 42, 50, 36, 29, 32 };

        /* s-box permutation (3D array representation)
           Sn  0000 0001 0010 0011 ...
           00  ---- ---- ---- ---- ...
           01  ---- ---- ---- ---- ...
           10  ---- ---- ---- ---- ...
           11  ---- ---- ---- ---- ...
        */
        int S[][][] = { { { 14, 4, 13, 1, 2, 15, 11, 18, 3, 10, 6, 12, 5, 9, 0, 7 }, //S1
                          { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                          { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                          { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
                        { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 }, //S2
                          { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                          { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                          { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
                        { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, //S3
                          { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                          { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                          { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
                        { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, //S4
                          { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                          { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                          { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
                        { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, //S5
                          { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                          { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                          { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
                        { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, //S6
                          { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                          { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                          { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
                        { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, //S7
                          { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                          { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                          { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
                        { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, //S8
                          { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                          { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                          { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } },
        };

        //p-box permutation
        int P[] = { 16, 7,  20, 21, 29, 12, 28, 17,
                    1,  15, 23, 26, 5,  18, 31, 10,
                    2,  8,  24, 14, 32, 27, 3,  9,
                    19, 13, 30, 6,  22, 11, 4,  25 };

        //final permutation
        int FP[] = { 40, 8, 48, 16, 56, 24, 64, 32,
                     39, 7, 47, 15, 55, 23, 63, 31,
                     38, 6, 46, 14, 54, 22, 62, 30,
                     37, 5, 45, 13, 53, 21, 61, 29,
                     36, 4, 44, 12, 52, 20, 60, 28,
                     35, 3, 43, 11, 51, 19, 59, 27,
                     34, 2, 42, 10, 50, 18, 58, 26,
                     33, 1, 41, 9,  49, 17, 57, 25 };


        //ASCII characters to 64-bit binary
        String asciiToBinary(String s) {
            byte[] bytes = s.getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes) {
                int val = b;
                for (int i = 0; i < 8; i++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
                //binary.append(' ');
            }
            //System.out.println("'" + s + "' to binary: " + binary);
            return binary + "";
        }

        //64-bit binary to ASCII character
        String binaryToAscii(String bin){
            String input = bin;
            int counter = 8;
            int value = 0;
            String output = "";
            for(int i = 0; i < input.length(); i++){
                if(counter > 0){
                    if(input.charAt(i) == '1') {
                        value = value + (int) Math.pow(2, counter - 1);
                    }
                    counter--;
                }
                if(counter == 0) {
                    counter = 8;
                    output = output + (char) value;
                    value = 0;
                }
                //System.out.println("Value:" + value + "Counter:" + counter);
            }
            return output;
        }

        //Hexadecimal to binary
        String hexToBinary(String input){
            String output = "";
            input = input.toUpperCase();

            HashMap<Character, String> hashMap = new HashMap<Character, String>();
            hashMap.put('0', "0000");
            hashMap.put('1', "0001");
            hashMap.put('2', "0010");
            hashMap.put('3', "0011");
            hashMap.put('4', "0100");
            hashMap.put('5', "0101");
            hashMap.put('6', "0110");
            hashMap.put('7', "0111");
            hashMap.put('8', "1000");
            hashMap.put('9', "1001");
            hashMap.put('A', "1010");
            hashMap.put('B', "1011");
            hashMap.put('C', "1100");
            hashMap.put('D', "1101");
            hashMap.put('E', "1110");
            hashMap.put('F', "1111");

            char c;
            for(int i = 0; i < input.length(); i++) {
                c = input.charAt(i);
                if(hashMap.containsKey(c)){
                    output = output + hashMap.get(c);
                }
                else{
                    output = "Invalid string.";
                    return output;
                }
            }
            return output;
        }

        //Binary to hexadecimal
        String binaryToHex(String input){
            int n = input.length() / 4;
            input = Long.toHexString(Long.parseUnsignedLong(input, 2));
            while (input.length() < n) {
                input = "0" + input;
            }
            return input.toUpperCase();
        }

        //Calculates transposition given a permutation table
        String transpose(String input, int table[]){
            input = hexToBinary(input);
            String output = "";

            for(int i = 0; i < table.length; i++){
                output = output + input.charAt(table[i] - 1);
            }
            output = binaryToHex(output);

            return output;
        }

        //Bit shift operation for subkey generation by round
        String shifter(String input, int bits){

            input = hexToBinary(input);
            int len = input.length();
            int array[] = new int[len];
            int x = 1;
            while(x < bits + 1){
                array[len - bits + x - 1] = x;
                x++;
            }

            for(int i = 0 + bits; i < len; i++){
                array[i - bits] = i + 1;
            }
            for(int ii = 0; ii < array.length; ii++){
                //System.out.print(array[ii] + " ");
            }
            //System.out.println(input); //CHECK BIT SHIFT
            input = binaryToHex(input);

            input = transpose(input, array);
            //System.out.println(hexToBinary(input)); // CHECK BIT SHIFT
            //System.out.println(input + "   | " + bits + " bit(s) shifted");
            return input;
        }

        //Exclusive-or operation on two given inputs
        String xor(String input, String input2){
            String i1 = hexToBinary(input);
            String i2 = hexToBinary(input2);

            String builder = "";

            for(int i = 0; i < i1.length(); i++){
                if(i1.charAt(i) == i2.charAt(i)){
                    builder = builder + "0";
                }
                else{
                    builder = builder + "1";
                }
            }

            //System.out.println(i1 + "\n" + i2);
            //System.out.println(builder);
            builder = binaryToHex(builder);
            return builder;
        }

        /*
        [x][][] = num, det by each 6-bit group
        [][x][] = row, det by bit 1 & 6 of 6-bit group
        [][][x] = col, det by bit 2 - 5 of 6-bit group
         */
        //Substitution box permutation
        String sbox(String input, int sbox[][][]){
            String builder = "";
            String sixbit = "";
            input = hexToBinary(input);

            int num, row, col;
            for(int i = 0; i < 48; i = i + 6){
                sixbit = input.substring(i, i + 6);
                num = i / 6;
                row = Integer.parseInt(sixbit.charAt(0) + "" + sixbit.charAt(5), 2);
                col = Integer.parseInt(sixbit.substring(1, 5), 2);
                builder = builder + Integer.toHexString(sbox[num][row][col]);
            }

            return builder.toUpperCase();
        }

        /*
         - Calculate subkey (48 bit)
            - Apply PC1 (64 -> 56 bit)
            - Shift split key (56 -> [x2]28 bit) and combine
            - Apply PC2 (56 -> 48 bit)
         */
        String[] keySchedule(String key){
            String keySchedule[] = new String[16];

            key = transpose(key, PC1);
            for(int i = 0; i < 16; i++){
                key = shifter(key.substring(0, key.length()/2), shift[i]) + shifter(key.substring(key.length()/2, key.length()), shift[i]);
                keySchedule[i] = transpose(key, PC2);
                //System.out.println("Round " + (i + 1) + ": " + keySchedule[i]);
            }
            return keySchedule;
        }

        /*
        - Determine L(control) & R(function) block (32 bit)
         - Expansion of R-block (32 -> 48 bit)
         - subkey XOR expanded R-block
         - S-box
         - P-box
         */
        //Performs all Feistel Function operations by round
        String feistel(String input, String key, int round){
            String leftBlock = input.substring(0, 8);
            String rightBlock = input.substring(8, 16);
            String exp = rightBlock;
            String output;

            exp = transpose(exp, E);
            exp = xor(exp, key);
            exp = sbox(exp, S);
            exp = transpose(exp, P);
            leftBlock = xor(leftBlock, exp);

            output = rightBlock + leftBlock;
            //System.out.println(output);
            return output;
        }

        //Encryption function
        String encrypt(String plaintext, String key){

            String ciphertext = transpose(plaintext, IP);

            String[] keySchedule = keySchedule(key);
            for(int i = 0; i < 16; i++){
                //System.out.print("Round " + (i + 1) + ": ");
                ciphertext = feistel(ciphertext, keySchedule[i], i);
            }
            ciphertext = ciphertext.substring(8, 16) + ciphertext.substring(0, 8);
            ciphertext = transpose(ciphertext, FP);

            return ciphertext;
        }

        //Decryption function
        String decrypt(String ciphertext, String key){
            String plaintext = transpose(ciphertext, IP);

            String[] keySchedule = keySchedule(key);
            for(int i = 15; i > -1; i--){
                //System.out.print("Round " + (i + 1) + ": ");
                plaintext = feistel(plaintext, keySchedule[i], 15 - i);
            }
            plaintext = plaintext.substring(8, 16) + plaintext.substring(0, 8);
            plaintext = transpose(plaintext, FP);
            return plaintext;
        }

        //3DES encryption function
        String encryptTripleDES(String plaintext, String key, String key2, String key3){
            String ciphertext = encrypt(plaintext, key);
            ciphertext = decrypt(ciphertext, key2);
            ciphertext = encrypt(ciphertext, key3);

            return ciphertext;
        }

        //3DES decryption function
        String decryptTripleDES(String ciphertext, String key, String key2, String key3){
            String plaintext = decrypt(ciphertext, key3);
            plaintext = encrypt(plaintext, key2);
            plaintext = decrypt(plaintext, key);

            return plaintext;
        }
    }

    public static void main(String[] args) {
        //64-bit hexadecimal
        String text = "4D61646973656E53";
        String key =  "4348414D50494F4E";

        //For 3DES
        String key2 = "4C4F4E47484F524E";
        String key3 = "6669726562616C6C";

        DES des = new DES();
        try {
            String ciphertext = des.encrypt(text, key);

            System.out.println("[DES]");
            System.out.println("  [1][FILE]\n  [2][MANUAL]");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input == 1) {
                System.out.println("            Hex-------------   Ascii---");
                System.out.println("Plaintext:  " + text + "   " + des.binaryToAscii(des.hexToBinary(text)));
                System.out.println("Key:        " + key + "   " + des.binaryToAscii(des.hexToBinary(key)));
                System.out.println("Key2:       " + key2 + "   " + des.binaryToAscii(des.hexToBinary(key2)));
                System.out.println("Key3:       " + key3 + "   " + des.binaryToAscii(des.hexToBinary(key3)) + "\n");

                System.out.println("DES\nEncrypted:  " + ciphertext + "   " + des.binaryToAscii(des.hexToBinary(ciphertext)));
                String plaintext = des.decrypt(ciphertext, key);
                System.out.println("Decrypted:  " + plaintext + "   " + des.binaryToAscii(des.hexToBinary(plaintext)) + "\n");

                String ciphertextDES = des.encryptTripleDES(text, key, key2, key3);
                System.out.println("3DES\nEncrypted:  " + ciphertextDES + "   " + des.binaryToAscii(des.hexToBinary(ciphertextDES)));
                String plaintextDES = des.decryptTripleDES(ciphertextDES, key, key2, key3);
                System.out.println("Decrypted:  " + plaintextDES + "   " + des.binaryToAscii(des.hexToBinary(plaintextDES)));
            }
            if(input == 2){
                Scanner c = new Scanner(System.in);
                System.out.println("     [MANUAL]");
                System.out.println("       [1][DES]\n       [2][3DES]");
                int input1 = c.nextInt();

                if(input1 == 1) {
                    Scanner c2 = new Scanner(System.in);
                    System.out.println("          [DES]");
                    System.out.println("            [1][ENCRYPT]\n            [2][DECRYPT]");
                    int input11 = c2.nextInt();
                    System.out.println("64BIT[________________]");
                    System.out.print("[TXT] ");
                    Scanner sc2 = new Scanner(System.in);
                    String input2 = sc2.nextLine();
                    System.out.print("[KEY] ");
                    String input3 = sc2.nextLine();
                    if(input11 == 1) {
                        System.out.println(des.encrypt(input2, input3));
                    }
                    if(input11 == 2){
                        System.out.println(des.decrypt(input2, input3));
                    }
                }
                if(input1 == 2){
                    Scanner c3 = new Scanner(System.in);
                    System.out.println("          [3DES]");
                    System.out.println("            [1][ENCRYPT]\n            [2][DECRYPT]");

                    int input11 = c3.nextInt();
                    System.out.println("64BIT [________________]");
                    System.out.print("[TEXT] ");
                    Scanner sc2 = new Scanner(System.in);
                    String input2 = sc2.nextLine();
                    System.out.print("[KEY1] ");
                    String input3 = sc2.nextLine();
                    System.out.print("[KEY2] ");
                    String input4 = sc2.nextLine();
                    System.out.print("[KEY3] ");
                    String input5 = sc2.nextLine();

                    if(input11 == 1) {
                        System.out.println(des.encryptTripleDES(input2, input3, input4, input5));
                    }
                    if(input11 == 2){
                        System.out.println(des.decryptTripleDES(input2, input3, input4, input5));
                    }
                }
            }
        }
        catch(StringIndexOutOfBoundsException exception) {
            System.out.println("Operation failed: Ensure all input is in 64-bit hexadecimal (16 characters, [1-9a-fA-F]).");
        }
    }

}
