# 6-5 Putting the Pieces Together
- 이번 task에서는 telephone numbers를 문장으로 변환하는 프로그램을 설계할 것이다.

## Task
- 폰 넘버는 각각의 알파벳과 연결되어 있다.

```
val mnem = Map(
  '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
)
```

- 우리가 단어사전을 가지고 있다고 가정하고, 아래와 같은 method를 설계해 보자
- translate(phoneNumber)
- 예를 들면 "7225247386"이라는 전화번호는  "scala"라는 단어와 연관될 것이다.
- mnemonic : 기억을 돕는, 연사 기억코드
- 7:S, 2:C, 2:A, 5:L, 2:A ..... 이런식으로 숫자와 매칭하여 단어 생성
