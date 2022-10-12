package company;
import java.util.HashMap;

public class AES {
    
    //step-by-step printing
    boolean printOpAES = false;
    String roundPrintInitial[] = new String[32];
    String roundPrint[] = new String[80];
    String roundPrintFinal[] = new String[64];

    // 0     1     2     3     4     5     6     7     8     9     a     b     c     d     e     f
    int sbox[][] = {{ 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76 },
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
                    { 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 }};

    int sboxInv[][] = { { 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb },
                        { 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb },
                        { 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e },
                        { 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25 },
                        { 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92 },
                        { 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84 },
                        { 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06 },
                        { 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b },
                        { 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73 },
                        { 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e },
                        { 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b },
                        { 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4 },
                        { 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f },
                        { 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef },
                        { 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61 },
                        { 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d }};

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

    String[] blockToArray(String[][] block){
        String array[] = new String[16];
        int counter = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = 0; ii < 4; ii++){
                array[counter] = block[ii][i];
                counter++;
            }
        }
        return array;
    }

    String sbox(String input, int choice){
        int num = 0;
        if(choice == 1) {
            num = sbox[Integer.parseInt(input.substring(0, 1), 16)][Integer.parseInt(input.substring(1), 16)];
        }
        if(choice == 2) {
            num = sboxInv[Integer.parseInt(input.substring(0, 1), 16)][Integer.parseInt(input.substring(1), 16)];
        }
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

    String xor(String input, String input2){
        String i1 = "000000000000".substring(input.length()) + input;
        String i2 = "000000000000".substring(input2.length()) + input2;
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

    String[] rotWord(String input[]){
        String temp = input[0];
        input[0] = input[1];
        input[1] = input[2];
        input[2] = input[3];
        input[3] = temp;
        return input;
    }

    String[] subWord(String input[]){
        for(int i = 0; i < 4; i++){
            input[i] = "00".substring(sbox(input[i], 1).length()) + sbox(input[i], 1);
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

        
        if(printOpAES)
            System.out.println("\n_____________________Key Schedule_____________________");

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
        return ks;
    }

    String[] subBytes(String roundKey[], int num){
        for(int i = 0; i < roundKey.length; i++){
            roundKey[i] = "00".substring(sbox(roundKey[i], num).length()) + sbox(roundKey[i], num);
        }

        return roundKey;
    }

    String[][] shiftRows(String block[][], int num){

        String temp = "";
        String temp2 = "";

        if(num == 1) { //1=leftshift
            //row1->leftshift1
            temp = block[1][0];
            block[1][0] = block[1][1];
            block[1][1] = block[1][2];
            block[1][2] = block[1][3];
            block[1][3] = temp;

            //row2->leftshift2 or rightshift2
            temp = block[2][0];
            temp2 = block[2][1];
            block[2][0] = block[2][2];
            block[2][2] = temp;
            block[2][1] = block[2][3];
            block[2][3] = temp2;

            //row3-> leftshift3 or rightshift1
            temp = block[3][3];
            block[3][3] = block[3][2];
            block[3][2] = block[3][1];
            block[3][1] = block[3][0];
            block[3][0] = temp;
        }
        else {
            //row1->rightshift1
            temp = block[1][1];
            block[1][1] = block[1][0];
            block[1][0] = block[1][3];
            block[1][3] = block[1][2];
            block[1][2] = temp;

            //row2->leftshift2 or rightshift2
            temp = block[2][0];
            temp2 = block[2][1];
            block[2][0] = block[2][2];
            block[2][2] = temp;
            block[2][1] = block[2][3];
            block[2][3] = temp2;

            //row3-> rightshift3 or leftshift1
            temp = block[3][0];
            block[3][0] = block[3][1];
            block[3][1] = block[3][2];
            block[3][2] = block[3][3];
            block[3][3] = temp;

        }
        return block;
    }

    String mixor(int input1, int input2){ //mix matrix & old state multiplication, then return binary num to mixColumns
        if(input1 == 1){
            return Integer.toBinaryString(input2);
        }
        if(input1 == 2){
            if(input2 < 128){
                return Integer.toBinaryString(input2) + "0";
            }
            else{ 
                return xor(Integer.toBinaryString(input2) + "0", "000100011011");
            }
        }
        String temp;
        if(input1 == 3){
            if(input2 < 128){
                temp = Integer.toBinaryString(input2) + "0";
            }
            else{
                temp = xor(Integer.toBinaryString(input2) + "0", "000100011011");
            }
            return xor(temp, Integer.toBinaryString(input2));
        }
        return "";
    }

    int binaryDoubler(int value){
        String temp;
        if(value < 128){
            temp = Integer.toBinaryString(value) + "0";
        }
        else{ 
            temp = xor(Integer.toBinaryString(value) + "0", "000100011011");
        }
        return Integer.parseInt(temp, 2);
    }

    String mixorInv(int input1, int input2){ //9, 11, 13, 14
        String intToBin = Integer.toBinaryString(input2);
        String temp = "";

        if(input1 == 9){ // (((x * 2) * 2) * 2) + x
            temp = xor(Integer.toBinaryString(binaryDoubler(binaryDoubler(binaryDoubler(input2)))), intToBin);
        }
        if(input1 == 11){ // ((((x * 2) * 2) + x) * 2) + x
            temp = xor(Integer.toBinaryString(binaryDoubler(Integer.parseInt(xor(Integer.toBinaryString(binaryDoubler(binaryDoubler(input2))), intToBin), 2))), intToBin);
        }
        if(input1 == 13){ // ((((x * 2) + x) * 2) * 2) + x
            temp = xor(Integer.toBinaryString(binaryDoubler(binaryDoubler(Integer.parseInt(xor(Integer.toBinaryString(binaryDoubler(input2)), intToBin), 2)))), intToBin);
        }
        if(input1 == 14){ // ((((x * 2) + x) * 2) + x) * 2
            temp = Integer.toBinaryString(binaryDoubler(Integer.parseInt(xor(Integer.toBinaryString(binaryDoubler(Integer.parseInt(xor(Integer.toBinaryString(binaryDoubler(input2)), intToBin), 2))), intToBin), 2)));
        }
        return temp;
    }

    String[] mixColumns(String block[][], int mixer[][]){
        int blockInt[][] = new int[4][4]; //Convert old state byte strings to integers
        String matrix[] = new String[16]; //Final output "matrix" for mixColumns
        boolean inverse = false;

        if(mixer[0][0] == 2){
            inverse = true;
        }

        for(int i = 0; i < 4; i++){ //column-major print
            for(int ii = 0; ii < 4; ii++){
                blockInt[ii][i] = Integer.parseInt(block[i][ii], 16);
            }
        }

        int counter = 0;
        String temp = "000000000000"; //initial xor yields same value
        for(int i = 0; i < 4; i++){ //column-major print
            for(int ii = 0; ii < 4; ii++){
                for(int x = 0; x < 4; x++) {
                    if(inverse) {
                        temp = xor(temp, mixor(mixer[ii][x], blockInt[i][x]));
                    }
                    else{
                        temp = xor(temp, mixorInv(mixer[ii][x], blockInt[i][x]));
                    }
                }
                matrix[counter] = binaryToHex(temp.substring(4));
                counter++;
                temp = "000000000000";
            }
        }
        return matrix;
    }

    void roundPrintCalculate(String array[], String array2[][], int multiplier, int blockStart){ //given round array, perform print preparation
        int counter = 0;
        for(int i = 0; i < 4; i++){
            for(int ii = blockStart; ii < blockStart + 4; ii++){
                array[ii + (counter * multiplier)] = array2[i][ii % 4];
            }
            counter++;
        }
    }

    String[] roundInv(String keyBlock[], String roundKey[]){
        int mixerInv[][] = { { 14, 11, 13, 9 },
                                { 9, 14, 11, 13 },
                                { 13, 9, 14, 11 },
                                { 11, 13, 9, 14 } };

        //roundkey
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 0);

        //invShiftRows
        roundKey = blockToArray(shiftRows(arrayToBlock(roundKey), 2));
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 4);

        //invSubBytes
        roundKey = subBytes(roundKey, 2);
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 8);

        //addRoundKey
        roundKey = xorArray(keyBlock, roundKey);
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 12);

        //invMixColumns
        roundKey = mixColumns(arrayToBlock(roundKey), mixerInv);
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 16);
        
        return roundKey;
    }

    String[] round(String keyBlock[], String roundKey[]){
        String addRoundKey[] = new String[keyBlock.length];
        String mix[];
        String block[][];
        int mixer[][] = { { 2, 3, 1, 1 },
                            { 1, 2, 3, 1 },
                            { 1, 1, 2, 3 },
                            { 3, 1, 1, 2 } };
        
        //roundkey
        roundPrintCalculate(roundPrint, arrayToBlock(roundKey), 20, 0);
        
        //subBytes
        addRoundKey = subBytes(roundKey, 1);
        roundPrintCalculate(roundPrint, arrayToBlock(addRoundKey), 20, 4);

        //shiftRows
        block = arrayToBlock(addRoundKey);
        block = shiftRows(block, 1);
        roundPrintCalculate(roundPrint, block, 20, 8);
        
        //mixcolumns
        mix = mixColumns(block, mixer);
        roundPrintCalculate(roundPrint, arrayToBlock(mix), 20, 12);
        
        //addRoundKey
        addRoundKey = xorArray(mix, keyBlock);
        roundPrintCalculate(roundPrint, arrayToBlock(addRoundKey), 20, 16);

        return addRoundKey;
    }

    String handleInput(String input, String name){

        if(!input.matches("-?[0-9a-fA-F]+")){
            System.out.print("\nOperation Failed. Ensure input is not empty and is in hexadecimal.");
            System.exit(0);
        }

        int len = input.length();
        if(input.length() < 32){
            input = input + "00000000000000000000000000000000".substring(input.length());
            System.out.println(name + " too short, padding with " + (32 - len) + " zero(s) (" + ((32 - len) * 4) + " bits)");
        }
        if(input.length() > 32){
            input = input.substring(0, 32);
            System.out.println(name + " too long, trimming off " + (len - 32) + " character(s) (" + ((len - 32) * 4) + " bits)");
        }
        return input;
    }

    String encrypt(String text, String key){
        text = handleInput(text, "TXT");
        key = handleInput(key, "KEY");    

        String keySchedule[] = keySchedule(key);
        String keyBlock[] = new String[16]; //round key array (16 bytes)
        String textArray[] = new String[text.length() / 2]; //text to array
        String addRoundKey[] = new String[16];

        for(int x = 0; x < text.length(); x = x + 2){
            textArray[x / 2] = text.substring(0 + x, 2 + x).toLowerCase();
        }

        if(printOpAES) {
            System.out.print("\nTXT: ");
            System.out.print(text.toLowerCase());
            System.out.print("\nKEY: ");
            System.out.println(key.toLowerCase());
            System.out.println();
        }
        roundPrintCalculate(roundPrintInitial, arrayToBlock(textArray), 8, 0);
        

        //ROUND KEY INITIAL--------------------------
        for (int ii = 0; ii < 16; ii++) {
            keyBlock[ii] = keySchedule[ii];
        }
        addRoundKey = (xorArray(textArray, keyBlock)); //addRoundKey
        roundPrintCalculate(roundPrintInitial, arrayToBlock(addRoundKey), 8, 4);

        if(printOpAES) {
            System.out.println("    Plaintext        AddRoundKey");
            for (int a = 0; a < roundPrintInitial.length; a++) {
                if (a % 8 == 0) {
                    System.out.print("|");
                }
                System.out.print(" " + roundPrintInitial[a] + " ");
                if ((a + 1) % 4 == 0 && a != 0) {
                    System.out.print("|");
                }
                if ((a + 1) % 8 == 0 && a != 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }

        //ROUND KEY i-------------------------------
        for(int i = 1; i < keySchedule.length / 16 - 1; i++) { //9, 11, or 13 rounds
            if(printOpAES)
                System.out.println("ROUND " + i);
            for (int ii = 0; ii < 16; ii++) { //calculate next key value
                keyBlock[ii] = keySchedule[ii + (16 * i)];
            }

            addRoundKey = round(keyBlock, addRoundKey); //result of mixcolumn xor keyBlock, goes back into next round as addRoundkey

            if(printOpAES) {
                System.out.println("    PrevRound         SubBytes        ShiftRows        MixColumns       AddRoundKey");
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

        //ROUND KEY FINAL----------------------------
        if(printOpAES)
            System.out.println("ROUND " + (keySchedule.length / 16 - 1));
        
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 0);

        //subBytes
        addRoundKey = subBytes(addRoundKey, 1);
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 4);
        
        //shiftRows
        String builder = "";
        for(int i = 0; i < 16; i++){ //array to string
            builder = builder + addRoundKey[i];
        }
        String block[][] = stringToBlock(builder);
        block = shiftRows(block, 1);
        roundPrintCalculate(roundPrintFinal, block, 16, 8);
        
        for (int i = 0; i < 16; i++) { //calculate next key value
            keyBlock[i] = keySchedule[keySchedule.length - 16 + i];
        }
        addRoundKey = xorArray(blockToArray(block), keyBlock);
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 12);
        
        if(printOpAES) {
            System.out.println("    PrevRound         SubBytes        ShiftRows       Ciphertext");
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

        String finalBuilder = "";
        for(int i = 0; i < 16; i++){
            finalBuilder = finalBuilder + addRoundKey[i];
        }
        return finalBuilder;
    }

    String decrypt(String text, String key) { //keys used in reverse order from key schedule
        text = handleInput(text, "TXT");
        key = handleInput(key, "KEY"); 
        
        String keySchedule[] = keySchedule(key);
        String keyBlock[] = new String[16]; //round key array (16 bytes)
        String textArray[] = new String[text.length() / 2];
        String addRoundKey[] = new String[16];

        if(printOpAES) {
            System.out.print("\nTXT: ");
            System.out.print(text.toLowerCase());
            System.out.print("\nKEY: ");
            System.out.println(key.toLowerCase());
            System.out.println();
        }
        for(int x = 0; x < text.length(); x = x + 2){
            textArray[x / 2] = text.substring(0 + x, 2 + x).toLowerCase();
        }
        
        //ROUND INITIAL (Inverse)
        for(int i = 0; i < 16; i++){
            keyBlock[i] = keySchedule[keySchedule.length - 16 + i];
        }
        
        roundPrintCalculate(roundPrintInitial, arrayToBlock(textArray), 8, 0);

        addRoundKey = xorArray(textArray, keyBlock);
        roundPrintCalculate(roundPrintInitial, arrayToBlock(addRoundKey), 8, 4);
        
        if(printOpAES) {
            System.out.println("    Ciphertext       AddRoundKey");
            for (int a = 0; a < roundPrintInitial.length; a++) {
                if (a % 8 == 0) {
                    System.out.print("|");
                }
                System.out.print(" " + roundPrintInitial[a] + " ");
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
        for(int i = 0; i < (keySchedule.length / 16) - 2; i++){
            for(int ii = 0; ii < 16; ii++){
                keyBlock[ii] = keySchedule[keySchedule.length - 32 - (i * 16) + ii];
            }
            addRoundKey = roundInv(keyBlock, addRoundKey);

            if(printOpAES) {
                System.out.println("ROUND " + ((keySchedule.length / 16) - 1 - i));
                System.out.println("    PrevRound       InvShiftRows      InvSubBytes      AddRoundKey     InvMixColumns");
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

        

        //ROUND FINAL (Inverse)
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 0);

        //InvShiftRows
        addRoundKey = blockToArray(shiftRows(arrayToBlock(addRoundKey), 2));
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 4);

        //InvSubBytes
        addRoundKey = subBytes(addRoundKey, 2);
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 8);

        for(int i = 0; i < 16; i++){
            keyBlock[i] = keySchedule[i];
        }
        addRoundKey = xorArray(addRoundKey, keyBlock);
        roundPrintCalculate(roundPrintFinal, arrayToBlock(addRoundKey), 16, 12);

        if(printOpAES) {
            System.out.println("ROUND 1");
            System.out.println("    PrevRound       InvShiftRows      InvSubBytes      Plaintext        ");
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

        String finalBuilder = "";
        for(int i = 0; i < 16; i++){
            finalBuilder = finalBuilder + addRoundKey[i];
        }
        return finalBuilder;
    }

} //end class AES