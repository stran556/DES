//Data Encryption Standard
package company;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.*;
import java.util.Timer;

import javax.sound.sampled.SourceDataLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        try {
            Scanner cs = new Scanner(System.in);
            System.out.println("|--[ALG]");
            System.out.println("|    [1]DES \n|    [2]AES \n|    [3]RSA");
            System.out.print("|     ");
            int enc = cs.nextInt();

            if(enc == 1) { //DES...
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
                AES aes = new AES();
                System.out.println("|------[AES]");
                System.out.println("|        [1]FILE \n|        [2]MANUAL ");
                Scanner sc = new Scanner(System.in);
                System.out.print("|         ");
                int typeInput = sc.nextInt();
                if(typeInput == 1) {
                    String text = "MADISENSKINNER06"; //4D41444953454E534B494E4E45523036
                    String key = "TEXASVOLLEYBALL1"; //5445584153564F4C4C455942414C4C31
                    System.out.println();

                    System.out.println("\n________________[FILE]________________");
                    Thread.sleep(50);
                    System.out.println("[TXT] " + text);
                    System.out.println("[KEY] " + key);
                    Thread.sleep(50);
                    String ciphertext = aes.encrypt(text, key);
                    System.out.println("\n_______________[OUTPUT]_______________");
                    Thread.sleep(50);
                    System.out.println("[ENC] " + ciphertext);
                    System.out.println("[DEC] " + aes.decrypt(text, key));
                    Thread.sleep(50);
                    System.out.println();
                }

                if(typeInput == 2){
                    System.out.println("|----------[MANUAL]");
                    System.out.println("|            [1]ENCRYPT \n|            [2]DECRYPT ");
                    System.out.print("|             ");
                    Scanner c = new Scanner(System.in);
                    int manualInput = c.nextInt();
                    if(manualInput == 1){
                        System.out.println("|--------------[ENCRYPT]");
                        System.out.println();
                        System.out.println("128BIT[________________]");
                        System.out.print("[TXT]  ");
                        Scanner sc2 = new Scanner(System.in);
                        String input2 = sc2.nextLine();
                        System.out.print("[KEY]  ");
                        String input3 = sc2.nextLine();
                        System.out.println("[OUT]  " + aes.encrypt(input2, input3));

                    }
                    if(manualInput == 2){
                        
                        System.out.println("|--------------[DECRYPT]");
                        System.out.println();
                        System.out.println("128BIT[HEX_____________________________]");
                        System.out.print("[TXT]  ");
                        Scanner sc2 = new Scanner(System.in);
                        String input2 = sc2.nextLine();
                        System.out.println("128BIT[ASCII___________]");
                        System.out.print("[KEY]  ");
                        String input3 = sc2.nextLine();
                        System.out.println("[OUT]  " + aes.decrypt(input2, input3));
                    }
                }
            }
            if(enc == 3){ //RSA...
                RSA rsa = new RSA();
                BigInteger rsaKeyInfo[] = new BigInteger[3];
                String check = "";
                System.out.println("|------[RSA]");
                try{
                    BufferedReader br = new BufferedReader(new FileReader("importRSA.txt"));
                    check = br.readLine();
                    if(check == null){
                        System.out.println("|         - IMPORT KEY FROM FILE\n|        [2]ENTER KEY MANUALLY\n|        [3]CREATE KEY");
                    }
                    else{
                        System.out.println("|        [1]IMPORT KEY FROM FILE \n|        [2]ENTER KEY MANUALLY\n|        [3]CREATE KEY");
                    }
                }catch(IOException e){
                    System.out.println("Error.");
                }
                Scanner sc = new Scanner(System.in);
                System.out.print("|         ");
                int typeInput = sc.nextInt();

                if(typeInput == 1){ //FILE
                    if(check == null){
                        System.out.println("No data found. Generate key data with [CREATE KEY] and try again.");
                        System.exit(0);
                    }
                    else{
                        Scanner sc12 = new Scanner(System.in);
                        System.out.println("|----------[FILE]");
                        System.out.println("|            [1]ENCRYPT \n|            [2]DECRYPT ");
                        System.out.print("|             ");
                        int fileInput = sc12.nextInt();
                        if(fileInput == 1){
                            Scanner sc2 = new Scanner(System.in);
                            System.out.print("[ENCRYPT] ");
                            String input = sc2.nextLine();

                            System.out.println("\n[CIPHERTEXT] " + rsa.encrypt(input));
                        }
                        if(fileInput == 2){
                            Scanner sc2 = new Scanner(System.in);
                            System.out.print("[DECRYPT] ");
                            String input = sc2.nextLine();

                            System.out.println("\n[PLAINTEXT] " + rsa.decrypt(input));
                        }
                    }
                }

                if(typeInput == 2){ //MANUAL

                }

                if(typeInput == 3){ //CREATE KEY (if values not known)
                    System.out.println("|----------[CREATE KEY]");
                    System.out.println("|            [1]INPUT PRIMES TO GENERATE KEY\n|            [2]GENERATE AND IMPORT TWO PRIMES"); 
                    Scanner sc3 = new Scanner(System.in);
                    System.out.print("|             ");
                    int keyInput = sc3.nextInt();

                    if(keyInput == 1){ //KEY-GEN
                        System.out.println("Input p and q (preferably two large primes)");
                        Scanner sc4 = new Scanner(System.in);
                        rsaKeyInfo = rsa.generateKey(sc4.next(), sc4.next());
                        
                        //Write to file: order -> p, q, n, t, e, d
                        try {
                            File file = new File("importRSA.txt");
                            if (file.createNewFile()) {
                              System.out.print("");
                            } else {
                              System.out.print("");
                            }
                        } catch (IOException e) {
                            System.out.println("File error.");
                            e.printStackTrace();
                        }
                        try {
                            FileWriter writer = new FileWriter("importRSA.txt");
                            for(int i = 0; i < 3; i++){
                                writer.write(rsaKeyInfo[i].toString() + "\n");
                            }
                            writer.close();
                        } catch (IOException e) {
                            System.out.println("Write error.");
                            e.printStackTrace();
                        }
                        System.out.println("Key values successfully generated and written to FILE.");
                    }
                    if(keyInput == 2){ //PRIME-GEN
                        System.out.println("Input number of primes and digits");
                        Scanner sc4 = new Scanner(System.in);
                        
                        rsa.generatePrime(sc4.nextInt(), sc4.nextInt());
                    }
                }
            }
        }
        catch(InputMismatchException exception){
            System.out.println("Operation failed: Invalid input.");
        }
    }
}
