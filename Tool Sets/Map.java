// TreeMap.subMap
TreeMap<Integer, ArrayList<Integer>> tmap = new TreeMap<>();
// subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) Returns a view of the portion of this map whose keys range from fromKey to toKey.
List<List<Integer>> lists = tmap.subMap(0, true, Integer.MAX_VALUE, true).values();


