import java.util.*;

public class LSMTree<K extends Comparable<K>, V> {
    private BloomFilter<K> bloomFilter; // 布隆过滤器
    private TreeMap<K, V> memTable; // 内存表
    private List<TreeMap<K, V>> sstables; // 排序的磁盘文件列表
    private int maxMemTableSize; // 内存表大小上限
    private List<Integer> sstableSizeThresholds;  // 合并磁盘文件的大小阈值 - sstableSizeThreshold 通常会根据 SSTable 的层级来调整，通常越底层的 SSTable 的大小上限可以设置得更大，因为底层数据更多，为了减少合并操作的频率，可以容忍更大的大小。

    public LSMTree(int maxMemTableSize, int sstableSizeThreshold) { // sstableSizeThreshold is biggest size of sstable
        this.bloomFilter = new BloomFilter<>();
        this.memTable = new TreeMap<>();
        this.sstables = new ArrayList<>();
        this.sstables.add(new TreeMap<>());
        this.maxMemTableSize = maxMemTableSize;
        this.sstableSizeThresholds = new ArrayList<>();
        this.sstableSizeThresholds.add(sstableSizeThreshold);
    }

    // 插入键值对到内存表
    public void put(K key, V value) {
        memTable.put(key, value);
        if (memTable.size() >= maxMemTableSize) merge();
        bloomFilter.add(key);
    }

    // 删除键值，只能标记删除，实际中要更复杂一些，需要在后续合并时处理，而且布隆过滤器也不好处理删除操作
    public void remove(K key) {
        put(key, null);
    }

    // 从表中获取值
    public V get(K key) {
        if (!bloomFilter.mightContain(key)) return null;
        V value = memTable.get(key);
        if (value == null) {
            for (int i = sstables.size() - 1; i >= 0; i--) { // 从上往下找，越上方的数据才是越新的数据
                TreeMap<K, V> sstable = sstables.get(i);
                value = sstable.get(key);
                if (value != null) break;
            }
        }
        return value;
    }

    // 合并内存表数据到磁盘文件
    private void merge() {
        if (sstables.get(sstables.size() - 1).size() >= sstableSizeThresholds.get(sstables.size() - 1)) sstables.add(new TreeMap<>());
        if (sstables.size() > sstableSizeThresholds.size()) sstableSizeThresholds.add(sstableSizeThresholds.get(sstableSizeThresholds.size() - 1) / 2); // sstableSizeThreshold 可以设置得大一点以允许较多层数，实际上层数也应该设置一个极限，这里为了简单起见就略过了
        sstables.get(sstables.size() - 1).putAll(memTable);
        memTable.clear();

        // 根据磁盘文件大小阈值执行合并策略（实际中有两种不同的策略：size-tiered 策略、leveled 策略）
        while (sstables.size() >= 2 && sstables.get(sstables.size() - 1).size() >= sstableSizeThresholds.get(sstables.size() - 1)) {
            mergeSSTables();
        }
    }

    // 合并磁盘文件（实际中磁盘不使用 TreeMap 但是 SSTables 仍通常会采用类似归并排序的方法合并，因为 SSTables 实际用排序字符串表 - 有序键值对集合，所以合并策略效率很好）
    // 另外下面处理发生冲突时只是简单的采用更新的数据覆盖旧数据，实际中可能有更复杂的策略提供
    private void mergeSSTables() { // or named: compact()
        TreeMap<K, V> sstable = sstables.remove(sstables.size() - 1);
        sstables.get(sstables.size() - 1).putAll(sstable);
    }
}
