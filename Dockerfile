# Build stage
FROM gradle:7.6.1-jdk17 as BUILDER
WORKDIR /builder
COPY build.gradle.kts settings.gradle.kts /builder/

# 그래들 파일이 변경되었을 때만 의존 패키지를 설치하도록 설정
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌드 스테이지에서 해당 어플리케이션을 빌드
COPY . /builder
RUN gradle build -x test --parallel

# Production Stage
# 실행 환경에서는 JDK가 아닌 JRE를 사용하여 최대한 경량화
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
ARG APP_NAME=tutoring
ARG VERSION=0.0.1
COPY --from=BUILDER /builder/build/libs/${APP_NAME}-${VERSION}.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]