# Member 도메인

# controller
- 이 디렉토리는 클라이언트의 요청을 처리하고 응답을 반환하는 역할을 합니다.
- `MemberController`: 회원과 관련된 요청을 처리하는 컨트롤러 클래스.

## form
- `EmailMatchForm`: 이메일 매칭 요청을 위한 폼 데이터 클래스.
- `EmailPasswordForm`: 이메일 및 비밀번호 요청을 위한 폼 데이터 클래스.
- `MemberLoginForm`: 회원 로그인 요청을 위한 폼 데이터 클래스.
- `MemberRegisterForm`: 회원 가입 요청을 위한 폼 데이터 클래스.

# entity
- 이 디렉토리는 데이터베이스의 테이블과 매핑되는 엔티티 클래스들을 포함합니다.

- `Address`: 주소 정보를 나타내는 엔티티 클래스.
- `Authentication`: 인증 정보를 나타내는 엔티티 클래스.
- `BasicAuthentication`: 기본 인증 정보를 나타내는 엔티티 클래스.
- `Member`: 회원 정보를 나타내는 엔티티 클래스.
- `MemberProfile`: 회원 프로필 정보를 나타내는 엔티티 클래스.
- `Role`: 역할 정보를 나타내는 엔티티 클래스.

# repository
- 이 디렉토리는 데이터베이스에 접근하는 레포지토리 인터페이스들을 포함합니다.

- `AuthenticationRepository`: 인증 정보를 관리하는 레포지토리 인터페이스.
- `MemberProfileRepository`: 회원 프로필 정보를 관리하는 레포지토리 인터페이스.
- `MemberRepository`: 회원 정보를 관리하는 레포지토리 인터페이스.
- `RoleRepository`: 역할 정보를 관리하는 레포지토리 인터페이스.

# service
- 이 디렉토리는 비즈니스 로직을 처리하는 서비스 클래스들을 포함합니다.
- `MemberLoginResponse`: 회원 로그인 응답을 위한 데이터 클래스.
- `MemberService`: 회원 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
- `MemberServiceImpl`: `MemberService` 인터페이스의 구현 클래스.

## request
- `EmailMatchRequest`: 이메일 매칭 요청을 위한 데이터 클래스.
- `EmailPasswordRequest`: 이메일 및 비밀번호 요청을 위한 데이터 클래스.
- `MemberLoginRequest`: 회원 로그인 요청을 위한 데이터 클래스.
- `MemberRegisterRequest`: 회원 가입 요청을 위한 데이터 클래스.

