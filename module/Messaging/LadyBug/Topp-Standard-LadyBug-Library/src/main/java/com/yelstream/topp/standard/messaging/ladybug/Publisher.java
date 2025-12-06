package com.yelstream.topp.standard.messaging.ladybug;

public interface Publisher<A,M,P extends Publisher<A,M,P>> extends Sender<A,M,Void,P> {
}
