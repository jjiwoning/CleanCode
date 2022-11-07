package Chapter4;

/**
 * 코드로 의도를 표현하라
 */
public class ExpressYourIntentionsInCode {

    boolean badExample(int age) {

        // 회원의 나이를 검사한다.
        if (age < 60 && age > 15) {
            return true;
        }

        return false;
    }

    boolean goodExample(int age) {
        return checkMemberAge(age);
    }

    private boolean checkMemberAge(int age) {
        if (age < 60 && age > 15) {
            return true;
        }

        return false;
    }
}
