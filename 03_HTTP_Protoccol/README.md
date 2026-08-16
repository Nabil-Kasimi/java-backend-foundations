# 03 - HTTP Protocol

Http protocol is multiple rules that give data shared between two devices a meaning. It's like two people wanting to communicate; they need to agree on a language (vocabulary) and grammar (how words are ordered). If they don't, they are just making meaningless sounds at each other. HTTP (Hypertext Transfer Protocol) provides that exact shared dictionary for the web.

In this folder, we implement a basic HTTP request parser and response builder from scratch to understand how it works under the hood.

## What's inside

- `HttpRequest.java`: Takes a raw string request and parses out the method, path, version, and headers.
- `HttpResponse.java`: Does the reverse. You give it a status code and body, and it spits out the raw bytes to send back over the socket.
- `TestHttpRequest.java` & `TestHttpResponse.java`: Simple test files with `main` methods to see the parsing and byte generation in action.

## HTTP Request
When a browser asks for a page, it sends plain text like this:
```http
GET /index.html HTTP/1.1
Host: localhost
Connection: keep-alive

```

## HTTP Response
When the server answers, it replies with text like this:
```http
HTTP/1.1 200 OK
Content-Length: 27
Content-Type: text/plain; charset=UTF-8

hello welcome to our server
```

That's pretty much it. The whole protocol relies on splitting lines by `\r\n` and knowing where the headers end and the body begins!
