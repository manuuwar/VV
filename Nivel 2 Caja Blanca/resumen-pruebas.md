# Pruebas de caja negra

Probar software sin conocer su implementación.

### Errores que nos permiten encontrar

- Funcionalidades no implementadas
- Problemas de usabilidad
- Problemas de rendimiento
- Errores de concurrencia
- Errores de inicialización y terminación

### Tipos de pruebas disponibles

- Prueba de valor límite: Valores que se encuentran en los límites de los valores de entrada. Se aplica cuando los valores no válidos son un problema
- Prueba de valores especiales: Valores que el desarrollador tiene razones para creer que podrían producir un error. Se aplica cuando hay un conocimiento del dominio y se conocen puntos típicos de fallo.
- Clases de equivalencia: Se utiliza información sobre el mapeo funcional (dominio - rango) para identificar casos de prueba. Se aplica cuando hay un conocimiento sobre el dominio de las variables de entrada
- Tablas de decisión: Se utiliza para identificar las posibles condiciones de entrada y las posibles acciones de salida. Se aplica cuando hay reglas de negocio complejas pero no son muchas (si no la tabla se agranda demasiado).

![Pruebas de caja negra](https://www.imperva.com/learn/wp-content/uploads/sites/13/2020/03/thumbnail_Black-box.jpg)

# Pruebas de caja blanca

Probar el software conducido por la estructura lógica del programa. Para su correcto uso se debe garantizar que todos los caminos posibles en la unidad de código que se está comprobando se ejecuten.

### Errores que nos permiten encontrar

- Errores lógicos en el código
- Código muerto

### Tipos de pruebas disponibles

- Path testing: Comprobamos todos los caminos independientes posibles del software y los atravesamos uno a uno buscando errores lógicos.
- Code-based test coverage metrics: No es una prueba como tal, es una métrica de cuánta parte del código se prueba, a menudo requerida por contrato, estándares, etc.
- Loop testing: Comprobar todos los bucles con dos tests cada uno:
  - Bucles anidados:
    1. Probar el bucle más interno
    2. Condensar el bucle en un solo nodo
    3. Ir desde el bucle más interno al más externo
  - Bucles concatenados: Realizar pruebas individuales por cada bucle
  - Bucles enlazados: Reescribir el código

![Pruebas de caja blanca](https://www.imperva.com/learn/wp-content/uploads/sites/13/2020/03/thumbnail_White-box-2.jpg.webp)
