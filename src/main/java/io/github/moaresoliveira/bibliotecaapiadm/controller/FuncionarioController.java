package io.github.moaresoliveira.bibliotecaapiadm.controller;

import io.github.moaresoliveira.bibliotecaapiadm.dto.FuncionarioDTO;
import io.github.moaresoliveira.bibliotecaapiadm.dto.RelatorioFuncionarioDTO;
import io.github.moaresoliveira.bibliotecaapiadm.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionario")
@Tag(name = "Funcionario", description = "Funcionario Controller")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(summary = "Cadastrar Funcionario")
    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioDTO> cadastrar(
            @RequestBody(description = "Dados do Funcionário",
                    required = true,
                    content = @Content(schema =
                    @Schema(implementation = FuncionarioDTO.class))) FuncionarioDTO dto) {

        FuncionarioDTO funcionarioSalvo = funcionarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
    }

    @Operation(summary = "Atualizar Funcionario")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(
            @PathVariable Long id,
            @RequestBody(description = "Dados do Funcionário",
                    required = true,
                    content = @Content(schema =
                    @Schema(implementation = FuncionarioDTO.class))) FuncionarioDTO dto) {

        FuncionarioDTO funcionarioAtualizado = funcionarioService.atualizar(id, dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @Operation(summary = "Deletar Funcionario")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar Funcionarios")
    @GetMapping("/listar")
    public ResponseEntity<Page<FuncionarioDTO>> listarTodos(@RequestParam @ParameterObject Pageable pageable) {

        Page<FuncionarioDTO> clienteListado = funcionarioService.listarTodos(pageable);
        if (clienteListado.hasContent()) {
            return ResponseEntity.ok(clienteListado);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Apresenta o CEP com maior quantidade de funcionarios")
    @GetMapping("/contar")
    public ResponseEntity<RelatorioFuncionarioDTO> getFuncionarios() {

        RelatorioFuncionarioDTO relatorioFuncionarioDTO = funcionarioService.contarFuncionariosPorCep();

        if(relatorioFuncionarioDTO == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(relatorioFuncionarioDTO);
    }

}
