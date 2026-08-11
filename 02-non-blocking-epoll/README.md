# 02-non-blocking-epoll

A single-threaded echo server built with Java NIO.
No frameworks, no libraries, just Selector, ServerSocketChannel, and SocketChannel.

## What it does

* Listens on port 8080
* Accepts multiple clients simultaneously
* Echoes back whatever data a client sends
* Handles disconnections cleanly

All of that happens on one thread, without ever blocking.

## How it works

The key idea is simple: instead of blocking on accept() or read(), we ask the operating system to tell us when something is ready.

* ServerSocketChannel is registered with Selector for OP_ACCEPT
* When a new client connects, we accept it, set it to non-blocking, and register it for OP_READ
* When a client sends data and OP_READ fires, we read it into a ByteBuffer, then switch the interest to OP_WRITE and attach the data
* When the channel is writable and OP_WRITE fires, we write the attached buffer back. If it is fully written, we switch back to OP_READ and clear the attachment

This way, the server never waits for one client while other clients already have data ready.

## Why it matters

The previous server, 01-single-thread-blocking, could only handle one client at a time.

This one can handle thousands of concurrent connections on a single thread.

That is the foundation of high-performance servers like Nginx or Netty.

## Limitations

It only echoes raw bytes. It does not understand HTTP yet.

The next step is to build a reusable HTTP protocol layer that can be plugged into any server model, like blocking, NIO, or virtual threads.

## Run it

javac NioServer.java && java NioServer

In another terminal:

echo "hello" | nc localhost 8080
