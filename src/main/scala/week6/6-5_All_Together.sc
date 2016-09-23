val mnem = Map(
  '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
)

val charCode: Map[Char, Char] = {
  for ((digit, str) <- mnem; ltr <- str) yield ltr -> digit
}

def wordCode(word: String): String =
  word.toUpperCase map charCode

wordCode("Java")

val number = "7225247386"
val split = 1 to number.length
val t1 = number.take(4)
