package Chapter2;

/**
 * 의미 있는 맥락을 추가하라
 */
public class AddMeaningfulContext {

    //해당 메서드의 변수들은 함수를 끝까지 읽어야 통계 추측임을 알 수 있다. -> 맥락이 불분명하다.
    private void printGuessStatistics(char candidate, int count) {
        String number;
        String verb;
        String pluralModifier;
        if (count == 0) {
            number = "no";
            verb = "are";
            pluralModifier = "s";
        }  else if (count == 1) {
            number = "1";
            verb = "is";
            pluralModifier = "";
        }  else {
            number = Integer.toString(count);
            verb = "are";
            pluralModifier = "s";
        }
        String guessMessage = String.format("There %s %s %s%s", verb, number, candidate, pluralModifier );

        System.out.println(guessMessage);
    }

    //메서드의 변수들을 클래스로 만들어 맥락을 분명하게 만들어줄 수 있고, 메서드를 분리하기 쉬워 알고리즘도 명확해진다.
    public class GuessStatisticsMessage {
        private String number;
        private String verb;
        private String pluralModifier;

        public String make(char candidate, int count) {
            createPluralDependentMessageParts(count);
            return String.format("There %s %s %s%s", verb, number, candidate, pluralModifier );
        }

        private void createPluralDependentMessageParts(int count) {
            if (count == 0) {
                thereAreNoLetters();
            } else if (count == 1) {
                thereIsOneLetter();
            } else {
                thereAreManyLetters(count);
            }
        }

        private void thereAreManyLetters(int count) {
            number = Integer.toString(count);
            verb = "are";
            pluralModifier = "s";
        }

        private void thereIsOneLetter() {
            number = "1";
            verb = "is";
            pluralModifier = "";
        }

        private void thereAreNoLetters() {
            number = "no";
            verb = "are";
            pluralModifier = "s";
        }
    }

}
