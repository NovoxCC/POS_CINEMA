# # POS_CINEMA
systema POS para una sala de cine.

## Detalles del proyecto
Proyecto # 11:
Se quiere construir una aplicación que permita administrar una sala de cine. Esta aplicación permite
hacer reservas y registrar sus pagos.
La sala de cine tiene 220 sillas. De cada silla se conoce:
- Fila a la que pertenece, representada por un valor entre A y K.
- Número de la silla, valor entre 1 y 20.
- Tipo. Puede ser general o preferencial.
- Estado de la silla. Puede ser disponible, reservada o vendida.
El costo de boleta se determina según el tipo de la silla, y esta a su vez se determina según su número,
de la siguiente manera:
- General: sillas en las filas A – H. Costo por boleta de $8,000.
- Preferencial: sillas en las filas I – K. Costo por boleta de $11,000.
Para poder adquirir una boleta, el cliente debe primero hacer una reserva. Cada cliente puede reservar
hasta 8 sillas. De cada reserva se conoce:
- Cédula de la persona que hizo la reserva.
- Sillas que hacen parte de la reserva.
- Estado de pago de la reserva.
El cliente puede pagar sus reservas en efectivo o utilizando la tarjeta CINEMAS. Esta tarjeta le otorga a
su dueño un descuento del 10% en sus boletas. De cada tarjeta se conoce:
- Cédula del dueño de la tarjeta. No pueden existir dos tarjetas con la misma cédula.
- Saldo de la tarjeta: Cantidad de dinero disponible para pagar reservas.
Cuando se adquiere una tarjeta, el cliente debe cargar la tarjeta con un valor inicial de $70,000. Cada
tarjeta puede ser recargada una cantidad ilimitada de veces, sin embargo, cada recarga se debe hacer
por un monto de $50,000.
La aplicación debe permitir:
1. Crear una nueva tarjeta.
2. Recargar una tarjeta.
3. Crear una reserva.
4. Eliminar la reserva actual.
5. Pagar una reserva en efectivo.
6. Pagar la reserva con tarjeta CINEMAS.
7. Visualizar las sillas del cine.
8. Visualizar el dinero en caja.

## Tecnologias que se usaron
Se eligio usar  java con la api javaFX en maven  con una base de datos en mysql en local 

Para mas detalles revisar el documento 
