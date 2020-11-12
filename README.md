Mini Project 2 Practical Assessment
------------------------------------------------------
In this practical you are required to write a simple vector graphics drawing program. At a minimum your
program should support the following feature requirements.

### Running the tests (without stacscheck)
- Ensure that the libraries in the lib folder has executable permissions on linux or unix-like
  system by adding the 'execute' flag:  
  ```~/code/ $ chmod +x ./lib/*.jar```

- Compile the program (Linux):  
    ```javac -cp "./lib/*:./src/" -d build src/FileShareMain.java```
- Compile the program (Windows):  
    ```javac -cp "./lib/*;./src/" -d build src/FileShareMain.java```
- Create The JAR file (Include the build folder and any resources including config.resources)
    ```jar cmf manifest.mf FileShareMain.jar -C build . -C src resources```
- Run the Application with the Jackson JARs (Windows)
    ```java -cp ".\lib\jackson-annotations-2.11.1.jar;.\lib\jackson-core-2.11.1.jar;.\lib\jackson-databind-2.11.1.jar;.\FileShareMain.jar" FileShareMain```
- Run the Application with the Jackson JARs (Linux)
    ```java -cp "./lib/jackson-annotations-2.11.1.jar:./lib/jackson-core-2.11.1.jar:./lib/jackson-databind-2.11.1.jar:./FileShareMain.jar" FileShareMain```

  
### Example run
```shell script
```

