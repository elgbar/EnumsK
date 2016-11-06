# EnumsK

With this addon you can declare enums with skript. 

## Performance
My tests shows that accesing enums are just as fast (if not even sightly faster) than accessing normal global variables.

In my [test skript](https://gist.github.com/kh498/6fe84df0f1a37de294147e456f721eb5) I got these results from running it once:

For enums:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042714/5a50e676-a47f-11e6-88d7-e4d76cfff40f.png)

For variables:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042712/48eb86b6-a47f-11e6-97cd-e49e93bfc511.png)

**But note that performance may vary**

## Example

```ruby
enums: #You can only declare enums under this event 
	#Do not name the enum in plural, follow java see http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
	enum "Sword":
		"OK": iron sword named "&eOkay sword" #You can use all objects
		"COOL": diamond sword named "&cCool sword bro" with lore "&7Awe YEAH" #Even objects with spesial features like name and lore
	enum "Capability":
		"STRING": "This is a string"
		"NUMBER": 42
		"PLAYER": "kh498" parsed as player

command /enum:
	trigger:
		#As of 0.0.0-ALPHA you must set an enum to a variable before you can use it
		set {_player} to @ "Capability"."PLAYER"
		set {_sword} to @ "Sword"."COOL"
		give {_player} 1 of {_sword} #This works, try it out by editing the Capability.PLAYER enum
		
		#BUT you can use it inline like this:
		send "%@ ""Capability"".*%" #They are listed in the order they were declared
		send "%@ ""Sword"".""OK""%"
```

## Syntax
>The event
```C#
Enums
```

---

>Declare a new enum
```C#
Enum %string%
```

---

>Add a value to an enum
```C#
%string%[]:[]%object%
```

---

>Get an enum value
```C#
(@|E[num]) %string%.%string%
```

---

>Get all values for an enum
```C#
(@|E[num]) %string%.*
```
