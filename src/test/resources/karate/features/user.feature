Feature: User Integration Test

  Background:
    * url 'http://localhost:8080'
    * def faker = Java.type('net.datafaker.Faker')
    * def fake = new faker()
    * def username = fake.name().username()
    * def password = fake.internet().password(8, 16, true)
    * def id = fake.number().randomNumber(10, true)

  Scenario: Register User
      Given path '/user/register'
      And request {id: id, username: username, password: password}
      When method post
      Then status 201
      And match response.message == 'User created successfully'

  Scenario: User Login
    Given path '/auth/user'
    And header username = username
    And header password = password
    When method post
    Then status 201
    And match response.message == "Welcome!! You've Login successfully"

