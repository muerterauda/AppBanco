/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  user
 * Created: 09-abr-2018
 */
DROP DATABASE IF EXISTS banco;

CREATE DATABASE banco;

CREATE USER IF NOT EXISTS 'AppBanco'@'localhost' IDENTIFIED BY 'user';

GRANT ALL ON banco.* TO 'AppBanco'@'localhost';

USE banco;

CREATE TABLE CLIENTE (
    dni VARCHAR(9) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(75) NOT NULL,
    direccion VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL,
    telefono VARCHAR(75) NOT NULL,
    contrasenya VARCHAR(75) NOT NULL,
    PRIMARY KEY(dni)
) ENGINE=InnoDB;

CREATE TABLE CUENTA(
    numero    INT NOT NULL AUTO_INCREMENT,
    numero_str VARCHAR(24),
    cliente VARCHAR(9) NOT NULL,
    FOREIGN KEY(cliente) REFERENCES CLIENTE(dni),
    PRIMARY KEY(numero)
)   ENGINE=InnoDB;

CREATE TABLE EMPLEADO(
    id INT NOT NULL,
    contrasenya VARCHAR(75) NOT NULL,
    PRIMARY KEY(id)
)   ENGINE=InnoDB;

CREATE TABLE OPERACION(
    id INT NOT NULL AUTO_INCREMENT,
    empleado INT,
    tipo VARCHAR(30) NOT NULL,
    FOREIGN KEY(empleado) REFERENCES EMPLEADO(id),
    PRIMARY KEY(id)
)   ENGINE=InnoDB;


CREATE TABLE MOVIMIENTO(
    id INT NOT NULL AUTO_INCREMENT,
    cuenta INT NOT NULL,
    operacion INT NOT NULL,
    concepto VARCHAR(200) NOT NULL,
    fecha DATETIME NOT NULL,
    importe DOUBLE NOT NULL,
    saldo DOUBLE NOT NULL,
    FOREIGN KEY(operacion) REFERENCES OPERACION(id),
    FOREIGN KEY(cuenta) REFERENCES CUENTA(numero),
    PRIMARY KEY(id)
)   ENGINE=InnoDB;

/* Datos por defecto */

INSERT INTO CLIENTE VALUES
('12345678A', 'cliente', 'clienteApellido', 'C/ Cliente', 'cliente@mail.com', '123456789', 'cliente'),
('12345678B', 'cliente2', 'clienteApellido2', 'C/ Cliente2', 'cliente2@mail.com', '123456789', 'cliente');

INSERT INTO EMPLEADO VALUES
(1, 'empleado');

INSERT INTO CUENTA VALUES
(1, 'ES0012341234990000000001', '12345678A'),
(2, 'ES0012341234990000000002', '12345678B');

INSERT INTO OPERACION(empleado,tipo) VALUES
(1, 'INGRESO'),
(1, 'INGRESO'),
(1, 'REINTEGRO'),
(1, 'INGRESO'),
(1, 'REINTEGRO'),
(NULL, 'TRANSPASO');

INSERT INTO MOVIMIENTO(cuenta,operacion,concepto,fecha,importe,saldo) VALUES
(1, 1, 'Ingreso inicial', DATE_SUB(NOW(), INTERVAL 5 HOUR), 1000, 0),
(1, 2, 'Ingreso por que si', DATE_SUB(NOW(), INTERVAL 4 HOUR), 500, 1500),
(1, 3, 'Capricho', DATE_SUB(NOW(), INTERVAL 3 HOUR), -750, 750),
(2, 4, 'Ingreso inicial', DATE_SUB(NOW(),INTERVAL 5 HOUR), 2000, 2000),
(2, 5, 'Capricho 2', DATE_SUB(NOW(), INTERVAL 4 HOUR), -750, 1250),
(1, 6, 'Transpaso a ES0012341234990000000002', DATE_SUB(NOW(), INTERVAL 2 HOUR), -200, 550),
(2, 6, 'Transpaso a ES0012341234990000000001', DATE_SUB(NOW(), INTERVAL 2 HOUR), 200, 1450);