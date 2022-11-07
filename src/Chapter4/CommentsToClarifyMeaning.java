package Chapter4;

/**
 * 의미를 명료하게 밝히는 주석
 */
public class CommentsToClarifyMeaning {

    String a = "hello";
    String b = "hi";

    void testCompareTo() {
        System.out.println(a.compareTo(a) == 0); // a == a
        System.out.println(a.compareTo(b) != 0); // a != b
    }
}
