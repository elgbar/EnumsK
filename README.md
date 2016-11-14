# EnumsK

With this addon you can declare enums with skript.

## Syntax
>The only place where you can declare new enums (event)
```C#
Enums
```

---

>Declare a new enum (condition)
```C#
[create ][new ]enum %object%
```

---

>Add a value to an enum (condition)
```C#
[set value ]%object%( to |[]:[])%object%
```

---

>Get an enum value (expression)
```C#
value %object% (from|of) enum %object%
```
or
```C#
|%object%.%object%|
```
---

>Get all values for an enum (expression)
```C#
[all] values (from|of) enum %object%
```
or
```C#
|%object%.*|
```



## Example

```ruby
enums: #You can only declare enums under this event	#Do not name the enum in plural, follow java see http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
	create new enum "Sword": #Skript friendly syntax
		set value "OK" to iron sword #You can use all objects
		set value "COOL" to diamond sword named "&cCool sword bro" #Even objects with spesial features like name
	enum Capability: #A bit more minimalistic
		"STRING": "This is a string"
		455: 42
		PLAYER1: "kh498" parsed as offline player #Do not use a type as an enum name eg player, console, tool as skript parsed them as %player%, %console% or %tool%
		item1 : iron sword named "%|Capability.PLAYER1|%"

command /enum:
	trigger:
		set {_player} to |Capability.PLAYER1|
		set {_item} to |Capability.item1|
		give {_player} {_item} #This works, try it out by editing the Capability.PLAYER1 enum

		#!Note: You cannot do
		#give player |Capability.item1| #Gives no error, but doesnt give the player an item
		#!or
		#give |Capability.PLAYER1| {_item} #throws: 'single enum value can't have anything added to it'

		if {_item} is value item1 from enum Capability: #Works as expected
			send "true"
		#give player value item from enum Capability!
		#BUT you can use it inline like this:
		send "%all values from enum Capability%" #They are listed in the order they were declared
		send "%value ""OK"" from enum ""Sword""%"
		send "%value 455 from enum Capability%"

```

## Performance
My tests shows that **accessing enums are slightly faster** than accessing normal global variables. In the test you set a local variable to an enum or a global variable. This you do 100 000 times. To further smooth out the score I did the test 10 times each. This gives me:

**Enums takes an average of 536.27ms** <p>
**Global variable takes an average of 608.94ms (72.67ms more)**

I ran both commands ten times each and took the average of those two. I did not include the first timing as it was a great gap between its time and the other timings. I have a theory on why it is sush a gap. As the server just started up it did not have the variables loaded into the cache and therefore it took to get it as you have to access the disk.

In my [test skript](https://gist.github.com/kh498/6fe84df0f1a37de294147e456f721eb5) you can see the all the script I used, individual results from each test and the whole log for the test.

**But note that performance may vary**

## What's next?
* Better usability
 * This will mean you can do ```"text" is |enum.sameText|``` and ```give player |enum.item|```
