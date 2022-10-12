# reborn

## 프로젝트 소개 http://54.180.177.213/
운동 기록 사이트
> 본인이 직접 운동을 등록하여 기록할 수 있는 자율형 기록 시스템과 간단히 소통할 수 있는 일지 게시판 제공

## 구성원
이름 | 역할 | 수행
--- | --- | --- |
김봉조 | 팀장 | 프론트엔드, 백엔드(게시판, 지도)
한우석 | 팀원 | 백엔드, 프론트엔드(그래프)

## 기술스택
>프론트엔드
<img src="https://img.shields.io/badge/vue-black?style=flat-square&logo=Vue.js&logoColor=#4FC08D"/> <img src="https://img.shields.io/badge/html5-black?style=flat-square&logo=HTML5&logoColor=#E34F26"/> <img src="https://img.shields.io/badge/CSS3-black?style=flat-square&logo=CSS3&logoColor=#1572B6"/> <img src="https://img.shields.io/badge/JavaScript-black?style=flat-square&logo=JavaScript&logoColor=#F7DF1E"/> <img src="https://img.shields.io/badge/Bootstrap-black?style=flat-square&logo=Bootstrap&logoColor=#7952B3"/> <img src="https://img.shields.io/badge/Node.js-black?style=flat-square&logo=Node.js&logoColor=#339933"/> <img src="https://img.shields.io/badge/npm-black?style=flat-square&logo=npm&logoColor=#CB3837"/> <img src="https://img.shields.io/badge/Axios-black?style=flat-square&logo=Axios&logoColor=#5A29E4"/> <img src="https://img.shields.io/badge/Kakao-black?style=flat-square&logo=Kakao&logoColor=#FFCD00"/> 

>백엔드
<img src="https://img.shields.io/badge/IntelliJ IDEA-black?style=flat-square&logo=IntelliJ IDEA&logoColor=#000000"/> <img src="https://img.shields.io/badge/Spring Boot-black?style=flat-square&logo=Spring Boot&logoColor=#6DB33F"/> <img src="https://img.shields.io/badge/MySQL-black?style=flat-square&logo=MySQL&logoColor=#4479A1"/> <img src="https://img.shields.io/badge/Postman-black?style=flat-square&logo=Postman&logoColor=#FF6C37"/> <img src="https://img.shields.io/badge/Docker-black?style=flat-square&logo=Docker&logoColor=#2496ED"/> <img src="https://img.shields.io/badge/Redis-black?style=flat-square&logo=Redis&logoColor=#DC382D"/> <img src="https://img.shields.io/badge/Amazon AWS-black?style=flat-square&logo=Amazon AWS&logoColor=#569A31"/> <img src="https://img.shields.io/badge/Spring Security-black?style=flat-square&logo=Spring Security&logoColor=#6DB33F"/> 

## Data Model
<img src="https://user-images.githubusercontent.com/103818731/194981906-0fe1718d-21f2-42eb-8c9d-ab23632d68cd.png"  width="80%" height="500"/>

## 사이트 기능
* 메인페이지
> 나의 운동일지 사용수에 따른 인기 운동, 조회수에 따른 인기 운동일지 출력
<img src="https://user-images.githubusercontent.com/103818731/194981652-28754370-e848-4f4e-a293-8416cfc8f778.png"  width="80%" />


* 로그인
> 
<img src="https://user-images.githubusercontent.com/103818731/194983813-3d8a772d-e6c4-4ec8-a699-d99606b15f5b.png"  width="80%" />


* 회원가입
> kakao 주소검색 api를 이용한 주소 등록, id 닉네임 중복체크
<img src="https://user-images.githubusercontent.com/103818731/194983838-277a853e-e289-45c7-bc41-ffa1feb0a9c3.png"  width="80%" />


* 루틴관리 / 운동 리스트
> 개개인이 임의의 운동을 등록 할 수 있는 페이지 + 검색 
<img src="https://user-images.githubusercontent.com/103818731/194982316-45a5054d-0476-4d86-a4ba-e140aefd805b.png"  width="80%" />


* 루틴관리 / 운동 리스트 디테일
> 오늘 운동할 아이템을 나의 리스트로 넘기기 + 수정 삭제
<img src="https://user-images.githubusercontent.com/103818731/194991156-64db41c4-89ee-4f34-9f5d-b737c921b8fe.png"  width="80%" />


* 루틴관리 / 나의 리스트
> 운동 리스트에 등록된 목록 중 실제로 프로그램에 등록할 아이템을 담은 페이지 + 검색
<img src="https://user-images.githubusercontent.com/103818731/194982396-400d3794-67b9-4acb-abbd-424d814ba797.png"  width="80%" />


* 프로그램
> 나의 리스트에서 가져온 목록으로 실제 운동량을 기록
<img src="https://user-images.githubusercontent.com/103818731/194982770-a5bfae13-1f76-4235-972f-f72eff38e19a.png"  width="80%" />


* 운동일지
> 하루 운동을 기록할 수 있는 간단한 커뮤니티형 게시판
<img src="https://user-images.githubusercontent.com/103818731/194982919-8b30d300-ec12-4cd7-afd5-4830c5609765.png"  width="80%" />


* 글 내용
> 카드형으로 보여지는 내용 + 댓글 등록 수정 삭제
<img src="https://user-images.githubusercontent.com/103818731/194983202-475fc2f6-41f2-43f3-92b2-7695ae48d479.png"  width="80%" />


* 헬스장
> kakao map을 이용한 검색, 등록 가능한 헬스장 간단 평가 페이지
<img src="https://user-images.githubusercontent.com/103818731/194983396-be875bf3-e6b1-4110-b1e1-b87043cdeb86.png"  width="80%" />


* 달성도
> apexchart를 이용한 프로그램에서 받아온 데이터를 통계하여 보여주는 그래프
<img src="https://user-images.githubusercontent.com/103818731/194983502-61ac3a77-9461-4fa2-aa0c-27387c1a4557.png"  width="80%" />


* 내가 쓴 글
> 운동일지에서 등록된 글 중 본인이 등록한 게시글 보여줌
<img src="https://user-images.githubusercontent.com/103818731/194983599-cf1361e8-f306-4f99-ba68-0f1548b25da1.png"  width="80%" />


* 정보 수정
> 회원가입 페이지에서 작성한 내용 중 변경가능한 페이지
<img src="https://user-images.githubusercontent.com/103818731/194983713-9202f82f-56a4-40e0-b75d-74def6b05cf1.png"  width="80%" />

