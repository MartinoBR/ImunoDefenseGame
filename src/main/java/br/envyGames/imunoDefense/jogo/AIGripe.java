package br.envyGames.imunoDefense.jogo;

import java.awt.Point;

import s3t.gameEntities.AIAction;
import s3t.gameEntities.Entity;
import s3t.gameEntities.IAMessage;

public class AIGripe extends AIAction {
	private Estado estado = Estado.PARADO;
	private Point proxCasa = null;
	private Torre alvo;
	
	@Override
	public void doAction(Entity entidade) {
		Inimigo inimigo = (Inimigo)entidade;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(estado.toString());
		
		if(estado == Estado.PARADO) {
			comecarAndar(inimigo);
		} else if(estado == Estado.ANDANDO) {
			verificarCaminho(inimigo);
			if(estado == Estado.ANDANDO)
				andar(inimigo);
		} else {
			atacar(inimigo);
		}
	}
	
	private void atacar(Inimigo inimigo) {
		alvo.receberDano(inimigo.getForca());

		if(alvo.getVida() <= 0) {
			Tabuleiro.getTabuleiroAtual().setCasa(alvo.getCasaAtual(), null);
			comecarAndar(inimigo);
		}
	}

	private void andar(Inimigo inimigo) {
		inimigo.doMove(inimigo.getVelocidade(), 0);
		proxCasa.x++;
	}

	private void comecarAndar(Inimigo inimigo) {
		if(proxCasa == null) {
			proxCasa = inimigo.getCasaAtual();
			proxCasa.x++;
		} else {
			proxCasa.x++;
		}

		verificarCaminho(inimigo);
	}

	private void verificarCaminho(Inimigo inimigo) {
		if(proxCasa.x == Tabuleiro.getTabuleiroAtual().getWidth() - 1) {
			alvo = ((JogoCenario)inimigo.getScenario()).getCoracao();
			estado = Estado.ATACANDO;
		} else if(Tabuleiro.getTabuleiroAtual().isTorre(proxCasa)) {
			alvo = (Torre)Tabuleiro.getTabuleiroAtual().getCasa(proxCasa);
			estado = Estado.ATACANDO;
		} else {
			estado = Estado.ANDANDO;
		}
	}

	@Override
	public void receiveMessage(IAMessage msg) {}
}
