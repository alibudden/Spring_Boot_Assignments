package com.promineotech.jeep.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.jeep.dao.JeepSalesDao;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import lombok.extern.slf4j.Slf4j;

@Service
//spring wants to manage the defaultjeepservice
@Slf4j
public class DefaultJeepSalesService implements JeepSalesService {
  
  @Autowired
  private JeepSalesDao jeepSalesDao;

  @Transactional(readOnly = true)
  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    log.info("The fetchJeeps method was called with model={} and trim={}", model, trim);
    
    List<Jeep> jeeps = jeepSalesDao.fetchJeeps(model, trim);
 // tests to see if list of jeeps is empty. if it is throw an exception. it through a 500 status
    //but we created the handler method to retur n a 404 status with an exception message
    //made assertions first then we added those in to the returned message until we got all of the values
    if(jeeps.isEmpty() ) {
      String msg = String.format("No jeeps found with model=%s and trim=%s", model, trim);
      throw new NoSuchElementException(msg);
    }
    
    Collections.sort(jeeps);
    return jeeps;
    
  }

}
