// Listing 4.1 Range declarations
assert (0..10).contains(0) //#A
assert (0..10).contains(5) //#A
assert (0..10).contains(10) //#A
assert (0..10).contains(-1) == false //#A
assert (0..10).contains(11) == false //#A
assert (0..<10).contains(9) //#B
assert (0..<10).contains(10) == false //#B

def a = 0..10 //#1
assert a instanceof Range //#1
assert a.contains(5) //#1

a = new IntRange(0,10) //#C
assert a.contains(5) //#C

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