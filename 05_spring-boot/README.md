# 05 - Spring Boot

After building servers from scratch, we now use Spring Boot to see how it simplifies everything.

## What Spring Boot provides

- **Embedded Tomcat** – the same server we built manually, but production-ready.
- **DispatcherServlet** – handles HTTP parsing, routing, and responses automatically.
- **@RestController** – replaces our manual `HttpRequest`/`HttpResponse` handling.
- **Static file serving** – `src/main/resources/static/` works out of the box.

## Virtual threads

We enabled `spring.threads.virtual.enabled=true`.
This means Tomcat uses virtual threads, just like the server we built in `04_virtual_thread`.

## Run

```bash
mvn spring-boot:run
