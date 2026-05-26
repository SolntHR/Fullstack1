package com.compra.carrito.dto;

import lombok.Data;
import java.util.List;

@Data
public class PagoListadoDTO {

    private List<PagoSimpleDTO> pagos;
}
