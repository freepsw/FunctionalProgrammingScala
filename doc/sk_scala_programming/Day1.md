# Day 1
## function 선언시 관행?
- side effect가 함수에 있는 경우는 ()를 붙이고, 아닌 경우는 함수명만 표현한다.
- scala-lang에서 style guide에 있는 내용 참고, Arity-0
```
def abc() : Int = 1
def sum : Int = 2
```

## 함수형 언어의 2가지 특징
1. immutable (불변 데이터)
 - java의 final, static 변수와 같음.
 - scala에서는 val
2. side effect가 없다. (순수 함수)
 - 동일한 input에 동일한 output이 return


## Scala의 타입 추론
static type이지만 dynamic type인 것 처럼 보이도록 타입을 추론함.

```
val a = 10
val a:Int = 10 //same
```

compiler가 자동으로 value를 기반으로 변수의 type을 추론하여 static type으로 변환함.


## 명령형과 선언형의 차이
- 명령형(imperative) : if, else 등을 이용해 동작을 제어
- 선언형(declarative) : SQL(select * from table1)과 같이 사용할 수 있도록 함수형 언어
 * 프로그램이 어떤 방법으로 동작하는지를 나타내는 것 보다, 무엇을 해야 하는지 설명하는 경우.
 * 입력과 출력의 관계를 선언. (SQL이 그 예시)
 * Scala, JQuery, AngularJS

 ( https://namu.wiki/w/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%20%EC%96%B8%EC%96%B4)
 * 명령형과 가장 큰 차이는 순서가 의미 없다. 변수를 어디에서 선언을 하든 immutable이으로 이 값은 변하지 않는다. 하지만 명령형에서는 해당 변수가 변경될 수 있으므로 순서가 중요.
 * 따라서 표현식의 의미가 명료해지고, 변수의 변화를 추적하기 위해 제어흐름을 따라가지 않고, 값의 변화만을 보면 쉽게 디버깅 할 수 있다.
 * (???? 값의 변화가 제어흐름 아닌가?, 아마도 변수의 값이 언제 바뀌는지 추적하는데 시간이 소요된다는 의미인것 같음.)


## 제어문에 대한 확장 (if, else와 같은 연산자 확장)
if의 반대 개념인 unless를 작성해 보자
- if에서는 true일 경우 {}에 정의된 함수를 수행하지만
- unless에서는 false일 경우에 실행하도록 해 보자
```
def unless (condition: => Boolean)(block: => Unit)  {
  if(!condition) { block }
}

unless( 0 > 1) { println("unless111") }
```
- input parameter에서 condition: 에 type이 지정되지 않은것을 볼 수 있다.
- 이를 통해서 condition의 true/false 여부를 확인하는 parameter를 가져오고
- block: => Unit 에서는 실제 실행될 코드블록 {}을 받아온다.
- currying을 통해서 해당 함수를 작성
