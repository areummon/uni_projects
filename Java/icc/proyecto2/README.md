Introducción a Ciencias de la Computación
=========================================

Proyecto 2: Ordenador Lexicográfico
-------------------------------------------------------
Para compilar e instalar el programa:
```
$ mvn compile # compila el codigo 
...
$ mvn install # genera el archivo proyecto2.jar en el subdirectorio target
```

Para ejecutar el proyecto:
```
$ java -jar target/proyecto2.jar [-r|-o] <identificador> <archivo/s>
...
$ cat <archivo/s> | java -jar target/proyecto2.jar [-r|-o] <identificador> 
```
Las banderas y los archivos se pueden pasar al programa en el orden que el usuario quiera,
con la excepción de que la bandera **-o** que debe ser seguida de un identificador.

Las banderas y opciones son las siguientes:

* `-r` La reversa del texto ordenado
* `-o` Guarda el texto del programa con un identificador dado
* `identificador` El identificador con el que se guardara el archivo con la bandera -o
* `archivo/s` Los archivos que recibe el programa y que ordenara.



