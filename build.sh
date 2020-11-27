javac -cp "./src/main/java" -d build src/main/java/VectorMain.java
jar cmf manifest.mf VectorX.jar -C build . -C src/main resources
