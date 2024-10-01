package com.imepac.ads.dynamodb.entities;

import com.imepac.ads.dynamodb.dto.NotaDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;

@DynamoDbBean
public class Nota {

    private String pk;
    private String sk;
    private String nomeAluno;
    private String dataNascimento;
    private Double nota;
    private Instant dataLancamento;
    private String nomeDisciplina;
    private String nomeProfessor;

    public static Nota fromNota(String matricula, String idDisciplina, NotaDto notaDto){
        Nota notaEntity = new Nota();
        notaEntity.setPk(matricula);
        notaEntity.setSk("NOTA#".concat(idDisciplina));
        notaEntity.setNomeDisciplina(notaDto.disciplina());
        notaEntity.setNomeProfessor(notaDto.professor());
        notaEntity.setNota(notaDto.nota());
        notaEntity.setDataLancamento(Instant.now());

        return notaEntity;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    @DynamoDbAttribute("nome_aluno")
    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    @DynamoDbAttribute("dtnasc")
    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    @DynamoDbAttribute("nota")
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @DynamoDbAttribute("dt_lancamento")
    public Instant getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Instant dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @DynamoDbAttribute("disciplina")
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    @DynamoDbAttribute("professor")
    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI1"})
    public String getIndexProfDisc() {
        return sk;
    }

    public void setIndexProfDisc(String sk) {
        this.sk = sk;
    }

    @DynamoDbSecondarySortKey(indexNames = {"GSI1"})
    public String getSKProfDisc(){
        return pk;
    }

    public void setSKProfDisc(String pk){
        this.pk = pk;
    }


}
