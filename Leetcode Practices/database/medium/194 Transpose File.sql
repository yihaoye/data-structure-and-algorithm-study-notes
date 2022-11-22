/*
Given a text file file.txt, transpose its content.

You may assume that each row has the same number of columns, and each field is separated by the ' ' character.

Example:

If file.txt has the following content:

name age
alice 21
ryan 30
Output the following:

name alice ryan
age 21 30
*/



/*
# https://leetcode.com/problems/transpose-file/discuss/111382/Solution-using-AWK-with-explanations
# Here're some quick notes to help you understand:
# The code block with an "END" prefix is only executed after the last line is read; similarly, a code block with a "BEGIN" prefix will be executed before any line reads.
# AWK is line-based: the main code block (the code block without prefix) processes one line of input at a time.
# NR: a variable indicating the number of records (i.e. current line number) that's accumulated across multiple files read. FNR is similar to NR, but is reset for each file read. Since we only need to deal with one file in this question, either is fine to use.
# NF: a variable indicating the number of fields (i.e. number of "columns") on an input line.
# $i: the i-th field of the input line.
# t[]: an array for saving the transposed table. More on awk arrays here: http://www.grymoire.com/Unix/Awk.html#uh-22
*/
awk '
{
    for (i = 1; i <= NF; i++) {
        if (FNR == 1) {
            t[i] = $i
        } else {
            t[i] = t[i] " " $i
        }
    }
}
END {
    for (i = 1; t[i] != ""; i++) {
        print t[i]
    }
}
' file.txt
