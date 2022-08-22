/**
Design a food rating system that can do the following:

Modify the rating of a food item listed in the system.
Return the highest-rated food item for a type of cuisine in the system.
Implement the FoodRatings class:

FoodRatings(String[] foods, String[] cuisines, int[] ratings) Initializes the system. The food items are described by foods, cuisines and ratings, all of which have a length of n.
foods[i] is the name of the ith food,
cuisines[i] is the type of cuisine of the ith food, and
ratings[i] is the initial rating of the ith food.
void changeRating(String food, int newRating) Changes the rating of the food item with the name food.
String highestRated(String cuisine) Returns the name of the food item that has the highest rating for the given type of cuisine. If there is a tie, return the item with the lexicographically smaller name.
Note that a string x is lexicographically smaller than string y if x comes before y in dictionary order, that is, either x is a prefix of y, or if i is the first position such that x[i] != y[i], then x[i] comes before y[i] in alphabetic order.

 

Example 1:

Input
["FoodRatings", "highestRated", "highestRated", "changeRating", "highestRated", "changeRating", "highestRated"]
[[["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]], ["korean"], ["japanese"], ["sushi", 16], ["japanese"], ["ramen", 16], ["japanese"]]
Output
[null, "kimchi", "ramen", null, "sushi", null, "ramen"]

Explanation
FoodRatings foodRatings = new FoodRatings(["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]);
foodRatings.highestRated("korean"); // return "kimchi"
                                    // "kimchi" is the highest rated korean food with a rating of 9.
foodRatings.highestRated("japanese"); // return "ramen"
                                      // "ramen" is the highest rated japanese food with a rating of 14.
foodRatings.changeRating("sushi", 16); // "sushi" now has a rating of 16.
foodRatings.highestRated("japanese"); // return "sushi"
                                      // "sushi" is the highest rated japanese food with a rating of 16.
foodRatings.changeRating("ramen", 16); // "ramen" now has a rating of 16.
foodRatings.highestRated("japanese"); // return "ramen"
                                      // Both "sushi" and "ramen" have a rating of 16.
                                      // However, "ramen" is lexicographically smaller than "sushi".
 

Constraints:

1 <= n <= 2 * 10^4
n == foods.length == cuisines.length == ratings.length
1 <= foods[i].length, cuisines[i].length <= 10
foods[i], cuisines[i] consist of lowercase English letters.
1 <= ratings[i] <= 10^8
All the strings in foods are distinct.
food will be the name of a food item in the system across all calls to changeRating.
cuisine will be a type of cuisine of at least one food item in the system across all calls to highestRated.
At most 2 * 10^4 calls in total will be made to changeRating and highestRated.
 */



// My Solution:
class FoodRatings {
    Map<String, TreeSet<Food>> map; // <cuisine, foodObjTreeSet>
    Map<String, Food> foodMap; // <foorName, foodObj>

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        // 哈希表 + TreeSet
        map = new HashMap<>();
        foodMap = new HashMap<>();
        int n = foods.length;
        for (int i=0; i<n; i++) {
            Food newFood = new Food(foods[i], cuisines[i], ratings[i]);
            foodMap.put(foods[i], newFood);
            TreeSet<Food> cuisineFoodPq = map.getOrDefault(cuisines[i], new TreeSet<Food>(new MyComparator()));
            cuisineFoodPq.add(newFood);
            map.put(cuisines[i], cuisineFoodPq);
        }
    }
    
    public void changeRating(String food, int newRating) {
        Food foodObj = foodMap.get(food);
        TreeSet<Food> cuisineFoodPq = map.get(foodObj.cuisine);
        
        cuisineFoodPq.remove(foodObj);
        foodObj.rate = newRating;
        cuisineFoodPq.add(foodObj);
    }
    
    public String highestRated(String cuisine) {
        TreeSet<Food> cuisineFoodPq = map.get(cuisine);
        return cuisineFoodPq.first().name;
    }
    
    class Food {
        public String name;
        public String cuisine;
        public int rate;
        
        public Food(String name, String cuisine, int rate) {
            this.name = name;
            this.cuisine = cuisine;
            this.rate = rate;
        }
    }
    
    public class MyComparator implements Comparator<Food> {
        public int compare(Food f1, Food f2) {
            if (f1.rate == f2.rate) return f1.name.compareTo(f2.name);
            return f2.rate - f1.rate;
        } 
    }
}
/**
 * Your FoodRatings object will be instantiated and called as such:
 * FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
 * obj.changeRating(food,newRating);
 * String param_2 = obj.highestRated(cuisine);
 */
