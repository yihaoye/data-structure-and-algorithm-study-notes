// https://leetcode.com/discuss/interview-question/411357/
// Other's Solution (BFS):
public class Solution {
	public int humanDays(int[][] matrix) {
		Queue<int[]> q = new LinkedList<>();
    	int target = matrix.length * matrix[0].length;
    	int cnt = 0, res = 0;
    	for(int i=0;i<matrix.length;i++) {
    		for(int j=0;j<matrix[0].length;j++) {
    			if(matrix[i][j] == 1) {
    				q.offer(new int[] {i,j});
    				cnt++;
    			}
    		}
    	}
    	int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    	while(!q.isEmpty()) {
    		int size = q.size();
    		if(cnt == target)
    			return res;
    		for(int i=0;i<size;i++) {
    			int[] cur = q.poll();
    			for(int[] dir : dirs) {
    				int ni = cur[0] + dir[0];
    				int nj = cur[1] + dir[1];
    				if(ni >=0 && ni < matrix.length && nj >=0 && nj < matrix[0].length && matrix[ni][nj] == 0) {
    					cnt++;
    					q.offer(new int[] {ni, nj});
    					matrix[ni][nj] = 1;
    				}
    			}
    		}
    		res++;
    	}
    	return -1;
	}
}