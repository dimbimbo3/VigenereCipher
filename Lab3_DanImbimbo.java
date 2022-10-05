import java.util.Random;
import java.util.Scanner;

public class Lab3_DanImbimbo{
    public static void main(String[] args){
        String punctuationMarks = " .?!,;:-()[]{}'\""; //punctuation marks that have been acounted for
        Scanner keyboard = new Scanner(System.in); //Scanner object for user input
        boolean repeatLoop = true; //boolean flag for determining if the loop should repeat
            
        do{
            System.out.println("-----------------------------------------------------");
            System.out.print("Enter a message to be encrypted:");
            String userInput = (keyboard.nextLine()).toUpperCase(); //Retrieves the next line of input from the Scanner and stores in a String

            //Checks the letters of the user input, if they are found not to be all letters then it loops for the user to re-enter the String
            if(checkLetters(userInput, punctuationMarks)){
                System.out.println("-----------------------------------------------------");
                System.out.println("User Message:" + userInput);
                String key = generateKey(userInput);
                System.out.println("-----------------------------------------------------");
                System.out.println("Key:" + key);
                String encryptedMessage = encryptMessage(userInput, key, punctuationMarks);
                System.out.println("-----------------------------------------------------");
                System.out.println("Encrypted Message:" + encryptedMessage);
                String decryptedMessage = decryptMessage(encryptedMessage, key, punctuationMarks);
                System.out.println("-----------------------------------------------------");
                System.out.println("Decrypted Message:" + decryptedMessage);
                    
                repeatLoop = false; //ends the loop
            }
            else{
                System.out.println("ERROR: Please enter a message containing letters and punctuation marks only...");
            }
        }while(repeatLoop);
    }

    public static boolean checkLetters(String userInput, String punctuationMarks){
        boolean onlyLetters = true; //boolean flag to determine if the message only contains letters

        for(int i = 0; i < userInput.length(); i++){
            //if a character in the message is NOT a letter and NOT one of the allowed punctuation marks
            //then sets the flag to false and breaks out of loop
            if(!(Character.isLetter(userInput.charAt(i))) && !(punctuationMarks.contains((String.valueOf(userInput.charAt(i)))))){
                onlyLetters = false;
                break;
            }
        }

        return onlyLetters;
    }

    public static String generateKey(String userInput){
        String encryptionAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //characters for the key to choose from
        Random rand = new Random(); //random object for key generation
        String randomKey = "";

        //Generates key by randomly selecting characters from the encryption alphabet (key is the same length as the user's message)
        for(int i = 0; i < userInput.length(); i++){
            randomKey += encryptionAlphabet.charAt(rand.nextInt(encryptionAlphabet.length()));
        }

        return randomKey;
    }

    public static String encryptMessage (String userInput, String key, String punctuationMarks){
        String encryptedMessage = "";

        //uses the Vigenere Cipher to encrypt each letter in the message with the key
        //also accounts for spaces and punctuation
        for(int i = 0; i < userInput.length(); i++){
            int pmCount = 0; //counter to be used by the loop that checks for punctuation marks (resets for each letter checked)
            boolean punctuation = false; //flag for if the character is found to be a punctuation mark (resets for each letter checked)

            //checks each letter of the message against the characters in the punctuationMarks String
            for(; pmCount < punctuationMarks.length(); pmCount++){
                //if the letter is found to be a punctuationMark then the flag is marked and the loop ends
                if(userInput.charAt(i) == punctuationMarks.charAt(pmCount)){
                    punctuation = true;
                    break;
                }
            }
            
            //if the letter in the message was found to be a punctuation mark then add the punctuation mark to the encrypted message
            if(punctuation){
                encryptedMessage += punctuationMarks.charAt(pmCount);
            }
            else{
                int letter = (userInput.charAt(i) + key.charAt(i)) % 26; //performs the encryption on the letter
                letter += 'A'; //converts the value into ascii
                encryptedMessage += Character.toString((char)letter); //appends the encrypted letter to the string
            }
        }

        return encryptedMessage;
    }

    public static String decryptMessage (String encryptedMessage, String key, String punctuationMarks){
        String decryptedMessage = "";

        //uses the Vigenere Cipher to decrypt each letter in the encrypted message with the key
        //also accounts for spaces and punctuation
        for(int i = 0; i < encryptedMessage.length(); i++){
            int pmCount = 0; //counter to be used by the loop that checks for punctuation marks (resets for each letter checked)
            boolean punctuation = false; //flag for if the character is found to be a punctuation mark (resets for each letter checked)

            //checks each letter of the message against the characters in the punctuationMarks String
            for(; pmCount < punctuationMarks.length(); pmCount++){
                //if the letter is found to be a punctuationMark then the flag is marked and the loop ends
                if(encryptedMessage.charAt(i) == punctuationMarks.charAt(pmCount)){
                    punctuation = true;
                    break;
                }
            }

            //if the letter in the encrypted message was found to be a punctuation mark then add the punctuation mark to the decrypted message
            if(punctuation){
                decryptedMessage += punctuationMarks.charAt(pmCount);
            }
            else{
                int letter = (encryptedMessage.charAt(i) - key.charAt(i) + 26) % 26; //performs the decryption on the letter
                letter += 'A'; //converts the value into ascii
                decryptedMessage += Character.toString((char)letter); //appends the decrypted letter to the string
            }
        }

        return decryptedMessage;
    }
}