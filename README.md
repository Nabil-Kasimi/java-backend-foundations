# Java Backend Foundations

This repository tracks the evolution of a Java backend from raw sockets to a modern web framework. It explores the underlying mechanics of network I/O, protocol parsing, and concurrency models before introducing Spring Boot. 

The goal is to understand how web servers and frameworks operate under the hood by building their core components from scratch.

## Project Structure

The codebase is organized into five progressive steps:

### 1. Single-Threaded Blocking I/O (`01-single-thread-blocking`)
A fundamental TCP server using Java's `ServerSocket` and `Socket`.
- Handles one client at a time using synchronous, blocking I/O.
- Explores basic stream reading/writing and TCP connection management.

### 2. Non-Blocking I/O & epoll (`02-non-blocking-epoll`)
A concurrent server using Java NIO (`Selector` and `SocketChannel`).
- Replaces the thread-per-connection model with a single-threaded event loop.
- Uses OS-level multiplexing (epoll) to handle multiple connections simultaneously.
- Implements manual buffer management and non-blocking sockets.

### 3. The HTTP Protocol (`03_HTTP_Protoccol`)
An HTTP/1.1 server built on top of raw sockets.
- Parses incoming raw byte streams into structured HTTP requests.
- Handles headers, methods, and basic routing.
- Generates valid HTTP responses from scratch.

### 4. Virtual Threads (`04_virtual_thread`)
A high-throughput server using Java 21's Project Loom.
- Returns to a simpler synchronous thread-per-request programming model.
- Replaces heavy OS threads with lightweight Virtual Threads to achieve massive concurrency.

### 5. Spring Boot (`05_spring-boot`)
An implementation using the Spring Boot framework.
- Replaces the custom server implementation with the framework's embedded server (Tomcat).
- Applies standard enterprise patterns for routing, dependency injection, and request handling.
- Demonstrates how the underlying concepts from previous steps are abstracted and handled by the framework.

## Getting Started

Each directory is a self-contained project. You can run and test them independently to observe the differences in architecture, complexity, and how they handle concurrent connections.
