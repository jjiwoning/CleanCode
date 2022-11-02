package Chapter2;

public class EasyToSearchName {

    public static void main(String[] args) {

        //아래의 방식으로 코딩을 하면 검색을 하기가 어려워 진다.
        int s = 0;
        int[] t = new int[34];

        for (int i = 0; i < 34; i++) {
            s += (t[i] * 4) / 5;
        }

        //검색하기 쉬운 이름으로 만드는 경우
        int realDaysPerIdealDay = 4;
        final int WORK_DAYS_PER_WEEK = 5;
        final int NUMBER_OF_TASKS = 34;
        int sum = 0;
        int[] taskEstimate = new int[NUMBER_OF_TASKS];
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            int realTaskDays = taskEstimate[i] * realDaysPerIdealDay;
            int realTaskWeeks = realTaskDays / WORK_DAYS_PER_WEEK;
            sum += realTaskWeeks;
        }

    }
}
