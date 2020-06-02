struct trie {
  int next[100000][26], cnt;
  bool exist[100000];  // 该结点结尾的字符串是否存在

  void insert(char *s, int l) {  // 插入字符串
    int p = 0;
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