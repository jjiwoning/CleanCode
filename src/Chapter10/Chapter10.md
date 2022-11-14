# Chapter 10 클래스

---

## 클래스 체계

클래스를 정의하는 표준 자바 관례에 따르면, 가장 먼저 변수 목록이 나온다.

static public 상수가 있다면 맨 처음에 나온다. 그 다음 static private 변수가 나오며, 그 다음 비공개 인스턴스 변수가 나온다.

변수 목록 다음에는 공개 메서드가 나온다. 비공개 함수는 자신을 호출하는 공개 메서드 직후에 넣는다.

→ 추상화 단계가 순차적으로 내려간다. → 프로그램이 신문 기사처럼 읽힌다.

### 캡슐화

변수와 유틸리티 함수는 가능한 숨기는 게 좋다. → 무조건 숨겨야 하는 것은 아님

때로는 변수나 유틸리티 함수를 protected로 선언해 테스트 코드에 접근을 허용하기도 함

→ 테스트는 아주 중요하다. 같은 패키지 안에서 테스트 코드가 함수를 호출하거나 변수를 사용해야 한다면 그 함수나 변수를 protected로 선언하거나 패키지 전체로 공개

하지만 그 전에 비공개 상태를 유지할 온갖 방법을 강구한다!! → 캡슐화를 풀어주는 결정은 최후의 방법!!

---

## 클래스는 작아야 한다!

클래스를 만들 때 가장 중요한 규칙은 크기이다. **클래스의 크기는 작아야 한다**!!!

어떤 클래스를 만들어도 최대한 작은 크기로 만드는 것을 권장!!

단, 함수의 크기를 결정하는 척도(물리적인 행 수)로 크기를 작게하는 것이 아닌 **클래스는 맡은 책임의 개수로 크기를 결정한다!!**

즉, 메서드가 2개인 클래스를 만들더라도 책임이 여러 개이면 이는 크기가 큰 클래스이다!!

클래스의 이름은 해당 클래스의 책임을 기술해야 한다.

→ 클래스 작명은 클래스 크기를 줄이는 첫 관문이다. 만약 클래스 이름이 모호하다면 이는 클래스의 책임이 너무 많다는 증거가 된다.

→ 클래스 설명은 if, and, or, but을 사용하지 않고, 25단어 내외로 가능해야 한다.

### SRP (단일 책임 원칙)

단일 책임 원칙은 클래스나 모듈이 1가지의 책임을 가져야 한다는 원칙이다.

→ 변경할 이유가 하나뿐이어야 한다.

SRP는 객체 지향 설계에서 중요한 개념이다. → 이해하고 지키기 수월한 개념이다.

그런데 이를 잘 지키지 못하는 경우가 많은데 이는 개발자가 돌아가는 소프트웨어에 초점을 맞추기 때문이다.

→ **돌아가는 소프트웨어를 만들었으면 깨끗하게 체계적인 소프트웨어라는 다음 관심사로 전환해야 한다.**

복잡한 시스템을 잘 다루려면 체계적인 정리를 꼭 해줘야 한다.

→ **큰 클래스 몇 개가 아닌 작은 클래스를 여럿으로 만들어야 한다.**

### 응집도

클래스는 인스턴스 변수 수가 작아야 한다.

각 클래스 메서드는 클래스 인스턴스 변수를 하나 이상 사용해야 한다.

일반적으로 메서드가 변수를 더 많이 사용할수록 메서드와 클래스는 응집도가 높다.

일반적으로 이러한 응집도가 높은 클래스는 바람직하지 않다.

→ **하지만 응집도가 높다는 말은 클래스에 속한 메서드와 변수가 서로 의존하며 논리적인 단위로 묶인다는 의미이기 때문에 선호도가 높다.**

→ **응집도가 높아지도록 변수와 메서드를 적절히 분리해 새로운 클래스 두세 개로 쪼개준다.**

### 응집도를 유지하면 작은 클래스 여럿이 나온다.

큰 함수를 작은 함수 여럿으로 나누기만 해도 클래스 수가 많아진다.

- 변수가 많은 큰 함수의 일부를 작은 함수 하나로 빼고 싶음
- 빼고 싶은 코드가 큰 함수에 정의된 변수 4개를 사용
- 변수 4개를 인수로 넘기는건 좋지 않음 → 클린코드 함수 부분
- 변수 4개를 클래스 인스턴수 변수로 승격하면 됨 → 인수가 필요없고 함수를 쪼개기도 쉬워짐
- 하지만 이 경우 인스턴스 변수를 특정 메서드만 사용하므로 클래스가 응집력을 잃음
- 이 경우 **독자적인 클래스로 분리하여 응집도를 유지할 수 있음**
- 결과적으로 작은 클래스 여럿이 나오게 됨

이러한 경우의 코드 예제를 보면서 이해해보자.

```java
public class PrintPrimes {
	public static void main(String[] args) {
		final int M = 1000; 
		final int RR = 50;
		final int CC = 4;
		final int WW = 10;
		final int ORDMAX = 30; 
		int P[] = new int[M + 1]; 
		int PAGENUMBER;
		int PAGEOFFSET; 
		int ROWOFFSET; 
		int C;
		int J;
		int K;
		boolean JPRIME;
		int ORD;
		int SQUARE;
		int N;
		int MULT[] = new int[ORDMAX + 1];
		
		J = 1;
		K = 1; 
		P[1] = 2; 
		ORD = 2; 
		SQUARE = 9;
	
		while (K < M) { 
			do {
				J = J + 2;
				if (J == SQUARE) {
					ORD = ORD + 1;
					SQUARE = P[ORD] * P[ORD]; 
					MULT[ORD - 1] = J;
				}
				N = 2;
				JPRIME = true;
				while (N < ORD && JPRIME) {
					while (MULT[N] < J)
						MULT[N] = MULT[N] + P[N] + P[N];
					if (MULT[N] == J) 
						JPRIME = false;
					N = N + 1; 
				}
			} while (!JPRIME); 
			K = K + 1;
			P[K] = J;
		} 
		{
			PAGENUMBER = 1; 
			PAGEOFFSET = 1;
			while (PAGEOFFSET <= M) {
				System.out.println("The First " + M + " Prime Numbers --- Page " + PAGENUMBER);
				System.out.println("");
				for (ROWOFFSET = PAGEOFFSET; ROWOFFSET < PAGEOFFSET + RR; ROWOFFSET++) {
					for (C = 0; C < CC;C++)
						if (ROWOFFSET + C * RR <= M)
							System.out.format("%10d", P[ROWOFFSET + C * RR]); 
					System.out.println("");
				}
				System.out.println("\f"); PAGENUMBER = PAGENUMBER + 1; PAGEOFFSET = PAGEOFFSET + RR * CC;
			}
		}
	}
}
```

큰 함수가 하나인 나쁜 코드이다. 이를 작은 여러 함수와 클래스로 나눠서 보면 정말 깔끔해진다.

```java
public class PrimePrinter {
	public static void main(String[] args) {
		final int NUMBER_OF_PRIMES = 1000;
		int[] primes = PrimeGenerator.generate(NUMBER_OF_PRIMES);
		
		final int ROWS_PER_PAGE = 50; 
		final int COLUMNS_PER_PAGE = 4; 
		RowColumnPagePrinter tablePrinter = 
			new RowColumnPagePrinter(ROWS_PER_PAGE, 
						COLUMNS_PER_PAGE, 
						"The First " + NUMBER_OF_PRIMES + " Prime Numbers");
		tablePrinter.print(primes); 
	}
}

public class RowColumnPagePrinter { 
	private int rowsPerPage;
	private int columnsPerPage; 
	private int numbersPerPage; 
	private String pageHeader; 
	private PrintStream printStream;
	
	public RowColumnPagePrinter(int rowsPerPage, int columnsPerPage, String pageHeader) { 
		this.rowsPerPage = rowsPerPage;
		this.columnsPerPage = columnsPerPage; 
		this.pageHeader = pageHeader;
		numbersPerPage = rowsPerPage * columnsPerPage; 
		printStream = System.out;
	}
	
	public void print(int data[]) { 
		int pageNumber = 1;
		for (int firstIndexOnPage = 0 ; 
			firstIndexOnPage < data.length ; 
			firstIndexOnPage += numbersPerPage) { 
			int lastIndexOnPage =  Math.min(firstIndexOnPage + numbersPerPage - 1, data.length - 1);
			printPageHeader(pageHeader, pageNumber); 
			printPage(firstIndexOnPage, lastIndexOnPage, data); 
			printStream.println("\f");
			pageNumber++;
		} 
	}
	
	private void printPage(int firstIndexOnPage, int lastIndexOnPage, int[] data) { 
		int firstIndexOfLastRowOnPage =
		firstIndexOnPage + rowsPerPage - 1;
		for (int firstIndexInRow = firstIndexOnPage ; 
			firstIndexInRow <= firstIndexOfLastRowOnPage ;
			firstIndexInRow++) { 
			printRow(firstIndexInRow, lastIndexOnPage, data); 
			printStream.println("");
		} 
	}
	
	private void printRow(int firstIndexInRow, int lastIndexOnPage, int[] data) {
		for (int column = 0; column < columnsPerPage; column++) {
			int index = firstIndexInRow + column * rowsPerPage; 
			if (index <= lastIndexOnPage)
				printStream.format("%10d", data[index]); 
		}
	}

	private void printPageHeader(String pageHeader, int pageNumber) {
		printStream.println(pageHeader + " --- Page " + pageNumber);
		printStream.println(""); 
	}
		
	public void setOutput(PrintStream printStream) { 
		this.printStream = printStream;
	} 
}

public class PrimeGenerator {
	private static int[] primes;
	private static ArrayList<Integer> multiplesOfPrimeFactors;

	protected static int[] generate(int n) {
		primes = new int[n];
		multiplesOfPrimeFactors = new ArrayList<Integer>(); 
		set2AsFirstPrime(); 
		checkOddNumbersForSubsequentPrimes();
		return primes; 
	}

	private static void set2AsFirstPrime() { 
		primes[0] = 2; 
		multiplesOfPrimeFactors.add(2);
	}
	
	private static void checkOddNumbersForSubsequentPrimes() { 
		int primeIndex = 1;
		for (int candidate = 3 ; primeIndex < primes.length ; candidate += 2) { 
			if (isPrime(candidate))
				primes[primeIndex++] = candidate; 
		}
	}

	private static boolean isPrime(int candidate) {
		if (isLeastRelevantMultipleOfNextLargerPrimeFactor(candidate)) {
			multiplesOfPrimeFactors.add(candidate);
			return false; 
		}
		return isNotMultipleOfAnyPreviousPrimeFactor(candidate); 
	}

	private static boolean isLeastRelevantMultipleOfNextLargerPrimeFactor(int candidate) {
		int nextLargerPrimeFactor = primes[multiplesOfPrimeFactors.size()];
		int leastRelevantMultiple = nextLargerPrimeFactor * nextLargerPrimeFactor; 
		return candidate == leastRelevantMultiple;
	}
	
	private static boolean isNotMultipleOfAnyPreviousPrimeFactor(int candidate) {
		for (int n = 1; n < multiplesOfPrimeFactors.size(); n++) {
			if (isMultipleOfNthPrimeFactor(candidate, n)) 
				return false;
		}
		return true; 
	}
	
	private static boolean isMultipleOfNthPrimeFactor(int candidate, int n) {
		return candidate == smallestOddNthMultipleNotLessThanCandidate(candidate, n);
	}
	
	private static int smallestOddNthMultipleNotLessThanCandidate(int candidate, int n) {
		int multiple = multiplesOfPrimeFactors.get(n); 
		while (multiple < candidate)
			multiple += 2 * primes[n]; 
		multiplesOfPrimeFactors.set(n, multiple); 
		return multiple;
	} 
}
```

정말 깔끔해졌지만 코드가 길어졌다. 그 이유에 대해서 설명해보면

1. 리팩토링한 프로그램은 조금 더 길고 서술적인 변수 이름을 사용한다.

2. 리팩토링한 프로그램은 코드에 주석을 추가하는 수단으로 함수 선언과 메서드 선언을 활용한다.

3. 가독성을 높이고자 공백을 추가하고 형식을 맞췄다.

→ **책임을 나눴기 때문에 프로그램의 유지보수에 큰 도움을 준다!!**

이렇게 코드를 나누는 방법은 아래와 같다.

- 프로그램의 정확한 동작을 검증하는 테스트 슈트를 작성.
- 한 번에 하나씩 수 차례에 걸쳐 조금씩 코드를 변경
- 변경된 코드의 테스트를 수행해 원래 프로그램과 동일하게 동작하는지 확인

---

## 변경하기 쉬운 클래스

대다수의 시스템은 지속적인 변경이 가해진다. → 변경할 때마다 시스템이 의도와 다르게 동작하는 위험이 있다.

→ 그렇기에 깨끗한 시스템으로 클래스를 체계적으로 관리해 변경이 일으키는 위험을 낮춰야 한다.

아래의 예시 코드를 보면서 이해해보자.

```java
public class Sql {
	public Sql(String table, Column[] columns)
	public String create()
	public String insert(Object[] fields)
	public String selectAll()
	public String findByKey(String keyColumn, String keyValue)
	public String select(Column column, String pattern)
	public String select(Criteria criteria)
	public String preparedInsert()
	private String columnList(Column[] columns)
	private String valuesList(Object[] fields, final Column[] columns) private String selectWithCriteria(String criteria)
	private String placeholderList(Column[] columns)
}
```

이 코드는 주어진 메타 데이터로 적절한 sql을 생성하는 클래스이다. 만약 해당 sql이 update 쿼리를 지원해야되는 시점이 오면 클래스를 고쳐야한다. → 위험이 생긴다.

그리고 해당 클래스는 insert, select 등 너무 많은 책임을 가지고 있다.

또한 클래스 일부에만 사용되는 비공개 메서드는 코드를 개선할 여지를 시사한다.

이러한 코드를 리팩토링하면 아래와 같다.

```java
abstract public class Sql {
    public Sql(String table, Column[] columns)
    abstract public String generate();
}

public class CreateSql extends Sql {
    public CreateSql(String table, Column[] columns)
        
    @Override
    public String generate();
}

public class SelectSql extends Sql {
    public SelectSql(String table, Column[] columns)
    @Override
    public String generate()
}

public class InsertSql extends Sql {
    public InsertSql(String table, Column[] columns, Object[] fields)
    @Override
    public String generate()
    private String valuesList(Object[] fields, final Column[] columns)
}

public class SelectWithCriteriaSql extends Sql {
    public SelectWithCriteriaSql(String table, Column[] columns, Criteria criteria)
    @Override
    public String generate()
}

public class SelectWithMatchSql extends Sql {
    public SelectWithMatchSql(String table, Column[] columns, Column column, String pattern)
    @Override
    public String generate()
}

public class FindByKeySql extends Sql
    public FindByKeySql(String table, Column[] columns, String keyColumn, String keyValue)
    @Override
    public String generate()
}

public class PreparedInsertSql extends Sql {
    public PreparedInsertSql(String table, Column[] columns)
    @Override public String generate() {
    private String placeholderList(Column[] columns)
}

public class Where {
    public Where(String criteria)
    public String generate()
}

public class ColumnList {
    public ColumnList(Column[] columns)
    public String generate()
}
```

공개 인터페이스들을 sql 클래스에서 파생하는 클래스로 만들고, 비공개 메서드는 해당 메서드를 사용하는 클래스의 메서드로 옮긴다. 그리고 모든 클래스가 사용하는 비공개 메서드는 유틸리티 클래스를 만들어 제공한다.

이런 방법으로 클래스를 단순하게 만들 수 있고, 코드를 쉽게 이해할 수 있다.

함수 하나를 수정해도 다른 함수가 망가질 위험도 사실상 사라졌다.

테스트도 모든 관점에서 할 수 있다.

그리고 update 쿼리를 추가하더라도 다른 코드가 망가질 위험이 사라졌다.

이러한 코드는 SRP를 만족하고, OCP도 만족하는 코드이다.

### 변경으로부터 격리

요구사항은 변하기 마련이다.

→ 상세한 구현에 의존하는 클라이언트 클래스는 구현이 바뀌면 위험에 빠진다.

→ **인터페이스와 추상 클래스를 사용해 구현이 미치는 영향을 격리한다.**

구체 클래스에 의존하는 코드는 테스트가 어렵다.

→ 테스트가 가능할 정도로 시스템의 결합도를 낮추면 (추상화 → 인터페이스 사용) 유연성과 재사용성도 더욱 높아진다.

결합도가 낮다는 소리는 각 시스템의 요소가 다른 요소로부터 그리고 변경으로부터 잘 격리되어 있다는 의미이다.

→ DIP(의존관계 역전 원칙)를 따르는 클래스가 완성된다.

**결론적으로 우리는 클래스 의존 관계를 설정할 때, 구체 클래스(구현)가 아닌 인터페이스(역할)에 의존할 수 있게 설계를 해야한다!!!  구체적인 사실은 숨겨야 한다!!!**