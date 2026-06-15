# Employee Management API

## Công nghệ

- Java 17
- Spring Boot 3.5.15
- Gradle
- Spring Web
- Spring Data JPA
- H2 In-Memory Database
- Spring Validation
- Spring AOP
- SLF4J Logging
- JUnit 5, Mockito, WebMvcTest

## Cấu trúc chính

```text
src/main/java/com/example/employeemanagement/
  aspect/LoggingAspect.java
  config/DataSeeder.java
  controller/EmployeeController.java
  exception/EmployeeNotFoundException.java
  exception/GlobalExceptionHandler.java
  model/Employee.java
  repository/EmployeeRepository.java
  service/EmployeeService.java
  EmployeeManagementApplication.java
```

## API

| Method | Endpoint | Chức năng | Thành công | Lỗi |
|---|---|---|---|---|
| GET | /api/employees | Lấy danh sách nhân viên | 200 | - |
| GET | /api/employees/{id} | Lấy chi tiết nhân viên | 200 | 404 |
| POST | /api/employees | Thêm nhân viên | 201 | 400 |
| PUT | /api/employees/{id} | Cập nhật toàn bộ | 200 | 400, 404 |
| DELETE | /api/employees/{id} | Xóa nhân viên | 204 | 404 |

## Request body POST / PUT

```json
{
  "fullName": "Nguyen Van A",
  "department": "Engineering",
  "salary": 15000000
}
```

## Validate

- `fullName`: không được null/rỗng.
- `department`: không được null/rỗng.
- `salary`: không được null và phải > 0.

## Chạy project

```bash
gradle bootRun
```

Sau khi chạy, truy cập:

```text
http://localhost:8080/api/employees
http://localhost:8080/h2-console
```

Thông tin H2 Console:

```text
JDBC URL: jdbc:h2:mem:employeedb
User Name: sa
Password: để trống
```

## Chạy test

```bash
gradle test
```

## Postman

Import file:

```text
postman/EmployeeManagementAPI.postman_collection.json
```

Chạy request theo thứ tự trong collection. Request POST sẽ tự lưu `employeeId` vào Collection Variable để GET/PUT/DELETE dùng tiếp.
