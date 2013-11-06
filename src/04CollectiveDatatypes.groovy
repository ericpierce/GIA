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

(0.0..1.0).each { print ' / ' + it }

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

