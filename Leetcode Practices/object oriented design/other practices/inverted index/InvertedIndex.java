// Inverted Index
import java.util.*;
import java.util.concurrent.*;

public class InvertedIndex {
    private Map<String, Set<String>> keyToTexts;
    private Map<String, Set<String>> textToKeys;
    private Set<String> ignoreWords;
    
    /* search 性能提升，非必需 */
    // private Map<Operation, Map<Integer, Set<String>>> searchCache;
    // private Map<String, Set<Integer>> textToCacheKeys;

    public InvertedIndex(String[] ignoreWords) {
        this.keyToTexts = new HashMap<>(); // ConcurrentHashMap
        this.textToKeys = new HashMap<>(); // ConcurrentHashMap
        this.ignoreWords = new HashSet<>(); // ConcurrentSkipListSet
        for (String word : ignoreWords) this.ignoreWords.add(word);

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
            if (ignoreWords.contains(word)) continue;
            keyToTexts.computeIfAbsent(word, v -> new HashSet<>()).add(text);
            textToKeys.get(text).add(word);
        }
        return true;
    }

    public boolean delete(String text) {
        if (!textToKeys.containsKey(text)) return false;

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

        Set<String> result;
        for (String keyword : keywords) {
            if (result == null) {
                result = search(keyword);
                continue;
            }

            if (operation.equals(Operation.OR)) result.addAll(search(keyword));
            if (operation.equals(Operation.AND)) result.retainAll(search(keyword));
        }

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
