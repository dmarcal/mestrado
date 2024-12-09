CREATE TABLE IF NOT EXISTS public.editora
(
    id integer,
    nome character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.autor
(
    id integer,
    nome character varying NOT NULL,
    dt_nasc date NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.livro
(
    id integer,
    titulo character varying NOT NULL,
    ano_pub integer NOT NULL,
    preco numeric NOT NULL,
    editora_id integer NOT NULL,
    PRIMARY KEY (id)
);

-- Criar a tabela LivroAutor sem a restrição UNIQUE inicialmente
CREATE TABLE IF NOT EXISTS public.livro_autor
(
    id integer,
    livro_id integer,
    autor_id integer,
    papel "char" NOT NULL,
    PRIMARY KEY (id)
);

-- Adicionar as chaves estrangeiras
ALTER TABLE IF EXISTS public.livro_autor
    ADD CONSTRAINT "livro_autor_livroFk" FOREIGN KEY (livro_id) 
    REFERENCES public.livro (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.livro_autor
    ADD CONSTRAINT "livro_autor_autorFk" FOREIGN KEY (autor_id)
    REFERENCES public.autor (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

-- Adicionar a restrição UNIQUE após as chaves estrangeiras
ALTER TABLE IF EXISTS public.livro_autor
    ADD CONSTRAINT "livro_autorUk" UNIQUE (livro_id, autor_id);

ALTER TABLE public.livro
ADD CONSTRAINT "livro_editoraFk" FOREIGN KEY (editora_id)
REFERENCES public.editora (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;
-- Gerar 1.000 editoras
INSERT INTO public.editora (id, nome)
SELECT 
    i,
    'Editora ' || i
FROM generate_series(1, 1000) AS i;

-- Gerar 1.000 autores
INSERT INTO public.autor (id, nome, dt_nasc)
SELECT 
    i,
    'Autor ' || i,
    date('1950-01-01') + (random() * (date('2000-01-01') - date('1950-01-01'))) * interval '1 day'
FROM generate_series(1, 1000) AS i;

-- Gerar 1.000.000 de livros
INSERT INTO public.livro (id, titulo, ano_pub, preco, editora_id)
SELECT 
    i,
    'Livro ' || i,
    floor(random() * (2024 - 2020 + 1)) + 2020,
    round((random() * 100)::numeric, 2),
    floor(random() * 1000 + 1)  -- "editora_id" aleatório entre 1 e 1000
FROM generate_series(1, 1000000) AS i;

-- Gerar registros na tabela livro_autor
DO $$
DECLARE
    livro_id integer;
    autor_id integer;
    papel char;
    autores_adicionados integer[]; -- Array para armazenar os autores já adicionados ao livro
BEGIN
    FOR livro_id IN 1..1000000 LOOP
        autores_adicionados := array[]::integer[]; -- Inicializar o array para cada livro

        -- Autor principal
        autor_id := floor(random() * 1000 + 1);
        papel := 'P';
        INSERT INTO public.livro_autor (id, livro_id, autor_id, papel) 
        VALUES (nextval('livro_autor_id_seq'), livro_id, autor_id, papel);
        autores_adicionados := array_append(autores_adicionados, autor_id); -- Adicionar o autor ao array

        -- Coautores (0 a 3 coautores por livro)
        FOR i IN 1..floor(random() * 4) LOOP
            autor_id := floor(random() * 1000 + 1);
            
            -- Verificar se o autor já foi adicionado ao livro
            WHILE autor_id = ANY(autores_adicionados) LOOP
                autor_id := floor(random() * 1000 + 1);
            END LOOP;

            papel := 'C';
            INSERT INTO public.livro_autor (id, livro_id, autor_id, papel) 
            VALUES (nextval('livro_autor_id_seq'), livro_id, autor_id, papel);
            autores_adicionados := array_append(autores_adicionados, autor_id); -- Adicionar o autor ao array
        END LOOP;
    END LOOP;
END $$;

SELECT
  L.id AS livro_id,
  L.titulo,
  L.ano_pub,
  L.preco,
  L.editora_id,
  E.nome AS editora_nome,
  LA.id AS livro_autor_id,
  LA.papel,
  A.nome AS autor_nome,
  A.dt_nasc AS autor_dtnasc
FROM livro AS L
JOIN editora AS E
  ON L.editora_id = E.id
LEFT JOIN livro_autor AS LA
  ON L.id = LA.livro_id
LEFT JOIN autor AS A
  ON LA.autor_id = A.id;