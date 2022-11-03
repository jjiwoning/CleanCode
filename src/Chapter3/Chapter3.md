# Chapter 3 함수

---

## 작게 만들어라

함수를 만드는 규칙은 ‘작게’이다.

함수는 최대한 작게 만드는 것이 좋다!!

```java
public static String renderPageWithSetupsAndTeardowns( PageData pageData, boolean isSuite) throws Exception {
	boolean isTestPage = pageData.hasAttribute("Test"); 
	if (isTestPage) {
		WikiPage testPage = pageData.getWikiPage(); 
		StringBuffer newPageContent = new StringBuffer(); 
		includeSetupPages(testPage, newPageContent, isSuite); 
		newPageContent.append(pageData.getContent()); 
		includeTeardownPages(testPage, newPageContent, isSuite); 
		pageData.setContent(newPageContent.toString());
	}
	return pageData.getHtml(); 
}
```

이런 함수를 짧게 만들어야 한다.

```java
public static String renderPageWithSetupsAndTeardowns( PageData pageData, boolean isSuite) throws Exception { 
   if (isTestPage(pageData)) 
   	includeSetupAndTeardownPages(pageData, isSuite); 
   return pageData.getHtml();
}
```

**블록과 들여쓰기**

if 문/else 문/while 문 등에 들어가는 블록은 한 줄이어야 한다. → 중첩 구조가 생길만큼 함수가 커져서는 안된다.

**함수에서 들여쓰기 수준은 1단이나 2단을 넘어가지 않는게 좋다!!**

---

## 한 가지만 해라

**함수는 한 가지를 해야한다. 그 한 가지를 잘 해야 한다. 그 한 가지만을 해야 한다.**

지정된 함수 이름 아래에서 추상화 수준이 하나인 단계만 수행한다면 그 함수는 한 가지 작업만 한다.

이를 확인하는 방법은 만약 함수 하나에서 의미 있는 이름으로 다른 함수를 추출할 수 있다면 그 함수는 여러 작업을 하는 것이다.

→ 함수를 여러 작업을 수행한다면 섹션이 여러개이다.

---

## 함수 당 추상화 수준은 하나로

**함수가 확실히 한 가지 작업을 하려면 함수 내 모든 문장의 추상화 수준이 동일해야 한다.**

한 함수 내에 추상화 수준을 섞으면 코드를 읽는 사람이 헷갈린다.

> 추상화 수준이 높다: getHtml()
추상화 수준이 중간: String pagePathName = PathParser.render(pagepath);
추상화 수준이 낮다: .append(”\n”)
>

위에서 아래로 코드 읽기: **내려가기** 규칙

코드는 위에서 아래로 이야기처럼 읽혀야 좋다. → **위에서 아래로 프로그램을 읽으면 함수 추상화 수준이 한 번에 한 단계씩 낮아진다.**

---

## Switch 문

switch 문은 작게 만들기 어렵다. 마찬가지로 if/else 가 여러 개 이어지는 코드가 작게 만들기가 어렵다.

→ switch 문은 N가지를 처리하는 문장이기 때문에

```java
public Money calculatePay(Employee e) throws InvalidEmployeeType {
  switch (e.type) {
    case COMMISIONED :
      return calculateCommissionedPay(e);
    case HOURLY :
      return calculateHourlyPay(e);
    case SALARIED :
      return calculateSalariedPay(e);
    default :
      throw new InvalidEmployeeType(e.type);
  }
}
```

해당 코드의 문제점은 아래와 같다.

1. 함수가 길다. → 직원 유형이 추가되면 더 길어진다.
2. 한 가지 작업만 수행하지 않는다.
3. SRP(단일 책임 원칙)을 위반한다.
4. OCP(개방 폐쇄 원칙)을 위반한다. → 직원 유형이 추가될 때마다 코드를 변경하기 때문
5. 위 함수와 구조가 동일한 함수가 무한정 존재한다.

해당 코드의 문제를 해결한 코드는 아래와 같다.

```java
public abstract class Employee {
  public abstract boolean isPayday();
  public abstract boolean calculatePay();
  public abstract boolean deliverPay(Money pay);
}

-------------------------------------------------------------------

public interface EmployeeFactory {
  public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
}

-------------------------------------------------------------------

public class EmployeeFactoryImpl implements EmployeeFactory {
  public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
    switch (r.type) {
      case COMMISIONED :
        return new CommissionedEmployee(r);
      case HOURLY :
        return new HourlyEmployee(r);
      case SALARIED :
        return new SalariedEmployee(r);
      default :
        throw new InvalidEmployeeType(r.type);
    }
  }
}
```

switch 문을 추상 팩토리 패턴을 이용하여 숨긴다.

팩토리는 switch 문을 사용해 적절한 Employee 파생 클래스의 인스턴스를 생성한다.

정말 불가피하게 switch 문을 써야하는 상황이 생기기도 하지만 최대한 안 쓰고 쓴다면 잘 숨기는게 좋다.

책의 저자는 다형적 객체를 생성하는 코드 내부에서는 switch 문을 한 번은 참는다고 한다.

---

## 서술적인 이름을 사용하라

서술적인 이름을 사용하여 함수가 하는 일을 좀 더 잘 표현할 수 있다.

→ 코드를 읽으면서 짐작했던 기능을 각 루틴이 그대로 수행한다면 깨끗한 코드라 불러도 된다.

**함수가 작고 단순할수록 서술적인 이름을 고르기도 쉬워진다.**

이름이 길어도 괜찮다. 이름을 정하는데 시간이 오래 걸려도 괜찮다. 단, 이름을 붙일 때 일관성이 있어야 한다.

---

## 함수 인수

함수의 이상적인 인수는 0개(무항)이다.

인수가 3개를 넘어가는 경우는 피하는 것이 좋다. 4개를 넘어가면 특별한 이유가 있더라도 사용하면 안된다.

테스트 관점에서 여러 인수 조합으로 함수를 검증할텐데 이 경우 인수가 많아지면 테스트하기 어려워지는 문제도 있다.

출력 인수는 입력 인수보다 이해하기 어렵다.

```java
//출력 인수를 사용하는 경우
void addKeyword(StringBuffer word) {
		word.append("aaa");
}

//반환 값으로 돌려주는 게 좋다.
StringBuffer addKeyword(StringBuffer word) {
		word.append("aaa");
		return word;
}
```

**최선의 방법은 입력 인수가 없는 경우이고, 차선은 입력 인수가 1개인 경우이다.**

### **많이 쓰는 단항 형식**

- 인수에 질문을 던지는 경우 → `boolean fileExists(”MyFile”)`
- 인수를 뭔가로 반환해 결과를 반환하는 경우 → `InputStream fileOpen(”MyFile”)`
- 이벤트 함수 → 이벤트라는 사실이 코드에 명확하게 드러나야 한다.

이런 경우가 아니라면 단항 함수는 가급적 피한다.

만약 출력 인수를 사용한다면 변환 결과는 반드시 반환 값으로 돌려주는 게 좋다.

### **플래그 인수**

**함수로 boolean 값을 넘기는 것은 좋지 않다.** → 함수가 한꺼번에 여러 가지를 처리한다고 대놓고 공표하기 때문에

### **이항 함수**

**인수가 2개인 함수는 인수가 1개인 함수보다 이해하기 어렵다.**

`Point p = new Point(0, 0)`과 같이 인수 간에 자연적인 순서가 있는 경우는 괜찮지만 이런 경우가 아니라면 이항 함수를 사용하는 것을 고민해봐야 한다.

함수를 설계하다 보면 이항 함수가 불가피하게 필요한 경우가 생긴다. 이 때 이항 함수가 가져오는 위험을 인지하고 단항으로 바꿀 수 있으면 바꿔줘야 한다.

### **삼항 함수**

이항 함수랑 마찬가지로 이해하기 어렵다. 이항 함수보다 이해하기 어렵다. → 위험이 2배로 늘어난다.

### 인수 객체

인수가 2~3개 필요하다면 인수들을 클래스로 묶을 수 있는지 파악하고 묶을 수 있다면 묶어주는 게 좋다.

→ 눈속임처럼 보일 수 있으나 어차피 인수를 여러개 넣는 경우에는 변수에 의미를 줘야하기 때문에 하나의 클래스 변수로 의미를 주는 게 좋다.

### 인수 목록

인수 개수가 가변적인 함수가 필요할 경우도 있다.

이 경우는 가변 인수 전부를 동등하게 취급하면 List 형 인수 하나로 취급할 수 있다.

→ 가변 인수를 취하는 모든 함수는 단항, 이항, 삼항 함수로 취급할 수 있다. 단, 이를 넘어가는 인수는 사용하지 말자

### 동사와 키워드

단항 함수는 함수와 인수가 동사/명사 쌍을 이뤄야한다. → `write(name)`

함수이름에 키워드(인수 이름)을 추가하면 인수 순서를 기억할 필요가 없어진다.

→ `assertExpectedEqualsActual(expected, actual)`

---

## 부수 효과(side effect)를 일으키지 마라

**함수는 한 가지 일만 하기 때문에 부수 효과가 발생하면 안된다!!**

아래의 함수는 패스워드를 체크하면서 세션을 초기화하는 부수 효과가 발생하는 나쁜 코드이다.

```java
public class UserValidator {
	private Cryptographer cryptographer;
	public boolean checkPassword(String userName, String password) { 
		User user = UserGateway.findByName(userName);
		if (user != User.NULL) {
			String codedPhrase = user.getPhraseEncodedByPassword(); 
			String phrase = cryptographer.decrypt(codedPhrase, password); 
			if ("Valid Password".equals(phrase)) {
				Session.initialize();
				return true; 
			}
		}
		return false; 
	}
}
```

**출력 인수**

일반적으로 출력 인수는 피해야 한다.

함수에서 상태를 변경해야 한다면 함수가 속한 객체 상태를 변경하는 방식을 택하라.

```java
//이 방식보다는
appendFooter(s);

//이 방식을 사용하는게 좋다.
report.appendFooter();
```

---

## 명령과 조회를 분리하라

함수는 뭔가를 수행하거나 뭔가에 답하거나 둘 중 하나만 해야 한다.

→ 객체 상태를 변경하거나, 객체 정보를 반환하거나 둘 중 하나만 수행해야 한다.

---

## 오류 코드보다 예외를 사용하라

오류 코드를 사용하여 오류 코드에 관한 로직을 추가하기 보다는 예외를 사용하는 것이 더 좋다.

→ 오류 처리 로직이 분리되기 때문

```java
//오류 코드를 사용하는 함수
if (deletePage(page) == E_OK) {
	if (registry.deleteReference(page.name) == E_OK) {
		if (configKeys.deleteKey(page.name.makeKey()) == E_OK) {
			logger.log("page deleted");
		} else {
			logger.log("configKey not deleted");
		}
	} else {
		logger.log("deleteReference from registry failed"); 
	} 
} else {
	logger.log("delete failed"); return E_ERROR;
}

//예외를 사용하는 함수 -> 오류 처리 코드가 원래 코드에서 분리 된다.
public void delete(Page page) {
		try {
				deletePageAndAllReferences(page);
  	} catch (Exception e) {
	  		logError(e);
  	}
}

private void deletePageAndAllReferences(Page page) throws Exception { 
	deletePage(page);
	registry.deleteReference(page.name); 
	configKeys.deleteKey(page.name.makeKey());
}

private void logError(Exception e) { 
	logger.log(e.getMessage());
}
```

그리고 try/catch 블록을 사용할 때는 각 블록을 별도의 함수로 뽑아주는 게 코드 구조에 좋다.

오류 처리도 한 가지 작업이다. → 오류를 처리하는 함수는 오류만 처리하는 게 맞다.

그리고 예외를 사용하면 Exception 클래스에서 파생시킬 수 있어 편하게 예외 클래스를 추가할 수 있다. → OCP

---

## 반복하지 마라

여러 코드에서 특정 코드가 반복된다면 코드가 길어지게 되고, 변경을 하기 어려워 진다.

→ 반복되는 코드는 하나의 함수로 빼는 것이 좋다.

구조적 프로그래밍, AOP, COP 모두 중복을 제거하기 위해 나온 전략이다.

---

## 구조적 프로그래밍

다익스트라의 구조적 프로그래밍 원칙은 다음과 같다.

모든 함수와 함수 내 모든 블록에 입구와 출구가 하나여야 된다. 즉, 함수는 return문이 하나여야 되며, 루프 안에서 break나 continue를 사용해선 안되며 goto는 절대로, 절대로 안된다.

이러한 구조적 프로그래밍은 함수가 큰 경우에만 효과를 발휘한다.

함수를 작게 만드는 경우는 해당 규칙을 무조건 지키지 않아도 된다.

---

## 함수를 어떻게 짜죠?

글짓기와 같은 방법으로 짜면 된다.

먼저 생각나는대로 작성하고 단위 테스트를 만든다.

그 다음 코드를 다듬고, 함수를 만들고, 이름을 바꾸고, 중복을 제거하면서 더 좋은 코드를 만들어간다.

이 때 코드는 항상 단위 테스트를 통과해야 한다.