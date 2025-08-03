# [离散傅立叶变换](https://zh.wikipedia.org/wiki/%E7%A6%BB%E6%95%A3%E5%82%85%E9%87%8C%E5%8F%B6%E5%8F%98%E6%8D%A2)
离散傅里叶变换（Discrete Fourier Transform，缩写为 DFT），是傅里叶变换在时域和频域上都呈离散的形式，将信号的时域采样变换为其 DTFT 的频域采样。  
在形式上，变换两端（时域和频域上）的序列是有限长的，而实际上这两组序列都应当被认为是离散周期信号的主值序列。即使对有限长的离散信号作 DFT，也应当将其看作其周期延拓的变换。在实际应用中通常采用快速傅里叶变换计算 DFT。  
![](./dft-0.webp)  
![](./dft-1.gif)  
  
# 快速傅立叶变换
快速傅里叶变换（Fast Fourier Transform, FFT），是快速计算序列的离散傅里叶变换（DFT）或其逆变换的方法。傅里叶分析将信号从原始域（通常是时间或空间）转换到频域的表示或者逆过来转换。FFT 会通过把 DFT 矩阵分解为稀疏（大多为零）因子之积来快速计算此类变换。因此，它能够将计算 DFT 的复杂度从只用 DFT 定义计算需要的 O(n^2) 降低到 O(nlogn)，其中 n 为数据大小。  
FFT 支持在 O(nlogn) 的时间内计算两个 n 度的多项式的乘法，比朴素的 O(n^2) 算法更高效。由于两个整数的乘法也可以被当作多项式乘法，因此这个算法也可以用来加速大整数的乘法计算以及卷积计算之类。  
离散傅立叶变换 DFT 通常通过矩阵乘法来实现，而快速傅立叶变换 FFT 则是对这一矩阵运算进行优化的算法，FFT 利用 DFT 矩阵的对称性和周期性性质、并非直接在矩阵乘法的基础上进行优化。  

* **快速傅里叶变换推荐讲解：[Ref 1](https://blog.csdn.net/Flag_z/article/details/99163939) + [Ref 2](https://zhuanlan.zhihu.com/p/31584464)**
* [快速傅里叶变换详细讲解](https://www.youtube.com/watch?v=RlxT4Nmd45I&list=PLEUKC88yR4_al2oa2LF0SKS2RPpxmWg3n&index=9)
* [io wiki - 快速傅里叶变换应用](https://oi-wiki.org/math/poly/fft/)
* [从多项式乘法到快速傅里叶变换](https://itimetraveler.github.io/2017/09/08/%E3%80%90%E7%AE%97%E6%B3%95%E3%80%91%E4%BB%8E%E5%A4%9A%E9%A1%B9%E5%BC%8F%E4%B9%98%E6%B3%95%E5%88%B0%E5%BF%AB%E9%80%9F%E5%82%85%E9%87%8C%E5%8F%B6%E5%8F%98%E6%8D%A2/)

下面这个模板用于计算复数数组的离散傅立叶变换：
```java
// 华盛顿大学 C 语言模版 https://www.math.wustl.edu/~victor/mfmm/fourier/fft.c
// 普林斯顿大学 Java 模版 https://algs4.cs.princeton.edu/99scientific/FFT.java.html
import java.util.*;

public class FFT {
    private static final Complex ZERO = new Complex(0, 0);

    // Do not instantiate.
    private FFT() {}

    /**
     * Returns the FFT of the specified complex array.
     *
     * @param  x the complex array
     * @return the FFT of the complex array {@code x}
     * @throws IllegalArgumentException if the length of {@code x} is not a power of 2
     */
    public static Complex[] fft(Complex[] x) {
        int n = x.length;

        // base case
        if (n == 1)
            return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (n % 2 != 0)
            throw new IllegalArgumentException("n is not a power of 2");

        // fft of even terms
        Complex[] even = new Complex[n/2];
        for (int k = 0; k < n/2; k++)
            even[k] = x[2*k];
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd = even;  // reuse the array
        for (int k = 0; k < n/2; k++)
            odd[k] = x[2*k + 1];
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + n/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

    /**
     * Returns the inverse FFT of the specified complex array.
     *
     * @param  x the complex array
     * @return the inverse FFT of the complex array {@code x}
     * @throws IllegalArgumentException if the length of {@code x} is not a power of 2
     */
    public static Complex[] ifft(Complex[] x) {
        int n = x.length;
        Complex[] y = new Complex[n];

        // take conjugate
        for (int i = 0; i < n; i++)
            y[i] = x[i].conjugate();

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < n; i++)
            y[i] = y[i].conjugate();

        // divide by n
        for (int i = 0; i < n; i++)
            y[i] = y[i].scale(1.0 / n);

        return y;
    }

    /**
     * Returns the circular convolution of the two specified complex arrays.
     *
     * @param  x one complex array
     * @param  y the other complex array
     * @return the circular convolution of {@code x} and {@code y}
     * @throws IllegalArgumentException if the length of {@code x} does not equal
     *         the length of {@code y} or if the length is not a power of 2
     */
    public static Complex[] cconvolve(Complex[] x, Complex[] y) { // 循环卷积
        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length)
            throw new IllegalArgumentException("Dimensions don't agree");

        int n = x.length;

        // compute FFT of each sequence
        Complex[] a = fft(x);
        Complex[] b = fft(y);

        // point-wise multiply
        Complex[] c = new Complex[n];
        for (int i = 0; i < n; i++)
            c[i] = a[i].times(b[i]);

        // compute inverse FFT
        return ifft(c);
    }

    /**
     * Returns the linear convolution of the two specified complex arrays.
     *
     * @param  x one complex array
     * @param  y the other complex array
     * @return the linear convolution of {@code x} and {@code y}
     * @throws IllegalArgumentException if the length of {@code x} does not equal
     *         the length of {@code y} or if the length is not a power of 2
     */
    public static Complex[] convolve(Complex[] x, Complex[] y) { // 线性卷积
        Complex[] a = new Complex[2*x.length];
        for (int i = 0; i < x.length; i++)
            a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++)
            a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        for (int i = 0; i < y.length; i++)
            b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++)
            b[i] = ZERO;

        return cconvolve(a, b);
    }

    /**
     * Unit tests the {@code FFT} class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Complex[] x = new Complex[n];

        // original data
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(StdRandom.uniformDouble(-1.0, 1.0), 0);
        }

        // FFT of original data
        Complex[] y = fft(x);
        // take inverse FFT
        Complex[] z = ifft(y);
        // circular convolution of x with itself
        Complex[] c = cconvolve(x, x);
        // linear convolution of x with itself
        Complex[] d = convolve(x, x);
    }
}

public class Complex {
    private final double re;   // the real part
    private final double im;   // the imaginary part

    /**
     * Initializes a complex number from the specified real and imaginary parts.
     *
     * @param real the real part
     * @param imag the imaginary part
     */
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    /**
     * Returns the absolute value of this complex number.
     * This quantity is also known as the <em>modulus</em> or <em>magnitude</em>.
     *
     * @return the absolute value of this complex number
     */
    public double abs() {
        return Math.hypot(re, im);
    }

    /**
     * Returns the phase of this complex number.
     * This quantity is also known as the <em>angle</em> or <em>argument</em>.
     *
     * @return the phase of this complex number, a real number between -pi and pi
     */
    public double phase() {
        return Math.atan2(im, re);
    }

    /**
     * Returns the sum of this complex number and the specified complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this + that)}
     */
    public Complex plus(Complex that) {
        double real = this.re + that.re;
        double imag = this.im + that.im;
        return new Complex(real, imag);
    }

    /**
     * Returns the result of subtracting the specified complex number from
     * this complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this - that)}
     */
    public Complex minus(Complex that) {
        double real = this.re - that.re;
        double imag = this.im - that.im;
        return new Complex(real, imag);
    }

    /**
     * Returns the product of this complex number and the specified complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this * that)}
     */
    public Complex times(Complex that) {
        double real = this.re * that.re - this.im * that.im;
        double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }

    /**
     * Returns the product of this complex number and the specified scalar.
     *
     * @param  alpha the scalar
     * @return the complex number whose value is {@code (alpha * this)}
     */
    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * Returns the product of this complex number and the specified scalar.
     *
     * @param  alpha the scalar
     * @return the complex number whose value is {@code (alpha * this)}
     * @deprecated Replaced by {@link #scale(double)}.
     */
    @Deprecated
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * Returns the complex conjugate of this complex number.
     *
     * @return the complex conjugate of this complex number
     */
    public Complex conjugate() { // 共轭
        return new Complex(re, -im);
    }

    /**
     * Returns the reciprocal of this complex number.
     *
     * @return the complex number whose value is {@code (1 / this)}
     */
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    /**
     * Returns the real part of this complex number.
     *
     * @return the real part of this complex number
     */
    public double re() {
        return re;
    }

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part of this complex number
     */
    public double im() {
        return im;
    }

    /**
     * Returns the result of dividing the specified complex number into
     * this complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this / that)}
     */
    public Complex divides(Complex that) {
        return this.times(that.reciprocal());
    }

    /**
     * Returns the complex exponential of this complex number.
     *
     * @return the complex exponential of this complex number
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    /**
     * Returns the complex sine of this complex number.
     *
     * @return the complex sine of this complex number
     */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    /**
     * Returns the complex cosine of this complex number.
     *
     * @return the complex cosine of this complex number
     */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    /**
     * Returns the complex tangent of this complex number.
     *
     * @return the complex tangent of this complex number
     */
    public Complex tan() {
        return sin().divides(cos());
    }
}
```

其他：[并行快速傅立叶变换](https://zh.wikipedia.org/zh-hans/%E5%B9%B6%E8%A1%8C%E5%BF%AB%E9%80%9F%E5%82%85%E9%87%8C%E5%8F%B6%E5%8F%98%E6%8D%A2)  

例题：  
* Leetcode Q43 可以被 FFT 优化

## 应用
* 音讯与影像压缩
* 频谱分析、信号分析
* 图像处理与快速卷积
* 多项式乘法与同态加密
  * 加速大整数的乘法计算
* etc
