
# Commandes
## Lancer application
```bash
#Linux & Mac
./gradlew run 
#Windows
gradlew.bat run
```

## Lancer les tests
```bash
#Linux & Mac
./gradlew test
#Windows
gradlew.bat test
```

## Création jar 
```bash
#Linux & Mac
./gradlew shadowJar
#Windows
gradlew.bat shadowJar
```

### Lancement jar
```bash
export JAVAFX_HOME=
java --module-path ${JAVAFX_HOME}/lib --add-modules=javafx.base,javafx.controls,javafx.fxml -jar codingweek-1.0-all.jar
```