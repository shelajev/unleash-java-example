# Unleash Java SDK with Unleash Edge

This guide provides step-by-step instructions for using Unleash and Unleash Edge. You will configure an example feature flag, and see how changing it is reflected in a sample Java application.

## Part 0: Prerequisites

* Docker Desktop (or Docker CE)
* Java
* Maven

## Part 1: Using Unleash Directly

1.  **Start the Unleash server:**
    ```bash
    docker-compose -f compose-unleash.yml up
    ```

2.  **Access the Unleash UI:**
    Open your web browser and navigate to [http://localhost:4242](http://localhost:4242).
    Log in with the default credentials:
    *   **Username:** `admin`
    *   **Password:** `unleash4all`

3.  **Create a new feature flag:**
    *   In the Unleash UI, create a new feature flag named `example-flag`.

4.  **Run the Java application:**
    *   Compile and run the `Main.java` application.
    ```bash
    mvn compile exec:java -Dexec.mainClass="Main"
    ```
    You should see output indicating that the feature flag is not enabled.
5. **Change the feature flag value:**
   *   Add a "Gradual rollout" strategy to the flag using the admin UI of Unleash and set it to 100%.
   * Rerun the application (or input anything to its stdin). 
   * 

## Part 2: Using Unleash Edge

1.  **Update the application to use Unleash Edge:**
    *   Open the `src/main/java/Main.java` file.
    *   Change the `unleashApiUrl` to point to the Unleash Edge port (3063):
        ```java
        // ...
        config.setUnleashAPI("http://localhost:3063/api/");
        // ...
        ```
    * Rerunning the application naturally shows that it can't connect to the Unleash server and the flag evaluates to false.

2.  **Start Unleash and Unleash Edge:**
    *   First, stop the running Unleash container:
        ```bash
        docker-compose -f compose-unleash.yml down
        ```
    *   Then, start both Unleash and Unleash Edge:
        ```bash
        docker-compose -f compose-unleash.yml -f compose-unleash-edge.yml up 
        ```

3.  **Verify Unleash Edge is connected:**
    *   You can check the logs of the `unleash-edge` container to ensure it has connected to the Unleash server.
        ```bash
        docker-compose logs -f unleash-edge
        ```

4.  **Run the Java application again:**
    *   Compile and run the `Main.java` application as before:
    ```bash
    mvn compile exec:java -Dexec.mainClass="Main"
    ```
    You should see the feature flag is still enabled.

5.  **Change the rollout strategy:**
    *   Go back to the Unleash UI at [http://localhost:4242](http://localhost:4242).
    *   Find your `example-flag` and change the "Gradual rollout" strategy to 0%.

6.  **Observe the change in the application:**
    *   Run the Java application again.
    ```bash
    mvn compile exec:java -Dexec.mainClass="Main"
    ```
    This time, you should see that the feature flag is disabled, demonstrating that the application is now getting its flag configuration from Unleash Edge.