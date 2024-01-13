
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
export JAVAFX_HOME=your/path
cd jar
java --module-path ${JAVAFX_HOME}/lib --add-modules=javafx.base,javafx.controls,javafx.fxml --add-opens java.base/java.lang=ALL-UNNAMED -jar codingweek-1.0-all.jar 
```



### Annex
Vous avez un fichier Annex contenant:
- **class** diagramme de classe du projet
- **maquette** maquette initiale du projet
- **Taches_Figma** notre diagramme de Gantt des tâches à effectuer
- **Entity** contient le schéma relationnel de la base de données


### Jar
Le jar du projet qui peut être lancé avec les commandes fournies au-dessus

Vous avez aussi au root un fichier .sqlite qui contient une base de données avec des données fournies, pour l'utiliser déplacer celui-ci dans votre `user.HOME` `codingweek-11`



### Vidéo
Vous pourrez trouver notre vidéo sur Youtube à l'adresse suivante : 
https://www.youtube.com/watch?v=nSRcw8Cr52U

Les menus déroulants ne sont pas affichés dans la vidéo, mais ils sont bien présent sur l'application !
