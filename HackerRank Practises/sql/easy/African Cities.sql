/*
Given the CITY and COUNTRY tables, query the names of all cities where the CONTINENT is 'Africa'.

Note: CITY.CountryCode and COUNTRY.Code are matching key columns.

Input Format

The CITY and COUNTRY tables are described as follows:
https://s3.amazonaws.com/hr-challenge-images/8137/1449729804-f21d187d0f-CITY.jpg
https://s3.amazonaws.com/hr-challenge-images/8342/1449769013-e54ce90480-Country.jpg
*/



/* solution */
SELECT CITY.NAME FROM CITY INNER JOIN COUNTRY ON COUNTRYCODE=CODE WHERE COUNTRY.CONTINENT='Africa';



/* solution 2 */
Select City.Name From City, Country
Where Code = CountryCode And Continent = 'Africa';