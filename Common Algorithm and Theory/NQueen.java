/*
参考：https://blog.csdn.net/qianhaifeng2012/article/details/52300829  
1、回溯算法(Backtracking)简介  
回溯算法也叫试探法，它是一种系统地搜索问题的解的方法。回溯算法的基本思想是：从一条路往前走，能进则进，不能进则退回来，换一条路再试。用回溯算法解决问题的一般步骤为：  
1)、定义一个解空间，它包含问题的解。  
2)、利用适于搜索的方法组织解空间。  
3)、利用深度优先法搜索解空间。  
4)、利用限界函数避免移动到不可能产生解的子空间。  
问题的解空间通常是在搜索问题的解的过程中动态产生的，这是回溯算法的一个重要特性。  
https://zh.wikipedia.org/wiki/%E5%9B%9E%E6%BA%AF%E6%B3%95  
*/



/**
 * 
 * n 皇后问题
 * 在n×n格的国际象棋上摆放八个皇后，使其不能互相攻击，
 * 即任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 * @author QHF
 *
 */
public class NQueen {
	private static final int N = 4; // 4*4 as example
	private int[] x = new int[N];
	int sum = 0; // solutions count

	/**
	 * 回溯寻找
	 * 
	 * @param n
	 */
	void backTack(int n) {
		if(n == N) {
			for (int i = 0; i < x.length; i++) {
				printRow(x[i]);
			}
			System.out.println();
			sum++;
		} else {
			for (int i = 0; i < N; i++) {
				x[n] = i;
				if(isValidPlace(n)) backTack(n + 1); // 如果第n行可以放，继续看n+1行
			}
		}
	}

	// 已找到解决方案之一，画出其第i行(即上面的：for (int i = 0; i < x.length; i++)...)
	void printRow(int index) {
		for (int j = 0; j < N; j++) {
			if (j == index) {
				System.out.print("1 ");
			} else {
				System.out.print("0 ");
			}
		}
		System.out.println();
	}

	/**
	 * 在i行可否放置皇后 (0 <= i <= n)
	 * @param i
	 * @return
	 */
	private boolean isValidPlace(int i) {
		for (int j = 0; j < i; j++) {
			if(Math.abs(i - j) == Math.abs(x[i] - x[j]) || x[i] == x[j]) return false;
		}
		return true;
	}

	public static void main(String[] args) {
		NQueen queen = new NQueen();
		queen.backTack(0);
		System.out.println(queen.sum);
	}
}