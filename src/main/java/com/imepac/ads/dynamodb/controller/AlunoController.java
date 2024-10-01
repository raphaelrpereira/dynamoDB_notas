package com.imepac.ads.dynamodb.controller;

import com.imepac.ads.dynamodb.dto.AlunoDto;
import com.imepac.ads.dynamodb.entities.Aluno;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/alunos")
public class AlunoController {

    private DynamoDbTemplate dynamoDbTemplate;

    public AlunoController(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @PostMapping("/{matricula}/")
    public ResponseEntity<Void> salvar(@PathVariable("matricula") String matricula,
                                       @RequestBody AlunoDto alunoDTO) {

        var entity = Aluno.fromMatricula(matricula, alunoDTO);

        dynamoDbTemplate.save(entity);

        return ResponseEntity.noContent().build();

    }



}
