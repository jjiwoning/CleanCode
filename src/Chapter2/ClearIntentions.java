package Chapter2;

import java.util.ArrayList;
import java.util.List;

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

    //하는 일을 짐작하지 어려운 코드 -> 의미가 함축되어 있다. -> 코드를 읽는 독자가 사전 지식을 알고 있다는 가정을 하고 있다.
    List<int[]> theList = new ArrayList<>();

    public List<int[]> getThem() {
        List<int[]> list = new ArrayList<>();
        for (int[] ints : theList) {
            if (ints[0] == 4) {
                list.add(ints);
            }
        }
        return list;
    }

    //의도를 분명하게 밝히는 코드 -> 이름만으로 충분한 정보를 제공한다.
    List<int[]> gameBoard = new ArrayList<>();
    private final int STATUS_VALUE = 0;
    private final int FLAGGED = 4;

    public List<int[]> getFlaggedCells() {
        List<int[]> flaggedCells = new ArrayList<int[]>();
        for (int[] cell : gameBoard) {
            if (cell[STATUS_VALUE] == FLAGGED) {
                flaggedCells.add(cell);
            }
        }
        return flaggedCells;
    }
}
