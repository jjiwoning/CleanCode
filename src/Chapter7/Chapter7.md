# Chapter 7 오류 처리

---

깨끗한 코드와 오류 처리는 연관성이 있다. 상당수 코드 기반은 전적으로 오류 처리 코드에 좌우된다.

오류를 처리하는 기법과 고려 사항 몇 가지에 대해 알아보자

---

## 오류 코드보다 예외를 사용하라

Java에서는 예외를 제공한다.

→ 오류가 발생하면 예외를 던지는게 좋다. 논리와 오류 처리 코드가 뒤섞이지 않아 코드가 깔끔해진다.

```java
public class DeviceController {
  ...
  public void sendShutDown() {
    try {
      tryToShutDown();
    } catch (DeviceShutDownError e) {
      logger.log(e);
    }
  }
    
  private void tryToShutDown() throws DeviceShutDownError {
    DeviceHandle handle = getHandle(DEV1);
    DeviceRecord record = retrieveDeviceRecord(handle);
    pauseDevice(handle); 
    clearDeviceWorkQueue(handle); 
    closeDevice(handle);
  }
  
  private DeviceHandle getHandle(DeviceID id) {
    ...
    throw new DeviceShutDownError("Invalid handle for: " + id.toString());
    ...
  }
  ...
}
```

로직과 오류 처리를 분리하여 코드 품질이 확실히 개선 됨을 알 수 있다.

---

## Try-Catch-Finally 문부터 작성하라

예외에서 **프로그램 안에다 범위를 정의한다는 사실은 매우 흥미롭다.**

try 블록은 트랜잭션과 비슷하다. try 블록에서 무슨 일이 생기든지 catch 블록은 프로그램 상태를 일관성 있게 유지해야 한다.

→ 예외가 발생할 코드는 try-catch-finally 문으로 시작하는 편이 낫다.

→ try에서 무슨 일이 생기든지 호출자가 기대하는 상태를 정의하기 쉬워진다.

파일이 없으면 예외를 던지는지 알아보는 단위 테스트 예시를 보면서 알아보자.

```java
@Test(expected = StorageException.class)
public void retrieveSectionShouldThrowOnInvalidFileName() {
    sectionStore.retrieveSection("invalid - file");
}

public List<RecordedGrip> retrieveSection(String sectionName) {
    // 실제로 구현할 때까지 비어 있는 더미를 반환한다.
    return new ArrayList<RecordedGrip>();
}
```

이 경우는 코드가 예외를 던지지 않아 단위 테스트에 실패한다. 코드를 try-catch-finally를 사용하는 코드로 바꿔보자

```java
public List<RecordedGrip> retrieveSection(String sectionName) {
    try {
      FileInputStream stream = new FileInputStream(sectionName)
    } catch (Exception e) {
      throw new StorageException("retrieval error", e);
    }
  return new ArrayList<RecordedGrip>();
}
```

이 코드는 예외를 던져주므로 테스트에 통과한다. 이 때부터 리팩토링이 가능한데 이 때 생각해볼 리팩토링은 예외를 범용 예외인 Exception이 아닌 조금 더 상세한 예외를 던지게 리팩토링을 한다.

```java
public List<RecordedGrip> retrieveSection(String sectionName) {
    try {
      FileInputStream stream = new FileInputStream(sectionName);
      stream.close();
    } catch (FileNotFoundException e) {
      throw new StorageException("retrieval error", e);
    }
    return new ArrayList<RecordedGrip>();
}
```

이제 TDD를 사용해 필요한 나머지 논리를 추가하면 된다.

---

## 언체크 예외를 사용하라

Checked 예외는 많은 단점들을 가지고 다른 언어에서는 Unchecked 예외만으로 프로그래밍 하는데 문제가 없음을 확인했다.

> Checked Exception과 Unchecked Exception의 차이
Checked Exception은 반드시 로직을 try/catch로 감싸거나 throw로 던져서 처리해야 한다.
→ 반드시 처리를 해줘야 한다.
Unchecked Exception은 명시적인 예외처리를 하지 않아도 된다.
→ 명시하지 않으면 자동으로 Throw 한다.
>

그러면 Checked 예외가 무슨 문제를 들고 있는지 알아보자

- OCP를 위반한다.
  만약 Cheked 예외를 던지는데 catch 블록이 세 단계 위에 있다면 그 사이 메서드의 선언부에 모두 해당 예외를 명시해야 한다. → 하위 단계에서 코드를 변경하면 상위 코드도 모두 메서드 선언부를 수정해야 한다.
  → 경로에 있는 모든 메서드가 하위 메서드에서 던지는 예외를 알아야 하기 때문에 캡슐화가 깨진다.

이러한 이유로 언체크 예외를 사용하는 것을 권장한다.

---

## 예외에 의미를 제공하라

예외를 던질 때는 전후 상황을 충분히 덧붙인다.

→ 오류가 발생한 원인과 위치를 찾기 쉬워진다.

오류 메시지에 정보를 담아 예외와 함께 던진다. 실패한 연산 이름과 실패 유형도 언급한다.

로깅 기능을 사용한다면 catch 블록에서 오류를 기록하도록 충분한 정보를 넘겨준다.

---

## 호출자를 고려해 예외 클래스를 정의하라

오류를 분류하는 방법은 정말 많다. 오류가 발생한 위치, 유형 등 여러가지가 있다.

하지만 **가장 중요한 관심사는 오류를 잡아내는 방법이 되어야 한다.**

아래의 예시를 보면서 오류를 어떻게 잡아내는 지 확인해보자

```java
ACMEPort port = new ACMEPort(12);

  try {
    port.open();
  } catch (DeviceResponseException e) {
    reportPortError(e);
    logger.log("Device response exception", e);
  } catch (ATM1212UnlockedException e) {
    reportPortError(e);
    logger.log("Unlock exception", e);
  } catch (GMXError e) {
    reportPortError(e);
    logger.log("Device response exception");
  } finally {
    ...
  }
```

이 코드는 예외에 대응하는 방식(오류를 잡아내는 방법)이 예외 유형과 무관하게 거의 동일하다.

이런 코드는 아래의 방법으로 간결하게 고칠 수 있다.

```java
LocalPort port = new LocalPort(12);

try {
  port.open();
} catch (PortDeviceFailure e) {
  reportError(e);
  logger.log(e.getMessage(), e);
} finally {
  ...
}
  
public class LocalPort {
  private ACMEPort innerPort;
  public LocalPort(int portNumber) {
    innerPort = new ACMEPort(portNumber);
  }
  
  public void open() {
    try {
      innerPort.open();
    } catch (DeviceResponseException e) {
      throw new PortDeviceFailure(e);
    } catch (ATM1212UnlockedException e) {
      throw new PortDeviceFailure(e);
    } catch (GMXError e) {
      throw new PortDeviceFailure(e);
    }
  }
  ...
}
```

LocalPort 클래스로 ACMEPort 클래스가 던지는 예외를 잡아 변환하는 Wrapper 클래스를 만들어 문제를 해결한다.

이런 클래스는 상당히 유용하다. 특히 외부 API를 사용할 때는 이 방법이 최선이다.

이런 식으로 예외를 감싸주면 외부 라이브러리와 프로그램 사이의 의존성이 크게 줄어드는 효과를 가진다.

→ 라이브러리를 바꿔도 비용이 적다. 테스트 코드를 작성하기 쉬워진다.

흔히 예외 클래스가 하나만 있어도 충분한 코드가 많다. → 한 예외는 잡아내고 다른 예외는 무시해도 괜찮은 경우라면 여러 예외 클래스를 사용한다.

---

## 정상 흐름을 정의하라

위의 방식을 잘 따른다면 비즈니스 로직과 오류 처리가 잘 분리된 코드가 나온다.

하지만 이러한 방식이 적합하지 않은 경우가 있다. (예외를 던져 중단시키기 때문)

이럴 때는 특수 사례 패턴(Special Case Pattern)을 사용하면 된다.

아래의 코드 예시를 보자

```java
try {
    MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
    m_total += expenses.getTotal();
  } catch(MealExpensesNotFound e) {
    m_total += getMealPerDiem();
  }
```

이러한 방식보다는 특수한 상황을 처리하는 특수 사례 패턴을 적용하는 것이 좋다.

```java
public class PerDiemMealExpenses implements MealExpenses {
    public int getTotal() {
        // 기본값으로 일일 기본 식비를 반환한다.
    }
}
```

클래스를 만들거나 객체를 조작하는 방법으로 특수 사례를 처리하여 클라이언트 코드가 예외적인 상황을 처리하지 않아도 되는 장점이 있다.

→ 클래스나 객체가 예외적인 상황을 캡슐화해서 처리한다.

---

## null을 반환하지 마라

null을 반환하는 행위는 오류를 유발한다. 아래의 코드를 보자

```java
public void registerItem(Item item) {
     if (item != null) {
         ItemRegistry registry = peristentStore.getItemRegistry();
         if (registry != null) {
             Item existing = registry.getItem(item.getID());
             if (existing.getBillingPeriod().hasRetailOwner()) {
                 existing.register(item);
             }
         }
     }
 }
```

이 코드는 나쁜 코드이다. → null을 반환하는 코드는 일거리를 늘릴 뿐만 아니라 호출자에게 문제를 떠넘긴다.

그리고 null을 확인해야 되는 게 너무 많다.

만약 null을 반환해야 되는 경우라면, **null을 반환하기 보다는 예외를 던지거나 특수 사례 객체를 반환하는 방법을 권장한다.**

---

## null을 전달하지 마라

메서드에 null을 전달하는 방식은 null을 반환하는 코드보다 더 나쁘다.

정상적인 인수로 null을 기대하는 API가 아니라면 메서드로 null을 전달하는 코드는 최대한 피한다!!

null 인수를 적절하게 처리하는 마땅한 방법도 존재하지 않는다.

**애초에 null을 넘기지 못하도록 금지하는 정책이 더 합리적이다.**

---

## 결론

오류 처리를 프로그램 로직과 분리해 독자적인 사안으로 고려하면 깨끗하고 안정적인 코드를 작성할 수 있다.