# Reviewers
 도서/영화 정보를 제공하고 리뷰를 작성해 감상평을 사용자들과 자유롭게 나누는 문화 커뮤니티 플랫폼입니다.

### Tech
<img src="https://img.shields.io/badge/Java-FC4C02?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Spring boot-6DB33F?style=flat-square&logo=Spring boot&logoColor=white"/> <img src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=ApacheMaven&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-0078D4?style=flat-square&logo=Spring Data JPA&logoColor=white"/> <img src="https://img.shields.io/badge/Mapstruct-C70D2C?style=flat-square&logo=mapstruct&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-2AB1AC?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat-square&logo=amazon aws&logoColor=yellow"/> <img src="https://img.shields.io/badge/Junit-25A162?style=flat-square&logo=Junit5&logoColor=white"/>   

### Deploy
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/> <img src="https://img.shields.io/badge/Micrometer-2F6E96?style=flat-square&logoColor=white"/>


### Tool
<img src="https://img.shields.io/badge/IntelliJ IDEA-8A3391?style=flat-square&logo=IntelliJ IDEA&logoColor=black"/> <img src="https://img.shields.io/badge/Notion-FFFFFF?style=flat-square&logo=Notion&logoColor=black"/> <img src="https://img.shields.io/badge/Github-000000?style=flat-square&logo=Github&logoColor=white"/>


### Team
|Team leader|member|member|member|member|
|--|--|--|--|--|
|<img width="100" alt="정곤" src="https://avatars.githubusercontent.com/u/110841041?v=4" /> | <img width="100" alt="현이" src="https://avatars.githubusercontent.com/u/122597763?v=4" /> | <img width="100" alt="동준" src="https://avatars.githubusercontent.com/u/79890834?v=4" />|<img width="100" alt="지수" src="https://avatars.githubusercontent.com/u/99534272?v=4" />|<img width="100" alt="영광" src="https://avatars.githubusercontent.com/u/104816348?v=4" />
|[박정곤](https://github.com/wjd4204)|[노현이](https://github.com/Hyunoi)|[서동준](https://github.com/SD-gif)|[이지수](https://github.com/leedidu)|[임영광](https://github.com/youngkwanglim)|

## 기능
### [REST API 문서](http://3.36.118.226:8080/swagger-ui/index.html)
### User
| 기능          | HTTP 메소드 | URL                      |
|---------------|-------------|--------------------------|
| 회원가입      | ```POST```  | ```/api/v1/user/sign-up```     |
| 로그인        | ```POST```  | ```/api/v1/user/sign-in```     |
| 인증번호 발송 | ```POST```  | ```/api/v1/user/send```        |
| 인증번호 검증 | ```POST```  | ```/api/v1/user/verify```      |
| 토큰 재발급   | ```POST```  | ```/api/v1/user/refresh-token``` |
| 로그아웃      | ```POST```  | ```/api/v1/user/logout```      |
| Oauth2 로그인 | ```GET```   | ```/login```                   |

### Contents
| 기능               | HTTP 메소드 | URL                                           |
|--------------------|-------------|-----------------------------------------------|
| 콘텐츠 추가        | ```POST```  | ```/api/v1/contents```                        |
| 콘텐츠 전체 조회   | ```GET```   | ```/api/v1/contents?category=category```      |
| 콘텐츠 상세 조회   | ```GET```   | ```/api/v1/contents/{contentId}```            |
| 콘텐츠 수정        | ```PUT```   | ```/api/v1/contents/{contentId}```            |
| 콘텐츠 삭제        | ```DELETE```| ```/api/v1/contents/{contentId}```            |

### Review
| 기능                  | HTTP 메소드 | URL                                                 |
|-----------------------|-------------|-----------------------------------------------------|
| 리뷰 생성             | ```POST```  | ```/api/v1/contents/{contentId}/reviews```          |
| 컨텐츠 리뷰 조회      | ```GET```   | ```/api/v1/contents/{contentId}/reviews```          |
| 컨텐츠 리뷰 상세 조회 | ```GET```   | ```/api/v1/contents/{contentId}/reviews/{reviewId}```|
| 리뷰 수정             | ```PUT```   | ```/api/v1/contents/{contentId}/reviews/{reviewId}```|
| 리뷰 삭제             | ```DELETE```| ```/api/v1/contents/{contentId}/reviews/{reviewId}```|

### Comment
| 기능                   | HTTP 메소드 | URL                                         |
|------------------------|-------------|---------------------------------------------|
| 댓글 작성              | ```POST```  | ```/api/v1/reviews/{reviewId}/comments```    |
| 리뷰별 댓글 전체 조회  | ```GET```   | ```/api/v1/reviews/{reviewId}/comments```    |
| 댓글 수정              | ```PUT```   | ```/api/v1/comments/{commentId}```           |
| 댓글 삭제              | ```DELETE```| ```/api/v1/comments/{commentId}```           |

### Heart
| 기능                      | HTTP 메소드 | URL                                         |
|---------------------------|-------------|---------------------------------------------|
| 컨텐츠에 대한 좋아요 추가 | ```POST```  | ```/api/v1/contents/{contentId}/hearts```   |
| 컨텐츠에 대한 좋아요 취소 | ```DELETE```| ```/api/v1/contents/{contentId}/hearts```   |
| 리뷰에 대한 좋아요 추가   | ```POST```  | ```/api/v1/reviews/{reviewId}/hearts```     |
| 리뷰에 대한 좋아요 취소   | ```DELETE```| ```/api/v1/reviews/{reviewId}/hearts```     |


### ERD

![image](https://github.com/user-attachments/assets/63e3f1bc-dd9a-4fc0-a525-3440fa2ce38f)

