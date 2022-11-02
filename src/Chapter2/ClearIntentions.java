package Chapter2;

/**
 * 의도를 분명히 밝혀라
 * 존재 이유, 수행 기능, 사용 방법을 이름으로 파악할 수 있어야 한다.
 * 주석이 따로 필요하면 의도가 분명하지 않다
 */
public class ClearIntentions {

    //의도가 분명하지 않은 이름들 -> 해당 변수를 왜 사용하는지 파악할 수 없음 -> 주석을 사용하게 된다.
    int a; //시작 시간
    int b; //종료 시간

    //의도가 분명한 이름
    int startTime;
    int endTime;

    
}
