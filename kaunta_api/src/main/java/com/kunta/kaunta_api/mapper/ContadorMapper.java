package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.model.Contador;
import org.springframework.stereotype.Component;

@Component
public class ContadorMapper {

    /**
     * Mapper para pasar de ContadorCreateDTO a Contador
     * @param dto contador a transformar
     * @return contador trasnformado
     */
    public Contador contadorFromDTO(ContadorCreateDTO dto){
        
        Contador ret = new Contador();
        ret.setActive(true);
        ret.setCount(dto.getCount());
        ret.setDescrition(dto.getDescription());
        ret.setName(dto.getName());

        return ret;
    }
}
