Mini Project 2 Practical Assessment
------------------------------------------------------
In this practical you are required to write a simple vector graphics drawing program. At a minimum your
program should support the following feature requirements.

### Compiling and running the program
------------------------------------------------------

- Compile the program (Linux/Windows):  
    ``` javac -cp "./src/main/java" -d build src/main/java/VectorMain.java```
- Create The JAR file:<br/> 
    ```jar cmf manifest.mf VectorX.jar -C build . -C src\main resources```
- Run the Application:<br/>
     ```java -jar VectorX.jar```

  
### Example run
------------------------------------------------------
```shell script
$ javac -cp "./src/main/java" -d build src/main/java/VectorMain.java
$ jar cmf manifest.mf VectorX.jar -C build . -C src\main resources
$ java -jar VectorX.jar

```

