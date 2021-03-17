package com.redhat.fuse.demos.ws.wssuma;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumProcessor implements Processor {

	private static final Logger logger = LoggerFactory.getLogger(SumProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("Sum Request recieved: ");
		SumDTO dto = exchange.getIn().getBody(SumDTO.class);
		logger.debug("Message Recieved: "+dto.getOper1()+"+"+dto.getOper2());
		int res = dto.getOper1()+dto.getOper2();
		SumResult result = new SumResult();
		result.setResult(res);
		exchange.getIn().setBody(result);
		logger.debug("Sum Request added to body");
	}

}
