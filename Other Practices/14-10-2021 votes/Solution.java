import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    // winner
    // recieve list of vote(vote point: 3,2,1 up to 3 diff candidates name can be 0,1,2), 
    // return list of name(string) with total points in order
    //
    // ToDo: What about if total points is the same and still need to order? (count and compare their highest point number one by one, e.g. how many 3? if equals then compare how many 2? and so on)
    public static void main(String[] args) {

        /* Test Cases */

        ArrayList<Vote> votes = new ArrayList<>();
        
        Vote vote1 = new Vote();
        vote1.add("b");
        vote1.add("a");
        vote1.add("c");
        vote1.add("d");
        vote1.add("e");
        try {
            vote1.add("f");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        Vote vote2 = new Vote();
        vote2.add("c");
        vote2.add("b");
        
        Vote vote3 = new Vote();
        vote3.add("1");
        vote3.add("2");
        vote3.add("3");
        vote3.add("4");
        vote3.add("c");
        
        votes.add(vote1);
        votes.add(vote3);
        votes.add(vote2);

        /* Test Cases */
        
        HashMap<String, Integer> res = count(votes);
        Map sortedRes = valueSort(res);
        Set set = sortedRes.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry mp = (Map.Entry)i.next();
            System.out.println(mp.getKey() + ": " + mp.getValue());
        }
    }

    public static HashMap<String, Integer> count(ArrayList<Vote> votes) {
        HashMap<String, Integer> res = new HashMap<>();
        for (Vote vote : votes) {
            for (Map.Entry<String, Integer> entry : vote.map.entrySet()) {
                String name = entry.getKey();
                Integer point = entry.getValue();
                if (res.containsKey(name)) {
                    res.put(name, res.get(name) + point);
                } else {
                    res.put(name, point);
                }
            }
        }
        return res;
    }

    public static <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map) {
        // Static Method with return type Map and extending comparator class which compares values associated with two keys
        Comparator<K> valueComparator = new Comparator<K>() {
            // return comparison results of values of two keys
            public int compare(K k1, K k2) {
                int comp = map.get(k2).compareTo(map.get(k1));
                if (comp == 0) return 1;
                else return comp;
            }
        };
        
        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }
}

class Vote {
    public int point;
    public HashMap<String, Integer> map;
    public Vote() {
        point = 5;
        map = new HashMap<>();
    }
    
    public void add(String name) {
        if (point <= 0) {
            throw new RuntimeException("Out of max number of candidates.");
        }
        map.put(name, point);
        point--;
    }
}
