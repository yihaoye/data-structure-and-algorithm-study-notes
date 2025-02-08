// https://www.cnblogs.com/wintersun/p/18494447
// https://cloud.tencent.com/developer/article/1136585
import java.util.*;

public class CountMinSketch {
    private int width; // 控制误差范围（适用所有查询），更大意味着误差更小，w = ceil(e/ε)，一般至少 2719，可实现误差范围 < 0.001，高精度要求可设置为 10000 或更高
    private int depth; // 控制误报概率（本没出现，但是预计结果大于 0），更大意味着更稳定的估计，d = ceil(ln(1/δ))，一般 7 已达高精度可实现误报概率 < 0.001
    private int[][] sketch;
    private HashFunction[] funcs;

    public CountMinSketch(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.sketch = new int[depth][width];
        this.funcs = new HashFunction[depth];

        // 初始化哈希函数，每一行对应一个独立的哈希函数
        for (int r = 0; r < depth; r++) {
            this.funcs[r] = new HashFunction(width);
        }
    }

    public void add(String item) {
        for (int r = 0; r < depth; r++) {
            int c = funcs[r].hash(item);
            sketch[r][c]++;
        }
    }

    public int estimate(String item) {
        int min = Integer.MAX_VALUE;
        for (int r = 0; r < depth; r++) {
            int c = funcs[r].hash(item);
            min = Math.min(min, sketch[r][c]);
        }
        return min;
    }

    private class HashFunction {
        private int a;
        private int b;
        private int m;

        public HashFunction(int width) {
            this.a = (int) (Math.random() * (width + 1));
            this.b = (int) (Math.random() * (width + 1));
            this.m = width;
        }

        public int hash(String item) {
            return ((a * item.hashCode() + b) % m + m) % m; // 防止负数，hashCode 可用 MurMurHash 进一步优化均匀性
        }
    }
}

/*
public class CountMinSketchExample {
    public static void main(String[] args) {
        CountMinSketch cms = new CountMinSketch(1000, 5);

        cms.add("A"); cms.add("A");
        cms.add("C");
        cms.add("B"); cms.add("B"); cms.add("B");

        cms.estimate("A");
        cms.estimate("B");
        cms.estimate("C");
    }
}
*/
