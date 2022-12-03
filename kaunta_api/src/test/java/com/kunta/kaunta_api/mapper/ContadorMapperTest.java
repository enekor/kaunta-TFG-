package com.kunta.kaunta_api.mapper;

import com.kunta.kaunta_api.dto.ContadorCreateDTO;
import com.kunta.kaunta_api.model.Contador;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {ContadorMapper.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContadorMapperTest {

    private ContadorCreateDTO dto;
    private ContadorMapper mapper;

    @BeforeAll
    void init(){
        dto = new ContadorCreateDTO();
        dto.setCount(4);
        dto.setName("test");
        dto.setDescription("desc");

        mapper = new ContadorMapper();
    }

    @Test
    void ContadorMapperTest(){
        Contador ans = mapper.contadorFromDTO(dto);

        assertAll(
                ()->assertNotNull(ans),
                ()->assertEquals(4,ans.getCount()),
                ()->assertEquals("test",ans.getName()),
                ()->assertEquals("desc",ans.getDescrition()),
                ()->assertTrue(ans.isActive())
        );
    }
}
