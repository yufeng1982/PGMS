package com.photo.pgm.core.akka;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.photo.bas.core.model.common.Akka;
import com.photo.bas.core.model.common.AkkaNaming;

@Component
public class AkkaApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String contextId = event.getApplicationContext().getId();
		System.out.println("*******application context:" + contextId + " is Starting*******");
		if(contextId.indexOf("appServlet") == -1){
			System.out.println("akka actor is initing.");
			ActorSystem system = Akka.system();
			system.actorOf(new Props(PmsAkkaSupervisor.class), AkkaNaming.ERP_ROOT);
		}
	}
}
