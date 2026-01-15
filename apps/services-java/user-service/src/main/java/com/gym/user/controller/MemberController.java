package com.gym.user.controller;

import com.gym.user.dto.MemberDTO;
import com.gym.user.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de miembros del gimnasio.
 * Proporciona endpoints para crear, leer, actualizar y eliminar miembros.
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Endpoints para la gestión de miembros del gimnasio")
public class MemberController {

    private final MemberService memberService;

    /**
     * Obtiene una lista de todos los miembros registrados.
     *
     * @return ResponseEntity con la lista de MemberDTO y estado HTTP 200 OK.
     */
    @Operation(summary = "Obtener todos los miembros", description = "Devuelve una lista completa de miembros registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * Busca un miembro específico por su identificador único (ID).
     *
     * @param id El ID del miembro a buscar.
     * @return ResponseEntity con el MemberDTO encontrado y estado HTTP 200 OK.
     */
    @Operation(summary = "Obtener miembro por ID", description = "Busca y devuelve los detalles de un miembro específico basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Miembro encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
        @ApiResponse(responseCode = "404", description = "Miembro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(
            @Parameter(description = "ID del miembro a buscar", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * Crea un nuevo miembro en el sistema.
     *
     * @param memberDTO Objeto de transferencia de datos con la información del nuevo miembro.
     * @return ResponseEntity con el MemberDTO creado y estado HTTP 201 CREATED.
     */
    @Operation(summary = "Crear nuevo miembro", description = "Registra un nuevo miembro en el sistema. Valida los datos de entrada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Miembro creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDTO));
    }

    /**
     * Actualiza la información de un miembro existente.
     *
     * @param id El ID del miembro a actualizar.
     * @param memberDTO Objeto con los nuevos datos del miembro.
     * @return ResponseEntity con el MemberDTO actualizado y estado HTTP 200 OK.
     */
    @Operation(summary = "Actualizar miembro", description = "Actualiza los datos de un miembro existente identificado por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Miembro actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO.class))),
        @ApiResponse(responseCode = "404", description = "Miembro no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(
            @Parameter(description = "ID del miembro a actualizar", required = true)
            @PathVariable Long id,
            @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, memberDTO));
    }

    /**
     * Elimina un miembro del sistema por su ID.
     *
     * @param id El ID del miembro a eliminar.
     * @return ResponseEntity con estado HTTP 204 NO CONTENT si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar miembro", description = "Elimina permanentemente un miembro del sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Miembro eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Miembro no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @Parameter(description = "ID del miembro a eliminar", required = true)
            @PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Maneja las excepciones cuando no se encuentra una entidad.
     *
     * @param e La excepción EntityNotFoundException capturada.
     * @return ResponseEntity con el mensaje de error y estado HTTP 404 NOT FOUND.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Maneja las excepciones de argumentos inválidos.
     *
     * @param e La excepción IllegalArgumentException capturada.
     * @return ResponseEntity con el mensaje de error y estado HTTP 400 BAD REQUEST.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
