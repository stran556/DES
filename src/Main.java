//Data Encryption Standard
package company;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        try {
            Scanner cs = new Scanner(System.in);
            System.out.println("|--[ALG]");
            System.out.println("|    [1]DES \n|    [2]AES ");
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
                    String text = "4D41444953454E534B494E4E45523036";
                    String key = "5445584153564F4C4C455942414C4C31";
                    System.out.println();

                    System.out.println("\n________________[FILE]________________");
                    Thread.sleep(50);
                    System.out.println("[TXT] " + text.toLowerCase());
                    System.out.println("[KEY] " + key.toLowerCase());
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
                        System.out.println("128BIT[________________________________]");
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
                        System.out.println("128BIT[________________________________]");
                        System.out.print("[TXT]  ");
                        Scanner sc2 = new Scanner(System.in);
                        String input2 = sc2.nextLine();
                        System.out.print("[KEY]  ");
                        String input3 = sc2.nextLine();
                        System.out.println("[OUT]  " + aes.decrypt(input2, input3));
                    }
                }
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
