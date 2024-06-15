package com.hako.colorOfDays.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hako.colorOfDays.persistence.ColorLogTableManager;

@Configuration
public class DbTableConfig {
    @Autowired
	private ColorLogTableManager tableCreator;
    
    @Bean
    public ApplicationRunner applicationRunner() {
        LocalDate currentDate = LocalDate.now();

        // Extract the current year and month
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        return args -> tableCreator.createColorLogTable(currentYear, currentMonth);
    }
}
