package br.envyGames.imunoDefense.jogo;

import java.awt.Point;

import br.envyGames.imunoDefense.motor.Cenario;
import br.envyGames.imunoDefense.motor.Entidade;

import s3t.gameControl.system.GameSystem;

/*
 * Classe abstrata base dos inimigos
 */
public abstract class Inimigo extends Entidade {
	private int vida = 2;
	private int forca = 1;
	private float velocidadeNatural = 1;
	private float lentidao = 0;
	
	/*
	 * Construtor
	 * @param <code>name</code> - Nome do inimigo do qual ser� usado para encontra-lo na EntityCollection.
	 * @param <code>xy</code>   - Coordenadas "(x, y)" iniciais. 
	 */
	public Inimigo(String name, Point xy, Cenario cenario) {
		super(name, xy.x, xy.y, cenario);
		GameSystem.getEntityCollection().addEntity(this);
	}
	
	// Getters & Setters
	public int getVida() { return vida; }
	public int getForca() { return forca; }
	public float getVelocidade() { return velocidadeNatural * lentidao; }
	
	public void setForca(int dano) { forca = dano; }
	public void setVelocidadeNormal(int vel) { velocidadeNatural = vel; }
	
	/*
	 * Aplica efeito de lentid�o nesta unidade
	 * @param <code>porcentagem</code> - Quantos porcentos ser�o removidos (0~1)
	 */
	public void addSlow(float porcentagem) {
		if(lentidao < (1 - porcentagem))
			lentidao = 1 - porcentagem;
	}
	public void removeSlow() {
		lentidao = 0;
	}
	
	public void recebeDano(int dano) {
		vida -= dano;
		
		isDead();
	}
	
	private void isDead() {
		if(vida <= 0)
			mataInimigo();
	}
	
	public void mataInimigo() {
		GameSystem.getEntityCollection().getEntityByName(getName());
	}
}
