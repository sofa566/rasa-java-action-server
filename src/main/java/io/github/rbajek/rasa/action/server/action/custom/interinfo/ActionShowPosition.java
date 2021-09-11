package io.github.rbajek.rasa.action.server.action.custom.interinfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.rbajek.rasa.action.server.action.custom.interinfo.applyJob.Job;
import io.github.rbajek.rasa.action.server.action.custom.interinfo.applyJob.JobPosition;
import io.github.rbajek.rasa.sdk.ActionExecutor;
import io.github.rbajek.rasa.sdk.CollectingDispatcher;
import io.github.rbajek.rasa.sdk.action.Action;
import io.github.rbajek.rasa.sdk.dto.Domain;
import io.github.rbajek.rasa.sdk.dto.Tracker;
import io.github.rbajek.rasa.sdk.dto.event.AbstractEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ActionShowPosition implements Action, ApplicationListener<ContextRefreshedEvent> {
	
	@Getter
	private static JobPosition jobPosition;
	
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private ActionExecutor rasaActionExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        beanFactory.getBeansOfType(Action.class).values().forEach(rasaAction -> {
//            rasaActionExecutor.registerAction(rasaAction);
//        });
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File file = new File(classLoader.getResource("job_position.yml").getFile());

		// Instantiating a new ObjectMapper as a YAMLFactory
		ObjectMapper om = new ObjectMapper(new YAMLFactory());

		// Mapping the employee from the YAML file to the Employee class
		try {
			jobPosition = om.readValue(file, JobPosition.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }	
//	static {
//		// Loading the YAML file from the /resources folder
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		File file = new File(classLoader.getResource("job_position.yml").getFile());
//
//		// Instantiating a new ObjectMapper as a YAMLFactory
//		ObjectMapper om = new ObjectMapper(new YAMLFactory());
//
//		// Mapping the employee from the YAML file to the Employee class
//		try {
//			jobPosition = om.readValue(file, JobPosition.class);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
//	public static void main(String[] args) throws Exception {
//        for(Job val : jobPosition.getJobs()) {
//        	log.info("entitiesValue : "+ val);
//        }
//
//	}
	
    @Override
    public String name() {
        return "action_show_position";
    }

    @Override
    public List<AbstractEvent> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        // make an API call
    	log.info("＝＝＝ >>> show position");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        log.info("＝＝＝ >>> LatestMessage() :" + tracker.getLatestMessage().getText());
        String titles = "";
        for(int i=0; i < jobPosition.getJobs().size(); i++) {
        	titles += String.format("(%d) %s, ", (i+1), jobPosition.getJobs().get(i).getNameZh());
        }
        List<String> entitiesValue = tracker.getLatestEntityValues("job-title");
        log.info("job numbers = "+ entitiesValue.size());
        for(String val : entitiesValue) {
        	log.info("entitiesValue : "+ val);
        }
        // send the message back to the user
        dispatcher.utterMessage(titles);

        return Collections.emptyList();
    }

}
