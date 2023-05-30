String expression = "9/10+3/8-5/6";
Scanner sc = new Scanner(expression).useDelimiter("/|(?=[-+])"); // 正则表达式
while (sc.hasNext()) {
    int a = sc.nextInt(), b = sc.nextInt();
    System.out.println(a + "/" + b);
}
