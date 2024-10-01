package com.imepac.ads.dynamodb.entities;

import com.imepac.ads.dynamodb.dto.AlunoDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class Aluno {

    private String pk;
    private String sk;
    private String nomeAluno;
    private String dataNascimento;

    public static Aluno fromMatricula(String matricula, AlunoDto alunoDTO){
        Aluno alunoEntity = new Aluno();
        alunoEntity.setPk(matricula);
        alunoEntity.setSk("ALUNO");
        alunoEntity.setNomeAluno(alunoDTO.nome());
        alunoEntity.setDataNascimento(alunoDTO.dataNascimento());

        return alunoEntity;
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


}
