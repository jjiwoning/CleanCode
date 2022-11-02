package Chapter2;

/**
 * 인코딩을 피하라
 */
public class AvoidEncoding {

    //옛날 방식 -> 접두어가 붙는다. -> 개발자가 접두어가 뭘 의미하는 지를 알아야 하는 부담이 생김
    private String m_dsc;

    //인코딩을 피하는 것이 좋다.
    private String description;
}

/**
 * 인터페이스: 인코딩없이 그대로 이름을 사용
 */
interface ShapeFactory {
    //내부 생략
}

/**
 * 구현 클래스: Impl을 추가해 구현체라고 인코딩 정보를 준다.
 */
class ShapeFactoryImpl implements ShapeFactory {
    //내부 생략
}
