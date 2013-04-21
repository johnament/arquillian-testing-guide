package com.tad.arquillian.chapter8

import spock.lang.Specification
import javax.inject.Inject
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import javax.enterprise.inject.Instance

class StateManagementSpecification extends Specification {
	@Deployment
	def static JavaArchive "create deployment"() {
		return ShrinkWrap.create(JavaArchive.class)
	 	 .addClasses(Stateful.class, BasicStateObject.class, StateProgressor.class)
		 .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	StateProgressor progressor
	
	@Inject
	Instance<StateProgressor> progressorInstance
	
	def "state less than one or greater than five reset value"() {
		when:
		progressor.progressToNextState(inState)
		then:
		inState.state == resultState
		where:
		resultState << [2,1,2]
		inState << [new BasicStateObject("State Zero","Zero",0),
			new BasicStateObject("State Six","Six",6),
			new BasicStateObject("State One","One",1)]
	}
	
	def "validate the state of a stateful"() {
		expect:
		valid == progressor.isValidState(inState)
		where:
		valid << [false,false,true]
		inState << [new BasicStateObject("State Zero","Zero",0), 
			new BasicStateObject("State Six","Six",6), 
			new BasicStateObject("State One","One",1)]
	}
	
	def "validate the state in a database driven way"() {
		expect:
		progressor.getAllStates().each { inState ->
			assert (inState.state >= 1 && inState.state <= 5) == progressor.isValidState(inState);
		}
	}
}
/*

		where:
		inState << [, new BasicStateObject("State Negative","Negative",-1), new BasicStateObject("State Ten","Ten",10)]
		resultState << [2,2,1]
*/