package com.he.kuyun.comm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:config/book.properties")
@ConfigurationProperties(prefix="account.kooyuns")
public class Account {
    private String loginid;
    private String password;
}
