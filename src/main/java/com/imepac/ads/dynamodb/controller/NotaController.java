package com.imepac.ads.dynamodb.controller;

import com.imepac.ads.dynamodb.dto.NotaDto;
import com.imepac.ads.dynamodb.entities.Nota;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@RestController
@RequestMapping("/v1/notas")
public class NotaController {

    private DynamoDbTemplate dynamoDbTemplate;

    public NotaController(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @PostMapping("/{matricula}/{idDisciplina}/")
    public ResponseEntity<Void> salvar(@PathVariable("matricula") String matricula,
                                       @PathVariable("idDisciplina") String idDisciplina,
                                       @RequestBody NotaDto nota) {

        var entity = Nota.fromNota(matricula, idDisciplina, nota);

        dynamoDbTemplate.save(entity);

        return ResponseEntity.noContent().build();

    }


    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<List<Nota>> list(@PathVariable("matricula") String matricula){

        // cria a Key contendo as condições que serão filtradas
        var key = Key.builder()
                .partitionValue(matricula)
                .sortValue("NOTA#")
                .build();

        // cria a QueryConditional informando que o filtro da sortkey será BeginsWith
        var condition = QueryConditional.sortBeginsWith(key);

        //prepara query
        var query = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        try {
            // Execução da consulta no DynamoDB
            var nota = dynamoDbTemplate.query(query, Nota.class);

            List<Nota> notas = nota.items().stream().toList();
            return ResponseEntity.ok(notas);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<List<Nota>> buscarPorDisciplina(@PathVariable("idDisciplina") String idDisciplina){
        var key = Key.builder()
                .sortValue("#".concat(idDisciplina))
                .build();

        var condition = QueryConditional.sortBeginsWith(key);
        //prepara query
        var query = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        var nota = dynamoDbTemplate.query(query, Nota.class);

        List<Nota> notas = nota.items().stream().toList();
        return ResponseEntity.ok(notas);

    }

    @GetMapping("turma/{idDisciplina}")
    public ResponseEntity<List<Nota>> buscarTurma(@PathVariable("idDisciplina") String idDisciplina){

        // Prefixo utilizado na chave de sort para as notas
        String prefixoNota = "NOTA#";
        String codBusca = prefixoNota.concat(idDisciplina);

        var key = Key.builder()
                .partitionValue(codBusca)
                .build();

        var query = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .build();

        try {
            // Execução da consulta no DynamoDB usando o índice GSI1
            var notas = dynamoDbTemplate.query(query, Nota.class, "GSI1");

            List<Nota> listaNotas = notas.items().stream().toList();
            return ResponseEntity.ok(listaNotas);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
