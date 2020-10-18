/*
Samantha was tasked with calculating the average monthly salaries for all employees in the EMPLOYEES table, but did not realize her keyboard's 0 key was broken until after completing the calculation. She wants your help finding the difference between her miscalculation (using salaries with any zeroes removed), and the actual average salary.

Write a query calculating the amount of error (i.e.: actual-miscalculated average monthly salaries), and round it up to the next integer.

Input Format

The EMPLOYEES table is described as follows:
https://s3.amazonaws.com/hr-challenge-images/12893/1443817108-adc2235c81-1.png


Note: Salary is measured in dollars per month and its value is < 10^5.

Sample Input
https://s3.amazonaws.com/hr-challenge-images/12893/1443817161-299cc6eb7f-2.png

Sample Output
2061

Explanation
https://s3.amazonaws.com/hr-challenge-images/12893/1443817229-eb00d44a3b-3.png


The table below shows the salaries without zeroes as they were entered by Samantha:



Samantha computes an average salary of 98.00. The actual average salary is 2159.00.

The resulting error between the two calculations is 2159.00-98.00=2061.00 which, when rounded to the next integer, is 2061.
*/



/* other's solution */
SELECT CEIL(AVG(Salary)-AVG(REPLACE(Salary,'0',''))) FROM EMPLOYEES;
