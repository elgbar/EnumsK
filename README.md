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

---

>Get all values for an enum (expression)
```C#
[all] values (from|of) enum %object%
```

## Example

```ruby
enums: #You can only declare enums under this event
    #Do not name the enum in plural, follow java see http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
    create new enum "Sword": #Skript friendly syntax
        set value "OK" to iron sword named "&eOkay sword" #You can use all objects
        set value "COOL" to diamond sword named "&cCool sword bro" with lore "&7Awe YEAH" #Even objects with spesial features like name and lore
    enum "Capability": #A bit more minimalistic
        "STRING": "This is a string"
        455: 42
        "PLAYER": "kh498" parsed as player

command /enum:
    trigger:
        #As of 0.0.0-ALPHA you must set an enum to a variable before you can use it
        set {_player} to value "PLAYER" from enum "Capability"
        set {_sword} to value "COOL" from enum "Sword"
        give {_player} 1 of {_sword} #This works, try it out by editing the Capability.PLAYER enum

        #BUT you can use it inline like this:
        send "%all values from enum ""Capability""%" #They are listed in the order they were declared
        send "%value ""OK"" from enum ""Sword""%"
        send "%value 455 from enum ""Capability""%"

```

## Performance
My tests shows that accesing enums are just as fast (if not even sightly faster) than accessing normal global variables.

In my [test skript](https://gist.github.com/kh498/6fe84df0f1a37de294147e456f721eb5) I got these results from running it once:

For enums:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042714/5a50e676-a47f-11e6-88d7-e4d76cfff40f.png)

For variables:<br>
![image](https://cloud.githubusercontent.com/assets/1556738/20042712/48eb86b6-a47f-11e6-97cd-e49e93bfc511.png)

**But note that performance may vary**
