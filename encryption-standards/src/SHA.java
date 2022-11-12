package company;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SHA {

    String h[] = { "6a09e667", "bb67ae85", "3c6ef372", "a54ff53a", "510e527f", "9b05688c", "1f83d9ab", "5be0cd19" };

    int k[] = { 0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2 };

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

    String binaryToHex(String input){
        int n = input.length() / 4;
        input = Long.toHexString(Long.parseUnsignedLong(input, 2));
        while (input.length() < n) {
            input = "0" + input;
        }
        return input.toUpperCase();
    }

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

    String rightRotate(String s, int num){
        s = s.substring(s.length() - num) + s.substring(0, s.length() - num);
        return s;
    }

    String rightShift(String s, int num){
        String builder = "";
        for(int i = 0; i < num; i++){
            builder = builder + "0";
        }
        s = builder + s.substring(0, s.length() - num);
        return s;
    }

    String xor(String input, String input2){
        String i1 = input;
        String i2 = input2;

        String builder = "";

        for(int i = 0; i < i1.length(); i++){
            if(i1.charAt(i) == i2.charAt(i)){
                builder = builder + "0";
            }
            else{
                builder = builder + "1";
            }
        }
        return builder;
    }

    String and(String input, String input2){
        String i1 = input;
        String i2 = input2;

        String builder = "";

        for(int i = 0; i < i1.length(); i++){
            if(i1.charAt(i) == '1' && i2.charAt(i) == '1'){
                builder = builder + "1";
            }
            else{
                builder = builder + "0";
            }
        }
        return builder;
    }

    String not(String input){

        String builder = "";

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '1'){
                builder = builder + "0";
            }
            else{
                builder = builder + "1";
            }
        }
        return builder;
    }

    String padding(String code){
        int len = code.length();

        int k = 0;
        if(len % 512 > 447){
            k = 1024 - 65 - (len % 512);
        }
        else{
            k = 512 - 65 - ((len) % 512);
        }

        code = code + "1";

        for(int i = 0; i < k; i++) {
            code = code + "0";
        }

        int len2 = Integer.toBinaryString(len).length();
        for(int i = 0; i < 64 - len2; i++){
            code = code + "0";
        }
        code = code + Integer.toBinaryString(len);

        return code;
    }

    String[] messageSchedule(String code, String[] cs){
        String s0;
        String s1;
        String temp;
        String ms[] = new String[72];

        for(int y = 0; y < 8; y++){
            ms[y+64] = cs[y];
        }

        for(int i = 0; i < 64; i++) {
            if(i < 16)
                ms[i] = code.substring(i * 32, (i + 1) * 32);
            else
                ms[i] = "00000000000000000000000000000000";

            //System.out.println(i + ": " + ms[i]);
        }

        for(int ii = 16; ii < 64; ii++){
            s0 = xor(xor((rightRotate(ms[ii - 15], 7)), (rightRotate(ms[ii - 15], 18))), rightShift(ms[ii - 15], 3));
            s1 = xor(xor((rightRotate(ms[ii - 2], 17)), (rightRotate(ms[ii - 2], 19))), rightShift(ms[ii - 2], 10));
            ms[ii] = Long.toBinaryString((Long.parseLong(ms[ii - 16], 2) + Long.parseLong(s0, 2) + Long.parseLong(ms[ii - 7], 2) + Long.parseLong(s1, 2)) % (long)Math.pow(2,32));

            if(ms[ii].length() < 32) {
                temp = ms[ii];
                while(temp.length() < 32) {
                    temp = "0" + temp;
                }
                ms[ii] = temp;
            }
            //System.out.println(ii + ": " + ms[ii]);
        }

        return ms;
    }

    String[] compression(String[] ms){

        String a = hexToBinary(ms[64]);
            a = "00000000000000000000000000000000".substring(a.length()) + a;
        String b = hexToBinary(ms[65]);
            b = "00000000000000000000000000000000".substring(b.length()) + b;
        String c = hexToBinary(ms[66]);
            c = "00000000000000000000000000000000".substring(c.length()) + c;
        String d = hexToBinary(ms[67]);
            d = "00000000000000000000000000000000".substring(d.length()) + d;
        String e = hexToBinary(ms[68]);
            e = "00000000000000000000000000000000".substring(e.length()) + e;
        String f = hexToBinary(ms[69]);
            f = "00000000000000000000000000000000".substring(f.length()) + f;
        String g = hexToBinary(ms[70]);
            g = "00000000000000000000000000000000".substring(g.length()) + g;
        String h = hexToBinary(ms[71]);
            h = "00000000000000000000000000000000".substring(h.length()) + h;

        String builder[] = new String[8];

        String s0, s1, ch, temp1, temp2, maj;
        for(int i = 0; i < 64; i++) {
            s1 = xor(xor((rightRotate(e, 6)), (rightRotate(e, 11))), rightRotate(e, 25));
            ch = xor(and(e, f), (and(not(e), g)));
            temp1 = Long.toBinaryString(Long.parseLong(h, 2) + Long.parseLong(s1, 2) + Long.parseLong(ch, 2) + Long.parseLong(Integer.toBinaryString(k[i]), 2) + Long.parseLong(ms[i], 2) % (long)Math.pow(2,32));
            s0 = xor(xor((rightRotate(a, 2)), (rightRotate(a, 13))), rightRotate(a, 22));
            maj = xor(xor(and(a,b), and(a, c)), and(b,c));
            temp2 = Long.toBinaryString((Long.parseLong(s0, 2) + Long.parseLong(maj, 2)) % (long)Math.pow(2,32));

            h = g;
            g = f;
            f = e;
            e = Long.toBinaryString(Long.parseLong(d, 2) + Long.parseLong(temp1, 2));
            d = c;
            c = b;
            b = a;
            a = Long.toBinaryString((Long.parseLong(temp1, 2) + Long.parseLong(temp2, 2)) % (long)Math.pow(2,32));

            builder[0] = a;
            builder[1] = b;
            builder[2] = c;
            builder[3] = d;
            builder[4] = e;
            builder[5] = f;
            builder[6] = g;
            builder[7] = h;

            for(int x = 0; x < 8; x++){
                if(builder[x].length() > 32){
                    builder[x] = builder[x].substring(builder[x].length() - 32);
                }
                while(builder[x].length() < 32){
                    builder[x] = "0" + builder[x];
                }
            }
            a = builder[0];
            b = builder[1];
            c = builder[2];
            d = builder[3];
            e = builder[4];
            f = builder[5];
            g = builder[6];
            h = builder[7];
        }

        long t0, t1, t2, t3, t4, t5, t6, t7;

        t0 = (Long.parseUnsignedLong(ms[64], 16) + Long.parseUnsignedLong(builder[0], 2)) % (long)Math.pow(2,32);
        t1 = (Long.parseUnsignedLong(ms[65], 16) + Long.parseUnsignedLong(builder[1], 2)) % (long)Math.pow(2,32);
        t2 = (Long.parseUnsignedLong(ms[66], 16) + Long.parseUnsignedLong(builder[2], 2)) % (long)Math.pow(2,32);
        t3 = (Long.parseUnsignedLong(ms[67], 16) + Long.parseUnsignedLong(builder[3], 2)) % (long)Math.pow(2,32);
        t4 = (Long.parseUnsignedLong(ms[68], 16) + Long.parseUnsignedLong(builder[4], 2)) % (long)Math.pow(2,32);
        t5 = (Long.parseUnsignedLong(ms[69], 16) + Long.parseUnsignedLong(builder[5], 2)) % (long)Math.pow(2,32);
        t6 = (Long.parseUnsignedLong(ms[70], 16) + Long.parseUnsignedLong(builder[6], 2)) % (long)Math.pow(2,32);
        t7 = (Long.parseUnsignedLong(ms[71], 16) + Long.parseUnsignedLong(builder[7], 2)) % (long)Math.pow(2,32);

        builder[0] = binaryToHex(Long.toBinaryString(t0)).toLowerCase();
        builder[1] = binaryToHex(Long.toBinaryString(t1)).toLowerCase();
        builder[2] = binaryToHex(Long.toBinaryString(t2)).toLowerCase();
        builder[3] = binaryToHex(Long.toBinaryString(t3)).toLowerCase();
        builder[4] = binaryToHex(Long.toBinaryString(t4)).toLowerCase();
        builder[5] = binaryToHex(Long.toBinaryString(t5)).toLowerCase();
        builder[6] = binaryToHex(Long.toBinaryString(t6)).toLowerCase();
        builder[7] = binaryToHex(Long.toBinaryString(t7)).toLowerCase();

        for(int j = 0; j < 8; j++){
            builder[j] = "00000000".substring(builder[j].length()) + builder[j];
        }
        return builder;
    }

    

    String hash(String text){

        String code = asciiToBinary(text); //8 bits per character
        String builder = "";
        String input;
        String cs[] = new String[8]; //hash values into message schedule
        String ms[] = new String[72]; //[0-63] message schedule per chunk (512 bits) [64-71] compression hashes

        //Define initial hash values
        for(int x = 0; x < 8; x++){
            cs[x] = h[x];
        }

        code = padding(code);

        int chunks = code.length() / 512;
        for(int i = 0; i < chunks; i++){ //All for a chunk...
            input = code.substring((512 * i), 512 * (i + 1));
            ms = messageSchedule(input, cs);
            cs = compression(ms); //compression takes in message schedule & previous hashes

            for(int y = 0; y < 8; y++){
                ms[y+64] = cs[y];
            }
        }

        //Builds final hash (h0 + a) + (h1 + b) + ...
        for(int ii = 64; ii < 72; ii++){
            builder = builder + ms[ii];
        }

        return builder.toLowerCase();
    }
}