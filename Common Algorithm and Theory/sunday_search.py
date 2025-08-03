# https://blog.csdn.net/q547550831/article/details/51860017

def sunday_search(text, pattern):
    """
    参数:
    text (str): 主文本串。
    pattern (str): 要查找的模式串。
    
    返回:
    int: 如果找到，返回模式串在文本中第一次出现的起始索引；否则返回 -1。
    """
    n = len(text)
    m = len(pattern)
    
    # 1. 构建跳跃表（Shift Table）
    # 存储模式串中每个字符最后一次出现的位置到模式串末尾的距离
    shift = {}
    for i in range(m):
        shift[pattern[i]] = m - i
        
    # 2. 开始匹配
    i = 0  # 主串的当前匹配起始索引
    while i <= n - m:
        j = 0  # 模式串的当前匹配索引
        # 从左到右进行匹配
        while j < m and text[i + j] == pattern[j]:
            j += 1
        # 如果模式串完全匹配
        if j == m:
            return i
        # 如果不匹配，根据 Sunday 算法规则进行跳跃
        # 检查模式串末尾对齐的下一个字符
        if i + m < n:
            next_char = text[i + m]
            # 根据跳跃表中的值进行移动，默认值（字符不在 pattern 时）是 pattern 的长度 m + 1
            i += shift.get(next_char, m + 1)
        else:
            # 已经到达主串末尾，无法再跳跃
            break
            
    return -1