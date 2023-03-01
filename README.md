## URL SHORTENER PROJECT

This project is to shorten your original URL to varius type short URL.

### ENV 
- java 17

- MySQL For Database

- Spring Boot 2.7


### How To Build

In your 'application.yml' File.

You can specify your profile for yourself.


Default set profile is local and the file is specified.


### Architecture
<img width="353" alt="스크린샷 2022-12-04 오전 10 01 13" src="https://user-images.githubusercontent.com/64317196/205469292-2fa220b7-fbaf-4186-8a6c-74f8784a8e8f.png">

- Using Cache Layer for Higher Response Speed.
  - Also In many cases, the URL often gets many traffic within certain time when it shared.
  - The default setting for caching is Redis. But you can change by implementing Caching related interface,

### API Specification

static swagger-ui file which is created by following gradle task in project root dir

> run task with your gradle **copyOASToSwagger** 

**see**
> /src/main/resources/static/index.html
