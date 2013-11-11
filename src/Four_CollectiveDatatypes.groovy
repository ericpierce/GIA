// Listing 4.1 Range declarations
assert (0..10).contains(0) //#A
assert (0..10).contains(5) //#A
assert (0..10).contains(10) //#A
assert (0..10).contains(-1) == false //#A
assert !(0..10).contains(-1) //#A
assert (0..10).contains(11) == false //#A
assert (0..<10).contains(9) //#B
assert (0..<10).contains(10) == false //#B

def aRange = 0..10 //#1
assert aRange instanceof Range //#1
assert aRange.contains(5) //#1

aRange = new IntRange(0,10) //#C
assert aRange.contains(5) //#C

assert (0.0..1.0).contains(1.0) //#D
assert (0.0..1.0).containsWithinBounds(0.5) //#D

(0.0..1.0).each { println ' / ' + it }

def today = new Date() //#2
def yesterday = today - 1 //#2
assert (yesterday..today).size() == 2

assert ('a'..'c').contains('b') //#3
def charRange = ('a'..'c')
assert charRange.getAt(2).next() == 'd'

def log = '' //#E
for (element in 5..9){ //#E
    log += element //#E
} //#E
assert log == '56789' //#E

log = 0 //#E
for (element in 5..9){ //#E
    log += element //#E
} //#E
assert log == 35 //#E

log = '' //#F
for (element in 9..5){ //#F
    log += element //#F
} //#F
assert log == '98765' //#F

log = '' //#4
(9..<5).each { element -> //#4
    log += element //#4
} //#4
assert log == '9876' //#4

// Listing 4.2 Ranges are objects
def result = '' //#A
(5..9).each { element -> //#A
    result += element //#A
} //#A
assert result == '56789' //#A

assert 5 in 0..10 //#1
assert (0..10).isCase(5) //#1

def age = 36
switch(age){
    case 16..20 : insuranceRate = 0.05 ; break
    case 21..50 : insuranceRate = 0.06 ; break
    case 51..65 : insuranceRate = 0.07 ; break
    default: throw new IllegalArgumentException()
} //#1
assert insuranceRate == 0.06 //#1

def ages = [20, 36, 42, 56] //#2
def midage = 21..50 //#2
assert ages.grep(midage) == [36, 42] //#2

// Listing 4.3 Custom ranges: weekdays
// This works as a Range because our class implements: next, previous, compareTo
class Weekday implements Comparable {
    static final DAYS = [
            'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'
    ]
    private int index = 0

    Weekday(String day) {   //#A
        index = DAYS.indexOf(day)
    }

    Weekday next() {
        return new Weekday(DAYS[(index + 1) % DAYS.size()])
    }

    Weekday previous() {
        return new Weekday(DAYS[index - 1])     //#1
    }

    int compareTo(Object other) {
        return this.index <=> other.index
    }

    String toString() {
        return DAYS[index]
    }
}
def mon = new Weekday('Mon')
def fri = new Weekday('Fri')

def worklog = ''
for (day in mon..fri) {
    worklog += day.toString() + ' '
}
assert worklog == 'Mon Tue Wed Thu Fri '

// Listing 4.4 Specifying lists
List myList = [1, 2, 3]

assert myList.size() == 3
assert myList[0]== 1
assert myList instanceof ArrayList

List emptyList = []
assert emptyList.size() == 0

List longList = (0..1000).toList()      // convert range to list
assert longList[555] == 555

List explicitList = new ArrayList()     // old school
explicitList.addAll(myList)             //#1 - 'addAll' provided by java.util.List
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

explicitList = new LinkedList(myList)   // #1 - old school via constructor
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

// Listing 4.5 Accessing parts of a list with the overloaded subscript operator
myList = ['a','b','c','d','e','f']
assert myList[0..2] == ['a','b','c']    //#A getAt(Range)
assert myList[0,2,4] == ['a','c','e']   //#B getAt(collection of indexes)
myList[0..2] = ['x','y','z']                //#C putAt(Range)
assert myList == ['x','y','z','d','e','f']

myList[3..5] = []               //#1 Removing elements
assert myList == ['x','y','z']

myList[1..1] = [0, 1, 2]        //#2 Adding elements
assert myList == ['x', 0, 1, 2, 'z']
assert myList[-1..-3] == ['z',2,1]

// Listing 4.6 List operators involved in adding and removing items
myList = []

myList += 'a'           //#A plus(Object)
assert myList == ['a']

myList += ['b','c']         //#B plus(Collection)
assert myList == ['a','b','c']

myList = []
myList << 'a' << 'b'
assert myList == ['a','b'] //#C leftShift is like append

assert myList - ['b'] == ['a']      //#D minus(Collection)

assert myList * 2 == ['a','b','a','b'] //#E Multiply

// Listing 4.7 Lists taking part in control structures
myList = ['a', 'b', 'c']

assert myList.isCase('a')
assert 'b' in myList

def candidate = 'c'
switch(candidate){
    case myList : assert true; break    //#1 Classify by containment
    default : assert false
}

assert ['x','a','z'].grep(myList) == ['a'] //#2 Intersection filter

myList = []
if (myList) assert false        //#3 Empty lists are false

// Lists can be iterated with a 'for' loop
def expr = ''
for (i in [1,'*',5]){       // #4 for in Collection
    expr += i
}
assert expr == '1*5'

// Listing 4.8 Methods to manipulate list content
assert [1,[2,3]].flatten() == [1,2,3]
assert [1,2,3].intersect([4,3,1])== [3,1]
assert [1,2,3].disjoint([4,5,6])        // returns true when the intersection is empty

def list = [1,2,3]
popped = list.pop()     //#1 Treating a list like a stack
assert popped == 3
assert list == [1,2]

assert [1,2].reverse() == [2,1]
assert [3,1,2].sort() == [1,2,3]

list = [ [1,0], [0,1,2] ]
list = list.sort { a,b -> a[0] <=> b[0] }   //#2 Comparing lists by first element;  "If the Closure has two parameters it is used like a traditional Comparator. I.e. it should compare its two parameters for order, returning a negative integer, zero, or a positive integer when the first parameter is less than, equal to, or greater than the second respectively."
assert list == [ [0,1,2], [1,0] ]

list = list.sort { item -> item.size() }    //#3 Comparing lists by size (number of elements)
assert list == [ [1,0], [0,1,2] ]

assert ["hi","hey","hello"] == ["hello","hi","hey"].sort { it.length() }    //#3 Comparing lists by element string length

list = ['a','b','c']
list.remove(2)              //#4 Removing by index
assert list == ['a','b']
list.remove('b')         //#5 Removing by value
assert list == ['a']

list = ['a','b','b','c']
list.removeAll(['b','c'])
assert list == ['a']

def list2 = ['a','b','b','c']
list.remove(['b','c'])      // so how is removeAll different?
assert list == ['a']

def doubled = [1,2,3].collect{ item ->   //#6 Transforming one list into another
    item*2
}
assert doubled == [2,4,6]

def odd = [1,2,3].findAll{ item ->       //#7 Finding every element matching the closure
    item % 2 == 1
}
assert odd == [1,3]

// uniqueify a list
def x = [1,1,1]
assert [1] == new HashSet(x).toList()
assert [1] == x.unique()

// remove nulls from list
def x2 = [1,null,1]
assert [1,1] == x2.findAll{it != null}
assert [1,1] == x2.grep{it}

// Listing 4.9 List query, iteration, and accumulation
def list3 = [1, 2, 3]
assert list3.first() == 1
assert list3.head() == 1
assert list3.tail() == [2, 3]
assert list3.last() == 3
assert list3.count(2) == 1 //#A Querying - this one counts how many times '2' is in the list.
assert list3.max() == 3 //#A
assert list3.min() == 1 //#A

def list4 = [1, 2, 2, 3, 4]
assert list4.count(2) == 2 //#A Querying

def even = list3.find { item -> //#A
    item % 2 == 0 //#A
} //#A
assert even == 2 //#A
assert even != [2] //#A

even = list4.find { item -> //#A
    item % 2 == 0 //#A
} //#A

assert even == 2 //#A only finds the first match

even = list4.findAll { item -> //#A
    item % 2 == 0 //#A
} //#A

assert even == [2,2,4] //#A only finds the first match


//#A
assert list3.every { item -> item < 5 } //#A
assert list3.every { it < 5 } //#A
assert list3.any { item -> item < 2 } //#A

def store = 0
list3.each { item -> //#B Iteration
    store += item //#B
} //#B
assert store == 6 //#B

store = ''
list3.each { item -> //#B Iteration
    store += item //#B
} //#B
assert store == '123' //#B

//#B
store = '' //#B
list3.reverseEach { item -> //#B
    store += item //#B
} //#B
assert store == '321' //#B

//#B
store = '' //#B
list3.eachWithIndex { item, index -> //#B
    store += "$index:$item " //#B
} //#B
assert store == '0:1 1:2 2:3 ' //#B

assert list3.join('-') == '1-2-3' //#C Accumulation

result = list3.inject(0) { clinks, guests -> //#C
    clinks + guests //#C
} //#C
// clinks on each iteration: 1 (1+0), 3 (2+1), 6 (3+3)
assert result == 0 + 1 + 2 + 3 //#C
assert list3.sum() == 6 //#C

result = list3.inject(2) { clinks, guests -> //#C
    println "$clinks + $guests"
    clinks + guests //#C
} //#C
// clinks on each iteration: 3 (2+1), 5 (3+2), 8 (5+3)
assert result == 5 + 3 //#C     The last click + the last list element


//#C
factorial = list3.inject(1) { fac, item -> //#C
    println "$fac * $item"
    fac * item //#C
// fac on each iteration: 1 (1*1), 2 (1*2), 6 (2*3)
} //#C
assert factorial == 1 * 1 * 2 * 3 //#C

// Listing 4.10 Quicksort with lists
def quickSort(list) {
    if (list.size() < 2) return list
    def pivot = list[list.size().intdiv(2)]
    def left = list.findAll { item -> item < pivot }    //#1 Classify by pivot
    def middle = list.findAll { item -> item == pivot } //#1 Classify by pivot
    def right = list.findAll { item -> item > pivot }   //#1 Classify by pivot
    return quickSort(left) + middle + quickSort(right)  //#A Recursive calls
}

assert quickSort([]) == []
assert quickSort([1]) == [1]
assert quickSort([1,2]) == [1,2]
assert quickSort([2,1]) == [1,2]
assert quickSort([3,1,2]) == [1,2,3]
assert quickSort([3,1,2,2]) == [1,2,2,3]
assert quickSort([1.0f,'a',10,null]) == [null,1.0f,10,'a'] //#2 Ducktyped items
assert quickSort('bca') == 'abc'.toList()       //#3 Ducktyped structure

// Listing 4.11 Specifying maps
def myMap = [a:1, b:2, c:3]
assert myMap instanceof LinkedHashMap
assert myMap.size() == 3
assert myMap['a'] == 1

def emptyMap = [:]
assert emptyMap.size() == 0

def explicitMap = new TreeMap()
explicitMap.putAll(myMap)   // using explict map type and its constructor
assert explicitMap['a'] == 1

def composed = [x:'y', *:myMap]         //#A Spread operator
assert composed == [x:'y', a:1, b:2, c:3]

assert ['a':1] == [a:1]

x = 'a'
assert ['x':1] == [x:1]
assert ['a':1] == [(x):1]   // force Groovy to recognize a symbol as an expression by putting it inside parentheses

// Listing 4.12 Accessing maps (GDK map methods)
myMap = [a:1, b:2, c:3]
assert myMap['a']       == 1    //#A Retrieve existing elements
assert myMap.a          == 1
assert myMap.get('a')   == 1
assert myMap.get('a',0) == 1

assert myMap['d'] == null   //#B Attempt to retrieve missing elements
assert myMap.d == null
assert myMap.get('d') == null

assert myMap.get('d',0) == 0    //#C Default value
assert myMap.d == 0

myMap['d'] = 1      //#D Single putA
assert myMap.d == 1
myMap.d = 2
assert myMap.d == 2

myMap2 = ['a.b':1]       // special chars for key
assert myMap2.'a.b' == 1     // myMap.a.b doesn't work

// Listing 4.13 Query methods on maps
myMap = [a:1, b:2, c:3]
def other = [b:2, c:3, a:1]

assert myMap == other //#A Call to equals

assert !myMap.isEmpty() //#B JDK methods
assert myMap.size() == 3 //#B
assert myMap.containsKey('a') //#B
assert myMap.containsValue(1) //#B
assert myMap.entrySet() instanceof Collection

assert myMap.any {entry -> entry.value > 2 } //#1 GDK methods
assert myMap.every {entry -> entry.key < 'd'} //#1
assert myMap.keySet() == ['a','b','c'] as Set   //#C Set equals
assert myMap.values().toList() == [1, 2, 3]     //#D List equals

