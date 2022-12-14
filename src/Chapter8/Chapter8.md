# Chapter 8 경계

---

## 외부 코드 사용하기

인터페이스 제공자와 인터페이스 사용자 사이에는 특유의 긴장이 존재한다.

제공자: 적용성을 최대한으로 한다. → 더 많은 환경에서 돌아가는 걸 원하기 때문

사용자: 자신의 요구에 집중하는 인터페이스를 원한다.

이러한 차이로 시스템 경계에서 문제가 생길 소지가 많다.

Map을 예시로 생각해보자. Map은 다양한 인터페이스로 수많은 기능을 제공한다.

→ 사용자가 clear()를 하거나, 다른 타입의 객체를 넣는 것이 가능

→ 정말 유연하지만, 그만큼 위험성이 크다.

Sensor 객체를 담는 Map은 아래와 같이 정의한다.

`Map Sensors = new HashMap();`

Map에서 데이터를 가져올 때는 아래와 같이 가져온다.

`Sensor findSensor = (Sensor)sensors.get(sensorId);`

→ Map이 반환하는 Object를 캐스팅할 책임이 클라이언트에게 넘어간다. → 깨끗한 코드로 보기가 어렵다.

물론 이를 위해 제네릭을 사용하는 방법도 있다. 하지만 제네릭을 사용한다 하더라도 사용자에게 필요하지 않은 기능까지 제공한다는 문제는 해결할 수 없다. + Map 인터페이스가 변경되면 코드도 수정해야 한다.

이럴 때는 아래의 방법을 생각해보는 게 좋다.

```java
public class Sensors {
    
    private Map sensors = new HashMap();
    
    public Sensor getById(String id) {
        return (Sensor)sensors.get(id);
    }
		
		// 이하 생략
}
```

경계 인터페이스인 Map을 Sensors 내부로 숨긴다.

→ Map 인터페이스가 변하더라도 나머지 프로그램에는 영향을 주지 않는다. + 제네릭과 무관하게 타입 캐스팅 부분은 더 이상 문제가 되지 않는다. (클래스 내부에서 타입을 관리하고 리턴하기 때문)

그리고 Sensors 클래스에서 프로그램에 필요한 인터페이스만 제공한다. → 사용자가 이상한 짓 못하게 막을 수 있다.

물론 무조건 이런 방식을 사용하라는 게 아니다. **핵심은 Map과 같은 경계 인터페이스를 여기저기 넘기지 말라는 의미이다!!**

→ **경계 인터페이스를 사용하는 클래스나 클래스 계열 밖으로 노출되지 않게 주의하고 공개 API 인수로 넘기거나 반환값으로 사용하지 말자!!**

---

## 경계 살피고 익히기

외부 코드를 사용하면 적은 시간에 더 많은 기능을 출시하기 쉬워진다.

우리 자신을 위해 우리가 사용할 코드를 테스트하는 편이 바람직하다.

간단한 테스트 케이스를 작성해 외부 코드를 익히는 방법으로 학습한다.

이를 **학습 테스트**라 부른다. → API를 사용하려는 목적에 초점을 맞춘다.

학습 테스트는 프로그램에서 사용하려는 방식대로 외부 API를 호출한다. 통제된 환경에서 API를 제대로 이해하는지를 확인한다.

---

## log4j 익히기

해당 파트는 위의 학습 테스트 과정을 보여주는 예시이다.

---

## 학습 테스트는 공짜 이상이다.

학습 테스트는 필요한 지식만 확보하는 손쉬운 방법이다.

→ 이해도를 높여주는 정확한 실험이다.

패키지가 새 버전이 나온다면 학습 테스트를 돌려 차이가 있는지 확인한다.

→ 패키지가 예상대로 도는지 검증 → 새로운 위험이 생기면 학습 테스트가 이를 알려준다.

→ 결과적으로 학습 테스트가 패키지를 새 버전으로 이전하기 쉽게 도와준다.

---

## 아직 존재하지 않는 코드를 사용하기

경계와 관련해 또 다른 유형은 아는 코드와 모르는 코드를 분리하는 경계이다.

만약 상대가 개발을 하지 못해 정보가 없는 사유 등으로 모르는 코드가 있다면 아래의 방법을 사용하자.

- 자체적으로 우리가 바라는 인터페이스를 정의한다. → 인터페이스를 전적으로 통제 가능 + 가독성, 의도
- 필요한 인터페이스를 정의했으므로 Controller가 해당 인터페이스에 의존 가능
- 상대가 정의할 코드는 Adapter를 구현해 간극을 메꾼다. → Adapter 패턴
- 우리는 상대가 구현하기 전까지 가짜 코드로 실행

이러한 방식은 테스트도 매우 편하다는 장점이 있다.

---

## 깨끗한 경계

변경이 일어났을 때 너무 많은 비용이 발생하면 안된다.

경계에 위치하는 코드는 깔끔히 분리한다.

우리 코드가 외부 패키지를 세세하게 알아야 할 필요가 없다.

→ 통제가 안되는 외부 패키지에 의존하기 보다는 우리 코드에 의존하는 게 훨씬 좋다.

외부 패키지를 호출하는 코드를 가능한 줄여서 경계를 관리하자.

→ 새로운 클래스로 경계를 감싸거나 ADAPTER 패턴을 이용해 우리가 원하는 인터페이스를 패키지가 제공하는 인터페이스로 변환하자

→ 코드 가독성이 높아지고, 일관성도 높아지면서, 변경에 안전해지는 장점이 있다.