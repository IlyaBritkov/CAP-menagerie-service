package com.leverx.menagerie.handler;

import cds.gen.petservice.ExchangePetsContext;
import cds.gen.petservice.PetService_;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Component
@ServiceName(PetService_.CDS_NAME)
public class PetEventHandler implements EventHandler {

    @Qualifier("petServiceImpl")
    private final PetService petService;

    /**
     * Exchange pets, if they belong different owners
     *
     * @param context
     */
    @On(event = "exchangePets")
    public void exchangePets(ExchangePetsContext context) {
        Integer firstPetId = context.getFirstPetId();
        Integer secondPetId = context.getSecondPetId();

        log.info("Exchange request with pet ids: [{},{}]", firstPetId, secondPetId);

        petService.exchangePets(firstPetId, secondPetId);
        context.setCompleted();
    }

}
