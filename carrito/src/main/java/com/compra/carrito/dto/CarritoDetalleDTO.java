package com.compra.carrito.dto;

import java.util.List;
import lombok.Data;

@Data
public class CarritoDetalleDTO {

    private Integer idCarrito;
    private Integer idUsuario;
    private Integer total;
    private List<ItemDTO> items;
    
}
