// Task description
// Write a function:

// function solution(A);
// that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

// For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
// Given A = [1, 2, 3], the function should return 4.
// Given A = [−1, −3], the function should return 1.

// Assume that:
// N is an integer within the range [1..100,000];
// each element of array A is an integer within the range [−1,000,000..1,000,000].
// Complexity:
// expected worst-case time complexity is O(N);
// expected worst-case space complexity is O(N) (not counting the storage required for input arguments).



function solution(A) {
    // write your code in JavaScript (Node.js 8.9.4)
    var arr = A.sort(function(a, b) {
      return a - b
    })
    if(arr[arr.length-1]<1 || arr[0]>1) {
        return 1
    }
    
    for(var i=0; i<arr.length; i++) {
        if(arr[i+1]==arr[i] || arr[i+1]<=1) {
            continue
        } else if(arr[i+1]-arr[i]>1) {
            if(arr[i]>=0) {
                return arr[i]+1
            } else if(arr[i]<=0) {
                return 1
            }
        } else {
            continue;
        }
    }
    return arr[arr.length-1]+1
}