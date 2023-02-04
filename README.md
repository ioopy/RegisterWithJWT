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

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/register | Register | [JSON](#register) |
| POST   | /api/auth/login | Log in, Get token | [JSON](#login) |
| GET   | /api/auth/user/detail | User Detail | |

You can test them using postman or any other rest client.

### Sample Valid JSON Request Bodys

##### <a id="register">Register -> /api/auth/register</a>
```
$ curl -X POST 'http://localhost:8082/api/auth/register'
```
```json
{
    "username" : "username",
    "password": "password",
    "salary": "50000",
    "address": "address",
    "phone": "0971762856"
}
```

##### <a id="login">Log in -> /api/auth/login</a>
```
$ curl -X POST 'http://localhost:8082/api/auth/login'
```
```json
{
    "username" : "username",
    "password": "password",
}
```
##### <a id="detail">User Detail -> /api/auth/user/detail</a>
```
$ curl -X GET http://localhost:8082/api/auth/user/detail -H 'Authorization: Bearer <JWT_TOKEN>'
```

### Example Test
```bash
mvn test
```