# RegisterWithJWT

## Get Information. 
    This is registration service with Java restful API (Spring boot)

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/ioopy/RegisterWithJWT.git
```

**2. Build and run the app using maven**
.

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8082>.

## Explore Rest APIs

The app defines following CRUD APIs.

    POST /api/auth/register
    
    POST /api/auth/login
    
    GET /api/auth/user/detail

You can test them using postman or any other rest client.

### Example Body Request
```
    POST /api/auth/register
    Content-Type: application/json

    {
        "username" : "username",
        "password": "password",
        "salary": "50000",
        "address": "address",
        "phone": "0971762856"
    }
    Response: HTTP 200
    Content: User Detail 
```
```
    POST /api/auth/login
    Content-Type: application/json

    {
        "username" : "username",
        "password": "password",
    }
    Response: HTTP 200
    Content: Token
```
```
    GET /api/auth/user/detail
    Content-Type: application/json
    Authorization: Bearer TOKEN

    Response: HTTP 200
    Content: User Detail
```
