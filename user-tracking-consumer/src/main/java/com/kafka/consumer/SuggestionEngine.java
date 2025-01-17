package com.kafka.consumer;


import com.kafka.consumer.model.PreferredProduct;
import com.kafka.consumer.model.User;
import com.kafka.consumer.service.UserDB;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SuggestionEngine {

    private final UserDB userDB = new UserDB();

    public void processSuggestions(String userId, String product) {
        String[] valueSplit = product.split(",");
        String productType = valueSplit[0];
        String productColor = valueSplit[1];
        String productDesign = valueSplit[2];

        log.info("User with ID: " + userId +
                " showed interest over " + productType + " " +
                "of color " + productColor + " and design " + productDesign);

        // Retrieve preferences from Database
        User user = userDB.findByID(userId);

        // Update user preferences
        user.getPreferences()
                .add(new PreferredProduct(productColor, productType, productDesign));

        // Generate list of suggestions
        List<String> suggestions = generateSugestions(user.getPreferences());
        log.info("Suggestions for user " + user + " ====> " + suggestions);
        user.setSuggestions(suggestions);

        // Store the suggestions in the database / send them to a kafka topic
        userDB.save(user);
    }

    /**
     * @return hardcoded suggestions
     */
    private List<String> generateSugestions(List<PreferredProduct> preferences) {
        return Arrays.asList("TSHIRT,BLUE",
                "DESIGN,ORANGE,ROCKET",
                "TSHIRT,PURPLE,CAR");
    }

}