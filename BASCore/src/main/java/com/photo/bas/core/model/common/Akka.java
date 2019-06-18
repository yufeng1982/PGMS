package com.photo.bas.core.model.common;

import java.util.List;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.google.common.collect.Lists;

public final class Akka {
	public static ActorSystem system = ActorSystem.create("pms-solutions");

	public static ActorSystem system(){
		return system;
	}
	
	public static ActorRef getErpSupervisor(){
		return get();
	}
	
	public static ActorRef getEmailActor() {
		return get(AkkaNaming.EMAIL_SENDER);
	}
	
	public static ActorRef getEventLogActor() {
		return get(AkkaNaming.EVENT_LOG_ACTOR);
	}
	
	public static ActorRef get(String ... paths){
		// is the guardian actor for all user-created top-level actors; 
		//actors created using ActorSystem.actorOf are found below this one.
		List<String> list = Lists.newArrayList(paths);
		list.add(0, AkkaNaming.USER);
		list.add(1, AkkaNaming.ERP_ROOT);
		return system().actorFor(list);
	}
}
