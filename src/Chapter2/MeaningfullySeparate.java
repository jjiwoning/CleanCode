package Chapter2;

/**
 * 의미 있게 구분하라
 * 연속된 숫자를 붙이거나 불용어를 사용하는 방식은 적절하지 않다.
 */
public class MeaningfullySeparate {

    //연속적인 숫자를 붙인 이름은 아무런 정보도 제공할 수 없다. -> 코드의 의도가 전혀 드러나지 않는다.
    public static void copy(char a1[], char a2[]) {
        for (int i = 0; i < a1.length; i++) {
            a2[i] = a1[i];
        }
    }

    //불용어를 사용한 이름도 정보를 제공할 수 없다. -> 의미가 불분명하기 때문에
    String dog;
    String theDog;

    //명확한 관례가 없다면 아래의 변수들은 구분할 수 없다.
    int money;
    int moneyAccount;
}
