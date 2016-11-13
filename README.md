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
My tests shows that accesing enums are just as fast (if not even sightly faster) than accessing normal global variables.

In my [test skript](https://gist.github.com/kh498/6fe84df0f1a37de294147e456f721eb5) I got these results from running it once:

For enums:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042714/5a50e676-a47f-11e6-88d7-e4d76cfff40f.png)

For variables:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042712/48eb86b6-a47f-11e6-97cd-e49e93bfc511.png)

**But note that performance may vary**

## What's next?
* Better usability
 * This will mean you can do ```"text" is |enum.sameText|``` and ```give player |enum.item|```
