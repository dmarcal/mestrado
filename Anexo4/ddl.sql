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
