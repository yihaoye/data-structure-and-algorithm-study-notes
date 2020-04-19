<h1>Exercise 1.1-1 to Exercise 1.2-1 </h1>
--skip.


<h1>Exercise 1.2-2</h1>

Suppose we are comparing implementations of insertion sort and merge sort on the same machine. 
For inputs of size n, insertion sort runs in 8*(n^2) steps, while merge sort runs in 64n*lg(n) steps. 
For which values of n does insertion sort beat merge sort?

#include<stdio.h>
#include<math.h>

int n=2;
print("1");
while(1)
{
	i = 8*log2(n);
	if(n <= i)
	{
		print("%d", n);
	}
	else
	{
		break;
	}
	n++;
}

--answer: 2 <= n <= 43.



<h1>Exercise 1.2-3</h1>

What is the smallest value of n such that an algorithm whose running time is
100*(n^2) runs faster than an algorithm whose running time is 2^n on the same machine?

#include<stdio.h>
#include<math.h>

int n=1;
while(1)
{
	a = 100*n*n;
	b = pow(2,n);
	if(a <= b)
	{
		print("%d", n);
		break;
	}

	n++;
}

--answer: n = 15.




<h1>Problem 1-1</h1>

For each function f(n) and time t in the following table, determine the largest
size n of a problem that can be solved in time t, assuming that the algorithm
to solve the problem takes f(n) microseconds(¦Ìs).

            +----------+----------+----------+----------+----------+----------+----------+
            | 1 sec.   | 1 min.   | 1 hour   | 1 day    | 1 month  | 1 year   | 1 century|
    +-------+----------+----------+----------+----------+----------+----------+----------+
    | lg(n) |--------------------------- see below --------------------------------------|
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |sqrt(n)|  1.0e12  | 3.6e15   | 1.3e19   | 7.46e21  | 6.72e24  | 9.95e26  |  9.95e30 |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |   n   |  1.0e6   |  6.0e7   |  3.6e9   | 8.64e10  | 2.59e12  | 3.15e13  |  3.15e15 |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |n lg(n)|  62746   | 2801417  | 1.334e8  | 2.755e9  | 7.19e10  | 7.98e11  |  6.86e13 |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |  n^2  |   1000   |  7745    |  60000   | 293938   | 1609968  | 5.616e6  |  5.616e7 |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |  n^3  |   100    |   391    |  1532    |  4420    |  13736   |  31593   |  146645  |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |  2^n  |   19     |     25   |   31     |   36     |   41     |   44     |    51    |
    +-------+----------+----------+----------+----------+----------+----------+----------+
    |  n!   |    9     |     11   |   12     |   13     |   15     |   16     |    17    |
    +-------+----------+----------+----------+----------+----------+----------+----------+


<h2>Definitions</h2>

-- I know month, year, and century numbers aren't exact but nothing here is critical anyway... --

    second  = 1000000 microseconds
    minute  = 60 seconds
    hour    = 60 minutes
    day     = 24 hours
    month   = 30 days
    year    = 365 days
    century = 100 years

	
	
#include<stdio.h>
#include<math.h>

<h2> O(lg n) </h2>
log2(n) ¦Ìs = T ¦Ìs => n = pow(2,T) //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    log2(n) ¦Ìs = 1 second  = 1000000 ¦Ìs			 => n = 2^1000000 = 9.9e301029
    log2(n) ¦Ìs = 1 minute  = 60000000 ¦Ìs		 => n = 2^60000000 = 5.5e18061799
    log2(n) ¦Ìs = 1 hour    = 3600000000 ¦Ìs		 => n = 2^3600000000
    log2(n) ¦Ìs = 1 day     = 86400000000 ¦Ìs		 => n = 2^86400000000
    log2(n) ¦Ìs = 1 month   = 2592000000000 ¦Ìs	 => n = 2^2592000000000
    log2(n) ¦Ìs = 1 year    = 31536000000000 ¦Ìs	 => n = 2^31536000000000
    log2(n) ¦Ìs = 1 century = 3153600000000000 ¦Ìs => n = 2^3153600000000000


<h2> O(sqrt n) </h2>
sqrt(n) ¦Ìs = T ¦Ìs => n = T^2 //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    sqrt(n) ¦Ìs = 1 second  => n = 1000000^2          = 1.0e12 instructions
    sqrt(n) ¦Ìs = 1 minute  => n = 60000000^2         = 3.6e15
    sqrt(n) ¦Ìs = 1 hour    => n = 3600000000^2       = 1.296e19
    sqrt(n) ¦Ìs = 1 day     => n = 86400000000^2      = 7.46496e21
    sqrt(n) ¦Ìs = 1 month   => n = 2592000000000^2    = 6.718464e24
    sqrt(n) ¦Ìs = 1 year    => n = 31536000000000^2   = 9.94519296e26
    sqrt(n) ¦Ìs = 1 century => n = 3153600000000000^2 = 9.94519296e30


<h2> O(n) </h2>
n ¦Ìs = T ¦Ìs //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    n ¦Ìs = 1 second  => n = 1000000 instructions
    n ¦Ìs = 1 minute  => n = 60000000
    n ¦Ìs = 1 hour    => n = 3600000000
    n ¦Ìs = 1 day     => n = 86400000000
    n ¦Ìs = 1 month   => n = 2592000000000
    n ¦Ìs = 1 year    => n = 31536000000000
    n ¦Ìs = 1 century => n = 3153600000000000


<h2> O(n lg n) </h2>
n*log2(n) ¦Ìs = T ¦Ìs //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

--use a stupid program to solve it.

int n_result[7];
int function(int* n_result)
{
	int Time[7];
	int Size,index;
	int n = 1;

	/*
	>    times = [ 1000000          -- 1 second
	>            , 60000000         -- 1 minute
	>            , 3600000000       -- 1 hour
	>            , 86400000000      -- 1 day
	>            , 2592000000000    -- 1 month
	>            , 31536000000000   -- 1 year
	>            , 3153600000000000 -- 1 century
	>            ]
	*/
	Time = [1000000, 60000000, 3600000000, 86400000000, 2592000000000, 31536000000000, 3153600000000000];
	Size = sizeof(Time)/sizeof(Time[0])

	for(index = 0; index++; index<Size)
	{
		while(1)
		{
			if(n*log2(n)>Time[index])
			{
				n_result[index] = n - 1;
				break;
			}
			n++;
		}
		n = 1;
	}

	return n_result;
}


Evaluating for the required times results in:

    n*log2(n) ¦Ìs = 1 second  => n = 62746
    n*log2(n) ¦Ìs = 1 minute  => n = 2.80142e6
    n*log2(n) ¦Ìs = 1 hour    => n = 1.33378e8
    n*log2(n) ¦Ìs = 1 day     => n = 2.75515e9
    n*log2(n) ¦Ìs = 1 month   => n = 7.18709e10
    n*log2(n) ¦Ìs = 1 year    => n = 7.97634e11
    n*log2(n) ¦Ìs = 1 century => n = 6.86110e13 


<h2> O(n^2) </h2>
pow(n,2) ¦Ìs = T ¦Ìs  => n = sqrt(T) //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    pow(n,2) ¦Ìs = 1 second  => n = sqrt 1000000          = 1000 instructions
    pow(n,2) ¦Ìs = 1 minute  => n = sqrt 60000000         = 7745 (Rounded down from 7745.966692414834 because answer is for complete instructions)
    pow(n,2) ¦Ìs = 1 hour    => n = sqrt 3600000000       = 60000
    pow(n,2) ¦Ìs = 1 day     => n = sqrt 86400000000      = 293938
    pow(n,2) ¦Ìs = 1 month   => n = sqrt 2592000000000    = 1609968
    pow(n,2) ¦Ìs = 1 year    => n = sqrt 31536000000000   = 5615692
    pow(n,2) ¦Ìs = 1 century => n = sqrt 3153600000000000 = 56156922


<h2> O(n^3) </h2>
pow(n,3) ¦Ìs = T ¦Ìs => n = pow(T,1/3) //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    pow(n,3) ¦Ìs = 1 second  => n = pow(1000000,(1/3))          = 99 (rounded down from 99.99999999999997, like others)
    pow(n,3) ¦Ìs = 1 minute  => n = pow(60000000,(1/3))         = 391
    pow(n,3) ¦Ìs = 1 hour    => n = pow(3600000000,(1/3))       = 1532
    pow(n,3) ¦Ìs = 1 day     => n = pow(86400000000,(1/3))      = 4420
    pow(n,3) ¦Ìs = 1 month   => n = pow(2592000000000,(1/3))    = 13736
    pow(n,3) ¦Ìs = 1 year    => n = pow(31536000000000,(1/3))   = 31593
    pow(n,3) ¦Ìs = 1 century => n = pow(3153600000000000,(1/3)) = 14645


<h2> O(2^n) </h2>
pow(2,n) ¦Ìs = T ¦Ìs => n = log2(T) //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

    pow(2,n) ¦Ìs = 1 second  => n = log2(1000000)          = 19
    pow(2,n) ¦Ìs = 1 minute  => n = log2(60000000)         = 25
    pow(2,n) ¦Ìs = 1 hour    => n = log2(3600000000)       = 31
    pow(2,n) ¦Ìs = 1 day     => n = log2(86400000000)      = 36
    pow(2,n) ¦Ìs = 1 month   => n = log2(2592000000000)    = 41
    pow(2,n) ¦Ìs = 1 year    => n = log2(31536000000000)   = 44
    pow(2,n) ¦Ìs = 1 century => n = log2(3153600000000000) = 51


<h2> O(n!) </h2>
n! = T //(T is the value of 1 min/hour/day..century unit translate to ¦Ìs value)

--Using C++ program to solve for n in n!

int n_result[7];
int function(int* n_result)
{
	int Time[7];
	int Size,index;
	int n_value = 1;
	int sum = 1;

	/*
	>    times = [ 1000000          -- 1 second
	>            , 60000000         -- 1 minute
	>            , 3600000000       -- 1 hour
	>            , 86400000000      -- 1 day
	>            , 2592000000000    -- 1 month
	>            , 31536000000000   -- 1 year
	>            , 3153600000000000 -- 1 century
	>            ]
	*/
	Time = [1000000, 60000000, 3600000000, 86400000000, 2592000000000, 31536000000000, 3153600000000000];
	Size = sizeof(Time)/sizeof(Time[0])

	for(index = 0; index++; index<Size)
	{
		do
		{
			sum = n_value*sum;
			n_value++;
			if(sum>Time[index])
			{
				n_result[index] = n_value - 2;
				n_value = 1;
				sum = 1;
			}
		}while(n_value != 1);
	}

	return n_result;
}

--Results for resultsFactorial:

    1 second  => n! = 1000000 			 => n = 9
    1 minute  => n! = 60000000 			 => n = 11
    1 hour    => n! = 3600000000 		 => n = 12
    1 day     => n! = 86400000000 		 => n = 13
    1 month   => n! = 2592000000000 	 => n = 15
    1 year    => n! = 31536000000000 	 => n = 16
    1 century => n! = 3153600000000000 	 => n = 17
	
