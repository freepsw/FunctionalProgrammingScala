val list  = List (1 to 10)
list.foreach(list => println(list + " "))

var i = 0
while (i < list.length) {
  if( i != 0 ) print(" ")
  else print(list(i))

  i += 1
}

