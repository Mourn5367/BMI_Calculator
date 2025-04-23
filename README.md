<h1 align="center">📱 BMI & 음식 성분 계산기</h1>

<p align="center">
  사용자는 성명, 신장, 몸무게를 입력하여 BMI 지수와 등급을 확인할 수 있고,<br>
  음식 이름을 입력하여 칼로리, 단백질, 당류 성분을 확인할 수 있습니다.
</p>

---

## 💡 기술 스택

- **플랫폼**: Android Studio  
- **언어**: Java  
- **API**: [공공데이터 포털](https://www.data.go.kr/)

---

## 🔢 기능 소개

### ✅ BMI 계산기

1. 사용자는 성명, 신장, 몸무게를 입력하여 BMI 결과를 **텍스트와 이미지**로 확인할 수 있습니다.
2. 입력하지 않은 항목이 있을 경우, **경고 이미지 및 토스트 메시지**를 통해 사용자에게 알림을 줍니다.
3. `초기화` 버튼을 클릭하면 입력값, 출력값, 이미지가 초기화됩니다.
4. BMI 계산기 상단 아이콘 클릭 시 → **영양성분 계산기** 화면으로 이동합니다.

---

### ✅ 영양성분 계산기

1. 상단 아이콘 클릭 시 → **BMI 계산기** 화면으로 이동
2. 음식명 **자동완성 기능** 제공
3. 입력값 없을 시 `측정 버튼`은 비활성화 및 회색 처리
4. 유효한 음식 입력 시 `측정 버튼` 활성화
5. 측정 버튼 클릭 시, 공공데이터 API를 통해 **칼로리 / 단백질 / 당류** 출력
6. `초기화` 버튼 클릭 시 입력 및 출력 초기화

---

## 📡 안드로이드 스튜디오에서 API 사용 방법

공공데이터 포털의 식품 영양성분 정보를 활용하기 위해 다음 절차를 따릅니다:

1. [공공데이터 포털 활용 신청](https://www.data.go.kr/data/15100070/standard.do)에 접속하여 **회원가입 및 활용 신청**을 진행합니다.
2. 활용 신청이 승인되면 발급받은 **일반 인증키(API Key)**를 아래와 같이 안드로이드 프로젝트에 삽입합니다.

   ```properties
   # local.properties (Android Studio)
   API_KEY = 발급받은_일반_인증키

주의사항
`디코딩(Decoding)`된 인증키를 사용할 경우 작동하지 않습니다.

---

## 📱 앱 레이아웃 & UI

<p align="center">
  <img src="https://velog.velcdn.com/images/mourn5367/post/e9be339f-ec41-42f9-b0cf-1370eaa4d564/image.png">
</p>

<p align="center">
  <img src="https://velog.velcdn.com/images/mourn5367/post/5c594a42-f47e-4d98-9b2f-29e3cbdbe344/image.png" width="250" style="margin-right: 10px;">
  <img src="https://velog.velcdn.com/images/mourn5367/post/ba24d768-c1ca-4803-976a-c388bd0f00ad/image.png" width="250">
</p>

<p align="center">
  <img src="https://velog.velcdn.com/images/mourn5367/post/599d0a29-7175-4cf4-bbe8-58e5afcf1565/image.png">
</p>

<p align="center">
  <img src="https://velog.velcdn.com/images/mourn5367/post/5b089bc4-4ae5-4e8f-ae89-2a7098187422/image.png" width="250" style="margin-right: 10px;">
  <img src="https://velog.velcdn.com/images/mourn5367/post/895477e0-86fe-4485-a615-b665b28a5197/image.png" width="250">
</p>

---

## 🔁 앱 순서도

<p align="center">
  <img src="https://velog.velcdn.com/images/mourn5367/post/fa1e5b7f-8b79-47fa-8d69-1f62dda9109c/image.png" width="600">
</p>

---

## 🎬 시연 영상
<p align="center">
    
[![youtube](http://img.youtube.com/vi/mCzW7X5DYqI/0.jpg)](https://youtube.com/shorts/mCzW7X5DYqI?feature=share)

</p>
