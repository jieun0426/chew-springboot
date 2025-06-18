
# 맛집 예약 사이트 - Chewtopia

웹 기반 맛집 예약 서비스입니다. 사용자는 맛집을 검색하고, 원하는 시간에 맞춰 실시간으로 예약할 수 있습니다.<br>
이 프로젝트는 사용자 친화적인 UI/UX와 백엔드 예약 시스템의 설계에 중점을 두었습니다.
<br><br>

# 목차
- 프로젝트 소개
- 기술 스택
- 주요 기능
- UseCase Description
- UseCase Diagram
- 화면 미리보기
- 설치 및 실행 방법
- SQL 스크립트
- 회고 및 느낀점
- 첨부 파일
<br><br>

# 프로젝트 소개
- 목표 : 간편한 예약 시스템과 직관적인 맛집 탐색 기능 제공
- 대상 : 외식 전 예약을 선호하는 사용자
- 프로젝트 기간 : 2025.06.02 - 2025.06.13
<br><br>

# 기술 스택
## 🌐 프론트엔드
HTML, CSS, JavaScript, AJAX, jQuery, Thymeleaf, JSON

## 🔧 백엔드
Java 17, Spring Boot, Spring MVC(내장), Spring Security,
Spring Data JPA, Bean Validation, Lombok, Spring Web

## 🛢 데이터베이스
Oracle, Hibernate(JPA)

## ⚙️ 서버 및 도구
Embedded Tomcat 9.0, Git, GitHub, Kakao Map API
<br><br>

# 주요 기능
- 맛집 검색 (키워드, 지역 필터)
- 날짜 및 시간 선택 후 예약
- 사용자 로그인 및 마이페이지(회원정보 수정, 내가 쓴 리뷰, 예약 보기)
- FAQ 등록 및 비밀글 쓰기
- 관리자 페이지(매장 관리, 회원관리, 리뷰 관리, 예약 관리 등)
<br><br>
## ERD Diagram
![erd](https://github.com/user-attachments/assets/95b19d95-2d86-4a72-9988-31510382982c)

<br><br>
## UseCase Description
![image](https://github.com/user-attachments/assets/55120a83-e64a-44e7-89b1-9bf7a74c6d87)
<br><br>

## UseCase Diagram
![US Diagram](https://github.com/user-attachments/assets/bc30f79a-b3f6-4d74-ba00-a9ad870ee8e3)

<br><br>

# 화면 미리보기
## 메인화면
![main_gif](https://github.com/user-attachments/assets/79a96541-bd9c-4a05-a45d-0f8665503284)
<br><br><br>
## 맛집 상세보기
![detail](https://github.com/user-attachments/assets/826df5e8-f3dc-4b51-935c-08baec717933)
<br><br><br>
## 예약
![booking1](https://github.com/user-attachments/assets/f1638668-2b52-4a0c-9da0-ed45ad6252c4)

## 마이페이지-예약내역
![image](https://github.com/user-attachments/assets/70a7bc44-331e-4286-9bce-2d2cfa91ac2d)

<br><br><br>
## 매장 관리
![storemanage](https://github.com/user-attachments/assets/68838dea-db82-471c-89eb-3be9b784120d)
<br><br><br>
## 회원 관리
![memberlist](https://github.com/user-attachments/assets/ee23bdd1-b5c0-48dc-a975-8b9d08497e1f)
<br><br><br>
## 마이페이지 내정보수정
![mypage](https://github.com/user-attachments/assets/888ac7a4-8dcc-46eb-87a7-f61ea9c4c341)
<br><br><br>
## FAQ
![faq](https://github.com/user-attachments/assets/d1b33a18-5ed7-404d-a7b9-803a5a84be45)



<br><br>
# 설치 및 실행 방법
1. 이 저장소 클론 - git clone https://github.com/jieun0426/chew-springboot.git

2. IDE에서 열기

3. 서버 설정 - 포트번호 1602

4. DB 설정 - 접속 이름 : MBC / 패스워드 : 1234 / 호스트이름 : localhost / 포트 : 1521 / SID : xe

5. 주소창에 localhost:1602 검색
<br><br>
# SQL 스크립트

- SQL 파일은 'sql/' 폴더에 있습니다.
- 직접 DB에 적용하거나 테스트 시 참고하세요.<br>
  ※ commit 잊지 마세요 ※

| 파일명       | 설명                |
|--------------|---------------------|
| [schema.sql](./sql/schema.sql) | 테이블 구조 정의 |
| [data.sql](./sql/data.sql)     | 초기 데이터 삽입 |

<br><br>

# 회고 및 느낀점
- 기존 스프링 프로젝트를 부트 형식으로 전환하면서 유지보수의 중요성과 까다로움을 직접 체감할 수 있었습니다.


