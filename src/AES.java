package company;
import java.util.HashMap;

public class AES {

    boolean printOpAES = true;
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
        return input.toLowerCase();
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
                block[ii][i] = input[counter].substring(0, 2);
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
                if(printOpAES)
                    System.out.print("Key " + (keyCounter + 1) + ": ");
            }
            for (int x = 0; x < 4; x++) {
                if(printOpAES)
                    System.out.print(keySchedule[y][x] + " ");
                ks[counter] = keySchedule[y][x];
                counter++;
            }
            if ((y + 1) % 4 == 0) {
                if(printOpAES)
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
                //System.out.print(blockOut[i][ii]);
                roundPrint[ii + (multiplier * 20)] = blockOut[i][ii];
            }
            multiplier++;
            //System.out.println();
        }

        //subBytes
        addRoundKey = subBytes(roundKey);
        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 4; ii < 8; ii++){
                //System.out.print(arrayToBlock(addRoundKey)[i][ii % 4]);
                roundPrint[ii + (multiplier * 20)] = arrayToBlock(addRoundKey)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
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
                //System.out.print(block[i][ii % 4]);
                roundPrint[ii + (multiplier * 20)] = block[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
        }

        //mixcolumns
        mix = mixColumns(block);
        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 12; ii < 16; ii++){
                //System.out.print(arrayToBlock(mix)[i][ii % 4]);
                roundPrint[ii + (multiplier * 20)] = arrayToBlock(mix)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
        }

        addRoundKey = xorArray(mix, keyBlock);
        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 16; ii < 20; ii++){
                //System.out.print(arrayToBlock(addRoundKey)[i][ii % 4]);
                roundPrint[ii + (multiplier * 20)] = arrayToBlock(addRoundKey)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
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
        String roundPrintInitial[] = new String[32];


        for(int x = 0; x < text.length(); x = x + 2){
            textArray[x / 2] = text.substring(0 + x, 2 + x).toLowerCase();
        }
        if(printOpAES) {
            System.out.print("\nTEXT: ");
            System.out.print(text.toLowerCase());
            System.out.print("\nKEY: ");
            System.out.println(key.toLowerCase());
        }
        int multiplier = 0;
        for(int t = 0; t < 4; t++){
            for(int tt = 0; tt < 4; tt++) {
                roundPrintInitial[tt + (multiplier * 8)] = arrayToBlock(textArray)[t][tt] + " ";
            }
            multiplier++;
        }

        //ROUND KEY INITIAL
        for (int ii = 0; ii < 16; ii++) {
            keyBlock[ii] = keySchedule[ii];
            //System.out.print(keyBlock[ii] + " ");
        }

        String blockOut[][] = arrayToBlock(keyBlock);

        if(printOpAES) {
            System.out.println("\nINITIAL ROUND ");
            for (int h = 0; h < 16; h++) {
                //System.out.print(keyBlock[h] + "");
            }
            //System.out.print(" | roundkey");
        }
        addRoundKey = (xorArray(textArray, keyBlock)); //addRoundKey

        multiplier = 0;
        for(int t = 0; t < 4; t++){
            for(int tt = 4; tt < 8; tt++) {
                roundPrintInitial[tt + (multiplier * 8)] = arrayToBlock(addRoundKey)[t][tt % 4] + " ";
            }
            multiplier++;
        }
        if(printOpAES) {
            System.out.println("     Plaintext       AddRoundKey");
            for (int a = 0; a < roundPrintInitial.length; a++) {
                if (a % 8 == 0) {
                    System.out.print("|");
                }
                System.out.print(" " + roundPrintInitial[a]);
                if ((a + 1) % 4 == 0 && a != 0) {
                    System.out.print("|");
                }
                if ((a + 1) % 8 == 0 && a != 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }



        //ROUND KEY i
        for(int i = 1; i < keySchedule.length / 16 - 1; i++) { //9, 11, or 13 rounds
            if(printOpAES)
                System.out.println("ROUND " + i);
            //System.out.println();
            for (int ii = 0; ii < 16; ii++) { //calculate next key value
                keyBlock[ii] = keySchedule[ii + (16 * i)];
                //System.out.print(keyBlock[ii]);
            }

            blockOut = arrayToBlock(keyBlock);
            for(int iii = 0; iii < 4; iii++){
                for(int ii = 0; ii < 4; ii++){
                    //System.out.print(blockOut[iii][ii]);
                }
                //System.out.println();
            }

            addRoundKey = round(keyBlock, addRoundKey); //result of mixcolumn xor keyBlock, goes back into next round as addRoundkey
            //System.out.println("\nNext round input:");
            for(int t = 0; t < 16; t++){
                //System.out.print(addRoundKey[t] + "");
            }
            //System.out.println();
            if(printOpAES) {
                System.out.println("     RoundKey         SubBytes        ShiftRows        MixColumns       AddRoundKey");
                for (int a = 0; a < roundPrint.length; a++) {
                    if (a % 20 == 0) {
                        System.out.print("|");
                    }
                    System.out.print(" " + roundPrint[a] + " ");
                    if ((a + 1) % 4 == 0 && a != 0) {
                        System.out.print("|");
                    }
                    if ((a + 1) % 20 == 0 && a != 0) {
                        System.out.println();
                    }
                }
                System.out.println();
            }

        }


        //ROUND KEY FINAL
        String roundPrintFinal[] = new String[64];
        if(printOpAES)
            System.out.println("ROUND " + (keySchedule.length / 16 - 1));
        String builder = "";
        String builder2[] = new String[16];

        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 0; ii < 4; ii++){
                //System.out.print(arrayToBlock(mix)[i][ii % 4]);
                roundPrintFinal[ii + (multiplier * 16)] = arrayToBlock(addRoundKey)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
        }

        int counter = 0;
        addRoundKey = subBytes(addRoundKey);
        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 4; ii < 8; ii++){
                //System.out.print(arrayToBlock(mix)[i][ii % 4]);
                roundPrintFinal[ii + (multiplier * 16)] = arrayToBlock(addRoundKey)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
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
                //System.out.print(arrayToBlock(mix)[i][ii % 4]);
                roundPrintFinal[ii + (multiplier * 16)] = block[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
        }

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

        multiplier = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 12; ii < 16; ii++){
                //System.out.print(arrayToBlock(mix)[i][ii % 4]);
                roundPrintFinal[ii + (multiplier * 16)] = arrayToBlock(addRoundKey)[i][ii % 4];
            }
            multiplier++;
            //System.out.println();
        }
        String finalBuilder = "";

        if(printOpAES) {
            System.out.println("     RoundKey         SubBytes        ShiftRows        AddRoundKey");
            for (int a = 0; a < roundPrintFinal.length; a++) {
                if (a % 16 == 0) {
                    System.out.print("|");
                }
                System.out.print(" " + roundPrintFinal[a] + " ");
                if ((a + 1) % 4 == 0 && a != 0) {
                    System.out.print("|");
                }
                if ((a + 1) % 16 == 0 && a != 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
        if(printOpAES)
            System.out.print("\nFINAL: ");
        for(int i = 0; i < 16; i++){
            finalBuilder = finalBuilder + addRoundKey[i];
        }
        return finalBuilder;
    }

    String decrypt(String text, String key){
        return "coming soon...";
    }



}
