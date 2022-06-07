/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Cartao;
import br.com.senac.entidade.Cliente;
import static br.com.senac.util.GeradorUtil.*;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author silvio.junior
 */
public class CartaoDaoImplTest {
    
    private Cartao cartao;
    private CartaoDao cartaoDao;
    private Session sessao;
    
    public CartaoDaoImplTest() {
        cartaoDao = new CartaoDaoImpl();
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        Faker faker = new Faker();
        PessoaFisicaDaoImplTest pfdit = new PessoaFisicaDaoImplTest();
        pfdit.buscarPessoaFisicaBd();
        cartao = new Cartao(
                faker.finance().creditCard(CreditCardType.MASTERCARD),
                gerarBandeira(), "202" + gerarNumero(1));
        Cliente cliente = buscarClienteBd();
        cartao.setCliente(cliente);    
        sessao = HibernateUtil.abrirConexao();
        cartaoDao.salvarOuAlterar(cartao, sessao);
        sessao.close();
        assertNotNull(cartao.getId());
    }
    
//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        
    }

//    @Test
    public void testPesquisarPorNumero() {
        System.out.println("pesquisarPorNumero");
        buscarCartaoBd();
        sessao = HibernateUtil.abrirConexao();
        Cartao cartaoPesq = cartaoDao
                     .pesquisarPorNumero(cartao.getNumero(), sessao);
        sessao.close();
        assertNotNull(cartaoPesq);                
    }
    
    public Cartao buscarCartaoBd(){
        sessao = HibernateUtil.abrirConexao();
        Query<Cartao> consulta = sessao.createQuery("from Cartao c");
        consulta.setMaxResults(1);
        consulta.setFirstResult(1);
        List<Cartao> cartoes = consulta.getResultList();
        sessao.close();
        if(cartoes.isEmpty()){
            testSalvar();
        }else{
            cartao = cartoes.get(0);
        }
        return cartao;
    }
    
    public Cliente buscarClienteBd(){
        sessao = HibernateUtil.abrirConexao();
        Query<Cliente> consulta = sessao.createQuery("from Cliente c");
        List<Cliente> clientes = consulta.getResultList();
        sessao.close();
        Collections.shuffle(clientes);
        return clientes.get(0);
    }
    
    
    
}
