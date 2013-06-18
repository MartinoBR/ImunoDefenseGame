/*
 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.envyGames.imunoDefense.jogo.ia;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import br.envyGames.imunoDefense.jogo.entidade.FormaDeVidaColecao;

public class BuscaAStar {
	private CasaBusca atual;
	private ArrayList<CasaBusca> lista_aberta;
	private ArrayList<CasaBusca> lista_fechada;
	private FormaDeVidaColecao[][] source;
	
	/*
	 * Realiza a Busca A* no <code>tabuleiro</code>, tendo seu inico a casa <code>inicio</code> e ponto final <code>fim</code>.
	 * @param <code>tabuleiro</code> - A matriz de inteiros na qual a busca se basear�.
	 * @param <code>inicio</code>    - Coordenadas da casa no <code>tabuleiro</code> na qual a busca come�ar�.
	 * @param <code>fim</code>       - Coordenadas da casa no <code>tabuleiro</code> na qual a busca buscar� alcan�ar.
	 * @return <code>ArrayList<Point></code> - Caminho composto de casa por casa de onde a Entidade dever� passar para alcan�ar a casa <code>fim</code>, n�o obtendo a casa <code>inicio</code> em seu corpo.
	 */
	public ArrayList<Point> busca(FormaDeVidaColecao[][] source, Point inicio, Point fim) {
		this.source = source;
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
	
	private ArrayList<Point> obtemCaminho() {
		ArrayList<Point> caminho = new ArrayList<Point>();
		CasaBusca aux = atual;
		
		while(aux.getAntecessor() != null) {
			caminho.add(aux.getCasa());
			aux = aux.getAntecessor();
		}
		Collections.reverse(caminho);
		
		return caminho;
	}
	
	private boolean encontraCaminho(Point fim) {
		while(!lista_aberta.isEmpty()) {
			atual = lista_aberta.remove(0);
			lista_fechada.add(atual);
			
			if(atual.getCasa().x == fim.x) {
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
	
	private void verificaCasa(Point soma, Point fim) {
		int x = atual.getCasa().x + soma.x;
		int y = atual.getCasa().y + soma.y;
		
		if(x >= 0 && x < source[0].length &&  y >= 0 && y < source.length) {
			Point casaAux = new Point(x, y);
			
		if (isCasaVazia(casaAux) || hasInimigo(casaAux))
				if(!existeNaListaFechada(casaAux))
					adicionaCasa(casaAux, fim);
		}
	}
	
	private boolean isCasaVazia(Point casa) {
		return source[casa.y][casa.x].isVazia();
	}
	
	private boolean hasInimigo(Point casa) {
		return source[casa.y][casa.x].hasInimigo();
	}
	
	private void adicionaCasa(Point casaAux, Point fim) {
		if(!existeNaListaAberta(casaAux))
			lista_aberta.add(new CasaBusca(casaAux, atual, fim));
		else {
			pegaIndexDaCasa(casaAux).setAntecessor(atual);
		}
	}
	
	private CasaBusca pegaIndexDaCasa(Point casa) {
		for(CasaBusca n : lista_aberta) {
			if(n.mesmaCasa(casa))
				return n;
		}
		
		return null;
	}
	
	private boolean existeNaListaFechada(Point vizinho) {
		boolean existenaFechada = false;
		for(CasaBusca n : lista_fechada) {
			if(n.mesmaCasa(vizinho))
				existenaFechada = true;
		}
		
		return existenaFechada;
	}
	
	private boolean existeNaListaAberta(Point vizinho) {
		boolean existeNaAberta = false;
		for(CasaBusca n : lista_aberta) {
			if(n.mesmaCasa(vizinho))
				existeNaAberta = true;
		}
		
		return existeNaAberta;
	}
}