// ’00101‘

//  0 10   1
//   010   2
//    101  3

// how many 010 
// how many 101

public int[] count(String str) {
  int z = 0, o = 0, zo = 0, oz = 0, zoz = 0, ozo = 0; // DP
  // follow up: what about if k==4: 0101 and 1010? or k is more?
  // int[] dpZ = int[k+1]; // dpZ[i] means end with Z and length == i
  // int[] dpO = int[k+1];
  // 
  // then dpZ[i] += dpO[i-1], dpO[i] += dpZ[i-1]
  // dpZ[0] = 1, dpO[0] = 1
  // Time: O(N*k), Space: O(k)

  for (char c : str.toCharArray()) {
    if (c == '0') {
      z += 1;
      oz += o;
      zoz += zo;
    }
    if (c == '1') {
      o += 1;
      zo += z;
      ozo += oz;
    }
  }
  return new int[]{zoz, ozo};
}
