package com.gym.payment.controller;

import com.gym.payment.model.Invoice;
import com.gym.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de pagos y facturas.
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints para la gestión de facturación y pagos")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Obtiene las facturas asociadas a un miembro.
     *
     * @param memberId ID del miembro.
     * @return Lista de facturas.
     */
    @Operation(summary = "Obtener facturas por miembro", description = "Devuelve el historial de facturas de un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)))
    })
    @GetMapping("/invoices/member/{memberId}")
    public ResponseEntity<List<Invoice>> getMemberInvoices(
            @Parameter(description = "ID del miembro", required = true) @PathVariable Long memberId) {
        return ResponseEntity.ok(paymentService.getInvoicesByMember(memberId));
    }

    /**
     * Genera una nueva factura.
     *
     * @param invoice Objeto factura con los detalles.
     * @return Factura creada.
     */
    @Operation(summary = "Crear factura", description = "Genera una nueva factura pendiente de pago para un miembro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)))
    })
    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(paymentService.createInvoice(invoice));
    }

    /**
     * Procesa el pago de una factura.
     *
     * @param id ID de la factura.
     * @return Factura actualizada con estado 'paid'.
     */
    @Operation(summary = "Pagar factura", description = "Registra el pago de una factura específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada", content = @Content)
    })
    @PostMapping("/invoices/{id}/pay")
    public ResponseEntity<Invoice> payInvoice(
            @Parameter(description = "ID de la factura", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.payInvoice(id));
    }

    /**
     * Maneja errores de entidad no encontrada.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
