import math
print(abs(-5))  # 5

print(math.floor(3.7))  # 3  （向下取整）
print(math.ceil(3.2))   # 4  （向上取整）
print(round(3.5))       # 4  （四舍五入）

print(math.fmod(10, 3))     # 1.0 （10 % 3，支持浮点数）
print(divmod(10, 3))        # (3, 1) （商 & 余数）

print(math.pow(2, 3))   # 8.0  （浮点数）
print(2 ** 3)           # 8    （整型）
print(math.sqrt(25))    # 5.0  （平方根）

print(math.log(8, 2))   # 3.0  （以 2 为底的对数）
print(math.log10(100))  # 2.0  （常用：log₁₀）
print(math.log2(16))    # 4.0  （常用：log₂）
print(math.exp(1))      # 2.718  （e 的指数）

print(math.degrees(math.pi))  # 180.0  （弧度 → 角度）
print(math.radians(180))      # 3.14159  （角度 → 弧度）

print(math.sin(math.pi / 2))  # 1.0  （sin(90°)）
print(math.cos(math.pi))      # -1.0  （cos(180°)）
print(math.tan(math.pi / 4))  # 1.0  （tan(45°)）

print(math.asin(1))   # 1.5708  （sin⁻¹(1)，返回弧度）
print(math.acos(0))   # 1.5708  （cos⁻¹(0)）
print(math.atan(1))   # 0.7854  （tan⁻¹(1)）

print(math.gcd(12, 18))   # 6  （最大公约数）
print(math.lcm(12, 18))   # 36 （Python 3.9+ 才支持）

print(math.factorial(5))  # 120  （5! = 5 × 4 × 3 × 2 × 1）
print(math.comb(5, 2))    # 10   （组合数 C(5,2)）
print(math.perm(5, 2))    # 20   （排列数 P(5,2)）

print(math.pi)      # 3.141592653589793
print(math.e)       # 2.718281828459045
print(math.inf)     # 无穷大（Infinity）
print(-math.inf)    # 负无穷大
print(math.nan)     # Not a Number（NaN）
