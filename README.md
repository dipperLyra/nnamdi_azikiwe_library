## Nnamdi Azikiwe Digital Library
This projects attempts to capture the barest minimum functions of a digital library.

Specifically:
1. Add a book
2. Update a book
3. Delete a book
4. Lend out books
5. Search for books

### Set up

1.  Navigate to the root directory. (i.e. running `$ pwd` should show /`project_path/nnamdi_azikiwe`)

2.  Generate jar file. `mvn clean package`

3. Build docker image. (Ensure that your docker is up and running:  `$ docker -v` should show the docker version if it is running)
   `$ docker build -f ./Dockerfile -t nnamdi_azikiwe_lib_img .`

4. Run docker image.
   `docker run -p 8033:8033 -v data:/tmp/data nnamdi_azikiwe_lib_img`

[Note: H2 in memory storage is being used though for persistence I'm writing the data to file]

Docker will launch the application and listen at port :8033 (http://localhost:8033)


### API

The api is divided into two parts, the authentication section which starts with creating a role, and book operations api.

#### Section 1
#### 1. Create Role
- Sample request
```
curl -X POST -H "Content-Type: application/json" -d '{"name": "user"}' http://localhost:8033/role
```

- Sample response
```json
{
    "id": 1,
    "name": "user"
}
```

#### 2. Create User
- Sample request
```
curl -X POST -H "Content-Type: application/json" -d '{"username": "user", "password":"pass"}' http://localhost:8033/signup
```

- Sample response
```
 Successful, user created!
```

#### 3. Authenticate User
- Sample request
```
curl -X POST -H "Content-Type: application/json" -d '{"username": "user", "password":"pass"}' http://localhost:8033/authenticate
```

- Sample response
```
Authentication successful. Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZW5kYWNoaSIsImlhdCI6MTYxMDExNDQ1MSwiZXhwIjoxNjEwMjAwODUxfQ.76pW54L2_s8J6j1OCEO1QsRdKqytyw0TPSgKa8bOQp-_EmJzrI2v_5xEASpXrfM1mZtjj6YackNXkhpT0K5Cfg
```

The returned token will be used as the Bearer {token} in section 2. For the sake of convenience I used a placeholder, {token}, in header.

#### Section 2

#### Add book
- Sample request
```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer {token}" -d '{"title": "title", "author": "author", "callNumber": "callNumber"}'  http://localhost:8033/api/v1/book
```

- Sample response
```json
{
    "id": 4,
    "title": "Oath Bringer",
    "author": "Brendan Sanderson",
    "callNumber": "DS559.46 .H35 2017"
}
```

#### List books
- Sample request
```
curl -H "Authorization: Bearer {token}" http://localhost:8033/api/v1/book/
```

#### Update book
- Sample request
```
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer {token}" -d '{ "id": 4, "title": "Edgedancer", "author": "Brendan Sanderson", "callNumber": "DS559.46 .H35 1986" }' http://localhost:8033/api/v1/book
```

#### Search book
- Sample request
```
curl -H "Authorization: Bearer {token}" http://localhost:8033/api/v1/book?title="Oath"
```

#### Delete book
- Sample request
```
curl -X DELETE -H "Authorization: Bearer {token}" http://localhost:8033/api/v1/book/4
```

#### Lend book
This can lend out a list of books to different users.
- Sample request
```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer {token}" -d '[{ "title": "Oath Bringer", "author": "Brendan Sanderson", "callNumber": "DS559.46 .H35 2016", "username": "user" }]' http://localhost:8033/api/v1/book/lend
```
