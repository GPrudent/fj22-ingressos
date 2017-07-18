package br.com.caelum.ingresso.validacao;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;


public class GerenciadorDeSessaoTest {
	@Test	
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		Filme filme = new Filme();
		filme.setDuracao(120);
		
		Sala sala = new Sala("");
		
		LocalTime horario = LocalTime.now();
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		Sessao sessao = new Sessao(horario,filme,sala);
		GerenciadorDeSessao gds = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(gds.cabe(sessao));
		
	}
	
	@Test	
	public void garanteQueNaoDevePermitirSessaosTerminandoDentroDoHorarioDeUmaSessaoJaExistente(){
		Filme filme = new Filme();
		filme.setDuracao(120);
		
		Sala sala = new Sala("");
		
		LocalTime horario = LocalTime.now();
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		Sessao sessao = new Sessao(horario.plusHours(1),filme,sala);
		GerenciadorDeSessao gds = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(gds.cabe(sessao));
		
	}
	
	@Test	
	public void garanteQueNaoDevePermitirSessaosIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		Filme filme = new Filme();
		filme.setDuracao(120);
		
		Sala sala = new Sala("");
		
		LocalTime horario = LocalTime.now();
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario,filme,sala));
		
		GerenciadorDeSessao gds = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(gds.cabe(new Sessao(horario.plus(1,ChronoUnit.HOURS),filme,sala)));
		
	}
	
	@Test	
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes(){
		Filme filme1 = new Filme();
		filme1.setDuracao(90);
		
		Filme filme2 = new Filme();
		filme2.setDuracao(120);
		
		Sala sala = new Sala("");
		
		LocalTime dezHoras = LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras,filme1,sala);
		LocalTime dezoitoHoras = LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoito = new Sessao(dezoitoHoras,filme2,sala);
		
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez,sessaoDasDezoito);
		GerenciadorDeSessao gds = new GerenciadorDeSessao(sessoes);
		
		Assert.assertTrue(gds.cabe(new Sessao(LocalTime.parse("13:00:00"),filme2,sala)));
		
	}
}
