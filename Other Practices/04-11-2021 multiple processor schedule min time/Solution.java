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



// 一开始的思路是排序加贪婪算法，每次排序得出最大能力值的处理器然后用 processes 减去该值，然后把该处理器能力值减半，
// 然后重复上述过程直到 processes <= 0，返回循环的次数即是最小时间。
// 然而该思路在每次循环都执行一次排序，因此在测试中数据量较大的情况下时间复杂度不符合性能要求
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
        return res;
    }

    public static void main() {
        // ...
    }
}



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
        
        return res;
    }

    public static void main() {
        // ...
    }
}
