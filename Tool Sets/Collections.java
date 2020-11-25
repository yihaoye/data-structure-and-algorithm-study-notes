List<Integer> list = new ArrayList<>(Arrays.asList(5,10,9,3,7));
Collections.min(list);
Collections.max(list);
Collections.sort(list);

int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
