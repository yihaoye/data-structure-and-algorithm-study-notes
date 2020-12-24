https://leetcode.com/discuss/interview-question/565038/Amazon-or-Onsite-or-Package-Assignment  
  
Q: Given the amz top deals data (which included productId, timestamp, discuount), we have to return the products which will give more discount and particular time window.  
  
A: 
```java
public class TopDiscountProduct {
    public static void main(String[] args) {

        TopDiscountProduct topDiscountProduct = new TopDiscountProduct();
        int start = 9;
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1,12,13,90));
        productList.add(new Product(2,12,15,5));
        productList.add(new Product(3,12,16,90));
        productList.add(new Product(4,4,7,65));
        productList.add(new Product(5,5,9,98));
        productList.add(new Product(6,7,10,85));
        List<Integer> output = topDiscountProduct.getMaxDiscountProducts(start, productList);

        for(Integer out : output) {
            System.out.println(out);
        }

    }

    static class Product{
        Integer productId;
        Integer startTime;
        Integer endTime;
        Integer discount;

        public Product(Integer productId, Integer startTime, Integer endTime, Integer discount) {
            this.productId = productId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.discount = discount;
        }
    }

    private static List<Integer> getMaxDiscountProducts(int startTime, List<Product> productList) {

        List<Integer> result = new ArrayList<>();

        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product productOne, Product productTwo) {
                return productOne.startTime < productTwo.startTime ? -1 : productOne.startTime == productTwo.startTime ? 0 : 1;
            }
        });


        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product productOne, Product productTwo) {
                return productOne.endTime < productTwo.endTime ? -1 : productOne.endTime == productTwo.endTime ? 0 : 1;
            }
        });



        int max = Integer.MIN_VALUE;

        Map<Integer,Integer> product = new HashMap<>();

        for(Product products : productList){
            if(products.startTime >= startTime || products.endTime <= startTime) {
                product.put(products.productId, products.discount);
            }
        }

        for(Map.Entry<Integer,Integer> out : product.entrySet()) {
            if(out.getValue() >= max)
                max = out.getValue();
            result.add(out.getKey());
        }
        return result;
    }
}
```
  
If can use Priority Queue for this and maintain maxheap for discounts while adding in priority queue check the time constraints first and then add and have maxheap for discounts. This can improve the complexity as it are only adding those products which are in that time frame, worst case is NlogN but if there are only k products which come under that time frame it will be Nlogk.  
And one question: is there any limit on how many products it have to return? And is it like return sorted list?  