DROP DATABASE IF EXISTS bibliotecaMYSQL;
CREATE DATABASE bibliotecaMYSQL;
USE bibliotecaMYSQL;

CREATE TABLE libros (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    autor VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    editorial VARCHAR(255) NOT NULL,
    paginas INT NOT NULL
);


INSERT INTO libros (autor, titulo, editorial, paginas) VALUES
('Jose', 'Harry Potter y la piedra filosofal', 'Bloomsbury', 223),
('Maria', '1984', 'Secker & Warburg', 328),
('Lucas', 'El Hobbit', 'HarperCollins', 310);

