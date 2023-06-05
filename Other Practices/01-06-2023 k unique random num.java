// assume have a random number generator could be customize with input parameter,
// random(int n) -> will randomly generate one number between 1 to n
// then can improve it more
// random(int i, int j) -> will randomly generate one number between i to j inclusive
public int random(int i, int j) {
  return random(j-i) + i;
}

class Solution {
    public List<Integer> random(int i, int j, int k) { // randomly generate k distinct numbers within [i..j] without hashset
        List<Integer> res = new ArrayList<>();
        int num = random(i, j);
        res.add(num);
        k--;
        int k1 = (i-num) / (j-i) * k;
        int k2 = k - k1;
        res.addAll(random(i, num-1, k1));
        res.addAll(random(num+1, j, k2));
        return res;
    }
}
