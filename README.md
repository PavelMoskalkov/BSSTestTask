### How to compile
1. Скомпилируйте ваш Java-код выполнив компиляцию в файлы формата .class javac :
```
javac ./src/main/java/org/example/revolver/Main.java
```
2. Создайте JAR-файл, находясь в каталоге с вашим скомпилированным файлом класса:
```
jar cvfe Main.jar org.example.revolver.Main -C ./src/main/java/ .
```
3. Запустите JAR-файл:
```
java -jar Main.jar
```