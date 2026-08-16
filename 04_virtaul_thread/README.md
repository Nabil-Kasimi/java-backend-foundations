# VThreadServer

A basic HTTP server built with Java virtual threads.

## What it does

- Listens on port 8081
- Accepts many clients at the same time
- Reads an HTTP GET request
- Responds with:
  - `200 OK` for `/`
  - `404 Not Found` for other paths
  - `400 Bad Request` for non-GET or malformed requests

Everything runs on Java virtual threads, so each client gets its own lightweight thread.

## How it works

1. The main thread waits for new clients with `server.accept()`.
2. When a client connects, the server starts a virtual thread.
3. That thread reads the request headers using `BufferedReader`.
4. It rebuilds the raw request string.
5. It parses the request with the `HttpRequest` class.
6. It chooses an `HttpResponse` based on method and path.
7. It writes the response bytes to the client and closes the socket.

Because virtual threads are cheap, the server can handle many clients without blocking the main loop.

## Why virtual threads

In the old blocking server, one client blocked the whole server.
In the NIO server, the code was complex with selectors and interest ops.
With virtual threads, the code stays simple and blocking, but the JVM handles concurrency for us.

That is the heart of this server:
**Simple blocking code + cheap virtual threads = high concurrency without complex NIO.**

## How to run

Compile:

```bash
javac VThreadServer.java

java VThreadServer

How to test
    use : curl http://localhost:8081/
