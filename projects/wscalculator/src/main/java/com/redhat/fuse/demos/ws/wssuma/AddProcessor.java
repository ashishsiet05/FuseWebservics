package com.redhat.fuse.demos.ws.wssuma;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AddProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		AddDTO dto = exchange.getIn().getBody(AddDTO.class);
		int res = dto.getOper1()-dto.getOper2();
		AddResult result = new AddResult();
		result.setResult(res);
		exchange.getIn().setBody(result);
	}

}
