
val fruit = List("apple", "pear", "orange", "pineapple")
fruit sortWith (_.length < _.length)
fruit.sorted

fruit groupBy (_.head)
fruit.groupBy(_.head)


val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")

capitalOfCountry("US")
romanNumerals("V")
//capitalOfCountry("KOREA")
capitalOfCountry get "KOREA"
capitalOfCountry get "US"


def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "missing data"
}

showCapital("US")
showCapital("KOREA")
