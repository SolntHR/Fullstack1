package com.compra.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacionItemResenaDTO {
    private Integer idItemCarrito;
    private Integer idUsuario;
    private Integer idProducto;
    private Integer idCarrito;
    private Boolean itemValido;
    private Boolean carritoPagado;
    private Boolean pagoAprobado;
}