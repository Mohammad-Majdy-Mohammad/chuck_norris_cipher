package chucknorris;

import java.util.Arrays;

public class ChuckNorrisCipher {

    private String operation;  // decode or encode
    private boolean isValid;


    public ChuckNorrisCipher(String operation) {  // constructor
        if (operation.equalsIgnoreCase("decode") || operation.equalsIgnoreCase("encode")) {
            this.operation = operation;
            this.isValid = true;
        } else {
            System.out.println("There is no '" +  operation + "' operation");
            this.isValid = false;
            this.operation = "decode";
        }
    }

    public ChuckNorrisCipher() {
        this.operation = "decode";
    }


    public boolean isValidOperation() {  // check
        return this.isValid;
    }


    public void setOperation(String operation) {
        if (operation.equalsIgnoreCase("decode") || operation.equalsIgnoreCase("encode")) {
            this.operation = operation;
        } else {
            System.out.println("There is no \\'" +  operation + "\\' operation");
        }
    }



    // generating the result of either encryption or decryption
    public String generateResult(String text) {

        if (this.operation.equals("decode")) {
            boolean isValidEncodedCipher = isValidEncodedMessage(text);
            if (isValidEncodedCipher && chuckNorrisToAsciiDecoder(text).length() % 7 == 0) {
                return String.valueOf(asciiDecoder(chuckNorrisToAsciiDecoder(text).toString()));
            }

            System.out.println("Encoded string is not valid.");

            return "NotValid!";
        }

        return chuckNorrisFromAsciiEncoder(asciiEncoder(text)).toString();
    }


    // encoding English text into Ascii code
    private char[] asciiEncoder(String text) {

        final int bits = 7;  // number of bits in th Ascii code
        final int textLength = text.length();  // the length of the actual text
        final int textLengthAscii = textLength * bits;  // calculating the length of the encoded text
        char[] textInBinary = new char[textLengthAscii];  // making array of the encoded text
        /*
           char array is easier when converting to chuck norris cipher
           10000111000011, is easier than 1000011 1000011 while encoding
         */

        for (int i = 0; i < textLength; ++i) {

            int decimalAscii = text.charAt(i);  // decimal value of the Ascii of the charAt(i)

            // initiating the current 7 indices of the array with char '0' to avoid missing the leading zeros
            for (int j = i * bits; j < bits * (i + 1); j++) {
                textInBinary[j] = '0';
            }


            // after generating binary in the remainder way, binary values
            // must be filled from right to left, from index 6 to index 0
            // or in general filling from index (i + 1) * bits - 1 to index i * bits.
            // bits is a defined variable equals to 7.
            int index = (i + 1) * bits - 1;
            while (decimalAscii != 0 && index >= (i * bits)) {  // calculating binary in the remainder way
                textInBinary[index] = (char) (decimalAscii % 2 + '0');
                decimalAscii /= 2;
                --index;
            }

        }

        return textInBinary;
    }


    private StringBuilder chuckNorrisFromAsciiEncoder (char[] binaryText) {  // encoding Ascii into chuck norris

        StringBuilder chuckNorrisCode = new StringBuilder();
        int countZeros = 1;
        int countOnes  = 1;
        for (int i = 0; i < binaryText.length; ++i) {

            if (binaryText[i] == '1' && countOnes == 1) {  // first one in series detector
                chuckNorrisCode.append(i > 0 ? " " : "").append("0 0");
                ++countOnes;  // condition so not entering this condition again, only at detection
                countZeros = 1;  // so if a Zero detected after ones
            } else if (binaryText[i] == '0' && countZeros == 1) {
                chuckNorrisCode.append(i > 0 ? " " : "").append("00 0");
                ++countZeros;
                countOnes = 1;  // so if a one detected after zeros
            } else {
                chuckNorrisCode.append("0");  // detected zero or one and still
            }

        }

        return chuckNorrisCode;
    }


    private StringBuilder chuckNorrisToAsciiDecoder (String text) {

        // converting the chuckNorris String to array for much easier processing
        String[] encodedText = text.split("\\s+");

        StringBuilder decodedText = new StringBuilder();  // text in Ascii code

        // looping on the even indices that is represented in 0 for one or 00 for zero
        // which tells the next index values whether zeros or ones
        for (int i = 0; i < encodedText.length - 1; i += 2) {
            for (int j = 0; j < encodedText[i + 1].length(); j++) {
                if (encodedText[i].equals("00")) {
                    decodedText.append('0');
                } else {
                    decodedText.append('1');
                }
            }
        }

        return decodedText;
    }


    private char[] asciiDecoder (String text) {  // from Ascii to Characters

        // each 7 bits of ascii represents one char so the total length have to be divided by 7
        char[] decodedText = new char[text.length() / 7];

        int index = 0;
        for (int i = 0; i < text.length(); i += 7) {
            StringBuilder decodedCharacter = new StringBuilder();  // storing the 7 bit ascii code of charAt(i + j)
            for (int j = 0; j < 7; j++) {
                decodedCharacter.append(text.charAt(j + i));
            }
            decodedText[index] = (char) Integer.parseInt(decodedCharacter.toString(), 2);
            ++index;
        }

        return decodedText;
    }



    private boolean isValidEncodedMessage(String text) {

        // The encoded message includes characters other than 0 or spaces;
        for (int i = 0; i < text.length(); i++) {
            if (!(text.charAt(i) == ' ' || text.charAt(i) == '0')) {
                return false;
            }
        }


        // The first block of each sequence is not 0 or 00;
        //The number of blocks is odd;
        String[] textBlocks = text.split("\\s+");
        boolean isValidBlock = textBlocks[0].equals("0") || textBlocks[0].equals("00");
        if (!isValidBlock || textBlocks.length % 2 != 0) {
            return false;
        }

        for (int i = 2; i < textBlocks.length; i += 2) {
            isValidBlock = textBlocks[i].equals("0") || textBlocks[i].equals("00");
            if (!(!textBlocks[i].equals(textBlocks[i - 2]) && isValidBlock)) {
                return false;
            }
        }

        return true;
    }

}