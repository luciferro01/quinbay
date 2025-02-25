import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Q1. -> Given a list of strings, write a stream operation to convert all strings to lowercase.
        //Ans. ->
        List<String> list = List.of("Hello", "World", "Java", "Stream");
        list.stream().map(String::toLowerCase).forEach(System.out::println);


        //Q2. -> Filter a list of integers to keep only the odd numbers.
        //Ans. ->
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.stream().filter(n -> n % 2 != 0).forEach(System.out::println);

        //Q3. -> Find the product of all integers in a list using streams.
        //Ans. ->
        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);

        //Q4. -> Check if any string in a list starts with the letter "a".
        //Ans. ->
        List<String> list2 = List.of("Apple", "Banana", "Mango", "Orange");
        System.out.println(list2.stream().anyMatch(s -> s.toLowerCase().startsWith("a")));

        //Q5. -> Find the shortest string in a list of strings.
        //Ans. ->
        System.out.println(list2.stream().min((s1, s2) -> s1.length() - s2.length()).get());

    }
}