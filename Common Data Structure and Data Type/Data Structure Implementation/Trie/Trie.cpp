struct trie {
  int next[100000][26], cnt; // 本程序通过组合使用 next 和 exist 两个数组来构建 Trie 树及其树节点，比如可以认为 next 数组的一维里的一个元素为一个树节点
  bool exist[100000];  // 该结点结尾的字符串是否存在

  void insert(char *s, int l) {  // 插入字符串
    int p = 0; // p、cnt 和 next[x][y] 的值其实均是指向某个 Trie 树结点的指针，cnt 在这里是总是自增地分配新的 Trie 树节点的指针地址的变量，这里 next 数组的一维（其实即 p）预先分配了 0-100000 的指针地址范围。
    for (int i = 0; i < l; i++) {
      int c = s[i] - 'a';
      if (!next[p][c]) next[p][c] = ++cnt;  // 如果没有，就添加结点
      p = next[p][c];
    }
    exist[p] = 1;
  }
  bool find(char *s, int l) {  // 查找字符串
    int p = 0;
    for (int i = 0; i < l; i++) {
      int c = s[i] - 'a';
      if (!next[p][c]) return 0;
      p = next[p][c];
    }
    return exist[p];
  }
};