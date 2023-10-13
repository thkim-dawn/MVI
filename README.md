## 프로젝트 구조

### 1. MVI 아키텍처 패턴

* Model-View-Intent
    * Model - 뷰상태를 나타내는 불변 객체
    * View - 사용자에게 보여지는 화면
    * Intent - 사용자나 앱내에서 발생하는 액션(이벤트)
* 단방향 흐름을 가진 아키텍처
  ![image](https://user-images.githubusercontent.com/9432331/226881675-29a0b914-7146-465f-a66d-feeeb7cea3db.png)

### 2. Clean Architecture

* Domain Layer
    * `:feature:domain` - Usecase, Entity, Repository Interface
* Data Layer
    * `:feature:repository` - Repository 구현체, Datasource Interface
    * `:feature:datasource` - RemoteDatasource(Api관련 처리)
* Presentation Layer
    * `:feature:presentation` - MVI 패턴 Action, Viewstate 처리 로직
    * `:feature:main` - 사용자에게 보여지는 화면

* 의존성 그래프

  ![image](https://user-images.githubusercontent.com/9432331/266772133-67440946-f5bc-4817-bffb-4aa61571eed6.PNG)

## 주요 흐름

1. GalleryFragment 에서 onStart() 콜백 호출시 recyclerView item이 없다면 GalleryIntentOnStart 발행
2. GalleryActionOnStart Intent로 인해 `Flow<PagingData<GalleryEntity>>` 구독 진행
3. Paging3 라이브러리로 인해 데이터 페이징 처리

## Test Code

* GalleryPagingSourceTest - PagingSource 관련 테스트
* GalleryUseCaseTest - PagingData 관련 테스트

  ![image](https://user-images.githubusercontent.com/9432331/266658502-0056ca35-bdad-4bd7-ae98-f9e7375750a8.png)

