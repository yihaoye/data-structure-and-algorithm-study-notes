# Given three integers (, , and ) representing the three sides of a triangle, identify whether the triangle is scalene, isosceles, or equilateral.

# If all three sides are equal, output EQUILATERAL.
# Otherwise, if any two sides are equal, output ISOSCELES.
# Otherwise, output SCALENE.
# Input Format

# Three integers, each on a new line.

# Constraints


# The sum of any two sides will be greater than the third.

# Output Format

# One word: either "SCALENE" or "EQUILATERAL" or "ISOSCELES" (quotation marks excluded).

# Sample Input

# Sample Input 1

# 2
# 3
# 4
# Sample Input 2

# 6
# 6
# 6  
# Sample Output

# Sample Output 1

# SCALENE
# Sample Output 2

# EQUILATERAL  



# Other's Solution:
read X
read Y
read Z

if [[ $X == $Y && $X == $Z ]]; then
    echo EQUILATERAL
elif [[ $X == $Y || $X == $Z || $Y == $Z ]]; then
    echo ISOSCELES
else
    echo SCALENE
fi
