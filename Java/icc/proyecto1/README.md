Introducción a Ciencias de la Computación
=========================================

Proyecto 1: Base de datos sobre grupos de Kpop
-------------------------------------------------------
Para compilar e instalar el programa:
```
$ mvn compile 
...
$ mvn install
```

Para ejecutar el proyecto:
```
$ java -jar target/proyecto1.jar -g kgrupos.db # guarda la base de datos
...
$ java -jar target/proyecto1.jar -c kgrupos.db # carga la base de datos
```

Los campos de los grupos de kpop son los siguientes:

* `Nombre` El nombre del grupo de kpop
* `Año debut` El año debut del grupo de kpop
* `Generación` La generación del grupo de kpop
* `Tipo` El tipo del grupo de kpop(boy group/girl group)
* `Miembros` El número de miembros del grupo de kpop
* `Género musical` El género musical principal del grupo de kpop
* `Discografica` La compañía discográfica del grupo de kpop
* `Canción con más me gusta en Melon(멜론)` La canción con más me gusta en [Melon](https://www.melon.com/index.htm) del grupo de kpop 
* `Mejor calificación de album o EP en RYM` La mejor calificación de algúno de sus albumes o EP en [Rate Your Music](https://rateyourmusic.com/)




