# Chapter 12 창발성

---

창발성: 하위 계층(구성 요소)에는 없는 특성이나 행동이 상위 계층(전체 구조)에서 자발적으로 돌연히 출현하는 현상이다. - 위키백과

이번 챕터는 단순한 설계 규칙(하위 계층)을 이용하여 우수한 설계(상위 계층)를 만드는 방법에 대해서 알려주는 챕터이다.

---

## 창발적 설계로 깔끔한 코드를 구현하자

우수한 설계를 만들 수 있는 규칙 4가지가 있다.

이 규칙을 잘 지킨다면 SRP, DIP와 같은 원칙을 적용하기 쉬워진다.

이는 켄트 벡이 제시한 단순한 설계 규칙 4가지이다.

중요한 순서부터 설계 규칙을 서술하면 아래와 같다.

- 모든 테스트를 실행한다.
- 중복을 제거한다.
- 의도를 표현한다.
- 클래스와 메서드 수를 최소로 줄인다.

---

## 단순한 설계 규칙 1: 모든 테스트를 실행하라

가장 먼저 설계는 의도한 대로 돌아가는 시스템을 내놓아야 한다.

→ **시스템이 의도한 대로 돌아가는지 검증하는 테스트 코드가 필요하다.**

테스트가 불가능한 시스템은 검증이 불가능하고 이런 시스템은 절대로 출시하면 안된다.

**테스트가 가능한 시스템을 만드려고 애쓰면 설계 품질이 더불어 높아진다.**

→ 크기가 작고 목적 하나만 수행하는 클래스가 나오고, 이런 SRP를 준수하는 클래스는 테스트가 쉽다.

결합도가 높은 시스템은 테스트를 작성하기 어렵다.

→ DIP, DI, 인터페이스, 추상화 등을 적용하여 결합도를 낮춰 테스트를 만들게 되고 이는 설계 품질을 올려주는 결과를 야기한다.

**결론적으로 ‘테스트 케이스를 만들고 계속 돌려라’라는 간단한 원칙을 적용하면 낮은 결합도와 높은 응집력을 가지는 객체 지향 방법론이 지향하는 목표를 저절로 달성하게 된다.**

---

## 단순한 설계 규칙 2~4: 리팩토링

테스트 케이스를 모두 작성했다면 이제 리팩토링을 실시한다.

여러 방법으로 코드를 정리하면서 테스트 케이스를 주기적으로 돌려 기존 기능이 깨지지 않는지 검사한다.

**테스트 케이스 덕분에 코드를 정리하면서 시스템이 깨질까 걱정할 필요가 없다.**

리팩토링 단계에서는 설계 품질을 높일 수 있다면 어떤 방법을 적용해도 괜찮다. 이 단계는 단순한 설계 규칙 중 나머지 3가지를 적용해 중복을 제거하고, 의도를 표현하고, 클래스와 메서드를 줄이는 단계이다.

---

## 중복을 없애라

중복은 추가 작업, 추가 위험, 불필요한 복잡도를 뜻한다.

중복은 여러 가지 형태로 표출된다. 똑같은 코드는 당연히 중복이고, 비슷한 코드는 더 비슷하게 고쳐주면 리팩토링이 쉬워진다.

구현 중복도 중복의 한 형태이다. 아래의 예시를 보자.

```java
int size(){}
boolean isEmpty(){}
```

이 경우 isEmpty 메서드를 직접 구현하기 보다는 size 메서드를 이용해서 구현 중복을 피할 수 있다.

아래의 예시는 여러 메서드에서 중복되는 부분을 새 메서드로 추출하는 예시이다.

```java
public void scaleToOneDimension(float desiredDimension, float imageDimension) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold)
    return;
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);
  
  RenderedOpnewImage = ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor);
  image.dispose();
  System.gc();
  image = newImage;
}

public synchronized void rotate(int degrees) {
  RenderedOpnewImage = ImageUtilities.getRotatedImage(image, degrees);
  image.dispose();
  System.gc();
  image = newImage;
}
```

```java
public void scaleToOneDimension(float desiredDimension, float imageDimension) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold)
    return;
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float) Math.floor(scalingFactor * 10) * 0.01f);
  replaceImage(ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor));
}

public synchronized void rotate(int degrees) {
  replaceImage(ImageUtilities.getRotatedImage(image, degrees));
}

private void replaceImage(RenderedOpnewImage) {
  image.dispose();
  System.gc();
  image = newImage;
}
```

이렇게 중복되는 코드를 뽑아내 공통적인 코드로 만들어 내는 과정을 통해 클래스가 여러 책임을 가지고 있다는 사실을 발견할 수도 있다.

이러한 소규모 재사용은 시스템 복잡도를 극적으로 줄여주고 이를 제대로 익혀야 대규모 재사용도 가능해진다.

Template Method 패턴은 고차원 중복을 제거할 목적으로 자주 사용하는 기법이다. 아래 코드를 보자

```java
public class VacationPolicy {
  public void accrueUSDDivisionVacation() {
    // 지금까지 근무한 시간을 바탕으로 휴가 일수를 계산하는 코드
    // ...
    // 휴가 일수가 미국 최소 법정 일수를 만족하는지 확인하는 코드
    // ...
    // 휴가 일수를 급여 대장에 적용하는 코드
    // ...
  }

  public void accrueEUDivisionVacation() {
    // 지금까지 근무한 시간을 바탕으로 휴가 일수를 계산하는 코드
    // ...
    // 휴가 일수가 유럽연합 최소 법정 일수를 만족하는지 확인하는 코드
    // ...
    // 휴가 일수를 급여 대장에 적용하는 코드
    // ...
  }
}
```

두 메서드는 공통되는 부분이 있다. 공통되는 부분을 템플릿에 묶고 구현이 필요한 로직은 추상 메서드를 사용해 필요할 때 오버라이드하여 구현하게 만들 수 있다.

```java
abstract public class VacationPolicy {
  public void accrueVacation() {
    caculateBseVacationHours();
    alterForLegalMinimums();
    applyToPayroll();
  }

  private void calculateBaseVacationHours() { /* ... */ };
  abstract protected void alterForLegalMinimums();
  private void applyToPayroll() { /* ... */ };
}

public class USVacationPolicy extends VacationPolicy {
  @Override 
	protected void alterForLegalMinimums() {
    // 미국 최소 법정 일수를 사용한다.
  }
}

public class EUVacationPolicy extends VacationPolicy {
  @Override
	protected void alterForLegalMinimums() {
    // 유럽연합 최소 법정 일수를 사용한다.
  }
}
```

이런 식으로 하위 클래스가 공통되지 않는 메서드를 오버라이드한다.

---

## 표현하라

나만 이해할 수 있는 코드를 짜는 것은 쉽다. 본인이 코드를 구석구석 이해할 수 있기 때문에

하지만 이를 유지보수하기란 쉽지 않다. 유지보수하는 사람이 코드를 구석구석 이해하기가 쉽지 않으니

즉, 이러한 문제를 방지하기 위해 코드는 개발자의 의도를 분명하게 표현해야 한다. 개발자가 코드를 명백하게 짤수록 다른 사람이 그 코드를 이해하기 쉬워진다.

→ 유지보수 비용이 적게 들어간다.

어떻게 표현해야 의도를 분명하게 드러낼 수 있는지 알아보자

- 좋은 이름을 선택한다.
- 함수와 클래스 크기는 가능한 줄인다.
- 표준 명칭을 사용한다.
- 단위 테스트 케이스를 꼼꼼히 작성한다.
- 노력한다. → 제일 중요!!

항상 자신의 코드에 조금 더 주의를 기울이자!!

---

## 클래스와 메서드 수를 최소로 줄여라

중복을 제거하고, 의도를 표현하고, SRP를 준수한다는 기본적인 개념도 극단적으로 지키려하면 득보다 실이 많아진다.

클래스와 메서드의 크기를 줄이자고 작은 클래스와 메서드를 수없이 만드는 사례가 있다.

→ 위의 규칙을 지키면서 함수와 메서드를 가능한 줄이는게 좋다.

**목표는 함수와 클래스 크기를 작게 유지하면서 동시에 시스템 크기도 작게 유지하는 데 있다.**

물론 이 목표보다는 **테스트 케이스를 만들고 중복을 제거하고 의도를 표현하는 작업이 더 중요하다!!**

---

## 결론

단순한 설계 규칙을 잘 따른다면 우수한 기법과 원칙을 단번에 적용할 수 있다!!