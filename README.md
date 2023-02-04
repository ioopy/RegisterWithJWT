# RegisterWithJWT

## Get information about system health. 

```

http://localhost:8082/


```

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/monirozzaman/Salary-Maker-Backend.git
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
