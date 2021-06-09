package br.com.sporttads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender; //Usada para envio de email

    @Autowired
    private SpringTemplateEngine template; // Para comunicar com a pagina de confirmação de email

    /**
     * Metodo para preencher as informações da mensagem que e envia para confirma o cadastro do email
     * @param destino
     * @param codigo
     * @throws MessagingException
     */
    public void enviarPedidoDeConfirmacaoDeCadastro(String destino, String codigo) throws MessagingException {
        MimeMessage menssage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(menssage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariable("titulo", "Bem vindo a Sport Tads");
        context.setVariable("texto", "Precisamos que confirme seu cadastro, clicando no link abaixo");
        context.setVariable("linkConfirmacao","http://localhost:8080/usuario/confirmacao/cadastro?codigo=" + codigo);

        String html = template.process("email/confirmacao", context);
        helper.setTo(destino);
        helper.setText(html, true);
        helper.setSubject("Confirmacao de Cadastro");
        helper.setFrom("nao-responder@SportTads.com.br");

        helper.addInline("logo", new ClassPathResource("/static/img/logo/lg.png"));

        mailSender.send(menssage);
    }

    /**
     * Metodo para preencher as informações da mensagem do email para redefinir senha
     * @param destino
     * @param verificador
     * @throws MessagingException
     */
    public void enviarPedidoRedefinirSenha(String destino, String verificador) throws MessagingException {
        MimeMessage menssage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(menssage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariable("titulo", "Redefinição de Senha");
        context.setVariable("texto", "Para redefinir sua senha use o codigo de verificação quando exigido no formulario.");
        context.setVariable("verificador",verificador);

        String html = template.process("email/confirmacao", context);
        helper.setTo(destino);
        helper.setText(html, true);
        helper.setSubject("Redefinicao de Senha");
        helper.setFrom("nao-responder@SportTads.com.br");

        helper.addInline("logo", new ClassPathResource("/static/img/logo/lg.png"));

        mailSender.send(menssage);
    }

    public void pedidoFinalizado(String destino, int numero) throws MessagingException {
        MimeMessage menssage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(menssage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariable("titulo", "Parabéns pela Compra");
        context.setVariable("texto", "Seu pedido Nº "+ numero +", foi realizado com sucesso para mais detalhes acesse o link abaixo.");
        context.setVariable("meusPedidos","http://localhost:8080/pedido/meus-pedidos");

        String html = template.process("email/confirmacao", context);
        helper.setTo(destino);
        helper.setText(html, true);
        helper.setSubject("Pedido realizado com sucesso");
        helper.setFrom("nao-responder@SportTads.com.br");

        helper.addInline("logo", new ClassPathResource("/static/img/logo/lg.png"));

        mailSender.send(menssage);
    }

    public void statusPedido(String destino,int numero, String status) throws MessagingException {
        MimeMessage menssage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(menssage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

        Context context = new Context();
        context.setVariable("titulo", "Seu pedido Nº "+numero+" foi atualizado");
        context.setVariable("texto", "Status atual: "+status+", para mais detalhes acesse o link abaixo.");
        context.setVariable("meusPedidos","http://localhost:8080/pedido/meus-pedidos");

        String html = template.process("email/confirmacao", context);
        helper.setTo(destino);
        helper.setText(html, true);
        helper.setSubject("Pedido atualizado");
        helper.setFrom("nao-responder@SportTads.com.br");

        helper.addInline("logo", new ClassPathResource("/static/img/logo/lg.png"));

        mailSender.send(menssage);
    }

}
