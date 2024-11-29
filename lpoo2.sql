create database lpoo2;
use lpoo2;
create table cliente(
    cpf varchar(11) primary key,
    nome varchar(100) not null,
    sobrenome varchar(100) not null,
    rg varchar(20),
    endereco varchar(100)
);
create table conta(
	numero integer primary key auto_increment,
    cpf varchar(11),
    tipo char(1),
    saldo float,
    limite float,
    deposito_minimo float, 
    montante_minimo float,
    FOREIGN KEY (cpf) references cliente(cpf) ON DELETE CASCADE
);
