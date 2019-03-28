/*
159.102 Computer Science Fundamentals Assignment 1
You have been asked to write a C program that converts binary numbers to decimal, and decimal numbers to binary.
The program prompts the user to type in a number.
The user then types in either a binary or decimal number.
You must use the function gets() to read the character string form of the number.
If the program can determine the type of number (either binary or decimal) by considering the digits that make up the number,
then no further prompts occur, and the program outputs the converted number.
If the program cannot determine the type of number by considering the digits, then it asks the user to say whether the number
is binary or decimal, and then outputs the converted number.
Input numbers that have leading 0&#39;s will be binary. (The single 0 in the number zero, is not a leading 0!)
Input numbers that contain any of the digits 23456789 will be decimal.
Numbers that contain any character other than 0123456789 are invalid.
Your program should check for this and display an error message if any are detected.
Your program&#39;s output will be the same as in the examples below no other output should be produced.
Decimal numbers, on input, will all be in the range 0 to 255 inclusive (they are invalid outside this range).
Binary numbers, on input, will have at most 8 digits (they are invalid if they have more than 8 digits).
Output numbers that are decimal will be displayed with no leading 0&#39;s.
Output numbers that are binary will have (if necessary) leading 0&#39;s added so that there are always 8 digits displayed.
The &#39;space&#39; character will not be used on input. You do not have to check for this.
You must use the function gets() if you need to prompt the user for the type of number.
The program inputs only one number, does the conversion and then terminates.
Examples:
Type in a number: 8
Converting decimal to binary. Answer is: 00001000
Type in a number: 010
Converting binary to decimal. Answer is: 2
Type in a number: 10
Is this number decimal (d) or binary (b)? d
Converting decimal to binary. Answer is: 00001010
Type in a number: 8m
That is an invalid number!
Type in a number: 012
That is an invalid number!
Type in a number: 256
That is an invalid number!
The reply to the prompt &quot;Is this number decimal (d) or binary (b)? &quot; does not have to be checked,
it will be correct. Remember you must use the function gets() to read the users input.
*/

#include<stdio.h>
#include<string.h>
#include<math.h>

void main() {
    char number[8];
    char type;

    while (1) {
        printf("Please enter a binary or decimal number" );
        gets(number);
        if (isValidDecimal(number) && isValidBinary(number)) {
            printf("Is this number decimal (d) or binary (b)?");
            gets(type);
            switch (type) {
                case 'd':
                    printf("Converting decimal to binary. Answer is: ");
                    printf(convertDecimalToBinary(number));
                    break;
                case 'b':
                    printf("Converting binary to decimal. Answer is: ");
                    printf(convertBinaryToDecimal(number));
                    break;
                default:
                    printf("Wrong type, please enter the number again.");
                    break;
            }
        } else if (isValidDecimal(number)) {
            printf("Converting decimal to binary. Answer is: ");
            printf(convertDecimalToBinary(number));
        } else if (isValidBinary(number)) {
            printf("Converting binary to decimal. Answer is: ");
            printf(convertBinaryToDecimal(number));
        } else {
            printf("Error, this is an invalid input, please enter the number again.");
        }
    }
}

bool isDigit(char character) {
    if (character > '9' || character < '0') { // 如果该字符不是0-9中的其中一个，则不是有效数字字符
        return false;
    } else {
        return true;
    }
}

int StrToInt(char numberStr[8]) {
    int number = 0;
    for (int i = 0; i < strlen(numberStr); i++) {
        number += (numberStr[i]-'0')*pow(10,(strlen(numberStr)-i));
    }
    return number;
}

bool isValidDecimal(char number[8]) {
    if (number[0] == '0' && strlen(number) != 1) { // 如果开头为0且用户输入不是单个0，则不是十进制
        return false;
    }
    for (int i = 0; i < strlen(number); i++) { // 检查用户输入，如果不是0-9之间的数字，则是无效输入
        if(!isDigit(number[i])) {
            return false;
        }
    }
    if (StrToInt(number) > 255) {
        return false;
    }
    return true;
}

bool isValidBinary(char number[8]) {
    if (strlen(number) > 8) { // 若用户输入多于8位，就肯定不是问题要求的有效二进制
        return false;
    }
    for (int i = 0; i < strlen(number); i++){ // 检查用户输入，如果不是0-1之间的数字，则不是二进制
        if(number[i] > '1' || number[i] < '0') {
            return false;
        }
    }
    return true;
}

int convertBinaryToDecimal(char numberStr[8])
{
    int number = StrToInt(numberStr);
    int decimalNumber = 0, i = 0, remainder;
    while (number != 0) {
        remainder = number%10;
        number /= 10;
        decimalNumber += remainder*pow(2,i);
        ++i;
    }
    return decimalNumber;
}

char* convertDecimalToBinary(char numberStr[8])
{
    int number = StrToInt(numberStr);
    int binaryNumberArray[8];
    char binaryNumber[8];
    while (number > 0) {
        binaryNumberArray[i] = number%2;
        number /= 2;
    }
    for (int i = strlen(binaryNumberArray); i > 0; i--) {
        strcat(binaryNumber, binaryNumberArray[i]);
    }
    return binaryNumber;
}