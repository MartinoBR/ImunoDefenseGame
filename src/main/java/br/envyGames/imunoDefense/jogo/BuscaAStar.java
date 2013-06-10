package br.envyGames.imunoDefense.jogo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class BuscaAStar {
	private static CasaBusca atual;
	private static ArrayList<CasaBusca> lista_aberta;
	private static ArrayList<CasaBusca> lista_fechada;
	
	/*
	 * Realiza a Busca A* no <code>tabuleiro</code>, tendo seu inico a casa <code>inicio</code> e ponto final <code>fim</code>.
	 * @param <code>tabuleiro</code> - A matriz de inteiros na qual a busca se basear�.
	 * @param <code>inicio</code>    - Coordenadas da casa no <code>tabuleiro</code> na qual a busca come�ar�.
	 * @param <code>fim</code>       - Coordenadas da casa no <code>tabuleiro</code> na qual a busca buscar� alcan�ar.
	 * @return <code>ArrayList<Point></code> - Caminho composto de casa por casa de onde a Entidade dever� passar para alcan�ar a casa <code>fim</code>, n�o obtendo a casa <code>inicio</code> em seu corpo.
	 */
	public static ArrayList<Point> busca(Casa[][] tabuleiro, Point inicio, Point fim) {
		atual = null;
		lista_aberta = new ArrayList<CasaBusca>();
		lista_fechada = new ArrayList<CasaBusca>();
		
		lista_aberta.add(new CasaBusca(inicio, null, fim));
		
		boolean caminhoEncontrado = encontraCaminho(fim);
		
		if(!caminhoEncontrado)
			return null;
		else
			return obtemCaminho();
	}
	
	private static ArrayList<Point> obtemCaminho() {
		ArrayList<Point> caminho = new ArrayList<Point>();
		CasaBusca aux = atual;
		
		while(aux.getAntecessor() != null) {
			caminho.add(aux.getCasa());
			aux = aux.getAntecessor();
		}
		Collections.reverse(caminho);
		
		return caminho;
	}
	
	private static boolean encontraCaminho(Point fim) {
		while(!lista_aberta.isEmpty()) {
			atual = lista_aberta.remove(0);
			lista_fechada.add(atual);
			
			if(atual.getCasa().equals(fim)) {
				return true;
			} else {
				verificaCasa(new Point(-1, 0), fim);
				verificaCasa(new Point(0, -1), fim);
				verificaCasa(new Point(1, 0), fim);
				verificaCasa(new Point(0, 1), fim);
			}
		}
		
		return false;
	}
	
	private static void verificaCasa(Point soma, Point fim) {
		int x = atual.getCasa().x + soma.x;
		int y = atual.getCasa().y + soma.y;
		
		if(x < 0 || x >= Tabuleiro.getTabuleiroAtual().getWidth()  ||  y < 0 || y >= Tabuleiro.getTabuleiroAtual().getHeight()) {
			Point casaAux = new Point(x, y);
			
			if(Tabuleiro.getTabuleiroAtual().checaCasa(casaAux) != Casa.VAZIA)
				if(!existeNaListaFechada(casaAux))
					adicionaCasa(casaAux, fim);
		}
	}
	
	private static void adicionaCasa(Point casaAux, Point fim) {
		if(!existeNaListaAberta(casaAux))
			lista_aberta.add(new CasaBusca(casaAux, atual, fim));
		else {
			pegaIndexDaCasa(casaAux).setAntecessor(atual);
		}
	}
	
	private static CasaBusca pegaIndexDaCasa(Point casa) {
		for(CasaBusca n : lista_aberta) {
			if(n.mesmaCasa(casa))
				return n;
		}
		
		return null;
	}
	
	private static boolean existeNaListaFechada(Point vizinho) {
		boolean existenaFechada = false;
		for(CasaBusca n : lista_fechada) {
			if(n.mesmaCasa(vizinho))
				existenaFechada = true;
		}
		
		return existenaFechada;
	}
	
	private static boolean existeNaListaAberta(Point vizinho) {
		boolean existeNaAberta = false;
		for(CasaBusca n : lista_aberta) {
			if(n.mesmaCasa(vizinho))
				existeNaAberta = true;
		}
		
		return existeNaAberta;
	}
}