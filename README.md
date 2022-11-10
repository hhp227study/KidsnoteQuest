키즈노트 사전과제: KidsnoteQuest
=================

사용기술:
MDC Android
Retrofit2
Hilt
Paging3
AAC ViewModel
DataBinding
Kotlinx Serialization

Introduction
------------

앱 아키텍쳐는 여러 정형화된 앱 탬플릿들이 많지만 구글에서 권장하는 방식을 채택하여 설계하였습니다
[참고링크]: https://developer.android.com/topic/architecture
화면 구성은 최신 UIKit인 JetpackCompose를 고려하였지만 익숙하지않아, 기존방식의 MDC Android를 이용하여 작성하였습니다.
이미지가 메인이므로 리스트 형식의 화면을 구성하였으며 작가(author)가 노출되게 구성하였습니다.
기본적으로 api를 호출하기때문에 네트워크 체크 하는 기능을 구현하였습니다.
