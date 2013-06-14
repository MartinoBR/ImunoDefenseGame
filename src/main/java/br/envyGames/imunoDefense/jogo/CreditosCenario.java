package br.envyGames.imunoDefense.jogo;

import java.awt.event.MouseEvent;

import br.envyGames.imunoDefense.motor.Cenario;
import br.envyGames.imunoDefense.motor.CenarioItem;
import br.envyGames.imunoDefense.motor.CenarioLayer;
import br.envyGames.imunoDefense.motor.Imagem;
import br.envyGames.imunoDefense.motor.ResourceManager;

public class CreditosCenario extends Cenario {
	
	
	public CreditosCenario(int largura, int altura) {
		super("CreditosCenario", "Creditos", largura, altura);
		
		configurarCenario();
	}
	
	@Override
	public void run() {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int gridX = (int) (e.getX() / 32);
		int gridY = (int) (e.getY() / 32);	
		
		if (isVoltarButton(gridX, gridY))
			VoltarButtonClicked();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int gridX = (int) (e.getX() / 32);
		int gridY = (int) (e.getY() / 32);	
		
		if (isVoltarButton(gridX, gridY)) {
			this.getLayerPorID("creditos").getItemPorNome("telaCreditos").setVisible(false);
			this.getLayerPorID("creditos").getItemPorNome("telaCreditosOver").setVisible(true);			
		}
		else {
			this.getLayerPorID("creditos").getItemPorNome("telaCreditos").setVisible(true);
			this.getLayerPorID("creditos").getItemPorNome("telaCreditosOver").setVisible(false);
		}
	}
	
	private void configurarCenario() {
		int x = 0;
		int y = 0;
		
		CenarioLayer creditos = CenarioLayer.criarSolidLayer("creditos");
		
		Imagem telaCreditos = ResourceManager.getImagem("/imagens/Creditos.jpg");
		Imagem telaCreditosOver = ResourceManager.getImagem("/imagens/Creditos1.jpg");
		
		CenarioItem itemTelaCreditos = new CenarioItem("telaCreditos", telaCreditos, x, y);
		creditos.adicionarItem(itemTelaCreditos);
		creditos.getItemPorNome("telaCreditos").setVisible(true);
		
		CenarioItem itemTelaCreditos2 = new CenarioItem("telaCreditosOver", telaCreditosOver, x, y);
		creditos.adicionarItem(itemTelaCreditos2);
		creditos.getItemPorNome("telaCreditosOver").setVisible(false);
		
		creditos.adicionarItem(itemTelaCreditos);
		
		adicionarLayer(creditos);
	}
	
	private boolean isVoltarButton(int x, int y) {
		return x >= 0 && x <= 1 && (y == 15 || y == 16);
	}
	
	private void VoltarButtonClicked() {
		carregarNovoCenario("MenuCenario");
	}
}
