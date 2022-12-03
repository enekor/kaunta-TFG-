package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.model.Grupo;
import org.springframework.stereotype.Component;

@Component
public class GrupoMapper {

    /**
     * Mapper para pasar de GrupoCreateDTO a Grupo
     * @param dto grupo a transformar
     * @return grupo trasnformado
     */
    public Grupo grupoFromDTO(GrupoCreateDTO dto){
        Grupo ret = new Grupo();
        ret.setNombre(dto.getNombre());
        ret.setActivo(true);
        return ret;

    }
}
