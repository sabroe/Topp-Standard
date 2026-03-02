package com.yelstream.topp.standard.data.json.path;

import com.jayway.jsonpath.JsonPath;

import java.util.List;

public class Example {
    public static void main(String[] args) {
        String json = """
                {
                    "store": {
                        "books": [
                            {
                                "title": "JSON Primer",
                                "price": 8.99
                            },
                            {
                                "title": "XPath Guide",
                                "price": 12.50
                            }
                        ]
                    }
                }
                """;

        JsonPath cheapBooksPath = JsonPath.compile("$.store.books[?(@.price < 10)].title");
        JsonPath countPath      = JsonPath.compile("$.store.books.length()");

        // Extract all book titles where price < 10
        List<String> cheapBooks = cheapBooksPath.read(json);
        System.out.println(cheapBooks);  // → ["JSON Primer"]

        // Count books
        Integer count = countPath.read(json);  // Returns Integer for length()
        System.out.println(count);  // → 2
    }
}
