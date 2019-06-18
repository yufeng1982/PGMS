package com.photo.pgm.core.akka;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

import com.photo.bas.core.model.common.AkkaNaming;

public class PmsAkkaSupervisor extends UntypedActor {
	
	final ActorRef emailActor = getContext().actorOf(
		new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;

			public Actor create() throws Exception {
				return new EmailActor();
			}
		}), AkkaNaming.EMAIL_SENDER);

	@Override
	public void onReceive(Object arg0) throws Exception {
		System.out.println("hi, man, i'm " + PmsAkkaSupervisor.class.getSimpleName());
	}

}
