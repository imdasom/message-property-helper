# Message Property Helper Library
message.properties 다국어처리를 위한 유틸리티 라이브러리

![alt text](readme-resource/message-property-generator-gui-sample-20181104.png)

### MainLogic
<pre><code>public class MainLogicTest {

    @Test
    public void main() throws IOException {

        // module instatiate (모듈 준비)
        KeyValueTokenizer tokenizer = new KeyValueTokenizer();
        KeyValueSearcher searcher = KeyValueSearcher.getInstance();
        KeyValueReplacer replacer = new KeyValueReplacer();
        KeyValueCollector collector = KeyValueCollector.getInstance();

        //resource : message properties bundle  (Key-Value가 들어있는 파일이나 리소스 준비)
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);

        //resource : html file (타겟이 되는 html 파일 준비)
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        InputStream inputStream = FileUtils.getInputStream(htmlFile);
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream().map(Expression::new).collect(Collectors.toList());

        //regular expression pattern (패턴을 찾아주는 Search과 Pattener 준비)
        ThymeleafTextValuePatternSearcher thymeleafTextValuePatternSearcher = ThymeleafTextValuePatternSearcher.getInstance();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();

        //key name rule  (키생성 규칙 준비)
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceTokenList);

        //collect  (타겟html 파일에서 원하는 문자열 찾기)
        List<Expression> thymeleafTextExpressions = collector.collect(readLineExpressions, thymeleafTextValuePatternSearcher);

        //search  (찾은 문자열이 messages.proerties에 존재하는지 찾기)
        List<SearchResult> searchResults = searcher.search(thymeleafTextExpressions, resourceTokenList);

        // generate or get  (messages.properties에 Key-Value가 존재하지 않으면 생성하고, 존재하면 그 Key-Value를 가져온다)
        SearchResultFilter searchResultFilter = new SearchResultFilter();
        List<SearchResult> failureSearchReuslts = searchResultFilter.getSearchResult(searchResults, ResultClass.TotalSimilar, false);
        List<KeyValue> newMessageProperties = searchResultFilter.getMessageProperties(failureSearchReuslts, new Function<SearchResult, KeyValue>() {
            @Override
            public KeyValue apply(SearchResult result) {
                Expression failureExpressions = new Expression(result.getMessage().getOriginMessage());
                return KeyValueGenerator.generate(failureExpressions, keyNameRule);
            }
        });

        List<SearchResult> successSearchResluts = searchResultFilter.getSearchResult(searchResults, ResultClass.TotalSimilar, true);
        List<KeyValue> oldMessageProperties = searchResultFilter.getMessageProperties(successSearchResluts, new Function<SearchResult, KeyValue>() {
            @Override
            public KeyValue apply(SearchResult result) {
                Key key = result.getKeyByLevel(ResultClass.TotalSimilar).get();
                Value value = new Value(result.getMessage().getOriginMessage());
                return new KeyValue(key, value);
            }
        });
        oldMessageProperties.addAll(newMessageProperties);

        //replace  (타겟 html 파일에 Value가 존재하면 Key로 교체)
        List<Expression> afterLines = replacer.replace(oldMessageProperties,
                readLineExpressions,
                thymeleafTextValuePatterner,
                thymeleafTextValuePatternSearcher);
 
        //replace   (타겟 html 파일에 Value가 존재하면 Key로 교체)
        List<Expression> afterLines2 = replacer.replace(oldMessageProperties,
                afterLines,
                thymeleafTextValuePatterner,
                thymeleafTextValuePatternSearcher);

        for (KeyValue keyValue : newMessageProperties) {
            System.out.println(keyValue.getKey().getValue() + "=" + keyValue.getValue().getValue());
        }

        for(Expression e : afterLines2) {
            System.out.println(e.getValue());
        }
        
        //write file     (새로 생성한 Key-Value는 messages.properties에 쓰기)
        OutputStream outputStream = FileUtils.getOutputStream(htmlFile);
        for (Expression e : afterLines2) {
            outputStream.write(e.getValue().getBytes());
            outputStream.write("\n".getBytes());
        }
        outputStream.flush();
        outputStream.close();
        
        //set properties
        Properties properties = new Properties();
        for (KeyValue keyValue : newMessageProperties) {
            properties.setProperty(keyValue.getKey().getValue(), keyValue.getValue().getValue());
        }
        
        //ready properties output stream
        OutputStream propertiesOutputStream_default  = new FileOutputStream(location + "\\messages.properties", true);
        OutputStream propertiesOutputStream_ko = new FileOutputStream(location + "\\messages_ko.properties", true);
        OutputStream propertiesOutputStream_en = new FileOutputStream(location + "\\messages_en.properties", true);
        OutputStream[] propertiesOutputStreamList = new OutputStream[] {
            propertiesOutputStream_default, propertiesOutputStream_ko, propertiesOutputStream_en
        };
        
        //store properties
        Arrays.stream(propertiesOutputStreamList).forEach(outputStrema1 -> {
            try {
                outputStream1.write("\n\n".getBytes());
                properties.store(outputStream1, "comment");
            } catch (IOException e) {
                e.printStackTrace();
        });
    }
}
</code></pre>

### 1.module

#### 1.0 common
<pre><code>//base common model having String value 
//which is used when migrate data to other module.
public class Expression {
    private String value;
}
</code></pre>
<pre><code>//used to express Message Property
//having Key and Value
public class MessageProperty {
    private Key key;
    private Value value;
}
</code></pre>

#### 1.1 collect
특정 Input 데이터에 대하여 원하는 문자열을 수집한다.
<pre><code>//using interface (T:input type, U:output type)
public interface PatternSearcher<T, U> {
   List<U> get(T source);
}
</code></pre>
<pre><code>//define regular expression or something you want to get from pattern
PatternSearcher<Expression, Expression> thymeleafTextValuePatternSearcher = new ThymeleafTextPatternSearcher();
PatternSearcher<Expression, Expression> valuePatternSearcher = new ValuePatternSearcher();
</code></pre>
<pre><code>//collect from list of Expression
List<Expression> thymeleafTextExpressions = collector.collect(expressions, thymeleafTextValuePatternSearcher);
List<Expression> valuePatternExpressions = collector.collect(thymeleafTextExpressions, valuePatternSearcher);
</code></pre>

#### 1.2 search
message.properties파일에서 입력으로 받은 문자열을 검색한다.
<pre><code>KMP알고리즘을 이용하여 비교를 진행하며, 각 Message는 다음과 같은 결과로 구분되어짐
ResultClass.TotalEqual
ResultClass.TotalSimilar
ResultClass.PartialEquals
ResultClass.PartialSimlar
</code></pre>

Result Example
<pre><code><테스트코드>
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
</code></pre>
<pre><code><실행결과>
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

#### 1.3 generate
특정 Input데이터에 대하여 Key를 생성한다. 입력으로 받은 KeyNameRule 구현부를 이용하여 특정 규칙을 가지는 key를 생성한다.
<pre><code>//using interface KeyNameRule
public interface KeyNameRule {
    String getKey(Expression expression);
}
</code></pre>
<pre><code>//define key name rule
KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", messageProertyMap);
</code></pre>
<pre><code>//generate key name by KeyNameRule implementation
List<MessageProperty> messageProperties = generator.generate(failureExpressions, keyNameRule);
</code></pre>

#### 1.4 replace
완성된 key set을 이용하여 html내부에서 해당 문자열을 message.properties의 key로 교체한다.
