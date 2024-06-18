# Consonant
초성 게임 프로젝트
</br></br>

## ✔ 프로젝트 시연 영상
https://youtu.be/p-jBG3qrLbE
</br></br>

## ✔ 프로젝트 소개
우리말샘의 오픈 API를 활용하여 스피드 초성 게임과 초성 퀴즈를 구현한 토이 프로젝트입니다.

### 개발 기간
- 2022.09 - 2023.01 (4개월, AWS 스터디 및 배포 기간 포함)

### 맴버 구성
- 팀원1 : [송현정](https://github.com/Song-Hyun-Jung) - 퀴즈 생성 페이지, 초성 퀴즈 페이지, 초성 퀴즈 조회 페이지
- 팀원2 : [이유리](https://github.com/lee-code712) - 로그인 페이지, 회원가입 페이지, 메인 페이지, 스피드 초성 게임 페이지

### 개발 환경
- IDE : Eclipse(Java EE IDE)
- Programming Language : Java 8, JavaScript
- Backend Framework : `Spring Boot 2.7.4`
- Template Engine : `Thymeleaf`
- Database Access Technology : `MyBatis`
- Database : Oracle → PostgreSQL(AWS RDS)
- Version Control: Git
</br></br>

## ✔ 주요 기능
### 로그인
<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215466174-6e70b506-2742-4ed9-84a7-4d88bc04bb3f.png" />

> 비밀번호를 입력하지 않은 경우

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215467064-b11a6a80-c821-415c-944c-5f969a3b65fa.png" /><br>

> 올바르지 않은 비밀번호를 입력한 경우

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215467135-02436338-5cf0-4b9d-89a6-65310a8c1445.png" /><br>

### 회원가입
> id와 pw만 입력

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215466406-7c66dda5-61fc-4eb2-8eb2-9ab1a14e4153.png" /><br>

> 회원가입 시 필수정보인 id와 pw 중 하나라도 입력하지 않으면 회원가입 불가

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215687239-a5e883d2-9806-46c7-9a58-9fe18f76e31f.png"/><br>

### 홈 화면
> * top에는 id와 회원의 포인트가 표시됨
> * 오른쪽에는 획득한 점수 누적을 기준으로 실시간 랭킹이 표시됨

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215467530-e9d8d183-0de6-49e7-9e1a-1c0269a0e114.png"/><br>

### 스피드 초성 게임
> * 랜덤으로 1~2글자의 초성이 제시되고 60초 동안 최대한 많은 답을 제출해야함
> * 중복되는 단어를 입력했을 때에는 채점할 때 하나만 인정됨
> * 답안 중 국립국어원 표준국어사전에 등재된 단어만 정답으로 인정됨
> * 인정답안 10개당 10포인트를 획득할 수 있음
> * 인정답안 개수만큼 점수로 받게 되며 점수는 누적되어 랭킹에 반영됨

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215468241-04b4eb56-4f74-4caa-836e-65131fe6401d.png"/><br>

> 스피드 게임 플레이 결과

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215469179-c80f0b9b-6844-4ac2-b024-a52930f6d521.png"/><br>

> 정답인 답안을 클릭하면 단어 뜻을 볼 수 있음

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/217115216-1fca2fa3-b849-4374-adb0-c7c877b606e1.png"/><br>

### 초성 퀴즈 생성
> * 게임 생성 시 게임명, 카테고리, 난이도, 퀴즈소개, 퀴즈 등록은 필수
> * 퀴즈 추가 시 난이도는 후에 점수 산정의 기준이 됨 (상-3점 / 중-2점 / 하-1점)
> * 퀴즈 추가 시 힌트 사용 차감포인트를 지정할 수 있음 (10 ~ 50pt까지)

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215469669-c44685fc-6b51-440a-87c6-28419af1cc3c.png"/><br>
<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215470093-d74668bc-1401-48cf-9d7e-99cbdf004689.png"/><br>

> 게임 생성 시 필수항목을 입력하지 않은 경우

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215671436-11bfec6f-dc85-4bf9-a968-95ac1eac7a41.png"/><br>

> 게임 생성 시 퀴즈를 추가하지 않은 경우

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215671560-ffbb2eb1-b6eb-42e1-8807-97acd2f92c47.png"/><br>

> 자신이 만든 초성게임은 플레이할 수 없음

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215470236-24e579da-c7ef-496c-9342-c840376c081f.png"/><br>

### 초성 퀴즈 조회
> * 한 페이지 당 5개 게임 조회 가능 - 페이지 하단 Next 버튼으로 다음 5개 게임 조회
> * 최신 퀴즈 순으로 조회 가능
> * 내 점수는 플레이 한 적이 없으면 0점으로 표시되며, 플레이 한 적이 있으면 최고 점수가 표시됨

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215472846-8a60a37d-00e9-4094-ab8b-8943d0494e61.png"/><br>
<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215475756-4e6b1dfd-370b-41dc-9773-3aa2999543a6.png"/><br>

> 카테고리 선택

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215474249-010948e2-abb5-4393-837f-958c03801c0d.png"/><br>

### 초성 퀴즈
> * 다른 사용자가 만든 퀴즈만 플레이 할 수 있음
> * 각 퀴즈마다 포인트를 사용해 힌트를 볼 수 있으며 소모 포인트는 힌트 버튼 위에 기재되어 있음
> * 한 게임의 모든 퀴즈 문제를 다 맞아야 100포인트를 획득할 수 있음
> * 한 게임의 모든 퀴즈 난이도의 합산을 점수로 받게 되며 점수는 누적되어 랭킹에 반영됨

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215474962-8f2713f4-c316-413a-a448-e2c80f4f6525.png"/><br>

> 보유 포인트가 차감 포인트보다 적다면 힌트를 볼 수 없음

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215475048-1ba5f33c-1999-495d-bebe-c51ba432ca81.png"/><br>

> 초성게임 플레이 결과

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/215475140-e3bb2339-67c9-4dfe-8cd6-bd5463e6996b.png"/><br>

### 메뉴얼
> 초성게임 리스트에서 매뉴얼 버튼을 클릭하면 매뉴얼 화면으로 이동됨

<img width="935" alt="structure1" src="https://user-images.githubusercontent.com/57795722/216232340-e1e4f934-3562-4383-bfad-e151df4364c1.png"/><br>

