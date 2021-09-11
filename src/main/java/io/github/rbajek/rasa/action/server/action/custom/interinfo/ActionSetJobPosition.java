package io.github.rbajek.rasa.action.server.action.custom.interinfo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import io.github.rbajek.rasa.action.server.action.custom.interinfo.applyJob.Job;
import io.github.rbajek.rasa.action.server.action.custom.interinfo.applyJob.JobPosition;
import io.github.rbajek.rasa.sdk.ActionExecutor;
import io.github.rbajek.rasa.sdk.CollectingDispatcher;
import io.github.rbajek.rasa.sdk.action.Action;
import io.github.rbajek.rasa.sdk.dto.Domain;
import io.github.rbajek.rasa.sdk.dto.Tracker;
import io.github.rbajek.rasa.sdk.dto.Tracker.Entity;
import io.github.rbajek.rasa.sdk.dto.event.AbstractEvent;
import io.github.rbajek.rasa.sdk.dto.event.SlotSet;
import io.github.rbajek.rasa.sdk.util.CollectionsUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ActionSetJobPosition implements Action {
	
	@Getter
	private static JobPosition jobPosition;
	
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private ActionExecutor rasaActionExecutor;

	
    @Override
    public String name() {
        return "action_set_jobposition";
    }

    @Override
    public List<AbstractEvent> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        // make an API call
    	log.info("＝＝＝ >>> action set jobposition");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        log.debug("=== >>> 1");
        List<Job> jobs = beanFactory.getBean(ActionShowPosition.class).getJobPosition().getJobs();
        log.debug("=== >>> 2");
        Job[] jobArray = new Job[jobs.size()];
        log.debug("=== >>> 3");
        jobArray = (Job[]) jobs.toArray();
        log.debug("=== >>> 4");
        
        if (CollectionsUtils.isNotEmpty(tracker.getLatestMessage().getEntities()) ) {
            log.debug("=== >>> 5");
            log.debug("=== >>> Entities : ", tracker.getLatestMessage().getEntities());

        	String item = tracker.getLatestMessage().getEntities().get(0).getValue();
            log.debug("=== >>> 6");
        	new SlotSet("job_position", jobArray[Integer.valueOf(item) - 1].getNameZh());
        }
        	
        List<String> entitiesValue = tracker.getLatestEntityValues("job-title");
        log.info("job numbers = "+ entitiesValue.size());
        for(String val : entitiesValue) {
        	log.info("entitiesValue : "+ val);
        }
        // send the message back to the user
        dispatcher.utterMessage("");

        return Collections.emptyList();
    }

}
