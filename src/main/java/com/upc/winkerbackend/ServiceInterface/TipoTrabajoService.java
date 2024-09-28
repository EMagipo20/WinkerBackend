package com.upc.winkerbackend.ServiceInterface;

import com.upc.winkerbackend.DTO.TipoTrabajoDTO;
import com.upc.winkerbackend.Entities.TipoTrabajo;

import java.util.List;

public interface TipoTrabajoService {
    public void insertarTipoTrabajo (TipoTrabajo tipoTrabajo);
    public void actualizarTipoTrabajo (TipoTrabajo tipoTrabajo);
    public void eliminarTipoTrabajo (Long id);
    public List<TipoTrabajoDTO> listarTipoTrabajos();
}
