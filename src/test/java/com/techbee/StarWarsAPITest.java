package com.techbee;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StarWarsAPITest {

    @Test
    public void testPeopleEndpointResponse() {
        Response response = RestAssured.get("https://swapi.dev/api/people/");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void testPeopleWithHeightGreaterThan100() {
        //Greater than 200 I only get one
        //Greater than 100 I get 8 so I'm working with that
        Response response = RestAssured.get("https://swapi.dev/api/people/");
        int actualCount = response.path("results.findAll { it.height.toFloat() > 100 }.size()");
        int expectedCount = 8;
        Assert.assertEquals(actualCount, expectedCount);
    }

    @Test
    public void testSpecificIndividuals() {
        String[] expectedIndividuals = {
            "Luke Skywalker","C-3PO", "Darth Vader", "Leia Organa", "Owen Lars", "Beru Whitesun lars", "Biggs Darklighter", "Obi-Wan Kenobi"
        };

        Response response = RestAssured.get("https://swapi.dev/api/people/");
        JsonPath jsonPath = response.jsonPath();
        List<String> actualIndividuals = jsonPath.getList("results.findAll { it.height.toFloat() > 100 }.name");

        Assert.assertEquals(actualIndividuals.toArray(new String[0]), expectedIndividuals);
    }

    @Test
    public void testTotalPeopleCount() {
        Response response = RestAssured.get("https://swapi.dev/api/people/");
        JsonPath jsonPath = response.jsonPath();

        int expectedTotalCount = 82; // Adjust the expected total count according to the API data
        int actualTotalCount = jsonPath.getInt("count");

        Assert.assertEquals(actualTotalCount, expectedTotalCount, "Expected total count of people");
    }

}
