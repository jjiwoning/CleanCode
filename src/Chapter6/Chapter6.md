# Chapter 6 객체와 자료구조

---

## 자료 추상화

```java
// 구체 클래스
public class Point {
    public double x;
    public double y;
}

// 추상적인 인터페이스
public interface PointInterface {
    double getX();
    double getY();
    void setCartesian(double x, double y);
    double getR();
    double getTheta();
    void setPolar(double r, double theta);
}
```

인터페이스는 구현을 숨기면서 자료구조를 명백하게 표현한다.

구체 클래스는 구현을 그대로 노출한다. → private으로 감추고 getter, setter를 줄 수 있지만 이 방법 또한 구현을 외부로 노출시키는 것이다.

즉, 구현을 감추려면 추상화가 필요하다. → 추상 인터페이스를 사용해 **사용자가 구현을 모른 채 자료의 핵심을 조작할 수 있어야 진정한 의미의 클래스라고 볼 수 있다.**

**자료를 세세하게 공개하기보다는 추상적인 개념으로 표현하는 편이 좋다!!**

→ **개발자는 객체가 포함하는 자료를 표현할 가장 좋은 방법을 심각하게 고민해야 한다.**

---

## 자료/객체 비대칭

객체는 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개한다.

자료 구조는 자료를 그대로 공개하며 별다른 함수는 제공하지 않는다.

→ 두 개념은 정반대의 개념이다. 이 둘의 차이가 미치는 영향은 굉장하다.

이 차이를 절차적인 도형 클래스와 객체 지향적인 도형 클래스로 비교해보자.

먼저 절차적인 도형 클래스이다.

```java
public class Square {
    public Point topLeft;
    public double side;
}

public class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}

public class Circle {
    public Point center;
    public double radius;
}

public class Geometry{
    public final double PI = 3.141592;

    public double area(Object shape) throws NoSuchShapeException {
        if (shape instanceof Square) {
            Square s = (Square)shape;
            return s.side * s.side;
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle)shape;
            return r.height * r.width;
        } else if (shape instanceof Circle) {
            Circle c = (Circle)shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }
}
```

각 도형 클래스는 간단한 자료 구조이다. → 어떠한 메서드도 제공하지 않는다.

도형이 동작하는 방식은 Geometry 클래스에서 구현한다.

만약 **Geometry 클래스에 둘레 길이를 구하는 perimeter() 메서드를 추가해도 도형 클래스는 아무 영향을 받지 않는다**. 그러나 **새 도형을 추가하고 싶다면 Geometry 클래스에 속한 함수를 모두 고쳐야 한다.**

→ 두 조건은 완전히 정반대이다.

그러면 이번에는 객체 지향적인 도형 클래스를 살펴보자.

```java
public interface Shape{
    double area();
}

public class Square implements Shape {
    private Point topLeft;
    private double side;

    public double area() {
        return side * side;
    }
}

public class Rectangle implements Shape {
    private Point topLeft;
    private double height;
    private double width;

    public double area() {
        return height * width;
    }
}

public class Circle implements Shape {
    private Point center;
    private double radius;
    public final double PI = 3.141592653589793;

    public double area() {
        return PI * radius * radius;
    }
}
```

area() 메서드는 다형 메서드이다.

→ Geometry 클래스는 필요하지 않다. 그러므로 **새 도형을 추가해도 기존 함수에 아무런 영향을 주지 않는다. 그러나 새 함수를 추가한다면 도형 클래스를 전부 고쳐야 한다.**

이러한 둘의 특징은 상호 보완적인 특징이 있다. 이렇기에 객체와 자료 구조는 근본적으로 양분된다.

> (자료 구조를 사용하는) 절차적인 코드는 기존 자료 구조를 변경하지 않으면서 새 함수를 추가하기 쉽다. 반면, 객체 지향 코드는 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.

절차적인 코드는 새로운 자료 구조를 추가하기 어렵다. 그러려면 모든 함수를 고쳐야 한다. 객체 지향 코드는 새로운 함수를 추가하기 어렵다. 그러려면 모든 클래스를 고쳐야 한다.
>

이러한 둘의 특징을 잘 이해하고 상황에 맞는 해답을 찾아 사용하면 된다. 모든 것이 객체라는 생각은 미신이다!!

---

## 디미터 법칙

디미터 법칙은 잘 알려진 휴리스틱heuristic(경험에 기반하여 문제를 해결하거나 학습하거나 발견해 내는 방법)으로, **모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙이다.**

→ 객체는 자료를 숨기고 함수를 공개한다.

디미터 법칙은 "클래스 C의 메서드 f는 다음과 같은 객체의 메서드만 호출해야 한다"고 주장한다.

- 클래스 C
- f가 생성한 객체
- f의 인수로 넘어온 객체
- C 인스턴스 변수에 저장된 객체

→ 낯선 사람은 경계하고 친구랑만 놀라는 의미이다. (객체에서 허용된 메서드가 반환하는 객체의 메서드는 호출하면 안 된다.)

### 기차 충돌

```java
final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();
```

이 코드는 디미터 법칙을 어기는 듯 보인다. 이러한 코드를 **기차 충돌**이라고 부른다.

이런 코드는 일반적으로 조잡하다 여겨지는 방식으로 피하는 편이 좋다. 대신 아래의 방식으로 표현하는 게 좋다.

```java
Options opts = ctxt.getOptions();
File scratchDir = opts.getScratchDir();
final String outputDir = scratchDir.getAbsolutePath();
```

이 코드가 디미터 법칙을 무시하는 지 알려면 변수들이 객체인지 자료 구조인지를 알아야 한다. 객체라면 내부 구조를 숨겨야 하므로 디미터 법칙을 위반한다. 반면, 자료 구조라면 당연히 내부 구조를 노출하므로 문제되지 않는다.

하지만 우리가 사용하는 프레임 워크의 요구로 **자료 구조도 위와 같이 조회 함수와 설정 함수를 정의하는 게 좋다.**

### 잡종 구조

위와 같은 혼란으로 절반은 객체, 절반은 자료 구조인 잡종 구조가 나온다.

**잡종 구조는 중요한 기능을 수행하는 함수도 있고, 공개 변수나 공개 조회/설정 함수도 있다.**

이런 구조는 새로운 함수는 물론이고 새로운 자료 구조도 추가하기 어렵다. 둘의 단점만 모아놓은 구조다.

→ 잡종 구조는 피하도록 하자. 개발자가 함수나 타입을 보호할지 공개할지 확신하지 못해 어중간하게 내놓은 설계에 불과하다.

### 구조체 감추기

객체라면 내부 구조를 감춰야 한다. → ctxt, options, scratchDir을 사탕처럼 엮으면 안된다.

이럴 때는 **ctxt 객체에게 뭔가를 하라고 요구를 해야지 속으로 드러내라고 말하면 안된다!!**

즉, 해당 절대 경로가 왜 필요한지를 찾아내고 이를 ctxt에게 요구하는 메서드를 만들어야 한다.

```java
BufferedOutputStream bos = ctxt.createScratchFileStream(classFileName);
```

ctxt는 내부 구조를 드러내지 않으며, 모듈은 자신이 몰라야 하는 여러 객체를 탐색할 필요가 없다. 따라서 디미터 법칙을 위반하지 않는다.

---

## 자료 전달 객체

자료 구조체의 전형적인 형태는 공개 변수만 있고 함수가 없는 클래스다. 이를 때로는 자료 전달 객체(Data Transfer Object, DTO)라 한다.

**DTO는 데이터베이스에 저장된 가공되지 않은 정보를 애플리케이션 코드에서 사용할 객체로 변환하는 형식으로 많이 사용한다.**

```java
public class Address {
    private String street;
    private String streetExtra;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String streetExtra, String city, String state, String zip) {
        this.street = street;
        this.streetExtra = streetExtra;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetExtra() {
        return streetExtra;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
```

### 활성 레코드

DTO의 특수한 형태다. 공개 변수가 있거나 비공개 변수에 조회/설정 메서드가 있는 자료 구조지만, 대게 save나 find와 같은 탐색 함수도 제공한다. 활성 레코드는 데이터베이스 테이블이나 다른 소스에서 자료를 직접 변환한 결과다.

불행히도 활성 레코드에 비즈니스 규칙 메서드를 추가해 이런 자료 구조를 객체로 취급하는 개발자가 흔하다.

→ 잡종 구조가 나온다.

해결책은 **활성 레코드는 자료 구조로 취급한다.** 비즈니스 규칙을 담으면서 내부 자료를 숨기는 객체는 따로 생성한다. (여기서 내부 자료는 활성 레코드의 인스턴스일 가능성이 높다.)

---

## 결론

객체는 동작을 공개하고 자료를 숨긴다. 그래서 기존 동작을 변경하지 않으면서 새 객체 타입을 추가하기는 쉬운 반면, 기존 객체에 새 동작을 추가하기는 어렵다.

자료 구조는 별다른 동작 없이 자료를 노출한다. 그래서 기존 자료 구조에 새 동작을 추가하기는 쉬우나, 기존 함수에 새 자료 구조를 추가하기는 어렵다.

시스템을 구현할 때, 새로운 자료 타입을 추가하는 유연성이 필요하면 객체, 다른 경우로 새로운 동작을 추가하는 유연성이 필요하면 자료 구조와 절차적인 코드를 선택한다.