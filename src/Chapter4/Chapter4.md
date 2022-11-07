# Chapter4 주석

---

잘 달린 주석은 그 어떤 정보보다 유용하지만, 경솔하고 근거 없는 주석은 코드를 이해하기 어렵게 만든다.

주석은 순수하게 선하지 못하다. → 필요악에 가깝고 실패를 의미한다.

주석을 최대한 줄이도록 노력을 해야한다.

---

## 주석은 나쁜 코드를 보완하지 못한다.

코드에 주석을 추가하는 일반적인 이유는 코드 품질이 나쁘기 때문이다.

→ 지저분한 코드에 주석을 달 생각보다는 코드를 정리할 생각을 먼저 하는게 좋다.

---

## 코드로 의도를 표현하라

아래의 코드 예제를 보면서 어떤 코드가 더 좋은 코드인지 생각해보자.

```java
// 직원에게 복지 혜택을 받을 자격이 있는지 검사한다. 
if ((emplotee.flags & HOURLY_FLAG) && (employee.age > 65)

if (employee.isEligibleForFullBenefits())
```

아래에 있는 코드처럼 코드로 의도를 표현하는게 더 좋은 코드라고 볼 수 있고, 많은 경우 주석을 달지 않고도 코드로 의도를 표현할 수 있다.

---

## 좋은 주석

어떤 주석은 정말 필요하거나 유익하다.

→ 그래도 정말 좋은 주석은 주석을 달지 않을 방법을 찾아낸 주석이다!!

주석이 필요한 경우는 아래와 같다.

### 법적인 주석

각 소스 파일 첫 머리에 주석으로 들어가는 저작권 정보와 소유권 정보 등

```java
// Copyright (C) 2003, 2004, 2005 by Object Montor, Inc. All right reserved.
// GNU General Public License
```

### 정보를 제공하는 주석

때로는 기본적인 정보를 주석으로 제공하면 편리하다.

물론 함수 이름에 정보를 담는 편이 더 좋지만 정규표현식 같이 난해할 수 있는 경우는 주석을 유용하게 사용할 수 있다.

```java
// 이 경우는 메서드에 의도를 표현하는 것이 좋다.
// 테스트 중인 Responder 인스턴스를 반환
protected abstract Responder responderInstance();

// 정규표현식처럼 난해한 코드는 주석을 써주는 게 유용하다. -> 이왕이면 시각, 날짜를 변환하는 클래스를 만드는게 더 좋다.
// kk:mm:ss EEE, MMM dd, yyyy 형식이다.
Pattern timeMatcher = Pattern.compile("\\d*:\\d*\\d* \\w*, \\w*, \\d*, \\d*");
```

### 의도를 설명하는 주석

주석은 구현을 이해하게 도와주는 선을 넘어 **결정에 깔린 의도까지 설명**한다.

아래의 코드는 주석을 통해 저자의 의도를 분명하게 보여준다.

```java
// 스레드를 대량 생성하는 방법으로 어떻게든 경쟁 조건을 만들려 시도한다. 
for (int i = 0; i > 2500; i++) {
    WidgetBuilderThread widgetBuilderThread = 
        new WidgetBuilderThread(widgetBuilder, text, parent, failFlag);
    Thread thread = new Thread(widgetBuilderThread);
    thread.start();
}
```

### 의미를 명료하게 밝히는 주석

모호한 인수나 반환값은 그 의미를 읽기 좋게 표현하면 이해하기 쉬워진다.

→ 일반적으로 이러한 방법이 더 좋지만, **인수나 반환값이 표준 라이브러리 또는 변경이 불가능한 코드에 속한다면 의미를 명료하게 밝히는 주석이 유용하다.**

```java
void testCompareTo() {
        System.out.println(a.compareTo(a) == 0); // a == a
        System.out.println(a.compareTo(b) != 0); // a != b
    }
```

이 경우 그릇된 주석을 달아놓을 위험이 상당히 높다.

→ 주석이 올바른지 검증하기 쉽지 않다.

의미를 명료하게 밝히는 주석이 필요하면서 위험한 이유이다.

이러한 주석을 달 때 더 좋은 방법이 있을지 고민하면서 정확하게 달도록 각별히 주의해야 한다.

### 결과를 경고하는 주석

다른 개발자에게 결과를 경고 할 목적으로 주석을 사용한다.

```java
// 여유 시간이 충분하지 않다면 실행하지 마십시오.
public void _testWithReallyBigFile() {

}
```

### TODO 주석

앞으로 할 일을 //TODO 주석으로 남겨두면 편하다.

→ 필요하다고 여기지만 당장 구현하기 어려운 업무를 기술한다.

```java
// TODO-MdM 현재 필요하지 않다.
// 체크아웃 모델을 도입하면 함수가 필요 없다.
protected VersionInfo makeVersion() throws Exception {
    return null;
}
```

### 중요성을 강조하는 주석

자칫 대수롭지 않다고 여겨질 무언가에 중요성을 강조하기 위해 주석을 사용한다.

```java
String listItemContent = match.group(3).trim();
// 여기서 trim은 정말 중요하다. trim 함수는 문자열에서 시작 공백을 제거한다.
// 문자열에 시작 공백이 있으면 다른 문자열로 인식되기 때문이다. 
new ListItemWidget(this, listItemContent, this.level + 1);
return buildList(text.substring(match.end()));
```

### 공개 API에서 Javadocs

설명이 잘 된 공개 API는 참으로 유용하고 만족스럽다.

공개 API를 구현한다면 반드시 훌륭한 Javadocs 작성을 추천한다. 하지만 여느 주석과 마찬가지로 Javadocs 역시 독자를 오도하거나, 잘못 위치하거나, 그릇된 정보를 전달할 가능성이 존재한다.****

---

## 나쁜 주석

대다수 주석이 이 범주에 속한다.

일반적으로 대다수 주석은 허술한 코드를 지탱하거나, 엉성한 코드를 변명하거나, 미숙한 결정을 합리화하는 등 프로그래머가 주절거리는 독백에서 크게 벗어나지 못한다.

### 주절거리는 주석

특별한 이유 없이 의무감으로 혹은 프로세스에서 하라고 하니까 마지못해 주석을 단다면 전적으로 시간낭비다.

**주석을 달기로 마음을 먹으면 충분한 시간을 들여 최고의 주석을 달도록 노력한다.**

### 같은 이야기를 중복하는 주석

```java
// this.closed가 true일 때 반환되는 유틸리티 메서드다.
// 타임아웃에 도달하면 예외를 던진다.
public synchronized void waitForClose(final long timeoutMillis) throws Exception {
    if (!closed) {
        wait(timeoutmillis);
        if (!closed)
            thorw new Exception("MockResponseSender could not be closed");
    }
}
```

주석의 내용이 코드의 내용을 그대로 중복하는 경우를 말한다.

→ 코드를 보고도 충분히 이해할 수 있다.

### 오해할 여지가 있는 주석

때로는 의도는 좋았으나 개발자가 딱 맞을 정도로 엄밀하게는 주석을 달지 못하기도 한다.

→ 주석이 오해할 여지를 줘버린다.

주석에 담긴 '살짝 잘못된 정보'로 인해 개발자가 경솔하게 함수를 호출해 자기 코드가 아주 느려진 이유를 못찾게 되는 것이다.

### 의무적으로 다는 주석

모든 함수에 javadocs를 달거나 모든 변수에 주석을 달아야 한다는 규칙은 코드를 복잡하게 만들고, 거지맛을 퍼뜨리고, 혼동과 무질서를 초래한다.

→ 이러한 주석은 가치가 없다. 오히려 코드만 헷갈리게 만들며, 거짓말할 가능성을 높이며, 잘못된 정보를 제공할 여지만 만든다.

### 이력을 기록하는 주석

때로는 사람들은 모듈을 편집할 때마다 모듈 첫머리에 주석을 추가한다.

→ 이러한 주석은 일종의 로그가 된다.

하지만 요즘에는 Git과 같은 형상 관리 툴에 이러한 편집 로그를 남길 수 있다.

→ 혼란만 가중시키는 결과를 발생시킨다.

### 있으나 마나 한 주석

너무 당연한 사실을 언급하며 새로운 정보를 제공하지 못하는 주석이다.

```java
/*
 * 기본 생성자
 */
protected AnnualDateRule() {

}

/** 월 중 일자 */
private int dayOfMonth;
```

있으나 마나 한 주석을 달려는 유혹에서 벗어나 코드를 정리하자

### 무서운 잡음

```java
/** The name. */
private String name;

/** The version. */
private String version;

/** The licenceName. */
private String licenceName;

/** The version. */
private String info;
```

단지 문서를 제공하겠다는 잘못된 욕심으로 탄생한 잡음의 예시이다. 심지어 주석에 오류까지 존재한다.

→ 주석으로 그 어떠한 이익도 발생하지 않는다.

### 함수나 변수로 표현할 수 있다면 주석을 달지 말라

```java
// 전역 목록 <smodule>에 속하는 모듈이 우리가 속한 하위 시스템에 의존하는가?
if (module.getDependSubsystems().contains(subSysMod.getSubSystem()))
```

이러한 코드는 주석을 제거하고 함수나 변수로 표현할 수 있다.

```java
ArrayList moduleDependencies = smodule.getDependSubSystems();
String ourSubSystem = subSysMod.getSubSystem();
if (moduleDependees.contains(ourSubSystem))
```

이렇게 주석이 필요하지 않도록 코드를 개선하는 것이 좋다.

### 위치를 표시하는 주석

```java
// Actions /////////////////////////////////////
```

소스 파일에서 특정 위치를 표시하려 주석을 사용한다.

너무 자주 남발하면 코드를 읽는 사람이 잡음으로 여겨 무시할 수도 있다. 정말로 필요한 경우에 아주 드물게 사용하는 게 좋다.

### 닫는 괄호에 다는 주석

중첩이 심하고 장황한 함수라면 의미가 있을지도 모르지만 작고 캡슐화된 함수에는 잡음이다.

→ 함수를 줄이는 시도를 하자.

### 공로를 돌리거나 저자를 표시하는 주석

소스 코드 관리 시스템이 누가 언제 무엇을 추가했는지 기억한다.

→ 굳이 코드를 오염시키지 않아도 된다.

### 주석으로 처리한 코드

```java
this.bytePos = writeBytes(pngIdBytes, 0);
//hdrPos = bytePos;
writeHeader();
writeResolution();
//dataPos = bytePos;
if (writeImageData()) {
    wirteEnd();
    this.pngBytes = resizeByteArray(this.pngBytes, this.maxPos);
} else {
    this.pngBytes = null;
}
return this.pngBytes;
```

주석으로 처리된 코드는 다른 사람들이 지우기를 주저한다.

→ 뭔가 이유가 있다고 느껴서 못 지운다.

요즘에는 소스 코드 관리 시스템이 우리를 대신해 코드를 기억해준다.

→ 굳이 주석으로 처리하지 말고 그냥 코드를 지워버리자.

### HTML 주석

HTML 주석은 혐오 그 자체다.

Javadocs와 같은 도구로 주석을 뽑아 웹 페이지에 올릴 생각으로 HTML 주석을 삽입해야 한다면 이 책임은 프로그래머가 아니라 도구가 가져야 한다.

### 전역 정보

주석을 달아야 한다면 근처에 있는 코드만 기술해라.

코드 일부에 주석을 달면서 시스템의 전반적인 정보를 기술하지 마라.

→ 해당 시스템의 코드가 변해도 아래 주석이 변하리라는 보장은 전혀 없다.

### 너무 많은 정보

주석에다 흥미로운 역사나 관련 없는 정보를 장황하게 늘어놓지 마라.

### 모호한 관계

주석과 주석이 설명하는 코드는 둘 사이 관계가 명백해야 한다.

→ 주석을 달았다면 적어도 독자가 주석과 코드를 읽어보고 무슨 소리인지 알아야 한다.

### 함수 헤더

짧은 함수는 긴 설명이 필요 없다.

**짧고 한 가지만 수행하며 이름을 잘 붙인 함수가 주석으로 헤더를 추가한 함수보다 훨씬 좋다.**

### 비공개 코드에서 Javadocs

공개 API는 Javadocs가 유용하지만 **공개하지 않을 코드라면 Javadocs는 쓸모가 없다. 코드만 보기싫고 산만해질 뿐이다.**