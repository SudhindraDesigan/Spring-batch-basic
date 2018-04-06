package com.example.batch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.batch.entity.Numbers;

public class NumberItemProcessor implements ItemProcessor<Numbers, Numbers> {


	 private static final Logger log = LoggerFactory.getLogger(Numbers.class);
	 
	@Override
	public Numbers process(Numbers num) throws Exception {
		
		final int num1 = num.getNumber1();
		final int num2 = num.getNumber2();
		
		
		 log.info("Increemented : num1 : "+(num1+20)+ "num2: "+(num2+20));
		
		Numbers incNumbs = new Numbers(num1+20, num2+20);
		return incNumbs ; 
	}
	
	
	

}
