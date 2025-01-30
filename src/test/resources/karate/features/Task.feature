Feature: Task API Integration test
  Background:
    * url 'http://localhost:8080'
    * def username = 'Alby'

  Scenario: Create a new task
    Given path '/task/add'
    And header username = username
    And request { name: 'New Task', description: 'Description of the task', deadline: '2025-01-31', priority: 'HIGH', status: 'PENDING' }
    When method post
    Then status 200
    And match response.message == 'Task created successfully'

  Scenario: Retrieve user tasks
    Given path '/'
    And header username = username
    When method get
    Then status 200
    And match response.tasks[0].name == 'New Task'

  Scenario: Update a task
    Given path '/{taskId}'
    And header username = username
    And request { name: 'Updated Task', description: 'Updated description', deadline: '2025-02-28', priority: 'MEDIUM', status: 'IN_PROGRESS' }
    When method put
    Then status 200
    And match response.message == 'Task updated successfully'

  Scenario: Delete a task
    Given path '/{taskId}'
    And header username = username
    When method delete
    Then status 200
    And match response.message == 'Task deleted successfully'

  Scenario: Filter tasks
    Given path '/filter'
    And header username = username
    And param status = 'PENDING'
    And param priority = 'HIGH'
    When method get
    Then status 200
    And match response.tasks[0].status == 'PENDING'
    And match response.tasks[0].priority == 'HIGH'