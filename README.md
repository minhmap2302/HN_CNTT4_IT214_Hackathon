# Hackathon Microservice - Đề 04

Project gồm 5 service:

- config-server: cổng 8888
- eureka-server: cổng 8761
- api-gateway: cổng 8080
- author-service: cổng 8081
- book-service: cổng 8082

## Yêu cầu môi trường

- Java 21
- MySQL chạy tại localhost:3306
- MySQL user: root
- MySQL password: 123456

Hai database `author_db` và `book_db` được tự tạo khi service kết nối MySQL.

## Thứ tự chạy

1. Chạy config-server
2. Chạy eureka-server
3. Chạy author-service
4. Chạy book-service
5. Chạy api-gateway

Có thể chạy từng project bằng lệnh:

```bash
./gradlew bootRun
```

Trên Windows:

```bash
gradlew.bat bootRun
```

## API qua Gateway

Chỉ gọi nghiệp vụ qua cổng 8080.

### Lấy tác giả theo ID

```http
GET http://localhost:8080/api/authors/1
```

Author mặc định có sẵn ID 1, 2, 3.

### Lấy danh sách sách

```http
GET http://localhost:8080/api/books
```

### Thêm sách với authorId hợp lệ

```http
POST http://localhost:8080/api/books
Content-Type: application/json

{
  "title": "Cho toi xin mot ve di tuoi tho",
  "authorId": 1
}
```

Kết quả thành công trả về HTTP 201.

### Thêm sách với authorId không tồn tại

```http
POST http://localhost:8080/api/books
Content-Type: application/json

{
  "title": "Sach khong hop le",
  "authorId": 999
}
```

Kết quả trả về HTTP 404 và sách không được lưu.

## Kiến trúc

author-service và book-service lấy port, datasource, Eureka client và cấu hình Feign từ config-server. Hai service đăng ký vào Eureka. api-gateway định tuyến theo service name bằng `lb://author-service` và `lb://book-service`. book-service gọi author-service đồng bộ bằng OpenFeign để kiểm tra authorId trước khi lưu sách.
