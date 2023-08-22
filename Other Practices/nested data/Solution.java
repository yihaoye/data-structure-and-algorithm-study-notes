import java.util.*;

public class NestedData {
    public Object data; // can be Map, List, String
    public String rowData;

    public NestedData(String data) {
        this.data = parseData(data);
        this.rowData = data;
    }

    private Object parseData(String input) {
        if (input.startsWith("{") && input.endsWith("}")) {
            Map<String, NestedData> map = new HashMap<>();
            String tmp = input.substring(1, input.length() - 1);
            while (true) {
                int keyEndIndex = tmp.indexOf(":");
                int valueEndIndex = findValueIndex(tmp, keyEndIndex); // use stack to find the end index of value which before the first cooresponding "," or "}"
                String key = tmp.substring(0, keyEndIndex);
                String value = tmp.substring(keyEndIndex + 1, valueEndIndex);
                map.put(key, new NestedData(value));
                if (valueEndIndex == tmp.length() - 1) break;
                tmp = tmp.substring(valueEndIndex + 1);
            }
            return map;
        } else if (input.startsWith("[") && input.endsWith("]")) { // must be ["", "", ""] or [{}, {}] or [[], []]
            List<NestedData> list = new ArrayList<>();
            String tmp = input.substring(1, input.length() - 1);
            while (true) {
                int valueEndIndex = findValueIndex(tmp, 0);
                String value = tmp.substring(0, valueEndIndex);
                list.add(new NestedData(value));
                if (valueEndIndex == tmp.length() - 1) break;
                tmp = tmp.substring(valueEndIndex + 1);
            }
            return list;
        } else { // must be pure string "xxx"
            return input;
        }
    }

    public String resolvePath(List<String> path) {
        Object curr = data;
        for (String key : path) {
            if (curr instanceof Map) {
                curr = ((Map<?, ?>) curr).get(key);
            } else if (curr instanceof List) {
                int index = Integer.parseInt(key);
                if (index >= 0 && index < ((List<?>) curr).size()) {
                    curr = ((List<?>) curr).get(index);
                } else {
                    throw new RuntimeException("Invalid path");
                }
            } else { // (curr instanceof NestedData or something else) 
                throw new RuntimeException("Invalid path");
            }
        }
        if (!(curr instanceof NestedData)) throw new RuntimeException("Invalid path");
        return ((NestedData) curr).rowData;
    }

    private static int findValueIndex(String input, int startIndex) {
        int l = startIndex; int r = startIndex; int stack = 0;
        while (r < input.length() - 1) {
            if (input.charAt(r) == '{' || input.charAt(r) == '[') stack++;
            else if (input.charAt(r) == '}' || input.charAt(r) == ']') {
                stack--;
                if (stack == 0) return r;
            } else if (l == r && input.charAt(r) == '"') { // pure string here
                while (r < input.length() - 1 && input.charAt(r + 1) != '"') r++;
                return r;
            }
            r++;
        }
    }

    public static void main(String[] args) {
        String data = "{\"a\":[{\"b\":[{\"c\":\"foo\"},{\"c\":\"bar\"},{\"c\":[\"blah\",[\"baz\"]]}]}]}";
        NestedData nestedData = new NestedData(data);

        List<String> path = Arrays.asList("a", "b", "c");
        String result = nestedData.resolvePath(path);
        System.out.println(result);
    }
}
