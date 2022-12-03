package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.GrupoCreateDTO;
import com.kunta.kaunta_api.model.Grupo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {GrupoMapper.class})
@SpringBootTest
public class GrupoMapperTest {

    private GrupoMapper mapper;
    private GrupoCreateDTO dto;

    @BeforeAll
    void init(){
        mapper = new GrupoMapper();

        dto = new GrupoCreateDTO();
        dto.setNombre("test");
    }

    @Test
    void GrupoMapperTest(){
        Grupo ans = mapper.grupoFromDTO(dto);

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals("test",ans.getNombre()),
                ()->assertTrue(ans.isActivo())
        );
    }
}
