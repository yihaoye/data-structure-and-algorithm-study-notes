import java.util.*;

public class RandomHashMap<K, V> { // by ChatGPT
    private ArrayList<K> keys;
    private HashMap<K, Integer> keyIndexMap; // 用于存储键在 keys 中的索引
    private HashMap<K, V> map;
    private Random random;

    public RandomHashMap() {
        keys = new ArrayList<>();
        keyIndexMap = new HashMap<>();
        map = new HashMap<>();
        random = new Random();
    }

    public void put(K key, V value) { // O(1)
        if (!map.containsKey(key)) {
            keys.add(key);
            keyIndexMap.put(key, keys.size() - 1); // 存储键的索引
        }
        map.put(key, value);
    }

    public V get(K key) { // O(1)
        return map.get(key);
    }

    public V getRandom() { // O(1)
        if (keys.isEmpty()) return null;

        int randomIndex = random.nextInt(keys.size());
        return map.get(keys.get(randomIndex));
    }

    public void remove(K key) { // O(1)
        if (!map.containsKey(key)) return;

        int indexToRemove = keyIndexMap.get(key);
        K lastKey = keys.get(keys.size() - 1);

        keys.set(indexToRemove, lastKey); // 将要删除的键与最后一个键交换位置
        keyIndexMap.put(lastKey, indexToRemove);
        
        keys.remove(keys.size() - 1); // 删除最后一个键
        keyIndexMap.remove(key);
        map.remove(key);
    }
}
