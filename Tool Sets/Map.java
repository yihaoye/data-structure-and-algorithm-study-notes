// TreeMap.subMap
TreeMap<Integer, ArrayList<Integer>> tmap = new TreeMap<>();
// subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) Returns a view of the portion of this map whose keys range from fromKey to toKey.
Collection<List<Integer>> lists = tmap.subMap(0, true, Integer.MAX_VALUE, true).values();

// TreeMap.firstKey
int firstKey = tmap.firstKey();



// General map methods
/*
void	clear()
Removes all of the mappings from this map (optional operation).

default V	compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
Attempts to compute a mapping for the specified key and its current mapped value (or null if there is no current mapping).

default V	computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
If the specified key is not already associated with a value (or is mapped to null), attempts to compute its value using the given mapping function and enters it into this map unless null.

default V	computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
If the value for the specified key is present and non-null, attempts to compute a new mapping given the key and its current mapped value.

boolean	containsKey(Object key)
Returns true if this map contains a mapping for the specified key.

boolean	containsValue(Object value)
Returns true if this map maps one or more keys to the specified value.

Set<Map.Entry<K,V>>	entrySet()
Returns a Set view of the mappings contained in this map.

boolean	equals(Object o)
Compares the specified object with this map for equality.

default void	forEach(BiConsumer<? super K,? super V> action)
Performs the given action for each entry in this map until all entries have been processed or the action throws an exception.

V	get(Object key)
Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.

default V	getOrDefault(Object key, V defaultValue)
Returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key.

int	hashCode()
Returns the hash code value for this map.

boolean	isEmpty()
Returns true if this map contains no key-value mappings.

Set<K>	keySet()
Returns a Set view of the keys contained in this map.

default V	merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction)
If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value.

V	put(K key, V value)
Associates the specified value with the specified key in this map (optional operation).

void	putAll(Map<? extends K,? extends V> m)
Copies all of the mappings from the specified map to this map (optional operation).

default V	putIfAbsent(K key, V value)
If the specified key is not already associated with a value (or is mapped to null) associates it with the given value and returns null, else returns the current value.

V	remove(Object key)
Removes the mapping for a key from this map if it is present (optional operation).

default boolean	remove(Object key, Object value)
Removes the entry for the specified key only if it is currently mapped to the specified value.

default V	replace(K key, V value)
Replaces the entry for the specified key only if it is currently mapped to some value.

default boolean	replace(K key, V oldValue, V newValue)
Replaces the entry for the specified key only if currently mapped to the specified value.

default void	replaceAll(BiFunction<? super K,? super V,? extends V> function)
Replaces each entry's value with the result of invoking the given function on that entry until all entries have been processed or the function throws an exception.

int	size()
Returns the number of key-value mappings in this map.

Collection<V>	values()
Returns a Collection view of the values contained in this map.
*/

