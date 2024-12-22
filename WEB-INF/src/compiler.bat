rem compilation package connect
    javac -d ../classes connect/*.java

    rem compilation dao
    javac -d ../classes connect/dao/*.java
        rem compilation object
        javac -d ../classes connect/dao/object/temp/*.java
        javac -d ../classes connect/dao/object/finance/*.java
        javac -d ../classes connect/dao/object/etude/*.java
        javac -d ../classes connect/dao/object/evaluation/*.java
        javac -d ../classes connect/dao/object/personne/*.java
        rem javac -d ../classes connect/dao/object/temp/*.java

rem compilation package tools
    rem compilation reflect
    javac -d ../classes tools/reflect/*.java

    rem compilation lang
    javac -d ../classes tools/lang/*.java

    rem compilation util
    javac -d ../classes tools/util/*.java

rem compilation package main
    rem compilation object
        rem compilation temp
        javac -d ../classes main/object/temp/*.java        

        rem compilation etude
        javac -d ../classes main/object/etude/*.java

        rem compilation evaluation
        javac -d ../classes main/object/evaluation/*.java

        rem compilation finance
        javac -d ../classes main/object/etude/*.java

        rem compilation personne
        javac -d ../classes main/object/personne/*.java
    
    rem compilation swing

rem compilation Main
    javac -d ../classes Main.java