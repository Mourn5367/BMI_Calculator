BMI, 음식 성분 계산기

사용자는 성명, 신장, 몸무게를 측정하여 BMI 지수와 등급을 알 수 있다.
또한 메뉴 입력을 통해 칼로리, 단백질, 당류 성분을 구할 수 있다.

기술: Android Studio<br>
언어: Java <br>
API: <a href ="https://www.data.go.kr/">공공데이터 포털 

기능

BMI 계산기

1. 사용자는 성명, 신장, 몸무게 입력시 텍스트와 이미지를 통해 검사 결과 여부를 확인할 수 있다.
2. 미입력 란이 있을 경우 경고 이미지와 토스트 출력을 통해 해당 입력란을 표시한다.
3. 초기화 버튼 클릭시 입력한 텍스트, 출력 텍스트와 이미지를 삭제하여 초기화면으로 돌아간다.
4. BMI 계산기 제목의 오른쪽 아이콘을 클릭 시 영양성분 계산기 화면으로 이동한다.


영양성분 계산기


1. 영양성분 계산기 제목의 오른쪽 아이콘 클릭 시 BMI 계산기 화면으로 이동한다.
2. 사용자는 한 글자 입력시 자동완성 기능을 통해 음식을 선택할 수 있다.
3. 미 입력시 측정 버튼은 비활성화 상태가 되며 회색으로 변경된다.
4. 자동완성 기능 혹은 제공하는 음식을 정확하게 입력한 경우 측정 버튼이 활성화된다.
5. 측정 버튼 클릭시 공공데이터 포털 API 통신을 통해 칼로리 단백질 당류를 출력한다.
6. 초기화 버튼 클릭시 입력, 출력한 텍스트를 초기화 합니다.

App 레이아웃 및 이미지 파일
<div >
    <img src="https://velog.velcdn.com/images/mourn5367/post/e9be339f-ec41-42f9-b0cf-1370eaa4d564/image.png" alt="이미지 설명">
</div>
<div >
        <img style="margin: 30 30px;" src="https://velog.velcdn.com/images/mourn5367/post/5c594a42-f47e-4d98-9b2f-29e3cbdbe344/image.png" alt="이미지 설명" >
        <img style="margin: 30 30px;" src="https://velog.velcdn.com/images/mourn5367/post/ba24d768-c1ca-4803-976a-c388bd0f00ad/image.png" alt="이미지 설명">
</div>

<div >
    <img src="https://velog.velcdn.com/images/mourn5367/post/599d0a29-7175-4cf4-bbe8-58e5afcf1565/image.png" alt="이미지 설명">
</div>
<div >
    <img src="https://velog.velcdn.com/images/mourn5367/post/5b089bc4-4ae5-4e8f-ae89-2a7098187422/image.png" alt="이미지 설명">
    <img src="https://velog.velcdn.com/images/mourn5367/post/895477e0-86fe-4485-a615-b665b28a5197/image.png" alt="이미지 설명" >
</div>
App 순서도
<div >
    <img src="https://velog.velcdn.com/images/mourn5367/post/fa1e5b7f-8b79-47fa-8d69-1f62dda9109c/image.png" alt="이미지 설명">
</div>
시연 영상

