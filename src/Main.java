//Data Encryption Standard

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //Toggle print operations
    private static boolean printOpDES = false;
    private static boolean printOpAES = true;


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
            plaintext = "0000000000000000".substring(plaintext.length()) + plaintext;
            key = "0000000000000000".substring(key.length()) + key;
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
            ciphertext = "0000000000000000".substring(ciphertext.length()) + ciphertext;
            key = "0000000000000000".substring(key.length()) + key;
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
            plaintext = "0000000000000000".substring(plaintext.length()) + plaintext;
            key = "0000000000000000".substring(key.length()) + key;
            key2 = "0000000000000000".substring(key2.length()) + key2;
            key3 = "0000000000000000".substring(key3.length()) + key3;

            String ciphertext = encrypt(plaintext, key);
            ciphertext = decrypt(ciphertext, key2);
            ciphertext = encrypt(ciphertext, key3);

            return ciphertext;
        }

        //3DES decryption function
        String decryptTripleDES(String ciphertext, String key, String key2, String key3){
            ciphertext = "0000000000000000".substring(ciphertext.length()) + ciphertext;
            key = "0000000000000000".substring(key.length()) + key;
            key2 = "0000000000000000".substring(key2.length()) + key2;
            key3 = "0000000000000000".substring(key3.length()) + key3;

            String plaintext = decrypt(ciphertext, key3);
            plaintext = encrypt(plaintext, key2);
            plaintext = decrypt(plaintext, key);

            return plaintext;
        }
    }

    private static class AES {

        String roundPrint[] = new String[80];

        // 0     1     2     3     4     5     6     7     8     9     a     b     c     d     e     f
        int sbox[][] = { { 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76 },
                { 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0 },
                { 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15 },
                { 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75 },
                { 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84 },
                { 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf },
                { 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8 },
                { 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2 },
                { 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73 },
                { 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb },
                { 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79 },
                { 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08 },
                { 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a },
                { 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e },
                { 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf },
                { 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 } };

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

        String binaryToHex(String input){
            int n = input.length() / 4;
            input = Long.toHexString(Long.parseUnsignedLong(input, 2));
            while (input.length() < n) {
                input = "0" + input;
            }
            return input.toUpperCase();
        }

        String[][] stringToBlock(String input){ //COLUMN-MAJOR
            String block[][] = new String[4][4];
            int counter = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 0; ii < 4; ii++){
                    block[ii][i] = input.substring(0 + counter, 2 + counter);
                    counter = counter + 2;
                }
            }

            for(int i = 0; i < 4; i++){ //column-major print
                for(int ii = 0; ii < 4; ii++){
                    //System.out.print(block[i][ii] + " ");
                }
                //System.out.println();
            }
            return block;
        }

        String[][] arrayToBlock(String input[]){
            String block[][] = new String[4][4];
            int counter = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 0; ii < 4; ii++){
                    block[ii][i] = input[counter];
                    counter = counter + 1;
                }
            }
            return block;
        }

        String blockToString(String[][] block){
            String text = "";
            for(int i = 0; i < 4; i++){
                for(int ii = 0; ii < 4; ii++){
                    text = text + block[i][ii];
                }
            }
            return text;
        }



        String sbox(String input){
            int num = sbox[Integer.parseInt(input.substring(0, 1), 16)][Integer.parseInt(input.substring(1), 16)];

            return Integer.toHexString(num);
        }

        String[] xorArray(String[] input1, String[] input2) { //xor for two arrays
            int len = input1.length;
            String xor[] = new String[len];
            String builder = "";
            for(int i = 0; i < len; i++){ //each of 16 indexes
                for(int ii = 0; ii < 8; ii++){ //each of 8 bits per index (1 byte)
                    if(hexToBinary(input1[i]).charAt(ii) == hexToBinary(input2[i]).charAt(ii)){
                        builder = builder + "0";
                    }
                    else{
                        builder = builder + "1";
                    }
                }
                xor[i] = binaryToHex(builder).toLowerCase();
                builder = "";
            }
            return xor;
        }

        String[] rotWord(String input[]){
            String temp = input[0];
            input[0] = input[1];
            input[1] = input[2];
            input[2] = input[3];
            input[3] = temp;

            //for(int i = 0; i < 4; i++){
            //    System.out.println(input[i]);
            //}

            return input;
        }

        String[] subWord(String input[]){
            for(int i = 0; i < 4; i++){
                input[i] = "00".substring(sbox(input[i]).length()) + sbox(input[i]);
            }

            return input;
        }

        String[] rCon(String input[], int round) {
            String rnd[] = { "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36" }; //10 rounds, hex
            String rcon[] = { rnd[round], "00", "00", "00" };

            return xorArray(input, rcon);
        }


        String[] keySchedule(String key) {
            int len = key.length() / 2; //byte length

            String block[][] = stringToBlock(key.toLowerCase());

            int round, rcon;

            String temp[] = new String[4];
            if(len == 32) { //256
                round = 14;
                rcon = 7;
            }
            else if(len == 24) { //192
                round = 12;
                rcon = 8;
            }
            else { //128
                round = 10;
                rcon = 10;
            }
            String input[] = new String[4];
            String keySchedule[][] = new String[round * 4 + 4][4]; //length 44 for 128bit, 52 for 192bit, 60 for 256bit
            String ks[] = new String[(round + 1) * 16];

            for(int i = 0; i < 4; i++){ //Move column-major block into row-major block
                for(int ii = 0; ii < 4; ii++){
                    keySchedule[i][ii] = block[ii][i];
                    //System.out.println(keySchedule[i][ii]);
                }
            }
            for(int i = 0; i < round; i++){

                for(int s = 0; s < 4; s++){ //assign array w/o using reference space
                    input[s] = keySchedule[i * 4 + 3][s];
                }

                if(i < rcon) {
                    input = rotWord(input);
                    input = subWord(input);
                    input = rCon(input, i);
                }

                else{
                    input = subWord(rotWord(keySchedule[i * 4 + 3]));
                }

                keySchedule[i * 4 + 4] = xorArray(keySchedule[i * 4 + 0], input);
                keySchedule[i * 4 + 5] = xorArray(keySchedule[i * 4 + 1], keySchedule[i * 4 + 4]);
                keySchedule[i * 4 + 6] = xorArray(keySchedule[i * 4 + 2], keySchedule[i * 4 + 5]);
                keySchedule[i * 4 + 7] = xorArray(keySchedule[i * 4 + 3], keySchedule[i * 4 + 6]); //input to next round rotword
            }


            //fix the key issue...

            //System.out.println("Key Schedule (4 bytes per word, 4 words per key)");
            int keyCounter = 0;
            int counter = 0;
            for (int y = 0; y < round * 4 + 4; y++) {
                if (y % 4 == 0) {
                    System.out.print("Key " + (keyCounter + 1) + ": ");
                }
                for (int x = 0; x < 4; x++) {
                    System.out.print(keySchedule[y][x] + " ");
                    ks[counter] = keySchedule[y][x];
                    counter++;
                }
                if ((y + 1) % 4 == 0) {
                    System.out.println();
                    keyCounter++;
                }
            }

            for(int p = 0; p < ks.length; p++){
                //System.out.print(ks[p] + " ");
            }

            return ks;
        }

        String[] subBytes(String roundKey[]){
            for(int i = 0; i < roundKey.length; i++){
                roundKey[i] = "00".substring(sbox(roundKey[i]).length()) + sbox(roundKey[i]);
            }

            return roundKey;
        }

        String[][] shiftRows(String block[][]){

            String temp = "";
            String temp2 = "";
            temp = block[1][0];
            block[1][0] = block[1][1];
            block[1][1] = block[1][2];
            block[1][2] = block[1][3];
            block[1][3] = temp;

            temp = block[2][0];
            temp2 = block[2][1];
            block[2][0] = block[2][2];
            block[2][2] = temp;
            block[2][1] = block[2][3];
            block[2][3] = temp2;

            temp = block[3][3];
            block[3][3] = block[3][2];
            block[3][2] = block[3][1];
            block[3][1] = block[3][0];
            block[3][0] = temp;

            for(int i = 0; i < 4; i++){ //column-major print
                for(int ii = 0; ii < 4; ii++){
                    //System.out.print(block[i][ii] + " ");
                }
                //System.out.println();
            }
            return block;
        }

        String[] mixColumns(String block[][]){
            int mixer[][] = { { 2, 3, 1, 1 },
                    { 1, 2, 3, 1 },
                    { 1, 1, 2, 3 },
                    { 3, 1, 1, 2 } };
            int blockInt[][] = new int[4][4]; //Convert old state byte strings to integers
            String matrix[] = new String[16]; //Final output "matrix" for mixColumns

            for(int i = 0; i < 4; i++){ //column-major print
                for(int ii = 0; ii < 4; ii++){
                    //System.out.print(block[ii][i] + " ");
                    blockInt[ii][i] = Integer.parseInt(block[i][ii], 16);
                }
            }
            /*
            System.out.println();
            for(int i = 0; i < 4; i++){ //column-major print
                for(int ii = 0; ii < 4; ii++){
                    System.out.print(blockInt[ii][i] + " ");
                }
                System.out.println();
            }
            System.out.println();
            */


            int counter = 0;
            String temp = "000000000000"; //initial xor yields same value
            for(int i = 0; i < 4; i++){ //column-major print
                for(int ii = 0; ii < 4; ii++){
                    //System.out.print(block[ii][i] + " ");
                    for(int x = 0; x < 4; x++) {
                        temp = xor(temp, mixor(mixer[ii][x], blockInt[i][x]));
                        //System.out.println(mixer[ii][x] + " * " + blockInt[i][x]);
                        //System.out.println(temp + " temp");

                    }
                    //System.out.print(binaryToHex(temp.substring(4)) + " ");
                    matrix[counter] = binaryToHex(temp.substring(4));
                    counter++;
                    temp = "000000000000";
                }
            }

            for (int r = 0; r < 16; r++) {
                //System.out.print(matrix[r].toLowerCase() + " ");
                if ((r + 1) % 4 == 0 && r != 0) {
                    //System.out.println();
                }
            }

            return matrix;
        }

        String mixor(int input1, int input2){ //mix matrix & old state multiplication, then return binary num to mixColumns
            //System.out.println(input1 + "mixor" + input2);
            if(input1 == 1){
                return Integer.toBinaryString(input2);
            }
            if(input1 == 2){
                if(input2 < 128){
                    return Integer.toBinaryString(input2) + "0";
                }
                else{ //System.out.println("mixor xor");
                    return xor(Integer.toBinaryString(input2) + "0", "000100011011");
                }
            }
            String temp;
            if(input1 == 3){
                if(input2 < 128){
                    temp = Integer.toBinaryString(input2) + "0";
                }
                else{
                    //System.out.println("mixor xor");
                    temp = xor(Integer.toBinaryString(input2) + "0", "000100011011");
                }
                //System.out.println("mixor xor");
                return xor(temp, Integer.toBinaryString(input2));
            }

            return "";
        }

        String xor(String input, String input2){
            String i1 = "000000000000".substring(input.length()) + input;
            String i2 = "000000000000".substring(input2.length()) + input2;
            //System.out.println(i1 + " xor " + i2);
            String builder = "";

            for(int i = 0; i < i1.length(); i++){
                if(i1.charAt(i) == i2.charAt(i)){
                    builder = builder + "0";
                }
                else{
                    builder = builder + "1";
                }
            }
            //System.out.println("equals " + builder);
            //System.out.println(i1);
            //System.out.println(i2);

            //System.out.println(i1 + "\n" + i2);
            //System.out.println(builder);
            //builder = binaryToHex(builder);
            return builder;
        }

        String[] round(String keyBlock[], String roundKey[]){

            String addRoundKey[] = new String[keyBlock.length];
            String builder = "";
            String mix[];

            String blockOut[][] = arrayToBlock(roundKey);

            int multiplier = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 0; ii < 4; ii++){
                    System.out.print(blockOut[i][ii]);
                    roundPrint[ii + (multiplier * 20)] = blockOut[i][ii];
                }
                multiplier++;
                System.out.println();
            }

            //subBytes
            multiplier = 0;
            addRoundKey = subBytes(roundKey);
            for(int i = 0; i < 4; i++){
                for(int ii = 4; ii < 8; ii++){
                    System.out.print(arrayToBlock(addRoundKey)[i][ii % 4]);
                    roundPrint[ii + (multiplier * 20)] = arrayToBlock(addRoundKey)[i][ii % 4];
                }
                multiplier++;
                System.out.println();
            }

            //shiftRows
            for(int i = 0; i < 16; i++){ //array to string
                builder = builder + addRoundKey[i];
            }
            String block[][] = stringToBlock(builder);
            block = shiftRows(block);

            multiplier = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 8; ii < 12; ii++){
                    System.out.print(block[i][ii % 4]);
                    roundPrint[ii + (multiplier * 20)] = block[i][ii % 4];
                }
                multiplier++;
                System.out.println();
            }

            //mixcolumns
            mix = mixColumns(block);
            multiplier = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 12; ii < 16; ii++){
                    System.out.print(arrayToBlock(mix)[i][ii % 4]);
                    roundPrint[ii + (multiplier * 20)] = arrayToBlock(mix)[i][ii % 4];
                }
                multiplier++;
                System.out.println();
            }

            addRoundKey = xorArray(mix, keyBlock);
            multiplier = 0;
            for(int i = 0; i < 4; i++){
                for(int ii = 16; ii < 20; ii++){
                    System.out.print(arrayToBlock(addRoundKey)[i][ii % 4]);
                    roundPrint[ii + (multiplier * 20)] = arrayToBlock(addRoundKey)[i][ii % 4];
                }
                multiplier++;
                System.out.println();
            }

            //System.out.print("\nKey: ");
            for(int j = 0; j < 16; j++){
                //System.out.print(keyBlock[j]);
            }

            return addRoundKey;
        }

        String encrypt(String text, String key){
            String keySchedule[] = keySchedule(key);
            String keyBlock[] = new String[16]; //round key array (16 bytes)
            String textArray[] = new String[text.length() / 2]; //text to array
            String addRoundKey[] = new String[16];

            System.out.print("\nTEXT: ");
            for(int x = 0; x < text.length(); x = x + 2){
                textArray[x / 2] = text.substring(0 + x, 2 + x);
                System.out.print(textArray[x / 2].toLowerCase() + "");
            }

            //ROUND KEY INITIAL
            System.out.println("\nINITIAL ROUND: ");
            for (int ii = 0; ii < 16; ii++) {
                keyBlock[ii] = keySchedule[ii];
                System.out.print(keyBlock[ii] + " ");
            }

            System.out.println("\nROUND KEY INITIAL:");
            String blockOut[][] = arrayToBlock(keyBlock);

            if(printOpAES) {
                for (int h = 0; h < 16; h++) {
                    //System.out.print(keyBlock[h] + "");
                }
                //System.out.print(" | roundkey");
            }
            addRoundKey = (xorArray(textArray, keyBlock)); //addRoundKey
            System.out.println();

            for(int t = 0; t < 16; t++){
                //System.out.print(addRoundKey[t] + "");
            }

            //ROUND KEY i
            for(int i = 1; i < keySchedule.length / 16 - 1; i++) { //9, 11, or 13 rounds
                System.out.println("ROUND " + i + ": ");
                //System.out.println();
                for (int ii = 0; ii < 16; ii++) { //calculate next key value
                    keyBlock[ii] = keySchedule[ii + (16 * i)];
                    //System.out.print(keyBlock[ii]);
                }

                System.out.println("\nKEY TO BLOCK " + i + ":");
                blockOut = arrayToBlock(keyBlock);
                for(int iii = 0; iii < 4; iii++){
                    for(int ii = 0; ii < 4; ii++){
                        System.out.print(blockOut[iii][ii]);
                    }
                    System.out.println();
                }

                addRoundKey = round(keyBlock, addRoundKey); //result of mixcolumn xor keyBlock, goes back into next round as addRoundkey
                //System.out.println("\nNext round input:");
                for(int t = 0; t < 16; t++){
                    //System.out.print(addRoundKey[t] + "");
                }
                //System.out.println();

                System.out.println();
                for(int a = 0; a < roundPrint.length; a++){
                    System.out.print(roundPrint[a] + " ");
                    if((a + 1) % 20 == 0 && a != 0){
                        System.out.println();
                    }
                }

            }


            //ROUND KEY FINAL
            System.out.println("FINAL ROUND(10): ");
            String builder = "";
            String builder2[] = new String[16];
            int counter = 0;
            addRoundKey = subBytes(addRoundKey);

            //shiftRows
            for(int i = 0; i < 16; i++){ //array to string
                builder = builder + addRoundKey[i];
            }
            String block[][] = stringToBlock(builder);
            block = shiftRows(block);

            for(int i = 0; i < 4; i++){
                for(int ii = 0; ii < 4; ii++){
                    builder2[counter] = block[ii][i];
                    //System.out.print(builder2[counter] + " ");
                    counter++;
                }
            }

            for (int i = 0; i < 16; i++) { //calculate next key value
                keyBlock[i] = keySchedule[keySchedule.length - 16 + i];
            }

            addRoundKey = xorArray(builder2, keyBlock);
            String finalBuilder = "";



            System.out.print("\nFINAL: ");
            for(int i = 0; i < 16; i++){
                finalBuilder = finalBuilder + addRoundKey[i];
            }
            return finalBuilder;
        }

        String decrypt(){
            return "";
        }



    }



    public static void main(String[] args) {



        try {
            Scanner cs = new Scanner(System.in);
            System.out.println("|--[ALG]");
            System.out.println("|    [1]DES \n|    [2]AES ");
            System.out.print("|     ");
            int enc = cs.nextInt();

            if(enc == 1) { //DES
                DES des = new DES();
                System.out.println("|------[DES]");
                System.out.println("|        [1]FILE \n|        [2]MANUAL ");
                Scanner sc = new Scanner(System.in);
                System.out.print("|         ");
                int typeInput = sc.nextInt();

                if (typeInput == 1) { //DES/FILE
                    //64-bit hexadecimal
                    String text = "4D61646973656E53";
                    String key =  "4348414D50494F4E";

                    //For 3DES
                    String key2 = "4C4F4E47484F524E";
                    String key3 = "6669726562616C6C";
                    String ciphertext = des.encrypt(text, key);

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
                if (typeInput == 2) { //DES/MANUAL
                    Scanner c = new Scanner(System.in);
                    System.out.println("|----------[MANUAL]");
                    System.out.println("|            [1]DES \n|            [2]3DES ");
                    System.out.print("|              ");
                    int manualInput = c.nextInt();

                    if (manualInput == 1) { //DES/MANUAL/DES
                        Scanner c2 = new Scanner(System.in);
                        System.out.println("|---------------[DES]");
                        System.out.println("|                 [1]ENCRYPT \n|                 [2]DECRYPT ");
                        System.out.print("|                  ");
                        int inputDES = c2.nextInt();
                        if(inputDES == 1){
                            System.out.println("|-------------------[ENCRYPT]");
                        }
                        if(inputDES == 2){
                            System.out.println("|-------------------[DECRYPT]");
                        }
                        System.out.println();
                        System.out.println("64BIT[0123456789abcdef]");
                        System.out.print("[TXT] ");
                        Scanner sc2 = new Scanner(System.in);
                        String input2 = sc2.nextLine();
                        System.out.print("[KEY] ");
                        String input3 = sc2.nextLine();
                        if (inputDES == 1) {
                            System.out.println("[OUT] " + des.encrypt(input2, input3).toLowerCase());
                        }
                        if (inputDES == 2) {
                            System.out.println("[OUT] " + des.decrypt(input2, input3).toLowerCase());
                        }
                    }
                    if (manualInput == 2) { //DES/MANUAL/3DES
                        Scanner c3 = new Scanner(System.in);
                        System.out.println("|---------------[3DES]");
                        System.out.println("|                 [1]ENCRYPT \n|                 [2]DECRYPT ");
                        System.out.print("|                  ");
                        int input3DES = c3.nextInt();
                        if(input3DES == 1){
                            System.out.println("|-------------------[ENCRYPT]");
                        }
                        if(input3DES == 2){
                            System.out.println("|-------------------[DECRYPT]");
                        }
                        System.out.println();

                        System.out.println("64-BIT[0123456789abcdef]");
                        System.out.print("[TEXT] ");
                        Scanner sc2 = new Scanner(System.in);
                        String input2 = sc2.nextLine();
                        System.out.print("[KEY1] ");
                        String input3 = sc2.nextLine();
                        System.out.print("[KEY2] ");
                        String input4 = sc2.nextLine();
                        System.out.print("[KEY3] ");
                        String input5 = sc2.nextLine();

                        if (input3DES == 1) {
                            System.out.println("[OUT+] " + des.encryptTripleDES(input2, input3, input4, input5).toLowerCase());
                        }
                        if (input3DES == 2) {
                            System.out.println("[OUT-] " + des.decryptTripleDES(input2, input3, input4, input5).toLowerCase());
                        }
                    }
                }
            }
            if(enc == 2){ //AES...
                System.out.println("|______________");
                AES aes = new AES();
                String text = "4D41444953454E534B494E4E45523036";
                String key = "5445584153564F4C4C455942414C4C31";
                System.out.println();
                System.out.println(aes.encrypt(text, key));



            }
        }
        //catch(StringIndexOutOfBoundsException exception) {
        //    System.out.println("Operation failed: Ensure all input is in 64-bit hexadecimal (Allowed: [0123456789abcdef])");
        //}
        catch(InputMismatchException exception){
            System.out.println("Operation failed: Invalid input.");
        }
    }


}
