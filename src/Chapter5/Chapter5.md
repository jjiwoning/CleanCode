# Chapter 5 형식 맞추기

---

개발자라면 형식을 깔끔하게 맞춰 코드를 짜야 한다.

코드 형식을 맞추기 위한 간단한 규칙을 정하고 모두가 그 규칙을 따라야 한다.

---

## 형식을 맞추는 목적

**코드 형식은 중요하다.**

코드 형식은 의사소통의 일환으로 전문 개발자의 일차적인 의무이다.

**원래 코드는 사라질지라도 개발자의 스타일과 규율은 사라지지 않는다.
→ 맨 처음 잡아놓은 구현 스타일과 가독성 수준은 유지보수 용이성과 확장성에 계속 영향을 미친다.**

---

## 적절한 행 길이를 유지하라

자바에서 파일 크기는 클래스 크기와 밀접하다.

일반적으로 **큰 파일보다는 작은 파일이 이해하기 쉽다.**

### 신문 기사처럼 작성하라

좋은 신문 기사를 떠올려보라.

독자는 위에서 아래로 기사를 읽는다.

최상단에는 기사를 몇 마디로 요약하는 표제가 나온다. → 읽을지 말지 결정

첫 문단은 전체 기사 내용을 요약한다. → 세세한 사실을 숨긴다.

쭉 읽으며 내려가면 세세한 사실이 조금씩 드러난다. → 세세한 사실이 조금씩 드러난다.

소스 파일도 이러한 방식으로 작성한다.

**이름이 간단하면서 설명이 가능하게 짓는다. → 이름만 보고도 올바른 모듈을 살펴보고 있는지 아닌지를 판단할 정도로 신경 써서 짓는다.**

소스 파일 첫 부분은 고차원 개념과 알고리즘을 설명한다.

아래로 내려갈수록 의도를 세세하게 묘사한다.

마지막에는 가장 저차원 함수와 세부 내역이 나온다.

### 개념은 빈 행으로 분리하라

일련의 행 묶음은 완결된 생각 하나를 표현한다.

→ 생각 사이에는 빈 행을 넣어 분리해야 마땅하다.

```java
// 빈 행이 빠진 코드
package fitnesse.wikitext.widgets;
import java.util.regex.*;
public class BoldWidget extends ParentWidget {
	public static final String REGEXP = "'''.+?'''";
	private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
		Pattern.MULTILINE + Pattern.DOTALL);
	public BoldWidget(ParentWidget parent, String text) throws Exception {
		super(parent);
		Matcher match = pattern.matcher(text); match.find(); 
		addChildWidgets(match.group(1));}
	public String render() throws Exception { 
		StringBuffer html = new StringBuffer("<b>"); 		
		html.append(childHtml()).append("</b>"); 
		return html.toString();
	} 
}

// 빈 행이 들어간 코드
package fitnesse.wikitext.widgets;

import java.util.regex.*;

public class BoldWidget extends ParentWidget {
	public static final String REGEXP = "'''.+?'''";
	private static final Pattern pattern = Pattern.compile("'''(.+?)'''", 
		Pattern.MULTILINE + Pattern.DOTALL
	);
	
	public BoldWidget(ParentWidget parent, String text) throws Exception { 
		super(parent);
		Matcher match = pattern.matcher(text);
		match.find();
		addChildWidgets(match.group(1)); 
	}
	
	public String render() throws Exception { 
		StringBuffer html = new StringBuffer("<b>"); 
		html.append(childHtml()).append("</b>"); 
		return html.toString();
	} 
}
```

### 세로 밀집도

줄 바꿈이 개념을 분리한다면 **세로 밀집도는 연관성을 의미한다.**

**서로 밀접한 코드 행은 세로로 가까이 놓여야 한다는 뜻이다.**

```java
public class ReporterConfig {
	private String m_className;
	private List<Property> m_properties = new ArrayList<Property>();
	
	public void addProperty(Property property) { 
		m_properties.add(property);
	}
```

서로 밀접한 코드를 가까이 놓아 이해가 쉽다. (변수끼리 붙이고 메서드랑 띄움)

### 수직 거리

서로 밀접한 개념은 세로로 가까이 둬야 한다.

두 개념이 서로 다른 파일에 속한다면 규칙이 통하지 않는다.

타당한 근거가 없다면 서로 밀접한 개념은 한 파일에 속해야 마땅하다.

→ protected 변수를 피해야 하는 이유

> protected 변수를 피하는 이유
protected의 경우, 해당 class에 선언된 친구가 아닌 변수를 자유롭게 호출 가능해지고, 심지어 외부 패키지의 변수를 갑자기 사용하는 케이스가 생길 수 있으므로, 밀접한 개념이 세로로 가까워야한다는 원칙에 벗어나기 때문이다.
출처: [https://velog.io/@hyj2508/클린코드Ch5-7](https://velog.io/@hyj2508/%ED%81%B4%EB%A6%B0%EC%BD%94%EB%93%9CCh5-7)
>

**같은 파일에 속할 정도로 밀접한 두 개념은 세로 거리로 연관성을 표현한다.**

→ 연관성이란 한 개념을 이해하는 데 다른 개념이 중요한 정도를 말한다.

- 변수선언
  변수는 사용하는 위치에 최대한 가까이 선언한다.
  우리가 만드는 함수는 매우 짧으므로 지역 변수는 각 함수 맨 처음에 선언한다.
- 인스턴스 변수
  인스턴스 변수는 클래스 맨 처음에 선언한다.
  변수 간에 세로로 거리를 두지 않는다. → 잘 설계한 클래스는 대다수 클래스 메서드가 인스턴스 변수를 사용하기 때문
- 종속 함수
  한 함수가 다른 함수를 호출한다면 두 함수는 세로로 가까이 배치한다.
  그리고 가능하다면 호출하는 함수를 호출되는 함수보다 먼저 배치한다.
- 개념적 유사성
  개념적인 친화도가 높을수록 코드를 가까이 배치한다.
  한 함수가 다른 함수를 호출하는 종속성, 변수와 그 변수를 사용하는 함수가 개념적 유사성을 가지는 예시이다
  비슷한 동작을 수행하는 함수도 좋은 예시이다.

### 세로 순서

함수 호출 종속성은 아래방향으로 유지한다. 즉, 호출되는 함수를 호출하는 함수보다 뒤에 배치한다.
그러면 소스 코드 모듈이 고차원에서 저차원으로 자연스럽게 내려간다.
가장 중요한 개념을 가장 먼저 표현하고, 세세한 사항은 마지막에 표현한다.

---

## 가로 형식 맞추기

개발자는 짧은 행을 선호한다.

120자 정도로 행 길이를 제한하는 것이 바람직하다.

### 가로 공백과 밀집도

가로로는 공백을 사용해 밀접한 개념과 느슨한 개념을 표현한다.

```java
private void measureLine(String line) { 
	lineCount++;
	int lineSize = line.length();
	totalChars += lineSize; 
	lineWidthHistogram.addLine(lineSize, lineCount);
	recordWidestLine(lineSize);
}
```

할당 연산자를 강조하려고 앞뒤에 공백을 준다. → 두 가지 주요 요소가 확실히 나뉜다는 사실이 분명해진다.

함수 이름과 이어지는 괄호 사이에는 공백을 넣지 않았다. → 함수와 인수는 서로 밀접하기 때문

인수는 공백으로 분리 → 인수가 별개라는 사실이 보이기 위함

### 가로 정렬

선언문과 할당문을 별도로 정렬하면 보기가 안좋다.

그리고 IDE의 코드 형식을 맞춰주는 도구는 이러한 정렬을 무시한다.

만약에 **정렬이 필요할 정도로 목록이 길다면 문제는 목록의 길이이지 정렬의 부족이 아니다.**

→ 클래스를 쪼개는 게 낫다.

그러니 가로 정렬은 하지말자

### 들여쓰기

평소에 우리가 코딩을 하는 것처럼 들여쓰기를 잘하면 된다.

→ 들여쓰기를 안하면 코드 가독성이 상당히 떨어지기 때문

- 들여쓰기 무시하기
  때로는 간단한 if 문, 짧은 while 문, 짧은 함수에서 들여쓰기를 무시하고픈 유혹이 생긴다.
  → **절대로 들여쓰기를 무시하지 말자!! 무조건 들여쓰기를 해서 범위를 제대로 표현하자!!**
- 가짜 범위
  때로는 빈 while 문이나 for 문을 접한다. (접해본 적 없긴함)
  이러한 구조는 피하는 게 좋고, 만약 피하지 못한다면 빈 블록을 올바로 들여쓰고 괄호로 감싸라.
  세미콜론을 붙인다면 새 행에다가 제대로 들여써서 넣어줘라.

---

## 팀 규칙

어딘가 팀에 속한다면 팀 규칙에 맞춰서 코딩을 하자

→ 이래야 일관적인 스타일을 가질 수 있고 이게 좋은 프로그램이 되기 때문이다.

---

## 밥 아저씨의 형식 규칙

책의 저자 로버트 마틴의 규칙 예시이다.

```java
public class CodeAnalyzer implements JavaFileAnalysis { 
	private int lineCount;
	private int maxLineWidth;
	private int widestLineNumber;
	private LineWidthHistogram lineWidthHistogram; 
	private int totalChars;
	
	public CodeAnalyzer() {
		lineWidthHistogram = new LineWidthHistogram();
	}
	
	public static List<File> findJavaFiles(File parentDirectory) { 
		List<File> files = new ArrayList<File>(); 
		findJavaFiles(parentDirectory, files);
		return files;
	}
	
	private static void findJavaFiles(File parentDirectory, List<File> files) {
		for (File file : parentDirectory.listFiles()) {
			if (file.getName().endsWith(".java")) 
				files.add(file);
			else if (file.isDirectory()) 
				findJavaFiles(file, files);
		} 
	}
	
	public void analyzeFile(File javaFile) throws Exception { 
		BufferedReader br = new BufferedReader(new FileReader(javaFile)); 
		String line;
		while ((line = br.readLine()) != null)
			measureLine(line); 
	}
	
	private void measureLine(String line) { 
		lineCount++;
		int lineSize = line.length();
		totalChars += lineSize; 
		lineWidthHistogram.addLine(lineSize, lineCount);
		recordWidestLine(lineSize);
	}
	
	private void recordWidestLine(int lineSize) { 
		if (lineSize > maxLineWidth) {
			maxLineWidth = lineSize;
			widestLineNumber = lineCount; 
		}
	}

	public int getLineCount() { 
		return lineCount;
	}

	public int getMaxLineWidth() { 
		return maxLineWidth;
	}

	public int getWidestLineNumber() { 
		return widestLineNumber;
	}

	public LineWidthHistogram getLineWidthHistogram() {
		return lineWidthHistogram;
	}
	
	public double getMeanLineWidth() { 
		return (double)totalChars/lineCount;
	}

	public int getMedianLineWidth() {
		Integer[] sortedWidths = getSortedWidths(); 
		int cumulativeLineCount = 0;
		for (int width : sortedWidths) {
			cumulativeLineCount += lineCountForWidth(width); 
			if (cumulativeLineCount > lineCount/2)
				return width;
		}
		throw new Error("Cannot get here"); 
	}
	
	private int lineCountForWidth(int width) {
		return lineWidthHistogram.getLinesforWidth(width).size();
	}
	
	private Integer[] getSortedWidths() {
		Set<Integer> widths = lineWidthHistogram.getWidths(); 
		Integer[] sortedWidths = (widths.toArray(new Integer[0])); 
		Arrays.sort(sortedWidths);
		return sortedWidths;
	} 
}
```
