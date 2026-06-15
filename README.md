# Library Management System — Base Project

## Cấu trúc project

```
src/
├── main/
│   ├── java/com/example/library/
│   │   ├── LibraryApplication.java
│   │   ├── controller/
│   │   │   └── BookController.java        ✅ Đã có sẵn
│   │   ├── service/
│   │   │   └── BookService.java           ⚠️ Sinh viên thêm @Slf4j + log
│   │   ├── repository/
│   │   │   └── BookRepository.java        ✅ Đã có sẵn
│   │   ├── entity/
│   │   │   └── Book.java                  ✅ Đã có sẵn
│   │   └── exception/
│   │       ├── BookNotFoundException.java ✅ Đã có sẵn
│   │       └── GlobalExceptionHandler.java✅ Đã có sẵn
│   └── resources/
│       ├── application.properties         ✅ Đã cấu hình (sửa password nếu cần)
│       └── logback-spring.xml             ⚠️ Sinh viên hoàn thiện
└── test/
    └── java/com/example/library/
        ├── BookServiceTest.java            ⚠️ Sinh viên viết test
        └── BookControllerTest.java         ⚠️ Sinh viên viết test
```

## Sinh viên cần làm

### 1. Cấu hình Logback (1 điểm)
Mở `src/main/resources/logback-spring.xml` và hoàn thiện:
- Console Appender với pattern: `[thời gian] [level] [tên logger] - message`
- File Appender ghi vào `logs/library-app.log`, RollingPolicy theo ngày, giữ 7 file
- Root level: INFO — package `com.example.library`: DEBUG

### 2. Logging trong BookService (3 điểm)
Mở `BookService.java`, thêm `@Slf4j` và log tại 3 method (`getAllBooks`, `getBookById`, `createBook`):
- `log.debug(...)` — log tham số đầu vào
- `log.info(...)` — log kết quả thành công
- `log.error(...)` — log lỗi khi ném BookNotFoundException

### 3. Unit Test — BookServiceTest (3 điểm)
Viết 3 test case trong `BookServiceTest.java` theo hướng dẫn trong file.

### 4. Unit Test — BookControllerTest (3 điểm)
Viết 3 test case trong `BookControllerTest.java` theo hướng dẫn trong file.

## Chạy project

```bash
# Đảm bảo MySQL đang chạy, sửa password trong application.properties nếu cần
mvn spring-boot:run

# Chạy tests
mvn test
```

## API Endpoints (đã có sẵn)

| Method | Endpoint          | Mô tả             | Status   |
|--------|-------------------|-------------------|----------|
| GET    | /api/books        | Lấy danh sách     | 200 OK   |
| GET    | /api/books/{id}   | Lấy theo ID       | 200 / 404|
| POST   | /api/books        | Thêm mới          | 201      |
| PUT    | /api/books/{id}   | Cập nhật toàn bộ  | 200 / 404|
| PATCH  | /api/books/{id}   | Cập nhật một phần | 200 / 404|
| DELETE | /api/books/{id}   | Xóa               | 204 / 404|
