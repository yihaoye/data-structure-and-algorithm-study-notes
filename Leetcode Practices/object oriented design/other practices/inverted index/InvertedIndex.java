// Inverted Index
import java.util.*;
import java.util.concurrent.*;

public class InvertedIndex {
    // 本例用哈希表实现。
    // 如果进阶搜索功能要考虑关键词的顺序甚至关心重复次数的话，可以改为使用 Map<String, Map<String, List<Integer>>> textToKeys，
    // 其中 Map<String, List<Integer>> 的 String 是关键词，List<Integer> 为关键词 key 在 text 中的位置/索引，因为会在解析关键词添加进 List 时保证有序所以可以二分搜索，
    // 具体过程为已记录上一个关键词（lastKey）在匹配的该 text 中的位置 idx，
    // 然后在 textToKeys.get(text).get(nextKey) 即 List<Integer> 中二分搜索大于 idx 的最小值，如果找到则设为新的 idx 且继续，否则该 text 不匹配直接 break。
    // 具体实现可以参考 search(String[] keywords, Operation operation) 方法中的注释，另外其实还可以进一步优化逻辑提升性能，但是会增加代码复杂度，这里就不展开了。
    // 关心顺序与重复的话也可以用字典树（Trie）实现，但是时空复杂度都会更高，而且最差可能要遍历整个字典树，所以不推荐。
    private Map<String, Set<String>> keyToTexts;
    private Map<String, Set<String>> textToKeys; // 用于提高删除效率和进阶场景如关心顺序和重复次数
    private Set<String> stopWords; // 停用词表，反向索引通常会忽略一些常用词，例如 a, the, is, are, will, can, with, etc
    
    /* search 性能提升，非必需 */
    // private Map<Operation, Map<Integer, Set<String>>> searchCache;
    // private Map<String, Set<Integer>> textToCacheKeys;

    public InvertedIndex(String[] stopWords) {
        this.keyToTexts = new HashMap<>(); // ConcurrentHashMap
        this.textToKeys = new HashMap<>(); // ConcurrentHashMap
        this.stopWords = new HashSet<>(); // ConcurrentSkipListSet
        for (String word : stopWords) this.stopWords.add(word);

        /* search 性能提升，非必需 */
        // searchCache = new HashMap<>(); // ConcurrentHashMap
        // searchCache.put(Operation.AND, new HashMap<>()); // ConcurrentHashMap
        // searchCache.put(Operation.OR, new HashMap<>()); // ConcurrentHashMap
        // textToCacheKeys = new HashMap<>(); // ConcurrentHashMap
    }

    public boolean insert(String text) {
        if (textToKeys.containsKey(text)) return false;
        textToKeys.put(text, new HashSet<>());
        
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (stopWords.contains(word)) continue;
            keyToTexts.computeIfAbsent(word, v -> new HashSet<>()).add(text);
            textToKeys.get(text).add(word);
        }
        return true;
    }

    public boolean delete(String text) {
        if (!textToKeys.containsKey(text)) return false; // 可酌情根据要求改为抛出错误

        for (String word : textToKeys.get(text)) {
            keyToTexts.get(word).remove(text);
        }
        textToKeys.remove(text);

        /* 使用缓存提高性能，非必需，但是注意要引入对 operation 和 delete 的考虑 */
        // for (int hashCode : textToCacheKeys.get(text)) {
        //     searchCache.get(Operation.AND).get(hashCode).remove(text);
        //     searchCache.get(Operation.OR).get(hashCode).remove(text);
        // }
        // textToCacheKeys.remove(text);

        return true;
    }

    public Set<String> search(String keyword) {
        Set<String> result = new HashSet<>();
        if (keyToTexts.containsKey(keyword)) {
            result.addAll(keyToTexts.get(keyword));
        }
        return result;
    }

    public Set<String> search(String[] keywords, Operation operation) {
        if (keywords == null || keywords.length == 0) return new HashSet<>();

        /* 使用缓存提高性能，非必需，但是注意要引入对 operation 和 delete 的考虑 */
        // int hashCode = Arrays.hashCode(keywords); // Arrays.hashCode() 方法会对数组中的每个元素进行哈希运算，然后将这些哈希值进行组合。具体的计算方式涉及到位运算和混合运算，是一种复杂的方法。这种方式保证了元素相同但顺序不同的数组会得到相同的哈希值
        // if (searchCache.get(operation).containsKey(hashCode)) return searchCache.get(operation).get(hashCode);

        Set<String> result = search(keywords[0]);
        for (String keyword : keywords) {
            if (operation.equals(Operation.OR)) result.addAll(search(keyword));
            if (operation.equals(Operation.AND)) result.retainAll(search(keyword));
        }

        /* 如果关心顺序和重复次数 */
        // for (String text : result) {
        //     Integer idx = -1;
        //     for (String keyword : keywords) {
        //         Integer nextIdx = textToKeys.get(text).get(keyword).ceilingKey(idx);
        //         if (nextIdx == null || nextIdx <= idx) {
        //             result.remove(text);
        //             break;
        //         }
        //         idx = nextIdx;
        //     }
        // }

        /* 使用缓存提高性能，非必需，但是注意要引入对 operation 和 delete 的考虑 */
        // searchCache.get(operation).put(hashCode, result);
        // for (String text : result) {
        //     textToCacheKeys.computeIfAbsent(text, v -> new HashSet<>()).add(hashCode);
        // }
        
        return result;
    }
}

enum Operation {
    AND, OR;
}
