## PDE_Consumo_Energetico

Link al repositorio: https://github.com/Oyupa/PDE_Consumo_Energetico.git

Participantes: Miriam Blanco Ponce, Adrián Thierry Puyo Olías, Sira González-Madroño García y Sonia Tejero Recio

## Proyecto 

En el proyecto se nos pedía realizar una aplicacion que se encargue de monitorear el consumo de un hogar, la aplicación debe estar compuesta por: 

  -> Pantalla de inicio con una bienvenida y unos botones que permiten al usuario navegar entre las distintas pantallas.
  
  -> Pantalla de gráficos que muestra el consumo del hogar.
  
  -> Pantalla para actualizar datos sobre el consumo.
  
  -> Pantalla para mostrar recomendaciones en el consumo.

## Pantalla inicio 

En esta activity se muestra un mensaje de bienvenida al usuario y contiene dos botones: 

  -> Botón ver consumo: este botón permite al usuario navegar a la activity que mostrará un gráfico con los datos del consumo del hogar.

  -> Botón actualizar datos: este botón permite al usuario navegar a la activity que permite al usuario modificar o añadir datos a la base.

Por otra parte, al iniciar el programa nos encontramos un splash screen que hace que simula una carga de la aplicación.

## Pantalla gráficos 

En esta activity se muestra un gráfico con el consumo de las semanas del hogar, esta activity esta compuesta por dos botones: 

  -> Botón volver al menu principal: este botón permite al usuario volver al menu principal y poder seguir navegandolos por la aplicación libremente.

  -> Botón ver recomendaciones: este botón permite al usuario navegar a la pantalla de recomendaciones donde se mostrarán las mismas.

## Pantalla actualización

En esta activity se le muestra al usuario una pantalla donde puede introducir la semana del consumo y el dinero que se ha gastado en total en esa semana (gasto de luz, gasto de agua, etc).

La activity esta compuesta por los TextEdits que permiten al usuario modificar un texto y añadir los valores que se necesiten. Además de esto contiene dos botones: 

  -> Botón actualizar: guardará los datos que el usuario ha introducido en la aplicación a la base de datos donde se guardan todos los datos del consumo enegético del hogar. Dependiendo si la operación haya sido exitosa se mostrará un mensaje u otro.
  
  -> Botón cancelar: este botón cancela la operación del usuario y se le devuelve a la pantalla principal para que siga operando libremente.

## Pantalla recomendaciones

En esta actvity se le muestra al usuario una pantalla donde puede ver si su consumo ha mejorado o empeorado comparando una semana con otra. A parte de esto el usuario encuentra un botón: 

  -> Botón volver: este botón permite al usuario volver a la pantalla donde se muestran los graficos de consumo.
