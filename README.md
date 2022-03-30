# Mini Table

## 1. 개요

## 요약 

이 프로젝트는 포트폴리용으로 작성 중 인 줄서기 서비스 입니다. 현장에서 직접 예약을하는 
"테이x링" 서비스를 모티브로 하여 고객이 점포에가서 줄서기 예약을 하는 시스템을 구현하였습니다.

참고 : Docker 는 미리 설치 되어 있음을 가정합니다.

## 개발환경

- Spring Boot 2.6.4
- Java 11
- Spring Data JPA
- Querydsl
- Validation
- Spring Web
- Java Mail Sender
- Thymeleaf
- Spring Security
- MariaDB
- Lombok
- Docker
- Tailwind UI CSS


## 프로젝트 기동시 필요사항

### Maria DB 를 위한 폴더 생성

```yml
services:
  db:
    image: mariadb:10
    env_file:
      - "./db/.env"
    volumes:
      - "./db/data:/var/lib/mysql"
      - "./db/dump:/disk"
      - "./db/initdb.d:/docker-entrypoint-initdb.d"
```

Mini Table 은 Docker 컨테이너에 Maria DB 환경 구성을 설정하고 실행해야 합니다.
docker-compose.yml 을 확인하면 위와 같은 서비스 구성 설정이 존재하고
프로젝트 최상위폴더 바로아래 `db` 폴더가 존재하고 그아래 `data`, `dump`, `initdb.d` 
3개의 폴더가 존재합니다.

위에 존재하는 3개의 폴더를 반드시 미리 생성해야합니다.

### .env 환경 파일 작성

```yaml
TZ=Asia/Seoul
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=
MYSQL_DATABASE=
MYSQL_USER=
MYSQL_PASSWORD=
```

Docker Compose 를 사용하여 생성할 DB 의 기본 설정을 작성하여 `db` 폴더에 위치 시켜야합니다.
.env 환경 설정에 작성된 내용을 기반으로 Docker 에서 DB 가 설정 될 것 입니다.

### Docker 를 통한 MariaDB 실행

```shell
docker-compose up -d  # 처음 구동시 (이미지 생성 및 구동)

docker-compose start # 이미지 생성이 된 이후에는 구동만..

docker-compose stop # docker container 를 중지 시킬 경우 
```

위와 같은 명령어로 Docker Compose 를 사용하여 Maria DB 를 구동시킨후
프로젝트를 실행하면 전체 프로젝트 실행이 가능합니다.

