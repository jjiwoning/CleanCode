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