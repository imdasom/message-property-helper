# Message Property Helper Library
message.properties 작업을 위한 유틸리티 라이브러리

### 1.module

#### 1.1 collect
html 파일에서 message properties를 적용해야 하는 문자열을 수집한다.

#### 1.2 generate
수집된 문자열을 바탕으로 message.properties파일을 검사하여 해당 메세지의 key를 가져온다.
search를 진행하여 존재하지 않는 message는 새로 생성한다. 

#### 1.3 search
message.properties파일에서 원하는 문자열을 검색한다.

- (1) Map<String, Message> 형태의 맵을 생성한다.
<pre><code>
resourceMap = message.properties에 정의된 key-value를 String-Message 타입으로 변환한 맵
inputMap = input으로 받은 문자열을 String-Message 타입으로 변환한 맵
Message클래스 = 메세지 문자열을 처리하는 단위. 스페이스문자열을 제거한 value와 단어로 구분한 token 배열을 속성으로 함
</code></pre>

- (2) search(resourceMap, inputMap) 로 문자열 검색진행
<pre><code>
KMP알고리즘을 이용하여 비교를 진행하며 각 Message는 다음과 같은 결과로 구분되어짐
ResultClass.TotalEqual
ResultClass.TotalSimilar
ResultClass.PartialEquals
ResultClass.PartialSimlar
</code></pre>

- Result Example
<pre><code>
<테스트코드>
@Test
public void findFromResourceFile() throws IOException {
    String[] inputDatas = new String[]{
            "제휴 계약 관리"
    };
    String location = ".\\resources\\";
    String bundleName = "messages";
    MessageSearchManager messageSearchManager = new MessageSearchManager();
    messageSearchManager.findFromResourceFile(inputDatas, location, bundleName);
}
<br><br><br>
<실행결과>
----찾고 싶은 메세지----
INPUTKEY_0001 : 제휴 계약 관리 
----결과----
	[TotalEqual]
	PROD_MANA_0001 : 제휴 계약 관리 

	[PartialEquals]
	PROD_MANA_0005 : 제휴 계약 
	PROD_MANA_0006 : 계약 

	[TotalSimilar]
	PROD_MANA_0012 : 제휴계 약 관리 
	PROD_MANA_0011 : 제휴계약관리 
</code></pre>

#### 1.4 replace
완성된 key set을 이용하여 html내부에서 해당 문자열을 message.properties의 key로 교체한다.