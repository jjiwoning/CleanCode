# Chapter 9 단위 테스트

---

## TDD 법칙 세 가지

- 첫째 법칙: 실패하는 단위 테스트를 작성할 때까지 실제 코드를 작성하지 않는다.
- 둘째 법칙: 컴파일은 실패하지 않으면서 실행이 실패하는 정도로만 단위 테스트를 작성한다.
- 셋째 법칙: 현재 실패하는 테스트를 통과할 정도로만 실제 코드를 작성한다.

이 규칙을 따르면 개발과 테스트가 대략 30초 주기로 묶인다. 테스트 코드와 실제 코드가 함께 나올뿐더러 테스트 코드가 실제 코드보다 불과 몇 초 전에 나온다.

이렇게 일하면 매일 수십 개, 매달 수백 개, 매년 수천 개에 달하는 테스트 케이스가 나온다.

→ 실제 코드와 맞먹을 정도로 방대한 테스트 코드는 심각한 관리 문제를 유발하기도 한다.

---

## 깨끗한 테스트 코드 유지하기

지저분한 테스트 코드는 많은 문제를 일으킨다.

- 테스트 코드가 지저분할수록 변경하기 어려워진다.
- 실제 코드를 변경해 기존 테스트가 실패하기 시작하면, 지저분한 코드로 인해, 실패하는 테스트 케이스를 점점 더 통과하기 어려워진다.
- 새 버전을 출시할 때마다 팀이 테스트 케이스를 유지하고 보수하는 비용도 늘어난다.
  → 테스트 코드가 가장 큰 불만으로 자리잡는다.
- 그렇다고 테스트 코드가 없다면 시스템의 결함율이 높아진다.
  → 개발자가 변경을 주저하게 된다.
- 결국에는 코드가 망가지기 시작한다.

결론적으로 **테스트 코드는 실제 코드 못지 않게 중요하다!!**

### 테스트는 유연성, 유지보수성, 재사용성을 제공한다.

테스트 코드를 깨끗하게 유지하지 않으면 결국은 잃어버린다.

→ 테스트 케이스를 잃어버리면 실제 코드를 유연하게 만드는 버팀목도 사라진다.

→ 코드에 유연성, 유지보수성, 재사용성을 제공하는 버팀목이 바로 **단위 테스트**다.

**테스트 코드가 잘 설계되어 있다면 아키텍처가 부실한 코드나 설계가 모호하고 엉망인 코드라도 별다른 우려 없이 변경할 수 있다.**

**실제 코드를 점검하는 자동화된 단위 테스트 슈트는 설계와 아키텍처를 최대한 깨끗하게 보존하는 열쇠다.**

테스트 케이스가 있으면 변경이 쉬워지기 때문이다.

---

## 깨끗한 테스트 코드

**깨끗한 테스트 코드의 핵심은 가독성이다!!!!**

→ 정말 제일 중요하다!!

가독성을 높이기 위해 명료성, 단순성, 풍부한 표현력이 필요하다.

최소한의 표현으로 많은 것을 나타내야 한다.

아래의 테스트 코드를 개선해보자

```java
public void testGetPageHieratchyAsXml() throws Exception {
  crawler.addPage(root, PathParser.parse("PageOne"));
  crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
  crawler.addPage(root, PathParser.parse("PageTwo"));

  request.setResource("root");
  request.addInput("type", "pages");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("<name>PageOne</name>", xml);
  assertSubString("<name>PageTwo</name>", xml);
  assertSubString("<name>ChildOne</name>", xml);
}

public void testGetPageHieratchyAsXmlDoesntContainSymbolicLinks() throws Exception {
  WikiPage pageOne = crawler.addPage(root, PathParser.parse("PageOne"));
  crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
  crawler.addPage(root, PathParser.parse("PageTwo"));

  PageData data = pageOne.getData();
  WikiPageProperties properties = data.getProperties();
  WikiPageProperty symLinks = properties.set(SymbolicPage.PROPERTY_NAME);
  symLinks.set("SymPage", "PageTwo");
  pageOne.commit(data);

  request.setResource("root");
  request.addInput("type", "pages");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("<name>PageOne</name>", xml);
  assertSubString("<name>PageTwo</name>", xml);
  assertSubString("<name>ChildOne</name>", xml);
  assertNotSubString("SymPage", xml);
}

public void testGetDataAsHtml() throws Exception {
  crawler.addPage(root, PathParser.parse("TestPageOne"), "test page");

  request.setResource("TestPageOne"); request.addInput("type", "data");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("test page", xml);
  assertSubString("<Test", xml);
}
```

해당 테스트 코드는 addPage와 assertSubString을 부르느라 중복되는 코드가 매우 많다.

→ 이러한 자질구레한 사항이 너무 많아 테스트 코드의 표현력이 떨어진다.

```java
public void testGetPageHierarchyAsXml() throws Exception {
  makePages("PageOne", "PageOne.ChildOne", "PageTwo");

  submitRequest("root", "type:pages");

  assertResponseIsXML();
  assertResponseContains(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>");
}

public void testSymbolicLinksAreNotInXmlPageHierarchy() throws Exception {
  WikiPage page = makePage("PageOne");
  makePages("PageOne.ChildOne", "PageTwo");

  addLinkTo(page, "PageTwo", "SymPage");

  submitRequest("root", "type:pages");

  assertResponseIsXML();
  assertResponseContains(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>");
  assertResponseDoesNotContain("SymPage");
}

public void testGetDataAsXml() throws Exception {
  makePageWithContent("TestPageOne", "test page");

  submitRequest("TestPageOne", "type:data");

  assertResponseIsXML();
  assertResponseContains("test page", "<Test");
}
```

BUILD-OPERATE-CHECK 패턴을 적용한다.

- 첫 부분은 테스트 자료를 만든다.
- 두 번째 부분은 테스트 자료를 조작한다.
- 세 번째 부분은 조작한 결과가 올바른지 확인한다.

잡다하고 세세한 코드를 거의 다 없애고 테스트 코드는 본론에 돌입해 진짜 필요한 자료 유형과 함수만 사용한다.

코드를 읽는 사람은 코드가 수행하는 기능을 재빨리 이해한다.

### 도메인에 특화된 테스트 언어

위의 테스트 코드는 도메인에 특화된 언어로 테스트 코드를 구현하는 기법을 보여준다.

흔히 쓰는 시스템 조작 API를 사용하는 대신 API 위에다 함수와 유틸리티를 구현한 후 그 함수와 유틸리티를 사용하므로 테스트 코드를 짜기도 읽기도 쉬워지는 장점이 있다.

이렇게 구현한 함수와 유틸리티는 테스트 코드에서 사용하는 특수 API가 된다.

잡다하고 세세한 사항으로 범벅된 코드를 리팩토링하다가 진화된 API이다.

### 이중 표준

테스트 코드에 적용하는 표준은 실제 코드에 적용하는 표준과 확실히 다르다.

테스트 코드의 경우 단순하고, 간결하고, 표현력이 풍부해야 하지만, 실제 코드만큼 효율적일 필요는 없다.

→ 실제 환경과 테스트 환경의 요구사항은 다르기 때문에

```java
@Test
public void turnOnLoTempAlarmAtThreashold() throws Exception {
  hw.setTemp(WAY_TOO_COLD); 
  controller.tic(); 
  assertTrue(hw.heaterState());   
  assertTrue(hw.blowerState()); 
  assertFalse(hw.coolerState()); 
  assertFalse(hw.hiTempAlarm());       
  assertTrue(hw.loTempAlarm());
}
```

해당 테스트 코드는 점검하는 상태 이름과 상태 값을 확인하느라 눈길이 이리저리 흩어진다.

heaterState라는 상태 이름을 확인하고 왼쪽으로 눈길을 돌려 assertTrue를 읽는다. 이런식으로 모든 state를 확인해야 하기 때문에 읽기가 어렵다.

아래와 같은 방법으로 테스트 코드를 작성하는 것이 좋다.

```java
@Test
public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
  tooHot();
  assertEquals("hBChl", hw.getState()); 
}
  
@Test
public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
  tooCold();
  assertEquals("HBchl", hw.getState()); 
}

@Test
public void turnOnHiTempAlarmAtThreshold() throws Exception {
  wayTooHot();
  assertEquals("hBCHl", hw.getState()); 
}

@Test
public void turnOnLoTempAlarmAtThreshold() throws Exception {
  wayTooCold();
  assertEquals("HBchL", hw.getState()); 
}
```

```java
public String getState() {
  String state = "";
  state += heater ? "H" : "h"; 
  state += blower ? "B" : "b"; 
  state += cooler ? "C" : "c"; 
  state += hiTempAlarm ? "H" : "h"; 
  state += loTempAlarm ? "L" : "l"; 
  return state;
}
```

이 코드도 실제 환경이라면 StringBuffer 또는 Builder를 쓰는게 이상적이다. 하지만 테스트 환경에서 하드웨어의 조건이 제한적이지 않기 때문에 String으로 깔끔하게 작성을 해도 된다.

→ 이것이 이중 표본의 핵심이다. 실제 환경에서는 절대로 사용하면 안되더라도 테스트 환경에서는 전혀 문제가 없는 방법이다.

---

## 테스트 당 assert 하나

assert 문이 하나인 함수는 결론이 하나라서 코드를 이해하기 쉽고 빠르다.

하지만 assert 문이 하나만 있으면 문제가 발생하는 경우도 있다. 이런 경우는 테스트를 적절히 나누어 assert 문을 사용하는 것을 권장한다.

아래는 테스트를 적절하게 나누는 예시 코드이다.

```java
public void testGetPageHierarchyAsXml() throws Exception { 
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  
  whenRequestIsIssued("root", "type:pages");
  
  thenResponseShouldBeXML(); 
}

public void testGetPageHierarchyHasRightTags() throws Exception { 
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  
  whenRequestIsIssued("root", "type:pages");
  
  thenResponseShouldContain(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>"
  ); 
}
```

given-when-then 케이스를 사용해서 테스트를 작성한 예시이다.

→ 테스트 코드를 읽기가 쉬워진다.

테스트를 나누는 경우에는 테스트에 중복되는 코드가 많아지는 단점이 있다.

→ TEMPLATE METHOD 패턴을 사용하면 중복을 제거할 수 있다. given/when 부분을 부모 클래스에 두고 then 부분을 자식 클래스에 두면 된다. 아니면 완전히 독자적인 테스트 클래스를 만들어 @Before 함수에 given/when 부분을 넣고 @Test 함수에 then 부분을 넣어도 된다.

→ 하지만 모두가 배보다 배꼽이 더 크다. 이것저것 감안해 보면 결국 목록 9-2처럼 assert 문을 여럿 사용하는 편이 좋다고 생각한다.

**결론은 하나의 assert 문을 사용하는 것이 가장 좋지만 상황에 따라 여러 개를 사용해도 된다. 단, assert 문의 갯수를 최소화하려고 노력하는 것을 잊지말자!!**

### 테스트 당 개념 하나

어쩌면 **테스트 함수마다 한 개념만 테스트**하라는 규칙이 더 낫다.

이것저것 잡다한 개념을 연속으로 테스트하는 긴 함수는 피하는 게 좋다.

여러 개념을 하나의 함수에 몰아넣으면 독자가 각 절이 거기에 존재하는 이유와 각 절이 테스트하는 개념을 모두 이해해야 한다.

결론적으로 **개념 당 assert 문의 갯수를 최소로 줄여라 + 테스트 함수 하나는 개념 하나만 테스트**하라이다.

---

## F.I.R.S.T

깨끗한 테스트 코드는 5가지 규칙을 따른다.

- 빠르게(FAST)
  테스트는 빨리 돌아야한다. → 자주 돌려야 하기 때문에 → 자주 돌려야 초반에 문제를 찾아 고친다.
- 독립적으로(Independent)
  각 테스트는 서로 의존하면 안 된다.
  각 테스트는 독립적으로 그리고 어떤 순서로 실행해도 괜찮아야 한다.
  → 의존적이게 되면 하나가 실패하면 나머지도 실패하므로 원인을 찾기가 어려워지고 후반 테스트가 찾아야 할 결험이 숨겨진다.
- 반복가능하게(Repeatable)
  테스트는 어떤 환경에서도 반복 가능해야 한다. 실제 환경, QA 환경 등 여러 환경에서 실행할 수 있어야 한다.
- 자가검증하는(Self-Validating)
  테스트는 boolean 값으로 결과를 내야 한다. → true or false가 나와야 한다.
  스스로 성공과 실패를 가늠하지 않는다면 판단은 주관적이게 되며 지루한 수작업을 해야하기 때문
- 적시에(Timely)
  테스트는 적시에 작성해야 한다. → 단위 테스트는 테스트하려는 실제 코드를 구현하기 직전에 구현한다.
  실제 코드를 먼저 구현한다면 테스트 코드를 작성하기 어렵거나, 테스트하기 어려운 상황에 빠질 수 있다.

---

## 결론

깨끗한 테스트 코드라는 주제는 책 한 권에도 부족한 주제이다.

테스트 코드는 실제 코드의 유연성, 유지보수성, 재사용성을 보존하고 강화한다.

**테스트 코드를 지속적으로 깨끗하게 관리하자. 표현력을 높이고 간결하게 정리하자.**

테스트 API를 구현해 도메인 특화 언어를 만들자.