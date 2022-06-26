package com.canalplus.meetingplanner.services;


import com.canalplus.meetingplanner.util.Chain;
import com.canalplus.meetingplanner.util.ChainBuilder;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ReservationServiceConfiguration {

    @Bean
    @Autowired
    public Chain<MeetingRequest> paRequestChain(final List<Handler<MeetingRequest>> handlers) {
        return buildReunionRequestChain(handlers);
    }

    public static Chain<MeetingRequest> buildReunionRequestChain(List<Handler<MeetingRequest>> handlers) {
        final ChainBuilder<MeetingRequest> builder = ChainBuilder.chainBuilder();
        final ChainBuilder.SuccessorBuilder successorBuilder = builder.first(handlers.get(0));
        for (int i = 1; i < handlers.size(); i++) {
            successorBuilder.next(handlers.get(i));
        }
        return successorBuilder.build();
    }
}
