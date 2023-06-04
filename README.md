# 🏠 나의 편리한 연결 메이트, 긱사생
<div align="center">
    <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot">
    <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white">
    <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white">
</div>
<br>

### 같은 학교 기숙사생들과의 배달 공유, 심부름, 거래 기능 및 단체 채팅방 기능을 제공하는 앱입니다.

**AppStore:** [긱사생](https://apps.apple.com/kr/app/긱사생/id6448669700)

<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/d89ab98c-4e17-4d60-bd04-c3116d0bae78" width=60% height=60%>

## 🫂 팀 소개 
|[PM & BE 김형준](https://github.com/hyeong-jun-kim)|[Designer 최예지](https://github.com/yewww-e)|
|:--------:|:--------:|
|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/ce09dce4-3f42-42c1-847a-0639410fe41a" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/75521672-9dfe-4859-90ce-53652047c64a" width=200>|<img 
                                                                                                                                   
|[iOS 파트장 서은수](https://github.com/EunsuSeo01)|[iOS 개발자 조동진](https://github.com/Jodongjin)|[AOS 파트장 고지민](https://github.com/JM2308)|[AOS 개발자 이준영](https://github.com/lee-june-young)|
|:--------:|:--------:|:--------:|:--------:|
|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/be4cd305-863e-4eb5-8a3b-9b7cd5477653" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/0e107db9-0c1b-4e31-b6a7-fa25626a91ba" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/a0c0227f-6fa6-494a-b212-160b3a9ad6be" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/dfa80041-696b-428c-95a0-848e3558518e" width=200>|

|[BE 파트장 최태규](https://github.com/xhaktmchl)|[BE 개발자 안수빈](https://github.com/happysubin)|[BE 개발자 김민희](https://github.com/KimMinheee)|[BE 개발자 홍주연](https://github.com/Juyeon0526)|
|:--------:|:--------:|:--------:|:--------:|
|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/e09902bf-fb4b-46bf-a1e1-5d687bfa98f0" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/f6d31054-c5cd-464c-996e-24cd66aa6c76" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/8369aa59-1172-49e4-99eb-986dd41c16d1" width=200>|<img src="https://github.com/hyeong-jun-kim/RNS-Spring/assets/53989167/6331b08f-92cd-4d5d-bdcb-1cd1b48fb078" width=200>|

## ⚒️ 1차 MVP 기능 소개
### 🍚 배달공유 기능
같은 기숙사에 사는 사람끼리 배달을 같이 시켜먹을 수 있는 기능입니다. 내가 원하는 시간, 음식, 
인원 수를 정하고 배달 파티를 모집하거나 참여할 수 있습니다.

### 💬 단체 채팅방 기능
단체 채팅방 기능을 통해 앱에서 제공하는 모든 기능들을 원활하게 사용할 수 있습니다.
배달공유 단체 채팅방에서는 송금완료, 주문확인, 배달확인 기능을 통해 파티원끼리 원활하게 배달 음식을 시켜먹을 수 있도록 도움을 줍니다.

|||||
|:-:|:-:|:-:|:-:|
|<img src="https://github.com/Send-Rabbit-Team/RNS-Spring/assets/53989167/dee076f2-dea5-4614-9ef3-d079cd88d071">|<img src="https://github.com/Send-Rabbit-Team/RNS-Spring/assets/53989167/da7560e5-5ac6-47a4-b991-f51e660a13ab">|<img src="https://github.com/Send-Rabbit-Team/RNS-Spring/assets/53989167/dfddc8aa-34b2-41a1-b1ea-199bca12a4c7">|<img src="https://github.com/Send-Rabbit-Team/RNS-Spring/assets/53989167/ce1dd8ec-f3e4-4f0f-81c4-846b85727738">

### 서버 개발 현황
- **인증**
    - 일반/카카오/애플 회원가입 및 로그인
    - 학교 이메일, 핸드폰 인증
- **배달파티**
    - 배달파티 목록
        - 배달파티 인피니티 스크롤 조회 API
        - 인원/시간 필터 조회 API
        - 배달파티 마감시간 계산 및 마감 처리
    - 배달파티 검색
    - 배달파티 생성
    - 배달파티 수정
    - 배달파티 상세조회
        - 배달파티 신청
            - 단체 채팅방 초대
        - 배달파티 신고
- **단체 채팅방**
    - 채팅방 목록
        - 가장 최근에 이루어진 채팅기준 오름차순으로 정렬
        - 안 읽은 메시지 카운트
    - 단체 채팅방
        - 메시지 발신 (WebSocket + MongoDB + RabbitMQ)
        - 메시지 읽음 처리
        - 채팅방 이미지 전송
        - 시스템 메시지 처리
        - 채팅 나가기
        - 송금 완료 / 주문 완료 / 배달 완료 알림 보내기
        - 강제 퇴장
        - 매칭 마감하기
- **마이 페이지**
    - 진행했던 활동 보기
    - 내 정보 수정
    - 로그아웃
    - 회원 탈퇴
- **API 명세서**
    - Swagger API 명세서 작성


### 긱사생 관련 Repository
<a href="https://github.com/Geek-sasaeng/Geek_sasaeng-iOS"><strong>Geek_sasaeng-iOS</strong></a></br>
<a href="https://github.com/Geek-sasaeng/Geek_sasaeng-Android"><strong>Geek_sasaeng-Android</strong></a></br>
