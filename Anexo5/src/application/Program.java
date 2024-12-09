package application;

import java.util.List;

import model.dao.interfaces.DaoFactory;
import model.dao.interfaces.LivroAutorDaoI;
import model.entities.LivroAutor;
import model.dao.interfaces.LivroDaoI;
import model.entities.Livro;
import model.dao.interfaces.AutorDaoI;
import model.entities.Autor;
import model.dao.interfaces.EditoraDaoI;
import model.entities.Editora;

public class Program {

	public static void main(String[] args) {
		ExecutarEditora();
	}
	private static void ExecutarLivroAutor(){
		for (int i = 1; i <= 20; i++) {
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
			long startTime = System.nanoTime();
			// Obter o uso de memória antes da execução
			long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			//Scanner sc = new Scanner(System.in);
			System.out.println("\n=== TEST " + i + ": LivroAutor list =====");
			LivroAutorDaoI livroAutorDao = DaoFactory.createLivroAutorDao();
			LivroAutor livroAutor = new LivroAutor();
			List<LivroAutor> list = livroAutorDao.listAll(livroAutor);
			//for (Seller obj : list) {
			//	System.out.println(obj);
			//}
			// Obter o tempo depois da execução
			long endTime = System.nanoTime();
			// Obter o uso de memória após a execução
			long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			// Calcular a diferença de tempo de execução
			long elapsedTime = endTime - startTime;
			// Calcular a diferença de uso de memória
			long memoryUsed = memoryAfter - memoryBefore;
			System.out.println("Elapsed time: " + (elapsedTime / 1000000) + " milliseconds");
			System.out.println("Memory used: " + memoryUsed + " bytes");
			// Limpar referências antes de medir o consumo de memória
			livroAutorDao=null;
			livroAutor=null;
			list=null;
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
		}
	}

	private static void ExecutarLivro(){
		for (int i = 1; i <= 20; i++) {
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
			long startTime = System.nanoTime();
			// Obter o uso de memória antes da execução
			long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			//Scanner sc = new Scanner(System.in);
			System.out.println("\n=== TEST " + i + ": Livro list =====");
			LivroDaoI livroDao = DaoFactory.createLivroDao();
			List<Livro> list = livroDao.findAll();
			//for (Seller obj : list) {
			//	System.out.println(obj);
			//}
			// Obter o tempo depois da execução
			long endTime = System.nanoTime();
			// Obter o uso de memória após a execução
			long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			// Calcular a diferença de tempo de execução
			long elapsedTime = endTime - startTime;
			// Calcular a diferença de uso de memória
			long memoryUsed = memoryAfter - memoryBefore;
			System.out.println("Elapsed time: " + (elapsedTime / 1000000) + " milliseconds");
			System.out.println("Memory used: " + memoryUsed + " bytes");
			// Limpar referências antes de medir o consumo de memória
			livroDao=null;
			list=null;
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
		}
	}

	private static void ExecutarAutor(){
		for (int i = 1; i <= 20; i++) {
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
			long startTime = System.nanoTime();
			// Obter o uso de memória antes da execução
			long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			//Scanner sc = new Scanner(System.in);
			System.out.println("\n=== TEST " + i + ": Autor list =====");
			AutorDaoI autorDao = DaoFactory.createAutorDao();
			List<Autor> list = autorDao.findAll();
			//for (Seller obj : list) {
			//	System.out.println(obj);
			//}
			// Obter o tempo depois da execução
			long endTime = System.nanoTime();
			// Obter o uso de memória após a execução
			long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			// Calcular a diferença de tempo de execução
			long elapsedTime = endTime - startTime;
			// Calcular a diferença de uso de memória
			long memoryUsed = memoryAfter - memoryBefore;
			System.out.println("Elapsed time: " + (elapsedTime / 1000000) + " milliseconds");
			System.out.println("Memory used: " + memoryUsed + " bytes");
			// Limpar referências antes de medir o consumo de memória
			autorDao=null;
			list=null;
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
		}
	}

	private static void ExecutarEditora(){
		for (int i = 1; i <= 20; i++) {
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
			long startTime = System.nanoTime();
			// Obter o uso de memória antes da execução
			long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			//Scanner sc = new Scanner(System.in);
			System.out.println("\n=== TEST " + i + ": Editora list =====");
			EditoraDaoI editoraDao = DaoFactory.createEditoraDao();
			List<Editora> list = editoraDao.findAll();
			//for (Seller obj : list) {
			//	System.out.println(obj);
			//}
			// Obter o tempo depois da execução
			long endTime = System.nanoTime();
			// Obter o uso de memória após a execução
			long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			// Calcular a diferença de tempo de execução
			long elapsedTime = endTime - startTime;
			// Calcular a diferença de uso de memória
			long memoryUsed = memoryAfter - memoryBefore;
			System.out.println("Elapsed time: " + (elapsedTime / 1000000) + " milliseconds");
			System.out.println("Memory used: " + memoryUsed + " bytes");
			// Limpar referências antes de medir o consumo de memória
			editoraDao=null;
			list=null;
			// Forçar a execução do garbage collector para liberar memória não utilizada
			System.gc();
		}
	}

}
