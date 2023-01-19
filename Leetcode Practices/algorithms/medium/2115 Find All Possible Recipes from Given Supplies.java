/**
You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. Ingredients to a recipe may need to be created from other recipes, i.e., ingredients[i] may contain a string that is in recipes.

You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.

Return a list of all the recipes that you can create. You may return the answer in any order.

Note that two recipes may contain each other in their ingredients.

 

Example 1:

Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
Example 2:

Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
Example 3:

Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".
 

Constraints:

n == recipes.length == ingredients.length
1 <= n <= 100
1 <= ingredients[i].length, supplies.length <= 100
1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
All the values of recipes and supplies combined are unique.
Each ingredients[i] does not contain any duplicate values.
 */



// My Solution:
class Solution {
    Map<String, Set<String>> indegree; // <itemA, <pre-requestA-of-itemA, pre-requestB-of-itemA, ...>>
	Map<String, Set<String>> outdegree; // <pre-requestA, <itemA-relys-pre-requestA, itemB-relys-pre-requestA, ...>>

    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        init(recipes, ingredients, supplies);
		return topoSort();
    }

	private void init(String[] recipes, List<List<String>> ingredients, String[] supplies) {
		indegree = new HashMap<>();
		outdegree = new HashMap<>();
        Set<String> filter = new HashSet<>();
        for (String supply : supplies) filter.add(supply);

		for (int i=0; i<recipes.length; i++) {
			indegree.computeIfAbsent(recipes[i], x -> new HashSet<>());
            outdegree.computeIfAbsent(recipes[i], x -> new HashSet<>());

            for (String ingredient : ingredients.get(i)) {
                if (!filter.contains(ingredient)) indegree.get(recipes[i]).add(ingredient);
                outdegree.computeIfAbsent(ingredient, x -> new HashSet<>()).add(recipes[i]);
            }
		}
	}

	private String findAvailableNode() { // available node means node has no pre-request
		for (Map.Entry<String, Set<String>> entry : indegree.entrySet()) {
			if (entry.getValue().size() == 0) return entry.getKey();
		}
		return null;
	}

	private boolean removeNode(String node) { // normally this is used for remove pre-request
		if (!outdegree.containsKey(node)) return false;
		Set<String> relyers = outdegree.get(node);
		for (String relyer : relyers) {
			indegree.get(relyer).remove(node);
		}
		outdegree.remove(node);
		indegree.remove(node);
		return true;
	}

	private List<String> topoSort() {
		List<String> sortedList = new ArrayList<>();
		while (indegree.size() > 0) {
			String nextAvailable = findAvailableNode();
			if (nextAvailable == null) return sortedList;
			sortedList.add(nextAvailable);
			removeNode(nextAvailable);
		}

		return sortedList;
	}
}
