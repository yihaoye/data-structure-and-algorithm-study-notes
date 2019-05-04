// https://aonecode.com : a's 2019 oa  



// My Solution Demo Q1:
import java.util.List;
import java.util.ArrayList;

public class Solution
{        
    public List<Integer> cellCompete(int[] states, int days)
    {
        List<Integer> res = new ArrayList<Integer>();
        List<Integer> temp = new ArrayList<Integer>();
        for (int i=0; i < states.length; i++) temp.add(states[i]);
        for (int i=0; i < days; i++) {
            res = cellCompeteOnce(temp);
            temp = res;
        }
        return res;
    }
    
    public List<Integer> cellCompeteOnce(List<Integer> states) {
        List<Integer> res = new ArrayList<Integer>();
        res.add(states.get(1) == 0 ? 0 : 1);
        for (int i=1; i < states.size()-1; i++) {
            res.add(states.get(i-1) == states.get(i+1) ? 0 : 1);
        }
        res.add(states.get(6) == 0 ? 0 : 1);
        return res;
    }
}



// My Solution Demo Q2:
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class GCD
{
    public int generalizedGCD(int num, int[] arr)
    {
        List<Integer> arrList = new ArrayList<Integer>();
        for (int i=0; i < arr.length; i++) arrList.add(arr[i]);
        Collections.sort(arrList);
        for (int i=arrList.get(0); i > 1; i--) {
            if (isDivisor(arr, i)) return i;
        }
        return 1;
    }
    
    public boolean isDivisor(int[] arr, int i) {
        for (int elem : arr) if (elem % i != 0) return false;
        return true;
    }
}



/* Q1 */
// My Solution:
public class Solution
{        
    public int[] movsOnFlight(int[] movieDurations, int d)
    {
        int[] res = new int[]{0, 0};
        int max = 0;
        for (int i=0; i < movieDurations.length; i++) {
            for (int j=i; j < movieDurations.length; j++) {
                if (movieDurations[i]+movieDurations[j] <= d-30 && movieDurations[i]+movieDurations[j] >= max) {
                    int longest = movieDurations[i] > movieDurations[j] ? movieDurations[i] : movieDurations[j];
                    if (longest > res[1]) {
                        res[0] = movieDurations[i] <= movieDurations[j] ? movieDurations[i] : movieDurations[j];
                        res[1] = longest;
                        max = movieDurations[i] + movieDurations[j];
                    }
                }
            }
        }

        return res;
    }
}



/* Q2 */
// My Solution:
import java.util.Queue;
import java.util.LinkedList;

public class Solution
{        
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // directions to top, bottom, left and right
        int[][] dirs={{-1,0},{1,0},{0,-1},{0,1}};
        // record shotest distance
        int[][] distance=new int[maze.length][maze[0].length];
        // Set all cell as -1
        for(int[] a:distance){
            Arrays.fill(a,-1);
        }
        // Initialize start distance to 0
        distance[start[0]][start[1]]=0;
        Queue<int[]> q=new LinkedList<>();
        q.add(start);
        while(!q.isEmpty()){
            int[] c=q.poll();
            for(int[] dir:dirs){
                int count=distance[c[0]][c[1]];
                int x=c[0];
                int y=c[1];
                while(x+dir[0]>=0&&x+dir[0]<maze.length&&y+dir[1]>=0&&y+dir[1]<maze[0].length&&maze[x+dir[0]][y+dir[1]]!=1){
                    x+=dir[0];
                    y+=dir[1];
                    count++;
                }
                // If this cell is first time to reach or the distance to this cell is shorter
                // add it to queue and update distance
                if(distance[x][y]==-1||distance[x][y]>count){
                    q.add(new int[]{x,y});
                    distance[x][y]=count;
                }
            }
        }
        return distance[destination[0]][destination[1]];
    }
}




/* Q3 */
// Other's Solution:
import java.util.Arrays;

public class Solution
{        
    public int[][] kNearest(int[] u, int[][] pOffices, int k)
    {
        // in some version u/uLocation may be default [0, 0], so just two input params above.
        // Arrays.sort(pOffices, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
        // return Arrays.copyOfRange(pOffices, 0, k);

        Arrays.sort(pOffices, (p1, p2) -> (p1[0]-u[0]) * (p1[0]-u[0]) + (p1[1]-u[1]) * (p1[1]-u[1]) - (p2[0]-u[0]) * (p2[0]-u[0]) - (p2[1]-u[1]) * (p2[1]-u[1]));
        return Arrays.copyOfRange(pOffices, 0, k);
    }
}
// or (this example is version with no input u/uLocation)
import java.util.Queue;
import java.util.PriorityQueue;

public class Solution {   
    public int[][] kClosest(int[][] pOffices, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
        for (int[] p : pOffices) {
            pq.add(p);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        int[][] res = new int[k][2];
        while (k > 0) {
            res[--k] = pq.poll();
        }
        return res;
    }
}