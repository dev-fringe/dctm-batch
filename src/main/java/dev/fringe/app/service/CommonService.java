package dev.fringe.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.fringe.app.mapper.TestMapper;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommonService {

	@Autowired TestMapper mapper;
	
	public void selectTest() {
		mapper.select().forEach(e -> {log.info(e);});
	}
}
