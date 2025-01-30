# Task-Management-API - Testing Guide 

This guide provides manual testing steps for the Task Management API, 
which supports task prioritization, status tracking, filtering, and secure JWT authentication.

## Pre-requisites
_**Bearer Token:**_ Authenticate using JWT before accessing user-specific endpoints.
**_Database:_** MySQL, with the database name taskdb.
**_Sample Requests:_** Available at resources/test-sample/Task Management.postman_collection.json.
Steps

## Register User: POST /user/register
Login: POST /auth/login (Obtain JWT token from the response)
Authenticate: Add the JWT token to the Authorization header for subsequent requests.
- login `POST /auth/login`
- Obtain JWT Token from the login response object
-  Add the JWT token to the `Authorization` header for the rest of the endpoint
