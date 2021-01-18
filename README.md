# Statistics-app

Statistic app is a demo spring app for handling files from a specific path

## Installation
it is a maven app you can build with the following

```
mvn clean package
```

## Usage

The first argument is the path that the application checks for new files
```
 java -jar file-statistics-app-0.0.1-SNAPSHOT.jar /Users/dimitriskasiaras/kasi

```

## TODO
I have to add unit tests
I had a problem with my compiler because in my first version I used the com.sun.tools.javac.util.Pair; (I had different jdk gor intelliJ and My System)
So then I could not build the project with maven and I had to replace it with the import org.apache.commons.lang3.tuple.Pair;

I lost some time there, so I did not have the time to complete the unit tests and some refactoring 
