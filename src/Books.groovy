class Book {
    private String title
    Book (String theTitle) {
        title = theTitle
    }
    String getTitle(){
        return title
    }
}

Book gina = new Book('Groovy in Action')

assert gina.getTitle() == 'Groovy in Action'

assert getTitleBackwards(gina) == 'noitcA ni yvoorG'

String getTitleBackwards(book) {
    String title = book.getTitle()
    return title.reverse()
}

println gina.dump()
println gina.title
println getTitleBackwards(gina)


// GROOVY BEAN
class BookBean {
    String title // #1 Property declaration

    // override implicit getter
    String getTitle(){
        return title + ' (' + title.length() + ')'
    }

}

def groovyBook = new BookBean()
groovyBook.setTitle('Groovy conquers the world') // #2 Property use with explicit method
assert groovyBook.getTitle() == 'Groovy conquers the world (25)'
println groovyBook.getTitle()

groovyBook.title = 'Groovy in Action' // #3 Property use with Groovy shortcuts
assert groovyBook.title == 'Groovy in Action (16)'
println groovyBook.title

// ANNOTATIONS
@groovy.transform.Immutable class FixedBook { // #1 AST annotation
    String title
}
def gina2 = new FixedBook('Groovy in Action') // #2 positional ctor
def regina = new FixedBook(title:'Groovy in Action') // #3 named arg ctor

assert gina2.title == 'Groovy in Action'
assert gina2 == regina // #4 standard equals()

try {
    gina2.title = "Oops!" /* #5 not allowed! */
    assert false, "should not reach here"
} catch (ReadOnlyPropertyException e) {}

// GSTRING
def nick = 'ReGina'
def book = 'Groovy in Action, 2nd ed.'
assert "$nick is $book" == 'ReGina is Groovy in Action, 2nd ed.'
println "$nick is $book"

// REGEX
assert '12345' =~ /\d+/            //  =~ find operator
assert 'xxxxx' == '12345'.replaceAll(/\d/,'x')
println '12Y45'.replaceAll(/\d/,'x')

// NUMBERS
def x = 1
def y = 2
assert x + y == 3
assert x.plus(y) == 3
assert x instanceof Integer

// LISTS
def roman = ['', 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII'] // #1 List of Roman numerals
assert roman[4] == 'IV' // #2 List access
roman[8] = 'VIII' // #3 List expansion assert
assert roman.size() == 9
roman[18] = 'XVIII' // #3 List expansion assert
assert roman.size() == 19
assert roman[16] == null

// MAPS
def http = [
        100 : 'CONTINUE',
        200 : 'OK',
        400 : 'BAD REQUEST'
]
assert http[200] == 'OK'
http[500] = 'INTERNAL SERVER ERROR'
assert http.size() == 4

// RANGES
def x2 = 1..10
assert x2.contains(5)
assert x2.contains(15) == false
assert x2.size() == 10
assert x2.from == 1
assert x2.to == 10
assert x2.reverse() == 10..1

// CLOSURES
[1,2,3].each{ entry -> println entry }

[1,2,3].each{ println it }


def totalClinks = 0
def partyPeople = 100
1.upto(partyPeople) { guestNumber ->
    clinksWithGuest = guestNumber-1
    totalClinks += clinksWithGuest // #1 modifies outer scope
}
assert totalClinks == (partyPeople * (partyPeople-1)) / 2
println totalClinks

totalClinks = 0
partyPeople = 100
1.upto(partyPeople) {
    clinksWithGuest = it-1
    totalClinks += clinksWithGuest // #1 modifies outer scope
}
println totalClinks

// CTRL STRUCTURES
if (false) assert false // #1 'if' as one-liner

if (null)   // #2 Null is false
{           // #3 Blocks may start on new line
    assert false
} else {
    assert true
}

def i = 0 // #4 Classic 'while'
while (i < 10) { // #4
    i++ // #4
}       // #4
assert i == 10 // #4

def clinks = 0 // #5 'for' in 'range'
for (remainingGuests in 0..9) { // #5
    clinks += remainingGuests // #5
} // #5
assert clinks == (10*9)/2 // #5

def list = [0, 1, 2, 3] // #6 'for' in 'list'
for (j in list) { // #6
    assert j == list[j] // #6
} // #6

list.each() { item -> // #7 'each' method with a closure
    assert item == list[item] // #7
} // #7

switch(3) { // #8 Classic 'switch'
    case 1 : assert false; break // #8
    case 3 : assert true; break // #8
    default: assert false // #8
} // #8


// GDK
if ('Hello World!'.startsWith('Hello')) {
    println '"Hello World!" really does start with "Hello"!'
    // Code to execute if the string starts with 'Hello'
}
