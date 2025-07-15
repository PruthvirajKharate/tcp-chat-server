# Binary Chat Server

This project is a lightweight, socket-based chat server written in Java. It is designed to handle compressed, binary-encoded messages and can support user login, logout, and messaging. The system is modular and prepared for extensions such as group messaging, persistent storage, and authentication.

## Features

- Binary message encoding using a compact token structure
- GZIP compression for efficient network usage
- Multi-threaded server capable of handling multiple clients
- Basic user session management
- Event-based responses to clients (e.g., login success, message delivered, errors)
- Modular design with separation of concerns using services (authentication, messaging)

## Getting Started

### Prerequisites

- Java 17 or above
- Maven

### Building the Project

Run the following command from the root of the project to build the JAR file:

```bash
mvn clean package
```

Running the Server
To start the server after building:

bash
Copy
Edit
java -jar target/chat-server-1.0-SNAPSHOT-jar-with-dependencies.jar
Make sure port 5000 (or the configured port) is open and available.

Protocol Overview
Message Format (Client to Server)
Messages sent by clients are represented by a Message object that contains:

type: Specifies the kind of message (e.g., login, msg, logout)

sender: The username of the sender

receiver: The target user (for direct messages)

body: The message content

groupId: For future use with group messages

These messages are encoded using a binary format and then compressed using GZIP before transmission.

Server Response Format
The server sends back structured responses using an EventResponse, which contains:

eventType: The type of event (e.g., LOGIN_SUCCESS, MESSAGE_DELIVERED, USER_OFFLINE)

message: A brief message related to the event

The response is also encoded and compressed using the same binary protocol.

##Notes
A simple test client is provided to simulate interactions with the server.

Group messaging logic is in progress and will be handled via middleware or extensions.

Future enhancements may include Spring Boot migration, database persistence, and authentication via user credentials.
