# API 명세서

## 공통 사항

- **Base URL:** `http://localhost:8080`
- **데이터 형식:** JSON (`application/json`)
- **인증 방식:** Cookie / Session
- **공통 응답:** 성공 시 적절한 HTTP 상태 코드 반환, 잘못된 요청은 `400`, 인증 실패는 `401`, 대상이 없으면 `404`

## 1. 일정 API

| 기능 | Method | URL | 요청값 | 반환값 | 인증 |
|---|---|---|---|---|---|
| 일정 생성 | POST | `/api/schedules` | 제목, 내용 | 생성된 일정 DTO | 필요 |
| 전체 조회 | GET | `/api/schedules` | 없음 | 일정 DTO 목록 | 불필요 |
| 단건 조회 | GET | `/api/schedules/{scheduleId}` | 일정 ID | 일정 DTO | 불필요 |
| 일정 수정 | PUT | `/api/schedules/{scheduleId}` | 일정 ID, 제목, 내용 | 수정된 일정 DTO | 필요 |
| 일정 삭제 | DELETE | `/api/schedules/{scheduleId}` | 일정 ID | 없음 | 필요 |

## 2. 유저 API

| 기능 | Method | URL | 요청값 | 반환값 | 인증 |
|---|---|---|---|---|---|
| 유저 생성 | POST | `/api/users` | 유저명, 이메일 | 생성된 유저 DTO | 불필요 |
| 전체 조회 | GET | `/api/users` | 없음 | 유저 DTO 목록 | 불필요 |
| 단건 조회 | GET | `/api/users/{userId}` | 유저 ID | 유저 DTO | 불필요 |
| 유저 수정 | PUT | `/api/users/{userId}` | 유저 ID, 유저명, 이메일 | 수정된 유저 DTO | 필요 |
| 유저 삭제 | DELETE | `/api/users/{userId}` | 유저 ID | 없음 | 필요 |

## 3. 회원가입 및 인증 API

| 기능 | Method | URL | 요청값 | 반환값 | 인증 |
|---|---|---|---|---|---|
| 회원가입 | POST | `/api/auth/signup` | 유저명, 이메일, 비밀번호(8자 이상) | 생성된 유저 DTO | 불필요 |
| 로그인 | POST | `/api/auth/login` | 이메일, 비밀번호 | 로그인 사용자 DTO, 세션 쿠키 | 불필요 |
| 로그아웃 | POST | `/api/auth/logout` | 없음 | 없음 | 필요 |

## 4. 데이터 형식 예시

### 일정 생성 요청

```json
{
  "title": "JPA 공부",
  "contents": "엔티티 연관관계 이해하기"
}
```

### 일정 응답

```json
{
  "id": 1,
  "userId": 10,
  "username": "홍길동",
  "title": "JPA 공부",
  "contents": "엔티티 연관관계 이해하기",
  "createdAt": "2026-10-21T10:00:00",
  "modifiedAt": "2026-10-21T10:00:00"
}
```

### 유저 응답

```json
{
  "id": 10,
  "username": "홍길동",
  "email": "hong@example.com",
  "createdAt": "2026-10-21T10:00:00",
  "modifiedAt": "2026-10-21T10:00:00"
}
```

## 5. 인증 및 응답 정책

- 로그인 성공 시 서버 세션을 생성하고 Cookie로 세션을 식별한다.
- 로그아웃 시 세션을 무효화한다.
- 일정 생성 시 작성자는 로그인 세션의 사용자 정보를 기준으로 결정한다.
- Entity를 직접 반환하지 않고 DTO를 사용한다.
- 비밀번호는 응답에 포함하지 않는다.
- 작성일과 수정일은 JPA Auditing으로 관리한다.
- 인증이 필요한 API는 유효한 로그인 세션이 없으면 `401 Unauthorized`를 반환한다.

# ERD
![일정 ERD](./images/%5B%EB%82%B4%EC%9D%BC%EB%B0%B0%EC%9B%80%EC%BA%A0%ED%94%84%5D%20%EC%9D%BC%EC%A0%95%20develop.jpg))

