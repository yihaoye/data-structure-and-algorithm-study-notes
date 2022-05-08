/**
 * Workday 笔试
 * 
 * 假设有 N 个处理器，每个处理器的能力不一（以 int 数据表示，变量名为 abilities[i]），
 * 现有给定任务量（以 long 数据表示，变量名为 processes），每次由一个处理器处理一部分任务（processes - abilities[i]），
 * 处理器使用一次后其处理能力减半（向下取整 floor: abilities[i] = abilities[i]/2），问最小处理时间为多少？
 * 
 * 例子：
 * 处理器数组，元素为其初始处理能力 abilities: [3, 1, 2, 7, 4]
 * 任务 processes: 15L
 * 
 * 最优如下：
 * 1. 使用 abilities[3]，剩余任务 = 15 - 7 = 8，abilities[3] = 7 / 2 = 3，abilities: [3, 1, 2, 3, 4]
 * 2. 使用 abilities[4]，剩余任务 = 8 - 4 = 4，abilities[4] = 4 / 2 = 2，abilities: [3, 1, 2, 3, 2]
 * 3. 使用 abilities[0]，剩余任务 = 4 - 3 = 1，abilities[0] = 3 / 2 = 1，abilities: [1, 1, 2, 3, 2]
 * 4. 剩余任务为 1，此时数组里的任意处理器都能处理完
 * 
 * 所以最小处理时间为 4.
 */



// 方案一
// 一开始的思路是排序加贪婪算法，每次排序得出最大能力值的处理器然后用 processes 减去该值，然后把该处理器能力值减半，
// 然后重复上述过程直到 processes <= 0，返回循环的次数即是最小时间。
// 然而该思路在每次循环都执行一次排序，因此在测试中数据量较大的情况下时间复杂度 O(res*N*logN) 不符合性能要求（后面得知 res <= N*logM，所以最坏情况下总时间复杂度为 O(N^2*logN*logM)）
public class Solution {
    public static int minTime(List<Integer> abilities, Long processes) {
        int res = 0;

        while (processes > 0L) {
            Collections.sort(abilities, Collections.reverseOrder());
            Long maxL = new Long(abilities.get(0));
            processes = processes - maxL;
            abilities.set(0, abilities.get(0)/2);
            res++;
        }

        return processes > 0L ? -1 : res;
    }

    public static void main() {
        // ...
    }
}



// 方案二
// 优化思路，为什么上面每次都重新排序，重复排序是因为在原元素上整除了 2，整除 2 是因为元素值每次只能使用一次，
// 而且需要得出每次整除 2 后的数值以供后续可能的使用，而整除 2 后顺序可能改变
// 
// 其实没必要每次都重新排序，方法在于对每个初始元素算出对其遍历整除 2 的所有可能结果，比如初始元素为 7，则所有结果为 3, 1, 0，因为 0 没有作用，所以只把 3 和 1 都 append 到原数组，
// 而更精简的实现是，一开始遍历数组，每个元素整除 2 的结果若大于 0 则 append 到数组，否则跳过到下一个元素，直到执行到数组最后一个元素且其整除 2 的结果不大于 0，
// 然后才对新的数组进行一次排序，此后再不需排序，然后再次 for 循环遍历该排序的新数组，processes = processes - abilities[i] 直到 <= 0L。
public class Solution {
    public static int minTime(List<Integer> abilities, Long processes) {
        int res = 0;

        Iterator<Integer> iterator = abilities.iterator();
        while (iterator.hasNext()) {
            Integer ability = iterator.next();
            ability = ability / 2;
            if (ability > 0) abilities.add(ability);
        }

        Collections.sort(abilities, Collections.reverseOrder());
        for (Integer ability : abilities) {
            Long temp = new Long(ability);
            processes = processes - temp;
            res++;
            if (processes <= 0L) return res;
        }
        
        return processes > 0L ? -1 : res;
    }

    public static void main() {
        // ...
    }
}



// 方案三
// 上面的改进是以空间换时间的思路，最坏情况下每个初始 ability 都极大，假设每个初始 ability 均为 M，abilities 长度为 N，则全部展开的空间复杂度为 N*logM
// 而此时进行排序则时间复杂度为 X*logX（X = N*logM），而最后的贪婪算法部分时间复杂度为 O(res)，总时间复杂度为 Max(X*logX, res)，但是因为 res 肯定 <= N*logM（若队列结束仍未减至 0 则直接停止），所以时间复杂度为更大的 X*logX
// 是否还有其他改进办法？可以尝试一下 贪婪算法 + 跳表 ConcurrentSkipListMap 或红黑树 TreeMap（增、删、查均为 O(logN)，且使用跳表或红黑树后无需上面的展开策略，因为跳表或红黑树直接更新性能 O(logN) 可接受）
// 因为跳表或红黑树每个操作为 O(logN)，创建该跳表的时间复杂度必小于 O(N*logN)，贪婪算法部分时间复杂度为 O(res*logN)，因为上面得出 res <= N*logM，所以(贪婪+(跳表或红黑树))方案的总最差时间复杂度为 O(N*logM*logN)，空间复杂度为 O(N)，双复杂度均比方案二有所提高
public class Solution {
    public static int minTime(List<Integer> abilities, Long processes) {
        int res = 0;

        ConcurrentSkipListMap<Integer, Integer> skipList = new ConcurrentSkipListMap<>().descendingMap(); // 比 new ConcurrentSkipListMap<>(Collections.reverseOrder()); 快一点
        // TreeMap<Integer, Integer> rbTree = new TreeMap<>().descendingMap(); // 或者可以使用红黑树，单线程情况下红黑树 TreeMap 性能比 ConcurrentSkipListMap 好，这里选择 ConcurrentSkipListMap 只是为了展示 Java 中跳表如何使用
        for (Integer ability : abilities) {
            skipList.put(ability, skipList.getOrDefault(ability, 0) + 1);
        }

        while (!skipList.isEmpty()) {
            Map.Entry<Integer, Integer> abilityCountEntry = skipList.firstEntry();
            Long ability = new Long(abilityCountEntry.getKey());
            Long count = new Long(abilityCountEntry.getValue());
            if (processes > (ability * count)) {
                processes -= (ability * count);
                res += count;
                skipList.remove(ability);
                ability = ability / 2;
                skipList.put(ability, skipList.getOrDefault(ability, 0) + count);
            } else {
                res += (processes / ability);
                if (processes % ability != 0L) res += 1;
                return res;
            }
        }

        return processes > 0L ? -1 : res;
    }

    public static void main() {
        // ...
    }
}



// 方案四
// 更简明的一个方案是 abilities 全部展开（记录后续所有可能 ability）并使用桶排序，但是如果输入为比如 [1, 3, 10000, 2] 时，会浪费掉 9996 个桶元素空间（数组法），而且后续从高往低遍历时性能也不好（需检查是否一大段均为 0）。
// 此时桶排序法可以使用一个 HashMap 和一个 TreeMap 来优化
// 空间复杂度 O(M) - M 为 Max(abilities.get(i))，不精确，因为不同数除 2 可能互相填充或不填充缝隙但必小于 M，时间复杂度 O(N+M*logM+M) => O(N+M*logM)，贪婪算法部分最坏为 M 即 tMap 全部遍历完
public class Solution {
    public static int minTime(List<Integer> abilities, Long processes) {
        int res = 0;

        Map<Integer, Integer> hMap = new HashMap<>();
        Iterator<Integer> iterator = abilities.iterator();
        while (iterator.hasNext()) {
            Integer ability = iterator.next();
            while (ability > 0) {
                hMap.put(ability, hMap.getOrDefault(ability, 0) + 1);
                ability = ability / 2;
            }
        }

        TreeMap<Integer, Integer> tMap = new TreeMap<>().descendingMap(); // new TreeMap<>().descendingMap() 性能比 new TreeMap<>(Collections.reverseOrder()) 快一点
        tMap.putAll(hMap);

        for (Map.Entry<Integer, Integer> abilityCountEntry : tMap.entrySet()) {
            Long ability = new Long(abilityCountEntry.getKey());
            Long count = new Long(abilityCountEntry.getValue());
            if (processes > (ability * count)) {
                processes -= (ability * count);
                res += count;
            } else {
                res += (processes / ability);
                if (processes % ability != 0L) res += 1;
                return res;
            }
        }
        
        return processes > 0L ? -1 : res;
    }

    public static void main() {
        // ...
    }
}



// 总结
// 若 N >> M 时使用方案四，若 M >> N 时使用方案三
