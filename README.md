## [REST API](http://localhost:8080/doc)

## Concept:

- **Spring Modulith**
  - [Spring Modulith: Have we reached modularity maturity?](https://habr.com/ru/post/701984/)
  - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
  - [Spring Modulith - Reference Documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- There are two common tables without foreign keys:
  - **_Reference_** - a reference table. Connections are made using the _code_ field (not by `id` because `id` is tied to a specific environment/database).
  - **_UserBelong_** - user binding with a type (owner, lead, etc.) to an object (task, project, sprint, etc.). Foreign keys will be manually validated.

## Analogues:

- https://java-source.net/open-source/issue-trackers

## Testing:

- https://habr.com/ru/articles/259055/

List of completed tasks:
## Sensitive Information Management

Moved sensitive information to a separate properties file:

- **Database login and password**
- **identifiers for OAuth registration/authorization**
- **Mail settings**

The values for these properties are read at server startup from the machine's environment variables.

### Implementation Details:

- Created a separate file named `application-secrets.properties` to store sensitive information.
- Added the following JVM argument to ensure the file is read at startup:

Replace `${CONFIG_FILE_PATH}` with the absolute path to the `application-secrets.properties` file.
This setup ensures secure and dynamic configuration of sensitive properties.

# Switching Test Database to In-Memory H2 Database

## Overview
The test setup for the project has been migrated from using a Docker container with PostgreSQL to an in-memory H2 database. This simplifies the test environment by eliminating the dependency on Docker during testing and improves test execution speed.

## Changes Implemented
1. **Database Switch:**
  - The previous test database (`PostgreSQL-test`), which ran as a container via Docker, has been replaced by the in-memory H2 database.
  - This allows tests to execute directly in memory without requiring external dependencies.

2. **Changelog Adjustments:**
  - The `changelog_test.sql` file was updated to ensure compatibility with the H2 database syntax.
  - The `data.sql` file, which was previously a separate script for initializing test data, has been integrated into the `changelog_test.sql` file for a more streamlined setup.

3. **Application Configuration:**
  - The `application-test.yaml` file was updated to include configuration for the H2 database.
  - The configuration now specifies an in-memory H2 database instead of PostgreSQL, including the necessary driver, URL, and database properties.

# Refactoring `FileUtil#upload`

## Overview

This refactoring updates the `FileUtil` class to adopt a modern and robust approach for handling file operations within the file system. The changes aim to improve maintainability, reliability, and overall code clarity while ensuring compatibility with existing functionality.

---

## Key Improvements

### 1. **Modern File System API Usage**
The updated implementation relies entirely on the `java.nio.file` package, which is a modern alternative to the older `java.io` approach used in the previous version. Benefits include:
- Better exception handling.
- Support for symbolic links and file system providers.
- Enhanced flexibility for file operations.

### 2. **Improved File Upload Method**
**Old Approach:**
- Used `FileOutputStream` to write files, which required more boilerplate code and explicit resource management.

**New Approach:**
- Replaced `FileOutputStream` with `Files.write()` for simplicity and automatic resource management.

### 3. **Enhanced Path Normalization**
- Ensured all paths are normalized and resolved relative to the `BASE_PATH` to prevent directory traversal vulnerabilities.

### 4. **Sanitization of File Names**
- Added robust sanitization for file names using the `sanitizeFileName` method to avoid special character injection and ensure compatibility across file systems.

### 5. **Readability and Error Handling**
- Improved exception messages for better debugging.
- Ensured all created directories and files are handled safely using modern `Files.createDirectories()` and `Files.write()` methods.

# Adding Tag Management Functionality for Tasks

## Overview

This update introduces a new feature allowing the addition and management of tags for tasks using a REST API. The functionality has been implemented on the backend, focusing solely on the service and API layers. The front-end implementation is not included as per the task requirements.

---

## Key Changes

### **1. Created `TaskTagController`**
The `TaskTagController` class is located in the `task` package and exposes REST endpoints to:
- Add a tag to a specific task.
- Retrieve tags for a task.
- Remove a tag from a task.
- Update the tags associated with a task.


# Task Time Calculation: Time Spent in Progress and Testing

## Overview

This feature introduces the ability to calculate how much time a task has spent in two critical statuses:
- **In Progress**: The duration from when the task moved to the `in_progress` status until it transitioned to `ready_for_review`.
- **In Testing**: The duration from when the task moved to the `ready_for_review` status until it was marked as `done`.

These calculations provide valuable insights into the task's lifecycle, helping teams analyze and optimize their processes.

---

## Implementation

### **1. New Methods in `TaskService`**

Two new methods were implemented to calculate the time spent in specific statuses:
- **`calculateTimeInProgress`**: Determines the time a task spent in the `in_progress` status by calculating the difference between the timestamps of `in_progress` and `ready_for_review`.
- **`calculateTimeInTesting`**: Determines the time a task spent in the `testing` status by calculating the difference between the timestamps of `ready_for_review` and `done`.

# Dockerization: Dockerfile and Docker Compose

## Overview

To simplify deployment and manage application dependencies, the project has been containerized using Docker. This includes creating a `Dockerfile` for the application and a `docker-compose.yml` file to manage the integration of the application with a PostgreSQL database.

---

## Dockerfile

The `Dockerfile` defines the instructions to build a Docker image for the application. It packages the Spring Boot application into a container, ensuring a consistent runtime environment.

### Key Features:
- **Base Image**: Uses `openjdk:17-jdk-slim` as the runtime environment.
- **Application Setup**: Copies the compiled JAR file (`app.jar`) into the container.
- **Port Exposure**: Exposes port `8080` for application access.
- **Command to Run**: Starts the application using `java -jar`.

