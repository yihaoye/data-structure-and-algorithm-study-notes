/**
Design a Snake game that is played on a device with screen size height x width. Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.

You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.

Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the snake eats the first piece of food.

When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body occupies after moving (i.e. a snake of length 4 cannot run into itself).

Implement the SnakeGame class:

SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and the positions of the food.
int move(String direction) Returns the score of the game after applying one direction move by the snake. If the game is over, return -1.
 

Example 1:


Input
["SnakeGame", "move", "move", "move", "move", "move", "move"]
[[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
Output
[null, 0, 0, 1, 1, 2, -1]

Explanation
SnakeGame snakeGame = new SnakeGame(3, 2, [[1, 2], [0, 1]]);
snakeGame.move("R"); // return 0
snakeGame.move("D"); // return 0
snakeGame.move("R"); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
snakeGame.move("U"); // return 1
snakeGame.move("L"); // return 2, snake eats the second food. No more food appears.
snakeGame.move("U"); // return -1, game over because snake collides with border
 

Constraints:

1 <= width, height <= 10^4
1 <= food.length <= 50
food[i].length == 2
0 <= ri < height
0 <= ci < width
direction.length == 1
direction is 'U', 'D', 'L', or 'R'.
At most 104 calls will be made to move.
 */



// My Solution with optimization:
class SnakeGame {
    Deque<int[]> snake = new LinkedList<>(), foods = new LinkedList<>();
    Set<Integer> snakeBodySet = new HashSet<>(); // optimize part, int[0] * max + int[1]
    int max = 10000, width, height;
    Map<String, int[]> dirMap = new HashMap<>();

    public SnakeGame(int width, int height, int[][] food) {
        // metrix (init with food assign 1, snake body assign -1) + deque (snake body index in metrix)
        this.width = width;
        this.height = height;

        for (int[] fIdx : food) foods.offerLast(fIdx);
        snake.offerFirst(new int[]{0, 0});
        snakeBodySet.add(arrHash(new int[]{0, 0}));

        dirMap.put("U", new int[]{-1, 0});
        dirMap.put("D", new int[]{1, 0});
        dirMap.put("L", new int[]{0, -1});
        dirMap.put("R", new int[]{0, 1});
    }
    
    public int move(String direction) { // O(1)
        int[] curDir = dirMap.get(direction); int[] curFood = foods.peekFirst();
        int[] lastHead = snake.peekFirst();
        int[] nextHead = new int[]{lastHead[0] + curDir[0], lastHead[1] + curDir[1]};
        if (nextHead[0] < 0 || nextHead[1] < 0 || nextHead[0] >= this.height || nextHead[1] >= this.width) {
            return -1;
        }
        if (curFood == null || (nextHead[0] != curFood[0] || nextHead[1] != curFood[1])) { // new pos is not cur food
            snakeBodySet.remove(arrHash(snake.pollLast()));
        }
        if (curFood != null && nextHead[0] == curFood[0] && nextHead[1] == curFood[1]) { // new pos is cur food and foods list is not empty
            foods.pollFirst();
        }
        if (!snakeBodySet.add(arrHash(nextHead))) { // check last, since allow snake head move to tail
            return -1;
        }
        // success move
        snake.offerFirst(nextHead);
        return snake.size() - 1;
    }

    public int arrHash(int[] arr) {
        return arr[0] * max + arr[1];
    }
}



// My Solution:
class SnakeGame {
    Deque<int[]> snake;
    Deque<int[]> foods;
    Map<String, int[]> dirMap;
    int width;
    int height;

    public SnakeGame(int width, int height, int[][] food) {
        // metrix (init with food assign 1, snake body assign -1) + deque (snake body index in metrix)
        this.width = width;
        this.height = height;

        foods = new LinkedList<>();
        for (int[] fIdx : food) foods.offerLast(fIdx);

        snake = new LinkedList<>();
        snake.offerFirst(new int[]{0, 0});

        dirMap = new HashMap<>();
        dirMap.put("U", new int[]{-1, 0});
        dirMap.put("D", new int[]{1, 0});
        dirMap.put("L", new int[]{0, -1});
        dirMap.put("R", new int[]{0, 1});
    }
    
    public int move(String direction) { // O(M), M is the length of snake
        int[] curDir = dirMap.get(direction);
        int[] snakeHead = snake.peekFirst();
        int[] curFood = foods.peekFirst();
        int[] newSnakeHead = new int[]{snakeHead[0] + curDir[0], snakeHead[1] + curDir[1]};
        if (newSnakeHead[0] < 0 || newSnakeHead[1] < 0 || newSnakeHead[0] >= this.height || newSnakeHead[1] >= this.width) {
            return -1;
        }
        if (curFood == null || (newSnakeHead[0] != curFood[0] || newSnakeHead[1] != curFood[1])) { // new pos is not cur food
            snake.pollLast();
        }
        if (curFood != null && newSnakeHead[0] == curFood[0] && newSnakeHead[1] == curFood[1]) { // new pos is cur food and foods list is not empty
            foods.pollFirst();
        }
        for (int[] snakeBody : snake) {
            if (newSnakeHead[0] == snakeBody[0] && newSnakeHead[1] == snakeBody[1]) return -1;
        }
        snake.offerFirst(newSnakeHead);
        return snake.size() - 1;
    }
}
/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
