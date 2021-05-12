package io.github.rbajek.rasa.action.server.action.custom.interinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.rbajek.rasa.sdk.CollectingDispatcher;
import io.github.rbajek.rasa.sdk.action.Action;
import io.github.rbajek.rasa.sdk.dto.Domain;
import io.github.rbajek.rasa.sdk.dto.Tracker;
import io.github.rbajek.rasa.sdk.dto.event.AbstractEvent;

/**
 * Custom action for Show Date
 *
 * @author Rafał Bajek
 */
@Component
public class ActionShowTime implements Action {

    @Override
    public String name() {
        return "action_show_time";
    }

    @Override
    public List<AbstractEvent> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        // make an API call
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        // send the message back to the user
        dispatcher.utterMessage(dtf.format(LocalDateTime.now()));

        return Collections.emptyList();
    }

}
