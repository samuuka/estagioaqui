package com.felipe.consultordefilmes.Model;

/**
 * Created by felip on 18/06/2016.
 */
public class FilmeDetalhado {

    private String imdbID;
    private String titulo;
    private String ano;
    private String tipo;
    private String lancamento;
    private String duracao;
    private String diretor;
    private String genero;
    private String escritores;
    private String atores;
    private String sinopse;
    private String urlPoster;

    public FilmeDetalhado() {
    }

    public FilmeDetalhado(String imdbID, String titulo, String ano, String tipo, String lancamento, String duracao, String diretor, String genero, String escritores, String atores, String sinopse, String urlPoster) {
        this.imdbID = imdbID;
        this.titulo = titulo;
        this.ano = ano;
        this.tipo = tipo;
        this.lancamento = lancamento;
        this.duracao = duracao;
        this.diretor = diretor;
        this.genero = genero;
        this.escritores = escritores;
        this.atores = atores;
        this.sinopse = sinopse;
        this.urlPoster = urlPoster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEscritores() {
        return escritores;
    }

    public void setEscritores(String escritores) {
        this.escritores = escritores;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }
}
