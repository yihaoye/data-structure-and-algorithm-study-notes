/*
Query the total population of all cities in CITY where District is California.

Input Format

The CITY table is described as follows: https://s3.amazonaws.com/hr-challenge-images/8137/1449729804-f21d187d0f-CITY.jpg
*/



/* my solution */
select sum(POPULATION) from CITY where DISTRICT = 'California';