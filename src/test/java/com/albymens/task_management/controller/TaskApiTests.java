package com.albymens.task_management.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskApiTests {

    private static String username;
    private static String password;
    private static String token;
    private static String taskId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        username = "user" + System.currentTimeMillis();
        password = "password123";
    }

    @Test
    public void testRegisterUser() {
        String payload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/user/register")
                .then()
                .statusCode(201)
                .extract().response();

        assertThat(response.jsonPath().getString("message"), equalTo("User created successfully"));
    }

    @Test
    public void testLoginUser() {
        Response response = given()
                .header("username", username)
                .header("password", password)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(201)
                .extract().response();

        token = response.jsonPath().getString("data.token");

        assertThat(response.jsonPath().getString("message"), equalTo("Welcome!! You've Login successfully"));
    }

    @Test
    public void testCreateTask() {
        String taskPayload = "{ \"name\": \"New Task\", \"description\": \"Description of the task\", " +
                "\"deadline\": \"2025-01-31\", \"priority\": \"HIGH\", \"status\": \"PENDING\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(taskPayload)
                .when()
                .post("/task/add")
                .then()
                .statusCode(200)
                .extract().response();

        taskId = response.jsonPath().getString("data.taskId");

        assertThat(response.jsonPath().getString("message"), equalTo("Task created successfully"));
    }

    @Test
    public void testGetTasks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tasks")
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("tasks[0].name"), equalTo("New Task"));
    }

    @Test
    public void testUpdateTask() {
        String updatePayload = "{ \"name\": \"Updated Task\", \"description\": \"Updated description\", " +
                "\"deadline\": \"2025-02-28\", \"priority\": \"MEDIUM\", \"status\": \"IN_PROGRESS\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .header("username", username)
                .body(updatePayload)
                .when()
                .put("/task/" + taskId)
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("message"), equalTo("Task updated successfully"));
    }

    @Test
    public void testDeleteTask() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("username", username)
                .when()
                .delete("/task/" + taskId)
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("message"), equalTo("Task deleted successfully"));
    }
}
