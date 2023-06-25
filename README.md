# 튜터링 사전과제
by 김도엽 (Doccimann)

## 실행 방법

현재 application.yml 을 통해서 AWS RDS를 통해 띄워둔 MySQL과 해당 어플리케이션이 연동되어있습니다.

따라서 아래의 커맨드를 통해 바로 실행 가능합니다.

~~~bash
# Docker build
docker build -t assignment-doccimann:latest .
# Docker run
docker run -p 8080:8080 assignment-doccimann:latest
~~~

## DDL 및 더미데이터 생성 코드

프로젝트의 루트 디렉토리 하에 **sql** 이라는 폴더가 존재하는데, 아래의 assignment.sql 파일에 DDL 및 더미데이터 생성 쿼리를 모두 저장해뒀습니다.

**assignment.sql** 파일에는 아래의 쿼리들이 존재합니다.

1. Table 생성문
2. Index 생성문
3. 더미데이터 insert문 

## 사용 도구 및 기술

### 언어
- Kotlin

### 프레임워크
- SpringBoot (Spring MVC)

### 데이터베이스
- MySQL

### ORM
- JPA (Java Persistence API)

### Test (Unit Test)
- Mockk, kotest, JUnit5

### 배포도구
- Docker

## 설계 고려 사항
